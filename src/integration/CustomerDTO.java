package integration;

public class CustomerDTO {
	private String name;
	private String id;

	public CustomerDTO() {

	}

	public CustomerDTO(String id, String name) {
		this.name = name;
		this.id = id;
	}

	public CustomerDTO(CustomerDTO customerDTO) {
		this.id = customerDTO.getId();
		this.name = customerDTO.getName();
	}

	/**
	 * gets name
	 * 
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * gets customer id
	 * 
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Compare two customer if are they same customer 
	 * Compare with id
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof CustomerDTO)) {
			return false;
		}
		CustomerDTO other = (CustomerDTO) object;
		return this.id == other.id;
	}

	// TODO remove this!!
	public String toString() {
		String s = "";
		s = s + this.getId() + "\n";
		s = s + this.getName() + "\n";
		return s;
	}
}
