package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import integration.Address;
import integration.CustomerRegister;
import integration.DiscountDTO;
import integration.DiscountRegister;
import integration.ExternalAccounting;
import integration.ExternalInventory;
import integration.ItemDTO;
import integration.MoneyObserver;
import integration.Printer;
import integration.TransactionObserver;
import model.CashRegister;
import model.Payment;
import model.Product;
import model.Sale;
import model.SaleInfoDTO;
import model.SaleObserver;
import util.enums.ExcPriority;
import util.enums.Method;
import util.exceptions.CustomerDiscountIdNotFoundException;
import util.exceptions.CustomerIdNotFoundException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ItemQuantityInInventoryIsIncorrectException;
import util.exceptions.ServerOfflineException;
import util.log.ExceptionFileLog;
import util.log.LogFactory;
/**
 * an important layer that handles communication between the layers
 * @author ozsan
 *
 */
public class Controller {
	@SuppressWarnings("unused")
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
	private int saleId = 0;

	private List<SaleObserver> saleObserverList = new ArrayList<>();
	private List<TransactionObserver> transactionObserverList = new ArrayList<>();
	private List<MoneyObserver> moneyObserverList = new ArrayList<>();

	/**
	 * This boolean attribute for simulating proposes with card 
	 * @return true/false
	 */
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
	 * @throws IOException	from log factory
	 */
	public Controller(Printer printer, DiscountRegister discountRegister, ExternalInventory externalIventory,
			ExternalAccounting externalAccounting, CustomerRegister customerRegister) throws IOException {
		this.printer = printer;
		this.discountRegister = discountRegister;
		this.externalAccounting = externalAccounting;
		this.customerRegister = customerRegister;
		this.externalInventory = externalIventory;
		Address address = new Address("Göteborg", "andra lång", 12, 42427);
		this.cashRegister = new CashRegister(address, printer);
		this.saleInfoDTO = new SaleInfoDTO(this.sale);
	}

