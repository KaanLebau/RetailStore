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
import integration.Printer;
import model.CashRegister;
import model.Payment;
import model.Product;
import model.Sale;
import model.SaleInfoDTO;
import model.SaleObserver;
import util.enums.Method;
import util.enums.ExcPriority;
import util.exceptions.CustomerDiscountIdException;
import util.exceptions.CustomerRegistryException;
import util.exceptions.ItemNotFoundException;
import util.exceptions.ItemQuantityInInventoryIsIncorrectException;
import util.exceptions.ServerOfflineException;
import util.log.DetailedSaleLog;
import util.log.ExceptionLoger;
import util.log.LogMessageHandler;

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
	private int saleId = 1;
	private LogMessageHandler logMessageHandler;
	private ExceptionLoger exceptionLogger;
	private DetailedSaleLog saleLog;
	private List<SaleObserver> saleObservers = new ArrayList<>();

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
	 * @throws IOException 
	 */
	public Controller(Printer printer, DiscountRegister discountRegister, ExternalInventory externalIventory,
			ExternalAccounting externalAccounting, CustomerRegister customerRegister) throws IOException {
		this.printer = printer;
		this.discountRegister = discountRegister;
		this.externalAccounting = externalAccounting;
		this.customerRegister = customerRegister;
		this.externalInventory = externalIventory;
		this.sale = new Sale(this.saleId);
		Address address = new Address("Göteborg", "andra lång", 12, 42427);
		this.cashRegister = new CashRegister(address, printer);
		this.saleInfoDTO = new SaleInfoDTO(this.sale);
		this.logMessageHandler = new LogMessageHandler();
		this.exceptionLogger = new ExceptionLoger();
		this.saleLog = new DetailedSaleLog();
	}
	/**
	 * Controls if all servers is online
	 * 
	 * @throws ServerOfflineException when server is not online
	 * @throws Exception unknown failure 
	 */
	public void serverConnection() throws ServerOfflineException,Exception {
		try {			
			discountRegister.connectionControl();
			externalAccounting.connectionControl();
			externalInventory.connectionControl();
			customerRegister.connectionControl();
		}catch(ServerOfflineException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			logMessageHandler.newExceptionLog(ExcPriority.HIGH, e, "ServerOfflineException",this.exceptionLogger.getPrintWriter());
			throw new ServerOfflineException("No connection");
		}catch(Exception e) {
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
	 * gets excLog
	 * 
	 * @return exception log
	 */
	public ExceptionLoger getExceptionLog() {
		return this.exceptionLogger;
	}

	/**
	 * Searches in external inventory create an Product sets product quantity and
	 * adds product to the sale uses discountCheck() to control any discount apply
	 * to the sale
	 * 
	 * @param itemId for searching item in external inventory
	 * @throws ItemNotFoundException when item id is not found in inventory
	 * @throws ServerOfflineException when external inventory could not be connected
	 * @throws Exception unknown failure 
	 */
	public void addProduct(String itemId, int itemQuantity) 
			throws ItemNotFoundException, ItemQuantityInInventoryIsIncorrectException
,			ServerOfflineException, Exception {
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
		}catch(ItemNotFoundException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			
			//logHandler.newExceptionLog(ExcPriority.LOW, e,"ItemNotFoundException");
		}catch(ItemQuantityInInventoryIsIncorrectException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.MEDIUM, e,"ItemQuantityInInventoryIsIncorrect");
			System.out.println("Is quantity of item correct scanned?");
		}catch(ServerOfflineException e) {
			throw new ServerOfflineException("no Connection");
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method addProduct manually");
		}
	}

	/**
	 * Searches in external inventory create an Product and adds product to the sale
	 * uses discountCheck() to control any discount apply to the sale
	 * 
	 * @param itemId for searching item in external inventory
	 * @throws ItemNotFoundException when item id is not found in inventory
	 * @throws ServerOfflineException when external inventory could not be connected
	 * @throws Exception unknown failure 
	 */
	public void addProduct(String itemId) 
			throws ItemNotFoundException,ItemQuantityInInventoryIsIncorrectException, Exception,
			ServerOfflineException {
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
		}catch(ItemNotFoundException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			logMessageHandler.newExceptionLog(ExcPriority.HIGH, e,"ItemNotFoundException",
					this.exceptionLogger.getPrintWriter());
			throw new ItemNotFoundException("no item");
		}catch(ItemQuantityInInventoryIsIncorrectException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			logMessageHandler.newExceptionLog(ExcPriority.MEDIUM, e,"ItemQuantityInInventoryIsIncorrect",this.exceptionLogger.getPrintWriter());
			throw new ItemQuantityInInventoryIsIncorrectException("inventory quantity balance is incorrect");
		}catch(ServerOfflineException e) {
			throw new ServerOfflineException("no connection");
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method addProduct with scanner");
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
	 * @throws Exception unknown failure in program
	 * @throws CustomerRegistryException when customer id is not found in customer register
	 * @throws CustomerDiscountIdException when discount id is not found in discount register
	 * @throws ServerOfflineException when there is no connection to the server
	 */
	public void discountRequest(String discountId, String customerId) 
			throws Exception, CustomerRegistryException, CustomerDiscountIdException, ServerOfflineException {
		try {
			serverConnection();
			DiscountDTO discountToAplly = new DiscountDTO();
			boolean customerControl = customerRegister.searchCustomerDTO(customerId);
			DiscountDTO foundDiscount = discountRegister.searchCustomerDiscount(discountId, customerControl);
			discountToAplly = foundDiscount;
			sale.getRegistredDiscount().add(discountToAplly);
		} catch (CustomerRegistryException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.LOW, e,"CustomerRegistryException");
			throw new CustomerRegistryException("customer not existing in customer registry");
		} catch (CustomerDiscountIdException e) {
			logMessageHandler.setLogger(this.exceptionLogger);
			//logHandler.newExceptionLog(ExcPriority.LOW, e,"CustomerDiscountIdException");
			throw new CustomerDiscountIdException("discount id not existing in discount registry");
		} catch(ServerOfflineException e) {
			throw new ServerOfflineException("Server offline");
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method diccountRequest");
		}

	}

	/**
	 * updates external inventory and external accounting
	 * @throws ServerOfflineException when external inventory or external accounting could not be connected
	 * @throws Exception unknown failure 
	 */
	private void updateExternalSystem()
			throws ServerOfflineException, Exception {
		try {
			serverConnection();
			externalInventory.updateExternalInventory(sale.getPurcheasedProducts());
			externalAccounting.updateExternalAccounting(sale.getEndSaleTotal());
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method updateExternalSystem");
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
	 * @throws ServerOfflineException when there is no connection to the server
	 * @throws InterruptedException @SuppressWarnings("unused")
	 * @throws Exception unknown failure 
	 */
	public void addPayment(Method method) throws ServerOfflineException, Exception {
		try {			
			serverConnection();
			this.payment = new Payment(Method.CARDTERMINAL, saleInfoDTO, cashRegister);
			payment.addSaleObserverList(saleObservers);
			boolean waiting = true;
			while (waiting) {
				if (payment.getPaymentDone()) {
					payment.createReceipt(payment);
					payment.getReceipt().sendReceiptToPrinter();
					updateExternalSystem();
					logMessageHandler.setLogger(this.saleLog);
					logMessageHandler.newSaleLog(this.saleInfoDTO, method, saleLog.getPrintWriter());
					waiting = false;
				}
			}
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method addPayment for card terminal");
		}
	}
	
	/**
	 * adds sale observer to the
	 * saleObservers list
	 * 
	 * @param saleObs
	 */
    public void addSaleObserver(SaleObserver saleObs) {
        saleObservers.add(saleObs);
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
	 * @throws ServerOfflineException when there is no connection to the server
	 * @throws InterruptedException
	 * @throws Exception unknown failure 
	 */
	public void addPayment(Method method, double amount) throws ServerOfflineException,Exception {
		try {			
			serverConnection();
			this.payment = new Payment(Method.CASH, amount, saleInfoDTO, cashRegister);
			payment.createReceipt(payment);
			payment.getReceipt().sendReceiptToPrinter();
			cashRegister.addToBalance(sale.getEndSaleTotal());
			updateExternalSystem();
			logMessageHandler.setLogger(this.saleLog);
			logMessageHandler.newSaleLog(this.saleInfoDTO, method, saleLog.getPrintWriter());
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method addPayment for cash");
		}
	}

	private void discountCheck(Product product) 
			throws ServerOfflineException,Exception{
		try {			
			serverConnection();
			for(Product newProduct: sale.getPurcheasedProducts())
				if(newProduct.equals(product))
					product = newProduct;
			DiscountDTO singelItem = discountRegister.searchItemDiscount(product.getId(), product.getQuantity());
			if (product.getQuantity() == 1) {
				sale.addItemDiscount(singelItem);
			} else {
				sale.addItemDiscount(singelItem);
			}
		}catch(Exception e) {
			throw new Exception("unknown failure class controller method discountCheck");
		}
	}
}
