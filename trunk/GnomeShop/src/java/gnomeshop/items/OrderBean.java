/**
 * 
 */
package gnomeshop.items;

/**
 * @author Kop
 *
 */

/**
 * This class represents an order entity
 */
public class OrderBean {
	private String contactName;
	private String deliveryAddress;
	private String creditCardName;
	private String creditCardNumber;
	private String creditCardExpiryDate;
	/**
	 * This method is used to set the name of the contact person
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * This method is used to get the name of the contact person
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * This method is used to set the delivery address of the contact person
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	/**
	 * This method is used to get the delivery address of the contact person
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	/**
	 * This method is used to set the credit card name of the contact person
	 * @param creditCardName the creditCardName to set
	 */
	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}
	/**
	 * This method is used to get the credit card name of the contact person
	 * @return the creditCardName
	 */
	public String getCreditCardName() {
		return creditCardName;
	}
	/**
	 * This method is used to set the credit card number of the contact person
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	/**
	 * This method is used to get the credit card number of the contact person
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	/**
	 * This method is used to set the credit card expire date of the contact person
	 * @param creditCardExpiryDate the creditCardExpiryDate to set
	 */
	public void setCreditCardExpiryDate(String creditCardExpiryDate) {
		this.creditCardExpiryDate = creditCardExpiryDate;
	}
	/**
	 * This method is used to get the credit card expire date of the contact person
	 * @return the creditCardExpiryDate
	 */
	public String getCreditCardExpiryDate() {
		return creditCardExpiryDate;
	}

}
