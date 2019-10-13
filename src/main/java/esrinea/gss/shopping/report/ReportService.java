package esrinea.gss.shopping.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private OperationRepository operationRepo;

	/**
	 * 
	 * @param json A JSON file containing the criteria you want. itemId, categoryId,
	 *             startDate and/or endDate (All fields are optional)
	 * @return JSON a JSON file with all operations that fit the the criteria
	 * @throws IncorrectInputException
	 */
	public ReportDTO getReport(ObjectNode json) throws IncorrectInputException {

		// Begins validations and Parsing
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
			throw new IncorrectInputException("The date format was not correct", e);
		}
		List<OperationModel> currentOperations = new ArrayList<OperationModel>();

		List<ItemModel> currentItems = new ArrayList<ItemModel>();
		if (categoryId != -1 && itemId == -1) {
			// System.out.println(categoryId);
			System.out.println(startDate + " " + endDate);
			// System.out.println(current.getId());
			currentItems.addAll(itemRepo.getItemByCatId(categoryId));
		}
		if (itemId != -1) {

			currentItems.add(itemRepo.getItemNoFilter(itemId));

		}
		if (!currentItems.isEmpty()) {
			for (int i = 0; i < currentItems.size(); i++) {
				// System.out.println(currentItems.get(i).getId());
				currentOperations.addAll(operationRepo.getOperationsByItem(currentItems.get(i)));

			}

		}

		if (!currentOperations.isEmpty()) { // If three or more fields are specified
			List<OperationModel> updatedList = new ArrayList<>();
			if (startDate != null && endDate != null) {
				for (int i = 0; i < currentOperations.size(); i++) {
					updatedList.addAll(operationRepo.getOperationsByDateAndItemId(currentOperations.get(i).getId(),
							startDate, endDate));
				}
			} else {
				if (startDate != null) {

					for (int i = 0; i < currentOperations.size(); i++) {
						System.out.println(new Date());
						updatedList.addAll(operationRepo.getOperationsByDateAndItemId(currentOperations.get(i).getId(),
								startDate, new Date()));

					}
				} else {
					if (endDate != null)
						for (int i = 0; i < currentOperations.size(); i++) {
							System.out.println(currentOperations.get(i).getDateOfOperation().toLocaleString());
							updatedList.addAll(operationRepo.getOperationsByDateAndItemId(
									currentOperations.get(i).getId(), operationRepo.getOldestDate(), endDate));

						}
				}
			}
			currentOperations = updatedList;
		} else { // if only the dates are specified
			if (startDate != null && endDate != null) {
				currentOperations = operationRepo.getOperationsByDate(startDate, endDate);
				startDate = null;
				endDate = null;
			}
			if (endDate != null) {
				Date oldestDate = operationRepo.getOldestDate();

				currentOperations = operationRepo.getOperationsByDate(oldestDate, endDate);

			}
			if (startDate != null)
				currentOperations = operationRepo.getOperationsByDate(startDate, new Date());
		}

		// End validation and parsing above

		ReportDTO output = new ReportDTO(currentOperations, "done", 200);
		return output;

	}

}
