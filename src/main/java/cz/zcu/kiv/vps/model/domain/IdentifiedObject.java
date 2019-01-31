package cz.zcu.kiv.vps.model.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdentifiedObject {
    @Id
    @Column(name = "longID")
    protected Long ID;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;


	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
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