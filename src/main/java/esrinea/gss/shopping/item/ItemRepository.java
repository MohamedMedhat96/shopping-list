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
import esrinea.gss.general.exceptions.IncorrectInput;
import esrinea.gss.general.exceptions.ItemNotFoundException;
import esrinea.gss.shopping.category.CategoryModel;

@Repository
public class ItemRepository {


	@Autowired
	private SessionFactory sessionFactory;

	public ItemRepository() {

	}



	public List<ItemModel> getAllItems() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<ItemModel> currentItems =  session.createNativeQuery("select * from item where item.deleted = false and item.category_id NOT IN (select category_id from category where category.deleted =true)",ItemModel.class).list();
	
		session.close();
		return currentItems;
	}

	public void addItem(ItemModel item) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	
		try {
			session.save(item);
			session.getTransaction().commit();
		} catch (PersistenceException e) {		
			session.getTransaction().rollback();
			session.close();
			throw new IncorrectInput("The name is not unique", e);
		}


		session.close();
	
		
	}

	public void editItem(CategoryModel category, int id, String name, String description) throws ItemNotFoundException {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
		ItemModel currentItem = session.get(ItemModel.class, id);
		//currentItem.setAmount(amount);
		currentItem.setName(name);
		currentItem.setDescription(description);
		currentItem.setCategory(category);
		currentItem.setLastUpdated(new Date());
		session.update(currentItem);
		session.getTransaction().commit();
		}catch(Exception e)
		{	session.getTransaction().rollback();
			throw new ItemNotFoundException("The item",e);
		}
		finally {
	
		session.close();
		}
	}

	public void deleteItem(int id) throws ItemNotFoundException {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
		ItemModel currentItem = session.get(ItemModel.class, id);
		currentItem.setDeleted(true);
		currentItem.setLastUpdated(new Date());
		currentItem.setDeletedDate(new Date());
		session.update(currentItem);
		session.getTransaction().commit();
		}catch(Exception e)
		{
			throw new ItemNotFoundException("",e);
		}finally {
		session.close();

		}
		
	}

	public List<ItemModel> getItemByCatId(int id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<ItemModel> data =  session.createNativeQuery("select * from item where item.category_id="+id,ItemModel.class).list();
		session.close();
		return data;
	}
	
	
	public ItemModel getItem(int id) {
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	ItemModel currentItem = null;
	try {
	 currentItem =  session.createNativeQuery("select * from item where item.deleted = false and item.item_id="+id,ItemModel.class).list().get(0);
	}catch(IndexOutOfBoundsException e)
	{
		throw new ItemNotFoundException("",e);
	}
	finally {
	session.close();
	}
	return currentItem;
	}
	
	public ItemModel getItemNoFilter(int id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		ItemModel currentItem =  session.createNativeQuery("select * from item where item.item_id="+id,ItemModel.class).list().get(0);
		session.close();
		return currentItem;
	}
}
