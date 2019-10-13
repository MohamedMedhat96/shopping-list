package esrinea.gss.shopping.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.shopping.category.CategoryModel;
import esrinea.gss.shopping.category.CategoryRepository;
import esrinea.gss.shopping.item.ItemModel;
import esrinea.gss.shopping.item.ItemRepository;
import esrinea.gss.shopping.operation.OperationModel;
import esrinea.gss.shopping.operation.OperationRepository;

@Service
public class ReportService {
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private OperationRepository operationRepo;

	/**
	 * 
	 * @param json A JSON file containing the criteria you want. itemId, categoryId,
	 *             startDate and/or endDate (All fields are optional)
	 * @return JSON a JSON file with all operations that fit the the criteria
	 * @throws IncorrectInputException
	 */
	public ReportDTO getReport(ObjectNode json) throws IncorrectInputException {
			//TODO Done and tested! edit report
		// Begins validations and Parsing
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		int categoryId;
		int itemId;
		String stringStartDate;
		String stringEndDate;
		Date startDate = null;
		Date endDate = null;

		if (json.has("categoryId"))
			categoryId = json.get("categoryId").asInt();
		else
			categoryId = -1;

		if (json.has("itemId"))
			itemId = json.get("itemId").asInt();
		else
			itemId = -1;

		if (json.has("startDate"))
			stringStartDate = json.get("startDate").asText();
		else
			stringStartDate = "";

		if (json.has("endDate"))
			stringEndDate = json.get("endDate").asText();
		else
			stringEndDate = "";

		try {
			if (!stringStartDate.equals(""))
				startDate = new SimpleDateFormat("dd-MM-yyyy").parse(stringStartDate);
			if (!stringEndDate.equals(""))
				endDate = new SimpleDateFormat("dd-MM-yyyy").parse(stringEndDate);
		} catch (ParseException e) {
			session.getTransaction().rollback();
			throw new IncorrectInputException("The date format was not correct", e);
		}
		finally {
			
			session.close();
			
		}
		List<OperationModel> currentOperations = new ArrayList<OperationModel>();

		try {
		currentOperations=	operationRepo.getOperationsCustom(itemId,categoryId,startDate,endDate,session);
		}catch(Exception e)
		{	session.getTransaction().rollback();
			throw e;
		}
		finally {
		
			session.close();
			
		}
		ReportDTO output = new ReportDTO(currentOperations, "done", 200);
		return output;

	}

}
