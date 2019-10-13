package esrinea.gss.shopping.item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import esrinea.gss.general.exceptions.CategoryNotFoundException;
import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.category.CategoryModel;
import esrinea.gss.shopping.category.CategoryRepository;

/** A service class that handles all the bussines logic of the ItemSerivce */
@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private SessionFactory sessionFactory;

	public ItemService() {

	}

	/**
	 * A method that returns all the items
	 * 
	 * @return JSON file with a List of All Items, Success Message and Code
	 */
	public ItemDTO getAllItems() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<ItemModel> currentItems = new ArrayList<>();
		try {
			currentItems = itemRepo.getAllItems(session);
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		ItemDTO output = new ItemDTO();
		output.setData(currentItems);
		output.setCode(200);
		output.setMessage("Items Reterived");
		return output;
	}

	/**
	 * Adding a new item to the application
	 * 
	 * @param json with categoryId, name, description, and amount fields
	 * @return JSON with the newly created item ID, success message and code
	 * @throws IncorrectInputException
	 * @throws CategoryNotFoundException
	 * @throws ItemNotFoundException
	 */
	public ItemDTO addItem(ObjectNode json)
			throws IncorrectInputException, CategoryNotFoundException, ItemNotFoundException {
		int categoryId = -1;
		String name = null;
		String description = null;
		Double amount = null;
	
		// Data validation STARTS below, checking if all needed inputs are available and
		// in the correct format.
		
		if(!json.hasNonNull("categoryId"))
			throw new IncorrectInputException("Category Id cannot be empty", new Exception());
		else
			categoryId = json.get("categoryId").asInt();
		
		if(!json.hasNonNull("name"))
			throw new IncorrectInputException("Name field cannot be empty", new Exception());
		else
			name = json.get("name").asText();

		if(!json.hasNonNull("description"))
			throw new IncorrectInputException("Description field cannot be empty", new Exception());
		else
			description = json.get("description").asText();
		
		if(!json.hasNonNull("amount"))
			throw new IncorrectInputException("Amount field cannot be empty", new Exception());
		else
			amount = json.get("amount").asDouble();
		
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.\\]^*\\[()%!-]");
		if (regex.matcher(name).find() || name.isEmpty() || description.isEmpty()
				|| regex.matcher(description).find()) {
			throw new IncorrectInputException("Data cannot contain special characters and it cannot be empty",
					new Exception());
		}

		// Data Validation ENDS above.
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel category = null;
		try {
		 category = categoryRepo.getACategory(categoryId, session); // Category Collected to check if it exists and
		
		}catch(Exception e) {
			session.getTransaction().rollback();
			
			throw e;
		}finally
		{	
			session.close();
		}

		// Editing the item with the actual input
		ItemModel item = new ItemModel();
		item.setAmount(amount);
		item.setCategory(category);
		item.setName(name);
		item.setDescription(description);
		try {
		itemRepo.addItem(item,session);
		session.getTransaction().commit();
		
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		finally {
			session.close();
		}
		
		
		// Creation is done and new item is added to the database above

		ItemDTO output = new ItemDTO(); // Creating the DTO that is to be sent to the user
		output.setCode(201);
		output.setMessage("Item Added Successfuly!");
		output.setData(item.getId());
		return output;
	}

	/**
	 * Editing an item in the application.
	 * 
	 * @param id   The ID of the item you want to edit
	 * @param json with categoryId, name and description fields to edit the item
	 *             with
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
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
		CategoryModel category = categoryRepo.getACategory(categoryId,session); // Retrieving category to edit the item
		
		itemRepo.editItem(category, id, name, description,session); // Editing is done and put in the database;
		session.getTransaction().commit();
		}catch(Exception e)
		{
			session.getTransaction().rollback();
			throw e;
		}finally {
			session.close();
		}
		// Creating and sending custom response below
		ItemDTO output = new ItemDTO();
		output.setCode(200);
		output.setMessage("Item Edited Successfuly!");
		return output;
	}

	/**
	 * Retrieves a single item from the database
	 * 
	 * @param id The id of the item you want to get
	 * @return JSON with the item you requested, success message and code
	 */

	public ItemDTO getItem(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		ItemModel currentItem = null;
		try {
			 currentItem = itemRepo.getItem(id, session);
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		finally {
			session.close();
		}
		ItemDTO output = new ItemDTO();
		output.setData(currentItem);
		output.setCode(200);
		output.setMessage("Item Reterived Succesfuly!");
		return output;
	}

	/**
	 * Deleting an item from the application given a specific ID
	 * 
	 * @param id The ID of the item you want to delete
	 * @return JSON with success message and code
	 */

	public ItemDTO deleteItem(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			itemRepo.deleteItem(id, session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
		ItemDTO output = new ItemDTO();
		output.setCode(200);
		output.setMessage("Item deleted succesfully!");
		return output;
	}

}
