package controller;

import integration.Address;
import integration.CustomerRegister;
import integration.DiscountDTO;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.ItemDTO;
import integration.Printer;
import model.CashRegister;
import model.Payment;
import model.Product;
import model.Sale;
import model.SaleInfoDTO;
import util.Util.Method;

public class Controller {
	private Printer printer;
	private final DiscountRegister discountRegister;
	private final ExternalAccounting externalAccounting;
	private final ExternalInventory externalInventory;
	private final CustomerRegister customerRegister;
	private CashRegister cashRegister;
	private Sale sale;
	private Payment payment;
	private SaleInfoDTO saleInfoDTO;
	private boolean paymentDone = false;

	public boolean getPaymentDone() {
		return this.paymentDone;
	}

	/**
	 * Controller constructor
	 * 
	 * gets references to the all lager and creates an instance of sale creates an
	 * instance of address creates an instance of cashRegister with address and
	 * printer as argument
	 * 
	 * @param printer            reference to the printer
	 * @param discountRegister   reference to the discount register
	 * @param externalIventory   reference to the external inventory
	 * @param externalAccounting reference to the external accounting
	 * @param customerRegister   reference to the customer register
	 */
	public Controller(Printer printer, DiscountRegister discountRegister, ExternalInventory externalIventory,
			ExternalAccounting externalAccounting, CustomerRegister customerRegister) {
		this.printer = printer;
		this.discountRegister = discountRegister;
		this.externalAccounting = externalAccounting;
		this.customerRegister = customerRegister;
		this.externalInventory = externalIventory;
		this.sale = new Sale();
		Address address = new Address("G�teborg", "andra l�ng", 12, 42427);
		this.cashRegister = new CashRegister(address, printer);
		this.saleInfoDTO = new SaleInfoDTO(sale.getPurcheasedProducts(), sale.getRegistredDiscount());
	}

	/**
	 * gets active Sale
	 * 
	 * @return Sale
	 */
	public SaleInfoDTO getSaleInfoDTO() {
		return this.saleInfoDTO;
	}

	/**
	 * gets active payment
	 * 
	 * @return payment
	 */
	public Payment getPayment() {
		return this.payment;
	}

	/**
	 * Searches in external inventory create an Product sets product quantity and
	 * adds product to the sale uses discountCheck() to control any discount apply
	 * to the sale
	 * 
	 * @param itemId for searching item in external inventory
	 */
	public void addProduct(String itemId, int itemQuantity) {
		ItemDTO foundItem = externalInventory.searchItem(itemId);
		String id = foundItem.getId();
		String name = foundItem.getName();
		Double netPrice = foundItem.getNetPrice();
		Double vat = foundItem.getVAT();
		Product product = new Product(id, name, netPrice, vat, itemQuantity);
		sale.addProductToSale(product);
		discountCheck(product);
	}

	/**
	 * Searches in external inventory create an Product and adds product to the sale
	 * uses discountCheck() to control any discount apply to the sale
	 * 
	 * @param itemId for searching item in external inventory
	 */
	public void addProduct(String itemId) {
		int singelItem = 1;
		ItemDTO foundItem = externalInventory.searchItem(itemId);
		String id = foundItem.getId();
		String name = foundItem.getName();
		Double netPrice = foundItem.getNetPrice();
		Double vat = foundItem.getVAT();
		Product product = new Product(id, name, netPrice, vat, singelItem);
		sale.addProductToSale(product);
		discountCheck(product);
	}

	/**
	 * adds discount to the sale
	 * 
	 * uses copyDiscountTypDTOToDiscount to convert DiscountTypDTO to Discount uses
	 * boolean method searchCustomerDTO to validate customer
	 * 
	 * 
	 * @param discountId uses for discount search
	 * @param customerId uses for customer identification
	 */
	public void discountRequest(String discountId, String customerId) {
		DiscountDTO discountToAplly = new DiscountDTO();
		boolean customerControl = customerRegister.searchCustomerDTO(customerId);
		DiscountDTO foundDiscount = discountRegister.searchCustomerDiscount(discountId, customerControl);
		discountToAplly = foundDiscount;
		sale.getRegistredDiscount().add(discountToAplly);

	}

	/**
	 * updates external inventory and external accounting
	 * 
	 */
	private void updateExternalSystem() {
		externalInventory.updateExternalInventory(sale.getPurcheasedProducts());
		externalAccounting.updateExternalAccounting(sale.getEndSaleTotal());

	}

	/**
	 * addPayment handles card terminal payment take method and creates an instance
	 * of Payment
	 * 
	 * Uses updateExternalSystem() for updating external system when payment
	 * succeeded.
	 * 
	 * @param method card terminal Method
	 * @param amount double
	 * @throws InterruptedException @SuppressWarnings("unused")
	 */
	public void addPayment(Method method) {
		this.payment = new Payment(Method.CARDTERMINAL, sale, cashRegister);
		boolean waiting = true;
		while (waiting) {
			if (payment.getPaymentDone()) {
				payment.createReceipt(payment);
				payment.getReceipt().sendReceiptToPrinter();
				updateExternalSystem();
				waiting = false;
			}
		}
	}

	/**
	 * addPayment handles cash payment take method and amount payment creates an
	 * instance of Payment
	 * 
	 * Uses updateExternalSystem() for updating
	 * Uses addToBalance for updating CashRgister balance 
	 * 
	 * @param method cash Method
	 * @param amount double
	 * @throws InterruptedException
	 */
	public void addPayment(Method method, double amount) {
		this.payment = new Payment(Method.CASH, amount, sale, cashRegister);
		payment.createReceipt(payment);
		payment.getReceipt().sendReceiptToPrinter();
		cashRegister.addToBalance(sale.getEndSaleTotal());
		updateExternalSystem();
	}

	private void discountCheck(Product product) {
		for(Product newProduct: sale.getPurcheasedProducts())
			if(newProduct.equals(product))
				product = newProduct;
		DiscountDTO singelItem = discountRegister.searchItemDiscount(product.getId(), product.getQuantity());
		if (product.getQuantity() == 1) {
			sale.addItemDiscount(singelItem);
		} else {
			sale.addItemDiscount(singelItem);
		}
	}
}
