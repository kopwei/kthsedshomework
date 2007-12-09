/**
 * 
 */
package gnomeshop.items;

import java.util.UUID;

/**
 * @author Kop
 *
 */
/**
 * This class represents an order entity
 */
public class OrderBean {

    private UUID orderId;
    private UUID memberId;
    private String contactName;
    private String deliveryAddress;
    private String creditCardName;
    private String creditCardNumber;
    private String creditCardExpiryDate;
    
    public OrderBean() {
        this.orderId = UUID.randomUUID();
        this.memberId = null;
        contactName = "";
        deliveryAddress= "";
        creditCardNumber = "";
        creditCardName = "";
        creditCardExpiryDate="";
    }

    /**
     * Constructor used to initialize the information
     * @param memberId The member id of the order
     * @param contactName Order's contact name
     * @param deliveryAddress Order's delivery address
     * @param creditCardName Order's credit card name
     * @param creditCardNumber Order's credit card number
     * @param creditCardExpiryDate Order's credit card expire date
     */
    public OrderBean(String memberId, String contactName, String deliveryAddress, String creditCardName,
            String creditCardNumber, String creditCardExpiryDate) {
        this.orderId = UUID.randomUUID();
        this.memberId = UUID.fromString(memberId);
        this.contactName = contactName;
        this.deliveryAddress = deliveryAddress;
        this.creditCardName = creditCardName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiryDate = creditCardExpiryDate;
    }

    /**
     * Constructor used to re-construct the object with the information read out of the database
     * @param orderId The order's ID
     * @param memberId The member id of the order
     * @param contactName Order's contact name
     * @param deliveryAddress Order's delivery address
     * @param creditCardName Order's credit card name
     * @param creditCardNumber Order's credit card number
     * @param creditCardExpiryDate Order's credit card expire date
     */
    public OrderBean(String orderId, String memberId, String contactName, String deliveryAddress, String creditCardName,
            String creditCardNumber, String creditCardExpiryDate) {
        this.orderId = UUID.fromString(orderId);
        this.memberId = UUID.fromString(memberId);
        this.contactName = contactName;
        this.deliveryAddress = deliveryAddress;
        this.creditCardName = creditCardName;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiryDate = creditCardExpiryDate;
    }

    /**
     * This method is used to get the unique id of the order
     * @return The order id
     */
    public String getOrderId() {
        return orderId.toString();
    }

    /**
     * This method is used to get the member id of the current order
     * @return member's id
     */
    public String getMemberId() {
        return memberId.toString();
    }

    /**
     * This method is used to set the member id of the current order
     * @param memberId member's id
     */
    public void setMemberId(String memberId) {
        this.memberId = UUID.fromString(memberId);
    }

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
    
    /**
     * This method is used for clear all the informations in the current order
     */
    public void clear() {
        this.orderId = UUID.randomUUID();
        this.memberId = null;
        this.contactName = null;
        this.deliveryAddress = null;
        this.creditCardName = null;
        this.creditCardNumber = null;
        this.creditCardExpiryDate = null;
    }
}
