package esrinea.gss.shopping.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * A REST Controller to handle all requests related to the Operations
 */
@RestController
public class OperationController {

	@Autowired
	private  OperationService operationService;
	
	/**
	 * Create a new operation given a JSON file with fields itemId, operation (true being purchase, false being adding), and quantity
	 * @param json with fields itemId, operation, quantity
	 * @return JSON with operation ID, success message and code
	 */
	@PostMapping("/operation")
	public OperationDTO issueOpreation(@RequestBody ObjectNode json) {
		return operationService.issueOperation(json);
	}
}
