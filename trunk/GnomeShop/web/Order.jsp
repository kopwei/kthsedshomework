<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Order
    Created on : Dec 7, 2007, 10:08:00 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{Order.page1}" id="page1">
            <webuijsf:html binding="#{Order.html1}" id="html1">
                <webuijsf:head binding="#{Order.head1}" id="head1">
                    <webuijsf:link binding="#{Order.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Order.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Order.form1}" id="form1">
                        <div style="position: absolute; left: 0px; top: 0px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="position: absolute; left: 216px; top: 528px">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <table style="height: 192px; left: 240px; top: 96px; position: absolute" width="192">
                            <tr>
                                <td>Contact Name:</td>
                                <td>
                                    <h:outputText value="#{OrderBean.contactName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Delivery Address:</td>
                                <td>
                                    <h:outputText value="#{OrderBean.deliveryAddress}"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="height: 44px">Credit Card Name</td>
                                <td>
                                    <h:outputText value="#{OrderBean.creditCardName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Credit card number:</td>
                                <td>
                                    <h:outputText value="#{OrderBean.creditCardNumber}"/>
                                </td>
                            </tr>
                        </table>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
