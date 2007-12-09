<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : OrderDetail
    Created on : Dec 9, 2007, 3:54:19 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{OrderDetail.page1}" id="page1">
            <webuijsf:html binding="#{OrderDetail.html1}" id="html1">
                <webuijsf:head binding="#{OrderDetail.head1}" id="head1">
                    <webuijsf:link binding="#{OrderDetail.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{OrderDetail.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{OrderDetail.form1}" id="form1">
                        <div style="position: absolute; left: 0px; top: 0px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="position: absolute; left: 216px; top: 528px">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <div style="height: 310px; left: 216px; top: 96px; position: absolute; width: 430px">
                            <table>
                                <tr>
                                    <td colspan="2" style="background-color: #336699; text-align: Center; border-style: outset; border-width: 1">
                                        <a name="Results">
                                            <font style="font-size: 12pt; color: #FFFFFF; font-weight: bold">Order Details</font>
                                        </a>
                                    </td>
                                </tr>
                                <br/>
                                <h:form>
                                    <h:dataTable border="1" value="#{OrderDetail.currentOrderDetail}" var="shoppingItem">
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Product Name</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{shoppingItem.productName}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Price</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{shoppingItem.price}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Quantity</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{shoppingItem.quantity}"/>
                                        </h:column>
                                    </h:dataTable>
                                </h:form>
                                <tr>
                                    <td></td>
                                    <td><h:outputText title= "Total Price" value="#{OrderDetail.totalPrice}"/></td>
                                </tr>
                            </table>
                            <br/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
