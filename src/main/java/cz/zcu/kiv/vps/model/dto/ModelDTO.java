package cz.zcu.kiv.vps.model.dto;

import java.sql.Timestamp;
import java.util.List;

public class ModelDTO {

	private Long id;

	private String name;
	
	private String description;
	
	private Timestamp created;

	private List<DummyUserDTO> customers;


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

	public List<DummyUserDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<DummyUserDTO> customers) {
		this.customers = customers;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
}