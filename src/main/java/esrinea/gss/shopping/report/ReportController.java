package esrinea.gss.shopping.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Rest controller that handles all reports requests 

 *
 */
@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	/**
	 * Submit a get request with fields categoryID, itemID, startDate or endDate (all optional) to get a report of all requests given your criteria
	 * @param json a JSON file with all criteria you need to generate the report. itemID,categoryId,startDate and/or endDate.
	 * @return JSON a JSON file with a report that fits all criteria 
	 */
	@GetMapping("/report")
	public ReportDTO getReport(@RequestBody ObjectNode json)
	{
		return reportService.getReport(json);
	}
}
