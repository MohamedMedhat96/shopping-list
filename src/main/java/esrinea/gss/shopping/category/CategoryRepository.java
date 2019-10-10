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
import esrinea.gss.general.exceptions.IncorrectInput;
import esrinea.gss.general.exceptions.ItemNotFoundException;

@Repository
public class CategoryRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public CategoryRepository() {

	}

	public CategoryModel getACategory(int id) throws CategoryNotFoundException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel category = null;
		try {
			category = (CategoryModel) session.createNativeQuery(
					"select * from category where category.deleted = false and category.category_id = " + id,
					CategoryModel.class).list().get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new CategoryNotFoundException("the category", e);
		} finally {
			session.close();
		}
		return category;
	}

	public List<CategoryModel> getAllCategories() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<CategoryModel> categoryModelData = session
				.createNativeQuery("select * from category where category.deleted=false", CategoryModel.class).list();
		session.getTransaction().commit();
		session.close();
		return categoryModelData;
	}

	public CategoryModel addCategory(CategoryModel category) throws IncorrectInput {
		Session session = sessionFactory.openSession();

		
		session.beginTransaction();

		try {
			session.save(category);
			session.getTransaction().commit();
		} catch (PersistenceException e) {		
			session.getTransaction().rollback();
			session.close();
			throw new IncorrectInput("The name is not unique", e);
		}
		catch(javax.validation.ConstraintViolationException e) {
			throw new IncorrectInput("Data cannot be empty",e);
		}


		session.close();
		return category;

	}

	public void editCategory(int id, String name, String description) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel currentCategory = session.get(CategoryModel.class, id);
		currentCategory.setName(name);
		currentCategory.setDescription(description);
		currentCategory.setLastUpdated(new Date());
		session.update(currentCategory);
		session.getTransaction().commit();
		session.close();

	}

	public void deleteCategory(int id) {

		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		CategoryModel currentCategory = (CategoryModel) session.get(CategoryModel.class, id);
		currentCategory.setDeleted(true);
		currentCategory.setLastUpdated(new Date());
		currentCategory.setDeletedDate(new Date());
		session.update(currentCategory);
		session.getTransaction().commit();
		session.close();

	}

}
