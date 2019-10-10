package esrinea.gss.shopping.operation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import esrinea.gss.shopping.item.ItemModel;

@Entity(name = "operation")
public class OperationModel {

	@Id
	@Column(name = "operation_id", insertable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemModel item;
	@Column(name = "operation_amount")
	private double amount;
	@Column(name = "operation_date")
	private Date dateOfOperation;
	@Column(name = "operation_type")
	private boolean type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemModel getItem() {
		return item;
	}

	public void setItem(ItemModel item) {
		this.item = item;
	}

	public Date getDateOfOperation() {
		return dateOfOperation;
	}

	public void setDateOfOperation(Date dateOfOperation) {
		this.dateOfOperation = dateOfOperation;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}



	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (((OperationModel) obj).getId() == this.id)
			return true;
		else
			return false;

	}

	
	@Override
	public int hashCode() {
	    return this.id;
	}
}
