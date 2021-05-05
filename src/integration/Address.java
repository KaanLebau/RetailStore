package integration;

public class Address {
	private String city;
	private String street;
	private int buildingsNumber;
	private int zipCode;

	/**
	 * address constructors
	 * 
	 * @param city            contains name of city
	 * @param street          contains name of street
	 * @param buildingsNumber contains buildings number
	 * @param zipCode         contains zip code
	 */
	public Address(String city, String street, int buildingsNumber, int zipCode) {
		this.city = city;
		this.street = street;
		this.buildingsNumber = buildingsNumber;
		this.zipCode = zipCode;
	}

	public Address() {

	}
	/**
	 * Gets city name
	 * @return string.
	 */
	public String getCity() {
		return this.city;
	}
	/**
	 *  gets street name
	 * @returnString 
	 */
	public String getStreet() {
		return this.street;
	}
	/**
	 * gets buildings number
	 * @return int
	 */
	public int getBuildingsNumber() {
		return this.buildingsNumber;
	}
	/**
	 * gets zip code
	 * @return int
	 */
	public int getZipCode() {
		return this.zipCode;
	}
}
