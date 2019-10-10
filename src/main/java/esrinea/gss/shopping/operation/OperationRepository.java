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

@Repository
public class OperationRepository {
@Autowired
	private  SessionFactory sessionFactory;

	public OperationRepository() {
		
	}
	public void issueOperation(OperationModel currentOpreation) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(currentOpreation);
		session.getTransaction().commit();
		session.close();

	}

	public List<OperationModel> getOperationsByDate(Date startDate, Date endDate) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class).add(Restrictions.between("dateOfOperation", startDate, endDate)).list();
		session.close();
		return currentOperations;
	}
	
	public List<OperationModel> getOperationsByItem(ItemModel id	)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class).add(Restrictions.eq("item", id)).list();
		session.close();
		return currentOperations;
	}

	public List<OperationModel> getAllOperations()
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OperationModel> currentOperations = session.createCriteria(OperationModel.class).list();
		session.close();
		return currentOperations;
	}
	public Date getOldestDate() {
		Session session = sessionFactory.openSession();
		System.out.println(session);
		Query query = session.createNativeQuery("SELECT min(operation_date) from public.operation");
		Date result = (Date)query.list().get(0);
		session.close();
		return result;
	}
	public List<OperationModel> getOperationsByDateAndItemId(int id, Date startDate, Date endDate) {
	
		
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(OperationModel.class).add(Restrictions.between("dateOfOperation", startDate, endDate));
		criteria.add(Restrictions.eq("id", id));
		List<OperationModel> output =  criteria.list();
		session.close();
		return output;
	}
	


}
