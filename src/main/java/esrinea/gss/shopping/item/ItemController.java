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

//The Item controller is used to accept requests related to the items
@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * Submit an empty GET request to /item in order to receive all items available
	 * 
	 * @return a JSON with all the items, a success message and a success code
	 */

	@GetMapping("/item")
	public ItemDTO getAllItems() {

		return itemService.getAllItems();
	}

	/**
	 * Submit a POST request to /item with
	 * 
	 * @param json with fields "categoryId","name" and"description" to create a new
	 *             item with the given name, category and description.
	 * @return JSON with the newly created item's ID and a success code and message
	 */
	@PostMapping("/item")
	@ResponseStatus(HttpStatus.CREATED)
	public ItemDTO addItem(@RequestBody ObjectNode json) {

		return itemService.addItem(json);
	}

	/**
	 * Submit a PUT request to /item/{id}, where
	 * 
	 * @param id   is the id of the item that you need to edit
	 * @param json with fields "categoryId","name" and"description" to edit an
	 *             existing item with the given name, category and description.
	 * @return JSON with a success code and message
	 */
	@PutMapping("/item/{id}")
	public ItemDTO editItem(@PathVariable int id, @RequestBody ObjectNode json) {
		return itemService.editItem(id, json);
	}

	/**
	 * Submit an empty DELETE request to /item/{id} 
	 * @param id being replaced with the item ID you want to delete
	 * @return JSON with a success code and message
	 */
	@DeleteMapping("/item/{id}")
	public ItemDTO deleteItem(@PathVariable int id) {
		return itemService.deleteItem(id);
	}
	/**
	 * Submit an empty GET request to /item/{id} 
	 * @param id being replaced with the item ID you want to get
	 * @return JSON with a success code and message
	 */
	@GetMapping("/item/{id}")
	public ItemDTO getItem(@PathVariable int id) {
		return itemService.getItem(id);
	}

}
