package esrinea.gss.shopping.item;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.general.exceptions.CategoryNotFoundException;
import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.category.CategoryModel;
import esrinea.gss.shopping.category.CategoryRepository;


/** A service class that handles all the bussines logic of the ItemSerivce*/
@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	public ItemService() {

	}
	/**
	 * A method that returns all the items
	 * @return JSON file with a List of All Items, Success Message and Code
	 */
	public ItemDTO getAllItems() {
		List<ItemModel> currentItems = itemRepo.getAllItems();
		ItemDTO output = new ItemDTO();
		output.setData(currentItems);
		output.setCode(200);
		output.setMessage("Items Reterived");
		return output;
	}

	
	/**
	 * Adding a new item to the application
	 * @param json with categoryId, name, description, and amount fields
	 * @return JSON with the newly created item ID, success message and code  
	 * @throws IncorrectInputException
	 * @throws CategoryNotFoundException
	 * @throws ItemNotFoundException
	 */
	public ItemDTO addItem(ObjectNode json) throws IncorrectInputException, CategoryNotFoundException, ItemNotFoundException {
		int categoryId = -1;
		String name = null;
		String description = null;
		Double amount = null;

		// Data validation STARTS below, checking if all needed inputs are available and
		// in the correct format.
		try {
			categoryId = json.get("categoryId").asInt();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("Category Id cannot be empty", e);
		}
		try {
			name = json.get("name").asText();

		} catch (NullPointerException e) {
			throw new IncorrectInputException("Name cannot be empty", e);

		}
		try {
			description = json.get("description").asText();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("Description cannot be empty", e);
		}
		try {
			amount = json.get("amount").asDouble();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("amount not found", e);
		}
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.\\]^*\\[()%!-]");
		if (regex.matcher(name).find() || name.isEmpty()|| description.isEmpty() || regex.matcher(description).find()) {
			throw new IncorrectInputException("Data cannot contain special characters and it cannot be empty", new Exception());
		}

		// Data Validation ENDS above.

		CategoryModel category = categoryRepo.getACategory(categoryId); // Category Collected to check if it exists and
																		// retrieving it from the database

		// Editing the item with the actual input
		ItemModel item = new ItemModel();
		item.setAmount(amount);
		item.setCategory(category);
		item.setName(name);
		item.setDescription(description);
		itemRepo.addItem(item);
		// Creation is done and new item is added to the database above

		ItemDTO output = new ItemDTO(); // Creating the DTO that is to be sent to the user
		output.setCode(201);
		output.setMessage("Item Added Successfuly!");
		output.setData(item.getId());
		return output;
	}
	/**
	 * Editing an item in the application.
	 * @param id The ID of the item you want to edit
	 * @param json with categoryId, name and description fields to edit the item with
	 * @return JSON with success message and code
	 */

	public ItemDTO editItem(int id, ObjectNode json) {
		// Parsing and Validation STARTS below
		int categoryId = -1;
		String name = null;
		String description = null;

		try {
			categoryId = json.get("categoryId").asInt();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("categoryId", e);
		}
		try {
			name = json.get("name").asText();

		} catch (NullPointerException e) {
			throw new IncorrectInputException("name not found", e);

		}
		try {
			description = json.get("description").asText();
		} catch (NullPointerException e) {
			throw new IncorrectInputException("description not found", e);
		}
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
		if (regex.matcher(name).find() || regex.matcher(description).find()) {
			throw new IncorrectInputException("Data cannot contain special characters", new Exception());
		}
		
		// Parsing and Validation ENDS above

		CategoryModel category = categoryRepo.getACategory(categoryId); //Retrieving category to edit the item
		
		itemRepo.editItem(category, id, name, description); //Editing is done and put in the database;
		
		//Creating and sending custom response below
		ItemDTO output = new ItemDTO();
		output.setCode(200);
		output.setMessage("Item Edited Successfuly!");
		return output;
	}
	
	/**
	 * Retrieves a single item from the database
	 * @param id The id of the item you want to get
	 * @return JSON with the item you requested, success message and code
	 */

	public ItemDTO getItem(int id) {
		ItemModel currentItem = itemRepo.getItem(id);
		ItemDTO output = new ItemDTO();
		output.setData(currentItem);
		output.setCode(200);
		output.setMessage("Item Reterived Succesfuly!");
		return output;
	}
	/**
	 * Deleting an item from the application given a specific ID 
	 * @param id The ID of the item you want to delete
	 * @return JSON with success message and code
	 */

	public ItemDTO deleteItem(int id) {
		itemRepo.deleteItem(id);
		ItemDTO output = new ItemDTO();
		output.setCode(200);
		output.setMessage("Item deleted succesfully!");
		return output;
	}

}
