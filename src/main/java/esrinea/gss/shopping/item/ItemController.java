package esrinea.gss.shopping.item;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping("/items")
	public ItemDTO getAllItems(){
		
		return itemService.getAllItems();
	}
	
	@PostMapping("/items")
	@ResponseStatus(HttpStatus.CREATED)
	public ItemDTO addItem(@RequestBody ObjectNode json) {
		
		return itemService.addItem(json);
	}
	
	@PutMapping("/items/{id}")
	public ItemDTO editItem(@PathVariable int id,@RequestBody ObjectNode json)
	{
		return itemService.editItem(id,json);
	}
	@DeleteMapping("/items/{id}")
	public ItemDTO deleteItem(@PathVariable int id) {
		return itemService.deleteItem(id);
	}
	@GetMapping("/items/{id}")
	public ItemDTO getItem(@PathVariable int id)
	{
		return itemService.getItem(id);
	}
	
}