	/**
	 * Controls if all servers is online if an Server is offline logs the server is
	 * offline then send exception to view
	 * 
	 * @throws ServerOfflineException when server is not online
	 * @throws Exception              unknown failure
	 */
	public void serverConnection() throws ServerOfflineException, Exception {
		try {
			discountRegister.connectionControl();
			externalAccounting.connectionControl();
			externalInventory.connectionControl();
			customerRegister.connectionControl();
		} catch (ServerOfflineException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "ServerOfflineException");
			throw e;
		} catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "from Controller method serverConnection");
			throw new Exception("unknown failure class controller method serverConnection");

		}
	}

	/**
	 * Create an new instance of Sale and SaleInfoDTO
	 */
	public void cerateNewSale() {
		saleId += 1;
		this.sale = new Sale(this.saleId);
		this.saleInfoDTO = new SaleInfoDTO(this.sale);
	}

	/**
	 * unique sale id since program started  
	 * every time program starts sale id start with 1
	 * 
	 * @return id
	 */
	public int getSaleId() {
		return this.saleId;
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
	 * @throws ItemNotFoundException  when item id is not found in inventory
	 * @throws Exception              unknown failure
	 * @throws ItemQuantityInInventoryIsIncorrectException when item quantity is below zero 
	 */
	public void addProduct(String itemId, int itemQuantity) throws ItemNotFoundException,
			ItemQuantityInInventoryIsIncorrectException, Exception {
		try {
			serverConnection();
			ItemDTO foundItem = externalInventory.searchItem(itemId, itemQuantity);
			String id = foundItem.getId();
			String name = foundItem.getName();
			Double netPrice = foundItem.getNetPrice();
			Double vat = foundItem.getVAT();
			Product product = new Product(id, name, netPrice, vat, itemQuantity);
			sale.addProductToSale(product);
			discountCheck(product);
		} catch (ItemNotFoundException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.LOW, e,"ItemNotFoundException");
			throw e;
		} catch (ItemQuantityInInventoryIsIncorrectException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.MEDIUM, e,"ItemQuantityInInventoryIsIncorrect");
		}  catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "from Controller method addProduct manually");
			throw e;
		}
	}

	/**
	 * Searches in external inventory create an Product and adds product to the sale
	 * uses discountCheck() to control any discount apply to the sale
	 * 
	 * @param itemId for searching item in external inventory
	 * @throws ItemNotFoundException  when item id is not found in inventory
	 *  @throws ItemQuantityInInventoryIsIncorrectException when item quantity is below zero 
	 * @throws Exception              unknown failure
	 */
	public void addProduct(String itemId) throws ItemNotFoundException, ItemQuantityInInventoryIsIncorrectException,
			Exception {
		try {
			serverConnection();
			int singelItem = 1;
			ItemDTO foundItem = externalInventory.searchItem(itemId, singelItem);
			String id = foundItem.getId();
			String name = foundItem.getName();
			Double netPrice = foundItem.getNetPrice();
			Double vat = foundItem.getVAT();
			Product product = new Product(id, name, netPrice, vat, singelItem);
			sale.addProductToSale(product);
			discountCheck(product);
		} catch (ItemNotFoundException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH,e,"ItemNotFoundException");
			throw e;
		} catch (ItemQuantityInInventoryIsIncorrectException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.MEDIUM,e,"ItemQuantityInInventoryIsIncorrect");
		} catch (Exception e) {			
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "from Controller method addProduct");
			throw e;
		}
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
	 * @throws Exception                   unknown failure in program
	 * @throws CustomerIdNotFoundException   when customer id is not found in customer register
	 * @throws CustomerDiscountIdNotFoundException when discount id is not found in discount register
	 */
	public void discountRequest(String discountId, String customerId)
			throws Exception, CustomerIdNotFoundException, CustomerDiscountIdNotFoundException {
		try {
			serverConnection();
			DiscountDTO discountToAplly = new DiscountDTO();
			boolean customerControl = customerRegister.searchCustomerDTO(customerId);
			DiscountDTO foundDiscount = discountRegister.searchCustomerDiscount(discountId, customerControl);
			discountToAplly = foundDiscount;
			sale.getRegistredDiscount().add(discountToAplly);
		} catch (CustomerIdNotFoundException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.LOW, e,"CustomerRegistryException");
			throw e;
		} catch (CustomerDiscountIdNotFoundException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.LOW, e,"CustomerDiscountIdException");
			throw e;
		} catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "unknown from controller discountRequaest");
			throw e;
		}

	}

	/**
	 * updates external inventory and external accounting
	 * 
	 * @throws Exception              unknown failure
	 */
	private void updateExternalSystem() throws ServerOfflineException, Exception {
		try {
			serverConnection();
			externalInventory.updateExternalInventory(sale.getPurcheasedProducts());
			externalAccounting.updateExternalAccounting(sale.getEndSaleTotal());
		} catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "unknown from controller method updateExternalSystem");
			throw e;
		}
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
	 * 
	 * @throws InterruptedException   @SuppressWarnings("unused")
	 * @throws Exception              unknown failure
	 */
	public void addPayment(Method method) throws ServerOfflineException, Exception {
		try {
			serverConnection();
			this.payment = new Payment(Method.CARDTERMINAL, saleInfoDTO, cashRegister);
			payment.addSaleObserverList(saleObserverList);
			payment.createReceipt(payment);
			payment.getReceipt().sendReceiptToPrinter();
			boolean waiting = true;
			while (waiting) {
				if (payment.getPaymentDone()) {
					payment.createReceipt(payment);
					payment.getReceipt().sendReceiptToPrinter();
					updateExternalSystem();
					waiting = false;
				}
			}
		} catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "unknown from controller method addpayment with card terminal");
			throw e;
		}
	}

	
	
	/**
	 * adds Transaction observer TransactionObserver list
	 * 
	 * @param observers takes saleObserver and TransactionObserver
	 */
	public void addTransactionObservers(TransactionObserver transactionObservers) {
		transactionObserverList.add(transactionObservers);
	}
	
	/**
	 * adds sale observer to the saleObservers list
	 * 
	 * @param saleObs
	 */
	public void addSaleObserver(SaleObserver saleObserver) {
		saleObserverList.add(saleObserver);
	}
	
	/**
	 * adds list of money observer to the money observer list
	 * 
	 * @param moneyObservers list
	 */
	public void addMoneyObservers(MoneyObserver moneyObservers) {
		moneyObserverList.add(moneyObservers);
	}

	/**
	 * addPayment handles cash payment take method and amount payment creates an
	 * instance of Payment
	 * 
	 * Uses updateExternalSystem() for updating Uses addToBalance for updating
	 * CashRgister balance
	 * 
	 * @param method cash Method
	 * @param amount double
	 * @throws InterruptedException
	 * @throws Exception              unknown failure
	 */
	public void addPayment(Method method, double amount) throws InterruptedException, Exception {
		try {
			serverConnection();
			this.payment = new Payment(Method.CASH, amount, saleInfoDTO, cashRegister);
			payment.addSaleObserverList(saleObserverList);
			payment.addObserversList(transactionObserverList);
			payment.getCashRegister().addMoneyObservers(moneyObserverList);
			payment.getCashRegister().getCashMachine().addMoneyObservers(moneyObserverList);
			payment.createReceipt(payment);
			payment.getReceipt().sendReceiptToPrinter();
			updateExternalSystem();
		}catch(InterruptedException e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "InterruptedException");
		}catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e, "unknown failure class controller method addPayment for cash");
			throw e;
		}
	}

	private void discountCheck(Product product) throws Exception {
		try {
			serverConnection();
			for (Product newProduct : sale.getPurcheasedProducts())
				if (newProduct.equals(product))
					product = newProduct;
			DiscountDTO singelItem = discountRegister.searchItemDiscount(product.getId(), product.getQuantity());
			if (product.getQuantity() == 1) {
				sale.addItemDiscount(singelItem);
			} else {
				sale.addItemDiscount(singelItem);
			}
		} catch (Exception e) {
			LogFactory.getLogFactory().getExceptionLogger().newExceptionLog(ExcPriority.HIGH, e,"unknown failure class controller method discountCheck"); 
			throw e;
		}
	}

}
