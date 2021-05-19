package integration;

import util.enums.Type;

public class Money {
	private int value;
	private int quantityInRegister = 0;;
	private int quantityInChange = 0;
	private Type type;
	private String currency = "USD"; 
	
	Money(int value, int quantity, Type type){
		this.setValue(value);
		this.setQuantity(quantity);
		this.setType(type);
	}
	
	Money(Money money, int quantityInChange){
		this.currency = money.getCurrency();
		this.type =money.getType();
		this.value = money.getValue();
		this.quantityInChange = quantityInChange;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getQuantity() {
		return quantityInRegister;
	}

	public void setQuantity(int quantity) {
		this.quantityInRegister = quantity;
	}
	
	public void addQuantity(int quantity) {
		this.quantityInRegister += quantity;
	}
	
	public void removeFromQuantity(int quantity) {
		this.quantityInRegister -= quantity;
	}
	
	public int getQuantityInChange() {
		return quantityInChange;
	}
	
	public void setQuantityInChange(int quantityInChange) {
		this.quantityInChange = quantityInChange;
	}
	

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public String toString() {
		String s ="";
		s += Integer.toString(value);
		s += " " + this.currency;
		s += " i register " + Integer.toString(getQuantity());
		s += " i change " + Integer.toString(quantityInChange);
		return s;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Money)) {
			return false;
		}
		Money other = (Money) object;
		return this.value == other.value;
	}

}
