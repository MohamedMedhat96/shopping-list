package esrinea.gss.shopping.category;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esrinea.gss.general.exceptions.IncorrectInputException;

/**
 * Service class that handles all the business logic for all category related
 * requests.
 */
@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private SessionFactory sessionFactory;

	public CategoryService() {

	}

	/**
	 * Sending a List of All Retrieved Categories with Success Code and Message
	 * 
	 * @return JSON with a List of all categories, success code and message
	 */
	public CategoryDTO getAllCategories() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CategoryDTO x = new CategoryDTO();
		x.setHttpMessage("All Categories Retrieved");
		x.setHttpStatusCode(200);
		try {
			x.setData(categoryRepo.getAllCategories(session));
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		} finally {

			session.close();
		}
		return x;
	}

	/**
	 * 
	 * @param category The category you want to add
	 * @return JSON with the Category ID with Success Code and Message
	 */
	public CategoryDTO addCategory(CategoryModel category) {
		// TODO Done! add validations
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println(category.getName());
		if (category.getName() == null) {
			session.getTransaction().rollback();
			session.close();
			throw new IncorrectInputException("The name is incorrect", new Exception());
		}
		if (category.getDescription() == null) {
			session.getTransaction().rollback();
			session.close();
			throw new IncorrectInputException("The description is incorrect", new Exception());
		}
		try {
			category = categoryRepo.addCategory(category, session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		} finally {

			session.close();
		}

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setHttpMessage("The Category Have been Added!");
		categoryDTO.setHttpStatusCode(201);
		categoryDTO.setData(category.getId());
		return categoryDTO;
	}

	/**
	 * Editing a category
	 * 
	 * @param id          The Id of the category you want edit
	 * @param name        The new name of the category
	 * @param description The new description
	 * @return JSON with success message and code
	 */

	public CategoryDTO editCategory(int id, String name, String description) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			categoryRepo.editCategory(id, name, description, session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		} finally {

			session.close();
		}
		CategoryDTO data = new CategoryDTO();
		data.setHttpMessage("The Category has been Edited!");
		data.setHttpStatusCode(200);
		return data;
	}

	/**
	 * Deleting a category.
	 * 
	 * @param id The ID of the category you want to delete
	 * @return JSON with success message and code
	 */
	public CategoryDTO deleteCategory(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			categoryRepo.deleteCategory(id, session);
			session.getTransaction().commit();
		} catch (Exception e) {

			session.getTransaction().rollback();
			throw e;
		} finally {

			session.close();
		}
		CategoryDTO data = new CategoryDTO();
		data.setHttpMessage("The Category has been Deleted!");
		data.setHttpStatusCode(200);
		return data;
	}

	/**
	 * Retrieving a specific category
	 * 
	 * @param id The id of the category you want to get
	 * @return JSON with success message and code
	 */
	public CategoryDTO getCategory(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel currentCategory = null;
		try {
			 currentCategory = categoryRepo.getACategory(id, session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		} finally {

			session.close();
		}
		CategoryDTO output = new CategoryDTO();
		output.setData(currentCategory);
		output.setHttpMessage("Category Retrieved!");
		output.setHttpStatusCode(200);
		return output;
	}

}
