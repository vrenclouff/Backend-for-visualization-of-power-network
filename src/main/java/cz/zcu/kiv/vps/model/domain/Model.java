package cz.zcu.kiv.vps.model.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "Model.findAllWhereIsCustomer", query = "SELECT m FROM Model m JOIN m.customers c WHERE c.userID = :userID AND c.permissions > 0"),
		@NamedQuery(name = "Model.findDummyUser", query = "SELECT c FROM Model m JOIN m.customers c WHERE c.userID = :userID"),
		@NamedQuery(name = "Model.findByIDS", query = "SELECT m FROM Model m WHERE m.id IN (:ids)")
})
public class Model {

	@Id @GeneratedValue
	private Long id;

	private String name;
	
	private String description;
	
	private Timestamp created;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<DummyUser> customers = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public List<DummyUser> getCustomers() {
		return customers;
	}

	public void setCustomers(List<DummyUser> customers) {
		this.customers = customers;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}

		Model model = (Model) obj;

		if (model.getId().equals(this.getId())) {
			return true;
		}

		return false;
	}
}