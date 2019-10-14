package esrinea.gss.shopping.category;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/** A Model Class representing a single Category */

@JsonInclude(Include.NON_NULL)
@Entity(name = "category")
@Audited
public class CategoryModel {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "Name Cannot be Empty")
	@Column(name = "category_name", unique = true, nullable = false, length = 50)
	private String name;
	@Column(name = "category_description", length = 250)
	@NotEmpty(message = "Description Cannot be Empty")
	private String description;
	@Column(name = "created_date")
	private final Date dateCreated = new Date();

	@Column(name = "deleted")
	private boolean deleted = false;
	@Column(name = "last_updated")
	private Date lastUpdated;
	@Column(name = "deleted_date")
	private Date deletedDate;

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Date getDateCreated() {
		return dateCreated;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
