package cz.zcu.kiv.vps.model.dto;


public abstract class IdentifiedObjectDTO {

	private Long ID;

	private String name;

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