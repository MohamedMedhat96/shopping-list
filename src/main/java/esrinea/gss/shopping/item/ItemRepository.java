package esrinea.gss.shopping.item;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import esrinea.gss.general.DatabaseSessionFactory;
import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.category.CategoryModel;

@Repository
public class ItemRepository {


	
	public ItemRepository() {

	}

	/**
	 * A method to return all items inside the database
	 * 
	 * @return List A list of all items in the database
	 */
	public List<ItemModel> getAllItems(Session session) {
		
		List<ItemModel> currentItems = session.createNativeQuery(
				"select * from item where item.deleted = false and item.category_id NOT IN (select category_id from category where category.deleted =true)",
				ItemModel.class).list(); //Returning all items that are not deleted and their categories are also not deleted

		return currentItems;
	}

	/**
	 * Adding an item to the database.
	 * @param item The item you need to add to the database.
	 */
	public void addItem(ItemModel item, Session session) throws IncorrectInputException {
		
		
		List<ItemModel> items = session.createNativeQuery("select * from item where item.item_name = \'" + item.getName() + "\'").list();
		if(items.size()>0)
			throw new IncorrectInputException("Item name already exists", new Exception());
		else
			session.save(item);

		
	}

	/**
	 * @param id The Id of the Item you need to edit.
	 * @param category The new Category of the Item. 
	 * @param name The new name for the item
	 * @param description The new Description of the item
	 * @throws ItemNotFoundException
	 */
	public void editItem(CategoryModel category, int id, String name, String description, Session session) throws ItemNotFoundException {

		
		try {
			ItemModel currentItem = session.get(ItemModel.class, id);
			// currentItem.setAmount(amount);
			currentItem.setName(name);
			currentItem.setDescription(description);
			currentItem.setCategory(category);
			currentItem.setLastUpdated(new Date());
			session.update(currentItem);
			} catch (Exception e) {
			throw new ItemNotFoundException("The item", e);
		} 
	}

	/**
	 * Deleting an item from the database
	 * @param id The id of the Item you want to delete form the database
	 * @throws ItemNotFoundException
	 */
	public void deleteItem(int id, Session session) throws ItemNotFoundException {

		
		try {
			ItemModel currentItem = this.getItem(id, session);
			currentItem.setDeleted(true);
			currentItem.setLastUpdated(new Date());
			currentItem.setDeletedDate(new Date());
			session.update(currentItem);
			
		} catch (Exception e) {
			throw new ItemNotFoundException("The item", e);
		} 

	}
	/**
	 * Getting all items that belong to a specific category
	 * @param id The id of the Category you want to list all its items
	 * @return List of all items related to the specified category
	 */

	public List<ItemModel> getItemByCatId(int id, Session session) {
		
		List<ItemModel> data = session
				.createNativeQuery("select * from item where item.category_id=" + id, ItemModel.class).list();
		
		return data;
	}

	/**
	 * Getting a specific item by its item ID
	 * @param id The id of the Item you want to retrieve
	 * @param session2 
	 * @return
	 */
	public ItemModel getItem(int id, Session session) {
		

		ItemModel currentItem = null;
		try {
			currentItem = session
					.createNativeQuery("select * from item where item.deleted = false and item.item_id=" + id,
							ItemModel.class) //getting the item and making sure its not deleted
					.list().get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new ItemNotFoundException("", e);
		} 
		return currentItem;
	}
	/**
	 * Getting an item regardless if its deleted or not
	 * @param id the ID of the item you want to return
	 * @return Item the Item you need.
	 */
	public ItemModel getItemNoFilter(int id, Session session) {
	
		ItemModel currentItem;
		List<ItemModel> currentItems = session
				.createNativeQuery("select * from item where item.item_id=" + id, ItemModel.class).list();
		if(currentItems.size()>0)
			 currentItem = currentItems.get(0);
		else
		{
			 currentItem = null;
			throw new ItemNotFoundException("Item not found", new Exception());
		}
		return currentItem;
	}

	public void editItem(ItemModel currentItem, Session session) {
		
	
		session.update(currentItem);
		
		
	}
}
