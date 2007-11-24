/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.rmi.Remote;

/**
 *
 * @author Kop
 */
public enum ItemType implements Remote{
    Unknown,
    Food,
    Electronic,
    Furniture,
    Clothes
}
