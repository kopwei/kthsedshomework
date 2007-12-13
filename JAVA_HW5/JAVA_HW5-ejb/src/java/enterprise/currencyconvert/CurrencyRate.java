/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package enterprise.currencyconvert;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kop
 */
@Entity
@Table(name = "currency_rate")
@NamedQueries({@NamedQuery(name = "CurrencyRate.findByCurrencyName", query = "SELECT c FROM CurrencyRate c WHERE c.currencyName = :currencyName"), @NamedQuery(name = "CurrencyRate.findByRate", query = "SELECT c FROM CurrencyRate c WHERE c.rate = :rate")})
public class CurrencyRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "currency_name", nullable = false)
    private String currencyName;
    @Column(name = "rate", nullable = false)
    private float rate;

    /**
     * 
     */
    public CurrencyRate() {
    }

    /**
     * 
     * @param currencyName
     */
    public CurrencyRate(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 
     * @param currencyName
     * @param rate
     */
    public CurrencyRate(String currencyName, float rate) {
        this.currencyName = currencyName;
        this.rate = rate;
    }

    /**
     * 
     * @return
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 
     * @param currencyName
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 
     * @return
     */
    public float getRate() {
        return rate;
    }

    /**
     * 
     * @param rate
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyName != null ? currencyName.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrencyRate)) {
            return false;
        }
        CurrencyRate other = (CurrencyRate) object;
        if ((this.currencyName == null && other.currencyName != null) || (this.currencyName != null && !this.currencyName.equals(other.currencyName))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return "enterprise.currencyconvert.CurrencyRate[currencyName=" + currencyName + "]";
    }

}
