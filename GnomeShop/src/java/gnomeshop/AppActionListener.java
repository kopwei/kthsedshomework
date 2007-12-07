/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gnomeshop;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 *
 * @author Kop
 */
public class AppActionListener implements ActionListener{

    public void processAction(ActionEvent event) throws AbortProcessingException {
        String actionCommand = event.getSource().toString();
        System.err.println(actionCommand);
        if ("MemberDetail".equals(actionCommand)) {
            
        }
    }
}
