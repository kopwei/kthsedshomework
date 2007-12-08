<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : ShoppingCart
    Created on : Dec 6, 2007, 8:58:31 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{ShoppingCart.page1}" id="page1">
            <webuijsf:html binding="#{ShoppingCart.html1}" id="html1">
                <webuijsf:head binding="#{ShoppingCart.head1}" id="head1">
                    <webuijsf:link binding="#{ShoppingCart.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{ShoppingCart.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{ShoppingCart.form1}" id="form1">
                        <div style="position: absolute; left: 0px; top: 0px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="position: absolute; left: 216px; top: 528px">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <h:dataTable binding="#{ShoppingCart.dataTable1}" headerClass="list-header" id="dataTable1" rowClasses="list-row-even,list-row-odd"
                            style="height: 96px; left: 264px; top: 144px; position: absolute" value="#{ShoppingCart.dataTable1Model}" var="currentRow" width="384">
                            <h:column binding="#{ShoppingCart.column1}" id="column1">
                                <h:outputText binding="#{ShoppingCart.outputText1}" id="outputText1" value="#{currentRow['productName']}"/>
                                <f:facet name="header">
                                    <h:outputText binding="#{ShoppingCart.outputText2}" id="outputText2" value="Name"/>
                                </f:facet>
                            </h:column>
                            <h:column binding="#{ShoppingCart.column2}" id="column2">
                                <h:outputText binding="#{ShoppingCart.outputText3}" id="outputText3" value="#{currentRow['price']}"/>
                                <f:facet name="header">
                                    <h:outputText binding="#{ShoppingCart.outputText4}" id="outputText4" value="Price"/>
                                </f:facet>
                            </h:column>
                            <h:column binding="#{ShoppingCart.column3}" id="column3">
                                <h:outputText binding="#{ShoppingCart.outputText5}" id="outputText5" value="#{currentRow['quantity']}"/>
                                <f:facet name="header">
                                    <h:outputText binding="#{ShoppingCart.outputText6}" id="outputText6" value="Quantity"/>
                                </f:facet>
                            </h:column>
                        </h:dataTable>
                        <webuijsf:hyperlink binding="#{ShoppingCart.hyperlink1}" id="hyperlink1" style="position: absolute; left: 552px; top: 288px"
                            text="Checkout" url="/faces/Checkout.jsp"/>
                        <webuijsf:label binding="#{ShoppingCart.label1}" id="label1" style="left: 264px; top: 264px; position: absolute" text="Total:">
                            <h:outputText value="#{ShoppingCart.shoppingCart.getTotal}"/>
                        </webuijsf:label>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
