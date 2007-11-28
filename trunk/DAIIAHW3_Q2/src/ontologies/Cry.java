/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontologies;

/**
 *
 * @author Kop
 */
public class Cry {
    private MobilePhone phone = null;
    private float cryPrice = 0.0f;
    
    public void setPhone(MobilePhone phone) {
        this.phone = phone;
    }
    
    public void setCryPrice(float price) {
        this.cryPrice = price;
    }
    
    public MobilePhone getPhone() {
        return phone;
    }
    
    public float getPrice() {
        return cryPrice;
    }

}
