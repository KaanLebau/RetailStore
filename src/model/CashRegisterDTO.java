package model;

import integration.Address;
import integration.Printer;

public class CashRegisterDTO {

	private final int cashRegisterId = 1;
	private final String name = "Kth Store";
	private final String telephone = "031 666 66 66";
	private final Address adres;
	private final Printer printer;

	/**
	 * CashRegister constructor
	 * 
	 * @param adres   where the cash register is.
	 * @param printer connected device.
	 */
	public CashRegisterDTO(Address adres, Printer printer) {
		this.adres = adres;
		this.printer = printer;
	}

	/**
	 * gets cashRegister id
	 * 
	 * @return id
	 */
	public int getCashRegisterId() {
		return cashRegisterId;
	}

	/**
	 * gets printer
	 * 
	 * @return reference for printer
	 */
	public Printer getPrinter() {
		return this.printer;
	}

	/**
	 * gets store name
	 * 
	 * @return name
	 */
	public String getStoreName() {
		return this.name;
	}

	/**
	 * gets telephone number
	 * 
	 * @return telephone number
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * gets city cash register in
	 * 
	 * @return city
	 */
	public String getCity() {
		return adres.getCity();
	}

	/**
	 * gets street cash register in
	 * 
	 * @return street
	 */
	public String getStreet() {
		return adres.getStreet();
	}

	/**
	 * get buildings number
	 * 
	 * @return buildings number
	 */
	public int getBuildingsNumber() {
		return adres.getBuildingsNumber();
	}

	/**
	 * gets zip code
	 * 
	 * @return zip code
	 */
	public int getZipCode() {
		return adres.getZipCode();
	}
}
