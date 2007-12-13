/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package currencyconvert;

import javax.ejb.Local;

/**
 * This interface describes the function of the currency transfer session bean 
 * @author kop
 */
@Local
public interface CurrencyTransferLocal {
    /**
     * This method is used for get the exchange value of the currency translation
     * @param originalCurrency The name of original foreign currency
     * @param targetCurrency The name of target foreign currency
     * @param inputValue The amount of oringinal currency
     * @return The amount of target currency after transferring
     */
    float transfer(String originalCurrency, String targetCurrency, float inputValue);
    
}
