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
import esrinea.gss.shopping.category.CategoryModel;
import esrinea.gss.shopping.category.CategoryRepository;
import esrinea.gss.shopping.item.ItemModel;
import esrinea.gss.shopping.item.ItemRepository;

/**
 * A repository class to handle all database operations related to the
 * operations.
 *
 * 
 */
@Repository
public class OperationRepository {

	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	public OperationRepository() {

	}

	/**
	 * A method to add an operation to the database
	 * 
	 * @param currentOpreation The operation you wish to add tot the operation
	 * @param session
	 */
	public void issueOperation(OperationModel currentOpreation, Session session) {

		session.save(currentOpreation);

	}

	/**
	 * Get all operations between two given dates the start and end date
	 * 
	 * @param startDate The first date you want to check
	 * @param endDate   The second date you want to check
	 * @return List The List of all operations between the start and end date
	 */
	public List<OperationModel> getOperationsByDate(Date startDate, Date endDate, Session session) {
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class)
				.add(Restrictions.between("dateOfOperation", startDate, endDate)).list();
		return currentOperations;
	}

	/**
	 * Returns all operations associated to a specific item given the item ID
	 * 
	 * @param id The item ID you want to get all operations related to it
	 * @return List the List of all operations related to that item
	 */
	public List<OperationModel> getOperationsByItem(ItemModel id, Session session) {
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class)
				.add(Restrictions.eq("item", id)).list();
		return currentOperations;
	}

	/**
	 * Getting all operations in the database
	 * 
	 * @return List a List of all operations in the database
	 * 
	 */
	public List<OperationModel> getAllOperations(Session session) {

		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class).list();
		session.close();
		return currentOperations;
	}

	/**
	 * Getting the date of the oldest operation in the database
	 * 
	 * @return Date the date of the oldest operation in the database
	 */
	public Date getOldestDate(Session session) {
		Query query = session.createNativeQuery("SELECT min(operation_date) from public.operation");
		Date result;

		if (query.list().size() == 0)
			result = new Date();
		else
			result = (Date) query.list().get(0);
		return result;
	}

	/**
	 * Getting operations related to a specific item between two dates
	 * 
	 * @param id        The Id of the item you want to get all operations related to
	 * @param startDate the start date of the operations
	 * @param endDate   the end date of the operations
	 * @return List a List of all opeartions that fit the criteria specified
	 */
	public List<OperationModel> getOperationsByDateAndItemId(int id, Date startDate, Date endDate, Session session) {

	
		Criteria criteria = session.createCriteria(OperationModel.class)
				.add(Restrictions.between("dateOfOperation", startDate, endDate));
		criteria.add(Restrictions.eq("id", id));
		List<OperationModel> output = criteria.list();
		return output;
	}

	public List<OperationModel> getOperationsCustom(int itemId, int categoryId, Date startDate, Date endDate, Session session) {
	
		Criteria criteria = session.createCriteria(OperationModel.class, "o");
		if (itemId != -1) {
			System.out.println("test");
			ItemModel x = (ItemModel) itemRepo.getItemNoFilter(itemId,session);
			System.out.println(itemId == x.getId());
			criteria.add(Restrictions.eq("o.item", x));
		}
		if (categoryId != -1) {
			System.out.println("category");
			criteria.createAlias("o.item", "oi");
			criteria.add(Restrictions.eq("oi.category", (CategoryModel) categoryRepo.getACategoryNoFilter(categoryId,session)));
			System.out.println("category");
		}
		if (startDate != null && endDate != null)
			criteria.add(Restrictions.between("dateOfOperation", startDate, endDate));
		else if (endDate != null)
			criteria.add(Restrictions.between("dateOfOperation", this.getOldestDate(session), endDate));
		else if (startDate != null)
			criteria.add(Restrictions.between("dateOfOperation", startDate, new Date()));
		List<OperationModel> operations = criteria.list();
		
		return operations;
	}

}
