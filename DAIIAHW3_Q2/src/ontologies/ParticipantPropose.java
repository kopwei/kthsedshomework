/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontologies;

import jade.content.Predicate;

/**
 *
 * @author Kop
 */
public class ParticipantPropose implements Predicate{

    private boolean accept = false;

    public void setAcceptance(boolean isAccept) {
        this.accept = isAccept;
    }
  
    public boolean getAcceptance() {
        return accept;
    }
}
