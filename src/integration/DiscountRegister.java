package integration;

import java.util.ArrayList;
import java.util.List;

import integration.Server.Connection;
import integration.Server.ServerTyp;
import util.enums.Category;
import util.exceptions.CustomerDiscountIdException;
import util.exceptions.CustomerRegistryException;
import util.exceptions.ServerOfflineException;

public class DiscountRegister {

	private List<DiscountDTO> discountRegister = new ArrayList<>();
	private Connection connection;
	private ServerTyp serverTyp;

	/**
	 * Discount Register constructor dont take any argument
	 */
	public DiscountRegister() {
		this.connection = Connection.ONLINE;
		this.serverTyp = ServerTyp.DISCOUNT;
		DiscountDTO typ1 = new DiscountDTO(Category.ITEM, "104", 10, "Campaing product");
		DiscountDTO typ2 = new DiscountDTO(Category.CUSTOMER, "456", 12, "12% discount ");
		DiscountDTO typ3 = new DiscountDTO(Category.CUSTOMER, "111", 25, "Loyality Discount");
		DiscountDTO typ4 = new DiscountDTO(Category.CUSTOMER, "123", 1, "New member discount");
		DiscountDTO typ5 = new DiscountDTO(Category.QUANTITY, "101", 3, 11, "Buy 3 pay for 2");
		discountRegister.add(typ1);
		discountRegister.add(typ2);
		discountRegister.add(typ3);
		discountRegister.add(typ4);
		discountRegister.add(typ5);
	}

	/**
	 * updates new discount list
	 * 
	 * @param list DiscountTypDTO
	 */
	public void updateList(List<DiscountDTO> list) {
		this.discountRegister = list;
	}

	/**
	 * gets discount list
	 * 
	 * @return
	 */
	public List<DiscountDTO> getList() {
		return this.discountRegister;
	}

	/**
	 * searchCustomerDiscount for registered customer only create an dummy discount
	 * Search throw the discount register if discountId exist and requirement
	 * measured copy discount to dummy discount
	 * 
	 * @param discountId      used for discount identification
	 * @param customerControl checks requirement
	 * @return dummy discount / real discount
	 * @throws CustomerRegistryException   when customer id is not found
	 * @throws CustomerDiscountIdException when discount id is not found
	 */
	public DiscountDTO searchCustomerDiscount(String discountId, boolean customerControl)
			throws CustomerRegistryException, CustomerDiscountIdException {
		if (customerControl) {
			for (DiscountDTO matchingDiscount : discountRegister)
				if ((matchingDiscount.getCategory() == Category.CUSTOMER)
						&& matchingDiscount.getDiscountId().equals(discountId)) {
					return matchingDiscount;
				}
		}
			throw new CustomerDiscountIdException("Catched in ExternalInventory class, "
					+ "quantityCheck metod, discount id: " + discountId);
	}

	/**
	 * searchItemDiscount for purchased products only create an dummy discount
	 * Search throw the discount register if there is a discount with purchased
	 * itemId returns the discount. if the number of items also matches the
	 * purchased item, then returns quantity discount
	 * 
	 * @param itemId   for product identification
	 * @param quantity for compare number of goods
	 * @return dummy discount / real discount
	 */
	public DiscountDTO searchItemDiscount(String itemId, int quantity) {
		DiscountDTO dummyDiscount = new DiscountDTO();
		for (DiscountDTO matchingDiscount : discountRegister)
			if ((matchingDiscount.getCategory() != Category.CUSTOMER)
					&& (matchingDiscount.getItemId().equalsIgnoreCase(itemId))
					&& (matchingDiscount.getItemQuantity() <= quantity)) {
				return matchingDiscount;
			}
		return dummyDiscount;
	}

	/**
	 * server get current status
	 * 
	 * @return status
	 */
	public Connection getConnectionStatus() {
		return this.connection;
	}

	/**
	 * set server status
	 * 
	 * @param connection status
	 */
	public void setConnectionStatus(Connection connection) {
		this.connection = connection;
	}

	/**
	 * controls server connection throw check exception
	 * 
	 * @throws ServerOfflineException when there is no connection to the server
	 */
	public void connectionControl() throws ServerOfflineException {
		Server.connectionCheck(this.serverTyp, this.connection);
	}

}
