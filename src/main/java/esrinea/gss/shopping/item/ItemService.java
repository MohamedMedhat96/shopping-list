package esrinea.gss.shopping.item;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.general.exceptions.CategoryNotFoundException;
import esrinea.gss.general.exceptions.IncorrectInput;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.category.CategoryModel;
import esrinea.gss.shopping.category.CategoryRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	public ItemService() {

	}

	public ItemDTO getAllItems() {
		List<ItemModel> currentItems = itemRepo.getAllItems();
		ItemDTO output = new ItemDTO();
		output.setData(currentItems);
		output.setCode(200);
		output.setMessage("Items Reterived");
		return output;
	}

	public ItemDTO addItem(ObjectNode json) throws IncorrectInput, CategoryNotFoundException, ItemNotFoundException {
		int categoryId = -1;
		String name = null;
		String description = null;
		Double amount = null;

		// Data validation STARTS below, checking if all needed inputs are availble and
		// in the correct format.
		try {
			categoryId = json.get("categoryId").asInt();
		} catch (NullPointerException e) {
			throw new IncorrectInput("Category Id cannot be empty", e);
		}
		try {
			name = json.get("name").asText();

		} catch (NullPointerException e) {
			throw new IncorrectInput("Name cannot be empty", e);

		}
		try {
			description = json.get("description").asText();
		} catch (NullPointerException e) {
			throw new IncorrectInput("Description cannot be empty", e);
		}
		try {
			amount = json.get("amount").asDouble();
		} catch (NullPointerException e) {
			throw new IncorrectInput("amount not found", e);
		}
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.\\]^*\\[()%!-]");
		if (regex.matcher(name).find() || name.isEmpty()|| description.isEmpty() || regex.matcher(description).find()) {
			throw new IncorrectInput("Data cannot contain special characters and it cannot be empty", new Exception());
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

	public ItemDTO editItem(int id, ObjectNode json) {
		// Parsing and Validation STARTS below
		int categoryId = -1;
		String name = null;
		String description = null;

		try {
			categoryId = json.get("categoryId").asInt();
		} catch (NullPointerException e) {
			throw new IncorrectInput("categoryId", e);
		}
		try {
			name = json.get("name").asText();

		} catch (NullPointerException e) {
			throw new IncorrectInput("name not found", e);

		}
		try {
			description = json.get("description").asText();
		} catch (NullPointerException e) {
			throw new IncorrectInput("description not found", e);
		}
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
		if (regex.matcher(name).find() || regex.matcher(description).find()) {
			throw new IncorrectInput("Data cannot contain special characters", new Exception());
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

	public ItemDTO getItem(int id) {
		ItemModel currentItem = itemRepo.getItem(id);
		ItemDTO output = new ItemDTO();
		output.setData(currentItem);
		output.setCode(200);
		output.setMessage("Item Reterived Succesfuly!");
		return output;
	}

	public ItemDTO deleteItem(int id) {
		itemRepo.deleteItem(id);
		ItemDTO output = new ItemDTO();
		output.setCode(200);
		output.setMessage("Item deleted succesfully!");
		return output;
	}

}
