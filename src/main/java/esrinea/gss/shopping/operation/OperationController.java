package esrinea.gss.shopping.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class OperationController {

	@Autowired
	private  OperationService operationService;
	
	@PostMapping("/operation")
	public OperationDTO issueOpreation(@RequestBody ObjectNode json) {
		return operationService.issueOperation(json);
	}
}
