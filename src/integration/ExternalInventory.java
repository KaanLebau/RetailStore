package integration;

import java.util.ArrayList;
import java.util.List;

import integration.Server.Connection;
import integration.Server.ServerTyp;
import model.Product;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ItemQuantityInInventoryIsIncorrectException;
import util.exceptions.ServerOfflineException;


public class ExternalInventory {
	List<ItemDTO> itemList = new ArrayList<>();
	List<Product> inventory = new ArrayList<>();
	private Connection connection;
	private ServerTyp serverTyp;
	

	ItemDTO cola = new ItemDTO("101", "Cola", 10, 10);
	ItemDTO prestOst = new ItemDTO("104", "Ost", 60, 10);
	ItemDTO kaffe = new ItemDTO("106", "Kaffe", 34, 10);
	ItemDTO hamburgare = new ItemDTO("107", "Hamburagare", 42, 10);
	ItemDTO hambDressign = new ItemDTO("108", "Hamburagare Dressing", 23, 10);
	ItemDTO hambBröd = new ItemDTO("109", "Hamburagare bröd", 25, 10);
	
	Product colaInv = new Product("101", "Cola", 10);
	Product prestOstInv = new Product("104", "Ost", 58);
	Product kaffeInv = new Product("106", "Kaffe", 34);
	Product hamburgareInv = new Product("107", "Hamburagare", 42);
	Product hambDressignInv = new Product("108", "Hamburagare Dressing", 23);
	Product hambBrödInv = new Product("109", "Hamburagare bröd", 25);
	
	public ExternalInventory() {
		this.connection = Connection.ONLINE;
		this.serverTyp = ServerTyp.INVENTORY;
		itemList.add(cola);
		itemList.add(hambDressign);
		itemList.add(hamburgare);
		itemList.add(kaffe);
		itemList.add(prestOst);
		itemList.add(hambBröd);
		
		inventory.add(colaInv);
		inventory.add(hambDressignInv);
		inventory.add(hamburgareInv);
		inventory.add(kaffeInv);
		inventory.add(prestOstInv);
		inventory.add(hambBrödInv);

	}
	/**
	 * gets product balance 
	 * 
	 * @return product quantity list 
	 */
	public List<Product> getInventoryList() {
		return this.inventory;
		
	}
	/**
	 * gets registered items information
	 * 
	 * @return item list
	 */
	public List<ItemDTO> getItemList(){
		return this.itemList;
	}

	/**
	 * searchItem serachs itemList with itemId
	 * create a dummy Item
	 * Controls if inventory has the item and have quantity
	 * if above check searches item id in ItemDTO list 
	 * dummy itemDTO gets found items attributes   
	 * throws check exception  
	 * @param itemId
	 * @return dummy item / real item
	 * @throws ItemNotFoundException when item id is not found in inventory
	 * @throws ItemQuantityInInventoryIsIncorrectException when inventory incomplete
	 */
	public ItemDTO searchItem(String itemId, int quantity) 
			throws ItemNotFoundException, ItemQuantityInInventoryIsIncorrectException {	
		for(ItemDTO search : itemList)
				if(search.getId().equalsIgnoreCase(itemId)) {					
					quantityCheck(itemId, quantity);
					return search;
				}
				
		throw new ItemNotFoundException("Throws from ExternalInventory class, "
				+ "searchItem metod item id " + itemId);
	}

	/**
	 * gets purchased goods list 
	 * compares products using id
	 * and updates products quantity in inventory
	 * @param purchasedProducts from Sale
	 */
	public void updateExternalInventory(List<Product> purchasedProducts) {
		for(Product purchased : purchasedProducts)
			for(Product inInventory : inventory) {
				if(purchased.getId().equalsIgnoreCase(inInventory.getId())) {
					inInventory.removeQuantity(purchased.getQuantity());
				}
		}
	}
	
	private boolean quantityCheck(String itemId, int quantity) 
			throws ItemQuantityInInventoryIsIncorrectException {

		for(Product inInventory : inventory)
			if((inInventory.getId().equalsIgnoreCase(itemId) &&
					((inInventory.getQuantity() - quantity) > 0)))
				return true;
			
		throw new ItemQuantityInInventoryIsIncorrectException("Throws from ExternalInventory class, "
				+ "quantityCheck metod item id:" + itemId + " quantity: " + quantity);
				
		
	}
	
	/**
	 * server status
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
	 * controls if server is online
	 * throws an Unchecked exception
	 * 
	 * the exception should be a checked and  send a message to the user about 
	 * if the quantity of the item is correct 
	 * if the user confirms the number of the exception is saved in a log 
	 * where the developer / entertainer checks possible bug in the program
	 * 
	 * @throws ServerOfflineException when external inventory could not be connected
	 */
	public void connectionControl() throws ServerOfflineException {
		Server.connectionCheck(this.serverTyp, this.connection);
	}
	
}
