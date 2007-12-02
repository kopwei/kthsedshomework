package agents;

import jade.core.AID;
import jade.util.leap.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashSet;

/**
 *
 * @author Ricky
 */
public class AIDMessageContent implements Serializable{
    private HashSet<AID> aidCollection = null;
    private AID aid = null;
    private int bestPrice = 0;
    
    public void setAIDCollection(HashSet<AID> aidCol) {
        aidCollection = aidCol;
    }
    
    public HashSet<AID> getAIDCollection() {
        return aidCollection;
    }
    
    public void setTheAID(AID aid) {
        this.aid = aid;
    }
    
    public AID getTheAID() {
        return aid;
    }
    
    public void setBestPrice(int price) {
        bestPrice = price;
    }
    
    public int getBestPrice() {
        return bestPrice;
    }
}
