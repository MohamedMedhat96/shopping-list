package esrinea.gss.shopping.operation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import esrinea.gss.general.DatabaseSessionFactory;
import esrinea.gss.shopping.item.ItemModel;

/**
 * A repository class to handle all database operations related to the
 * operations.
 *
 * 
 */
@Repository
public class OperationRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public OperationRepository() {

	}

	/**
	 * A method to add an operation to the database
	 * 
	 * @param currentOpreation The operation you wish to add tot the operation
	 */
	public void issueOperation(OperationModel currentOpreation) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(currentOpreation);
		session.getTransaction().commit();
		session.close();

	}

	/**
	 * Get all operations between two given dates the start and end date
	 * 
	 * @param startDate The first date you want to check
	 * @param endDate   The second date you want to check
	 * @return List The List of all operations between the start and end date
	 */
	public List<OperationModel> getOperationsByDate(Date startDate, Date endDate) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class)
				.add(Restrictions.between("dateOfOperation", startDate, endDate)).list();
		session.close();
		return currentOperations;
	}

	/**
	 * Returns all operations associated to a specific item given the item ID
	 * 
	 * @param id The item ID you want to get all operations related to it
	 * @return List the List of all operations related to that item
	 */
	public List<OperationModel> getOperationsByItem(ItemModel id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class)
				.add(Restrictions.eq("item", id)).list();
		session.close();
		return currentOperations;
	}

	/**
	 * Getting all operations in the database
	 * 
	 * @return List a List of all operations in the database
	 * 
	 */
	public List<OperationModel> getAllOperations() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class).list();
		session.close();
		return currentOperations;
	}

	/**
	 * Getting the date of the oldest operation in the database
	 * 
	 * @return Date the date of the oldest operation in the database
	 */
	public Date getOldestDate() {
		Session session = sessionFactory.openSession();
		System.out.println(session);
		Query query = session.createNativeQuery("SELECT min(operation_date) from public.operation");
		Date result = null;
		try {
			result = (Date) query.list().get(0);
		} catch (IndexOutOfBoundsException e) {
			return new Date();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Getting operations related to a specific item between two dates
	 * @param id The Id of the item you want to get all operations related to 
	 * @param startDate the start date of the operations 
	 * @param endDate the end date of the operations 
	 * @return List a List of all opeartions that fit the criteria specified 
	 */
	public List<OperationModel> getOperationsByDateAndItemId(int id, Date startDate, Date endDate) {

		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(OperationModel.class)
				.add(Restrictions.between("dateOfOperation", startDate, endDate));
		criteria.add(Restrictions.eq("id", id));
		List<OperationModel> output = criteria.list();
		session.close();
		return output;
	}

}
