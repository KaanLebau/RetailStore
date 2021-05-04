package integration;

import java.util.ArrayList;
import java.util.List;

import model.Product;


public class ExternalInventory {
	List<ItemDTO> itemList = new ArrayList<>();
	List<Product> inventory = new ArrayList<>();
	

	ItemDTO cola = new ItemDTO("101", "Cola", 10, 10);
	ItemDTO prestOst = new ItemDTO("104", "Ost", 60, 10);
	ItemDTO kaffe = new ItemDTO("106", "Kaffe", 34, 10);
	ItemDTO hamburgare = new ItemDTO("107", "Hamburagare", 42, 10);
	ItemDTO hambDressign = new ItemDTO("108", "Hamburagare Dressing", 23, 10);
	ItemDTO hambBröd = new ItemDTO("109", "Hamburagare bröd", 25, 10);
	
	Product colaInv = new Product("101", "Cola", 12);
	Product prestOstInv = new Product("104", "Ost", 58);
	Product kaffeInv = new Product("106", "Kaffe", 34);
	Product hamburgareInv = new Product("107", "Hamburagare", 42);
	Product hambDressignInv = new Product("108", "Hamburagare Dressing", 23);
	Product hambBrödInv = new Product("109", "Hamburagare bröd", 25);
	
	public ExternalInventory() {
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
	
	public List<Product> getInventoryList() {
		return this.inventory;
		
	}
	public List<ItemDTO> getItemList(){
		return this.itemList;
	}

	/**
	 * searchItem gets itemId
	 * create a dummy Item
	 * Controls if inventory has the item and have quantity
	 * if above check searches item id in ItemDTO list 
	 * dummy itemDTO gets found items attributes     
	 * @param itemId
	 * @return dummy item / real item
	 */
	public ItemDTO searchItem(String itemId) {
		ItemDTO foundItem = new ItemDTO();
		if(quantityCheck(itemId)) {	
			for(ItemDTO search : itemList)
				if(search.getId().equalsIgnoreCase(itemId)) {
					foundItem = search;
					break;
				}
		}
		return foundItem;
	}

//	public Product searchProduct(String itemId) {
//		Product foundProduct = new Product();
//		for(Product search : inventory)
//			if(search.getId().equalsIgnoreCase(itemId)) {
//				foundProduct = search;
//				break;
//			}
//		return foundProduct;
//	}

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
	
	/**
	 * 
	 * @param itemId
	 * @return
	 */
	private boolean quantityCheck(String itemId) {
		boolean exist = false;
		for(Product inInventory : inventory)
			if((inInventory.getId().equalsIgnoreCase(itemId) &&
					(inInventory.getQuantity() > 0)))
				exist = true;
				
		return exist;
	}
}
