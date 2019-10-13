package esrinea.gss.shopping.operation;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.shopping.item.ItemModel;
import esrinea.gss.shopping.item.ItemRepository;

/**
 * Service class to handle all operation related business logic
 */
@Service
public class OperationService {

	@Autowired
	private OperationRepository operationRepo;
	@Autowired
	private ItemRepository itemRepo;

	public OperationService() {

	}

	public OperationDTO issueOperation(ObjectNode json) throws IncorrectInputException {
		int itemId = 0;
		boolean operation = false;
		double quantity = 0;

		try {
			itemId = json.get("itemId").asInt();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("Item Id cannot be empty", e);
		}

		try {
			operation = json.get("description").asBoolean();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("Operation cannot be empty", e);
		}
		try {
			quantity = json.get("amount").asDouble();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("Quantity cannot be empty", e);
		}

		OperationModel currentOperation = new OperationModel();
		currentOperation.setDateOfOperation((new Date()));
		ItemModel currentItem = itemRepo.getItem(itemId);

		if (currentItem.getAmount() < quantity && operation == true)
			new IncorrectInputException("Quantity is not enough to excute operation", new Exception());

		if (operation)
			currentItem.setAmount(currentItem.getAmount() - quantity);
		else
			currentItem.setAmount(currentItem.getAmount() + quantity);
		currentItem.setLastUpdated(new Date());
		itemRepo.editItem(currentItem);
		currentOperation.setItem(currentItem);
		currentOperation.setAmount(quantity);
		currentOperation.setType(operation);
		operationRepo.issueOperation(currentOperation);

		OperationDTO output = new OperationDTO();
		output.setData(currentOperation.getId());
		output.setMessage("Operation Added");
		output.setCode(200);
		return output;
	}
}
