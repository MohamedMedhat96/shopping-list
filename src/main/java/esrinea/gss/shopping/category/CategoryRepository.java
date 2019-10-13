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

/**A category repository that is used to access the database for category related database operations.*/
@Repository
public class CategoryRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public CategoryRepository() {

	}

	/**
	 * A method used to get a single category from the database
	 * @param id The id of the category needed to be retrieved
	 * @return category The category that is retrieved from the database
	 * */
	public CategoryModel getACategory(int id) throws CategoryNotFoundException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel category = null;
		try {
			category = (CategoryModel) session.createNativeQuery(
					"select * from category where category.deleted = false and category.category_id = " + id,
					CategoryModel.class).list().get(0); // getting all the categories that are not deleted
		} catch (IndexOutOfBoundsException e) {
			throw new CategoryNotFoundException("the category", e);
		} finally {
			session.close();
		}
		return category;
	}


	/**
	 * A method used to get all categories from the database
	 * @return List<CategoryModel>  of the categories that is retrieved from the database
	 * */
	public List<CategoryModel> getAllCategories() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<CategoryModel> categoryModelData = session
				.createNativeQuery("select * from category where category.deleted=false", CategoryModel.class).list();
		session.close();
		return categoryModelData;
	}


	/** Adding a category to the database
	 * @param category
	 * 
	 */
	public CategoryModel addCategory(CategoryModel category) throws IncorrectInputException {
		Session session = sessionFactory.openSession();

		session.beginTransaction();

		try {
			session.save(category);
			session.getTransaction().commit();
		} catch (PersistenceException e) {
			session.getTransaction().rollback();
			throw new IncorrectInputException("The name is not unique", e);
		} catch (javax.validation.ConstraintViolationException e) {
			throw new IncorrectInputException("Data cannot be empty", e);
		} finally {
			session.close();
		}
		return category;

	}

	/**
	 * Editing a Category inside the Database.
	 * @param id Id of the new Category
	 * @param name The new name
	 * @param description The new Description
	 * @throws IncorrectInputException
	 */

	public void editCategory(int id, String name, String description) throws IncorrectInputException {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		CategoryModel currentCategory = null;
		try {
			currentCategory = this.getACategory(id);
		} catch (IncorrectInputException e) {
			session.close();
			throw e;
		}
		currentCategory.setName(name);
		currentCategory.setDescription(description);
		currentCategory.setLastUpdated(new Date());
		session.update(currentCategory);
		session.getTransaction().commit();
		session.close();

	}
	
	/**
	 * Deleting a category of the database.
	 * @param id The ID of the category you want to delete
	 * @throws IncorrectInputException
	 */
	public void deleteCategory(int id) throws IncorrectInputException {

		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel currentCategory = null;
		try {
			currentCategory = this.getACategory(id);
		} catch (IncorrectInputException e) {
			session.close();
			throw e;
		}
		currentCategory.setDeleted(true);
		currentCategory.setLastUpdated(new Date());
		currentCategory.setDeletedDate(new Date());
		session.update(currentCategory);
		session.getTransaction().commit();
		session.close();

	}
	


}
