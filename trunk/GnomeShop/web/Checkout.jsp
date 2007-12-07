<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Checkout
    Created on : Dec 7, 2007, 12:31:22 AM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{Checkout.page1}" id="page1">
            <webuijsf:html binding="#{Checkout.html1}" id="html1">
                <webuijsf:head binding="#{Checkout.head1}" id="head1">
                    <webuijsf:link binding="#{Checkout.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Checkout.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Checkout.form1}" id="form1">
                        <div style="position: absolute; left: 0px; top: 0px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="position: absolute; left: 216px; top: 528px">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <table style="left: 240px; top: 96px; position: absolute">
                            <tr>
                                <td>Contact Name:</td>
                                <td>
                                    <h:inputText id="cname" required="true" value="#{OrderBean.contactName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Delivery Address:</td>
                                <td>
                                    <h:inputText id="address" required="true" value="#{OrderBean.deliveryAddress}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Credit Card Type:</td>
                                <td>
                                    <h:selectOneRadio id="ccname" required="true" value="#{OrderBean.creditCardName}">
                                        <f:selectItem itemLabel="Visa" itemValue="visa"/>
                                        <f:selectItem itemLabel="Master" itemValue="master"/>
                                    </h:selectOneRadio>
                                </td>
                            </tr>
                            <tr>
                                <td>Credit Card Number:</td>
                                <td>
                                    <h:inputText id="addr" required="true" value="#{OrderBean.creditCardNumber}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Expiry Date:</td>
                                <td>                                    
                                    <h:inputText value="#{OrderBean.creditCardExpiryDate}" id="eod" required="true" >
                                        <f:convertDateTime pattern="MM-dd-yy"/>
                                    </h:inputText>
                                    (mm-dd-yy)
                                </td>
                            </tr>
                        </table>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
