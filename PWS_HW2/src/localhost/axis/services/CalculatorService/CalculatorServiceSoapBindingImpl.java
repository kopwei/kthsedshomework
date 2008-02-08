/**
 * CalculatorServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

//package localhost.axis.services.CalculatorService;
package localhost.axis.services.CalculatorService;

public class CalculatorServiceSoapBindingImpl implements localhost.axis.services.CalculatorService.CalculatorService{
    public int add(int addend1, int addend2) throws java.rmi.RemoteException {
        return addend1 + addend2;
    }

    public int division(int dividend, int divisor) throws java.rmi.RemoteException, localhost.axis.services.CalculatorService.DivideZeroException {
        if (divisor != 0){
        	return dividend / divisor;
        } 
        else {
        	throw(new localhost.axis.services.CalculatorService.DivideZeroException("Can not divide by ZERO !!!!"));
        }
    }

    public int multiplication(int multiplicand, int multiplier) throws java.rmi.RemoteException {
        return multiplicand * multiplier;
    }

    public int subtraction(int minuend, int subtrahend) throws java.rmi.RemoteException {
        return minuend - subtrahend;
    }

}
