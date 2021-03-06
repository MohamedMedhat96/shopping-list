package esrinea.gss.shopping.item;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import esrinea.gss.shopping.category.CategoryModel;

@JsonInclude(Include.NON_NULL)
@Entity(name = "item")

/** A Model class that repersents an Item*/

public class ItemModel {
	@Audited
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryModel category;
	@Column(name = "item_name")
	private String name;
	@Column(name = "item_description")
	private String description;
	@Column(name = "item_amount")
	private double amount;
	@Column(name = "created_date")

	private final Date dateCreated = new Date();
	@Audited
	@Column(name = "deleted")
	private boolean deleted;
	@Audited
	@Column(name = "last_updated")
	private Date lastUpdated;
	@Audited
	@Column(name = "deleted_date")
	private Date deletedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoryModel getCategory() {
		return category;
	}

	public void setCategory(CategoryModel category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
	
	@Override
    public boolean equals(Object o) { 
  
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof ItemModel)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        ItemModel c = (ItemModel) o; 
          
        // Compare the data members and return accordingly  
        return c.getId() == this.getId();
    } 

}
