/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package enterprise.currencyconvert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author kop
 */
@Stateless
public class CurrencyTransferBean implements CurrencyTransferLocal {
    
    EntityManager em;

    public float transfer(String originalCurrency, String targetCurrency, float inputValue) {
        CurrencyRate original = em.find(CurrencyRate.class, originalCurrency);
        CurrencyRate target = em.find(CurrencyRate.class, targetCurrency);
        
        return ( target.getRate() / original.getRate() ) * inputValue;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
