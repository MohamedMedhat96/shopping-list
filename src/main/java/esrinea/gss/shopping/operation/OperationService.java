package esrinea.gss.shopping.operation;

import java.util.Date;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.item.ItemModel;
import esrinea.gss.shopping.item.ItemRepository;

/**
 * Service class to handle all operation related business logic
 */
@Service
public class OperationService {

	@Autowired SessionFactory sessionFactory;
	@Autowired
	private OperationRepository operationRepo;
	@Autowired
	private ItemRepository itemRepo;

	public OperationService() {

	}

	public OperationDTO issueOperation(ObjectNode json) throws IncorrectInputException {
		
		// TODO Done Adjust session handling and (done)test with operation issuing 
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if(!json.hasNonNull("itemId"))
			throw new IncorrectInputException("Item Id cannot be empty", new Exception());
	
		if(!json.hasNonNull("quantity"))
			throw new IncorrectInputException("Quantity cannot be empty", new Exception());

		if(!json.hasNonNull("operation"))
			throw new IncorrectInputException("Operation cannot be empty", new Exception());
	
		boolean operation =json.get("operation").asBoolean();	
		double quantity =  json.get("quantity").asDouble();
		int itemId = json.get("itemId").asInt();
		
		OperationModel currentOperation = new OperationModel();
		currentOperation.setDateOfOperation((new Date()));
		try {
		ItemModel currentItem = itemRepo.getItem(itemId,session);
		
		if (currentItem.getAmount() < quantity && operation == true)
			new IncorrectInputException("Quantity is not enough to excute operation", new Exception());

		if (operation)
			currentItem.setAmount(currentItem.getAmount() - quantity);
		else
			currentItem.setAmount(currentItem.getAmount() + quantity);
		
		currentItem.setLastUpdated(new Date());
		itemRepo.editItem(currentItem,session);
		currentOperation.setItem(currentItem);
		currentOperation.setAmount(quantity);
		currentOperation.setType(operation);
		operationRepo.issueOperation(currentOperation,session);
		session.getTransaction().commit();
		}
		catch(ItemNotFoundException e)
		{
			session.getTransaction().rollback();
			throw e;
		}finally {
			
			session.close();
		}
		OperationDTO output = new OperationDTO();
		output.setData(currentOperation.getId());
		output.setMessage("Operation Added");
		output.setCode(200);
		return output;
	}
}
