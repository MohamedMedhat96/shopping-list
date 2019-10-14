package esrinea.gss.shopping.category;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import esrinea.gss.general.DatabaseSessionFactory;
import esrinea.gss.general.exceptions.CategoryNotFoundException;
import esrinea.gss.general.exceptions.IncorrectInputException;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.item.ItemModel;

/**
 * A category repository that is used to access the database for category
 * related database operations.
 */
@Repository
public class CategoryRepository {

	public CategoryRepository() {

	}

	/**
	 * A method used to get a single category from the database
	 * 
	 * @param id The id of the category needed to be retrieved
	 * @return category The category that is retrieved from the database
	 */
	public CategoryModel getACategory(int id, Session session) throws CategoryNotFoundException {

		CategoryModel category = null;

		List<CategoryModel> categoryList = session.createNativeQuery(
				"select * from category where category.deleted = false and category.category_id = " + id,
				CategoryModel.class).list(); // getting all the categories that are not deleted

		if (categoryList.size() == 0)
			throw new CategoryNotFoundException("the category", new Exception());
		else
			return categoryList.get(0);

	}

	/**
	 * A method used to get all categories from the database
	 * 
	 * @return List<CategoryModel> of the categories that is retrieved from the
	 *         database
	 */
	public List<CategoryModel> getAllCategories(Session session) {
	
		List<CategoryModel> categoryModelData = session
				.createNativeQuery("select * from category where category.deleted=false", CategoryModel.class).list();
		return categoryModelData;
	}

	/**
	 * Adding a category to the database
	 * 
	 * @param category
	 * 
	 */
	public CategoryModel addCategory(CategoryModel category, Session session) throws IncorrectInputException {
		// Done TODO Done handle hibernate session management in service layers instead
		

	List<CategoryModel> cat = session.createNativeQuery("select * from category where category.category_name = \'" + category.getName() +"\'", CategoryModel.class).list();
		if(cat.size()==0)
			session.save(category);
		else
			throw new IncorrectInputException("The name is not unique", new Exception());
		
		
		return category;

	}

	/**
	 * Editing a Category inside the Database.
	 * 
	 * @param id          Id of the new Category
	 * @param name        The new name
	 * @param description The new Description
	 * @throws IncorrectInputException
	 */

	public void editCategory(int id, String name, String description, Session session) throws IncorrectInputException {
		
		CategoryModel currentCategory = new CategoryModel();
		System.out.println(name);
		try {
			currentCategory = this.getACategory(id, session);
		} catch (IncorrectInputException e) {
			
			throw e;
		}
		System.out.println(currentCategory.getName());
		currentCategory.setName(name);
		currentCategory.setDescription(description);
		currentCategory.setLastUpdated(new Date());
		session.update(currentCategory);
	

	}

	/**
	 * Deleting a category of the database.
	 * 
	 * @param id The ID of the category you want to delete
	 * @throws IncorrectInputException
	 */
	public void deleteCategory(int id, Session session) throws IncorrectInputException {

		
		CategoryModel currentCategory = null;
		try {
			currentCategory = this.getACategory(id, session);
		} catch (IncorrectInputException e) {

			throw e;
		}
		currentCategory.setDeleted(true);
		currentCategory.setLastUpdated(new Date());
		currentCategory.setDeletedDate(new Date());
		session.update(currentCategory);
	

	}

	public CategoryModel getACategoryNoFilter(int categoryId, Session session) {
		
		CategoryModel category = null;
		try {
			category = (CategoryModel) session
					.createNativeQuery("select * from category where  category.category_id = " + categoryId,
							CategoryModel.class)
					.list().get(0); // getting all the categories that are not deleted
		} catch (IndexOutOfBoundsException e) {
			throw new CategoryNotFoundException("the category", e);
		}
		return category;
	}

}
