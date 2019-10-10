	package esrinea.gss.shopping.operation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.shopping.item.ItemModel;
import esrinea.gss.shopping.item.ItemRepository;

@Service
public class OperationService {

	@Autowired
	private  OperationRepository operationRepo;
	@Autowired
	private  ItemRepository itemRepo;
	public OperationService() {
		
	}

	public OperationDTO issueOperation(ObjectNode json) {
		
		int id = json.get("id").asInt();
		String operation = json.get("operation").asText();
		boolean op;
		if(operation.equalsIgnoreCase("true"))
				 op = true;
		else
			 op = false;
		int quantity = json.get("quantity").asInt();
		
		OperationModel currentOperation = new OperationModel();
		currentOperation.setDateOfOperation((new Date()));
		ItemModel currentItem = itemRepo.getItem(id);
		
		if(currentItem.getAmount() < quantity)
			return new OperationDTO(null,"Not enough Quantity", 406);
		
		currentOperation.setItem(currentItem);
		currentOperation.setAmount(quantity);
		currentOperation.setType(op);
		operationRepo.issueOperation(currentOperation);
		
		OperationDTO output = new OperationDTO();
		output.setData(currentOperation.getId());
		output.setMessage("Done!");
		output.setCode(200);
		return output;
	}
}
