package esrinea.gss.shopping.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping("/report")
	public ReportDTO getReport(@RequestBody ObjectNode json)
	{
		return reportService.getReport(json);
	}
}
