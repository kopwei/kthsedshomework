<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : AddProduct
    Created on : Dec 10, 2007, 7:19:13 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{AddProduct.page1}" id="page1">
            <webuijsf:html binding="#{AddProduct.html1}" id="html1">
                <webuijsf:head binding="#{AddProduct.head1}" id="head1">
                    <webuijsf:link binding="#{AddProduct.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{AddProduct.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{AddProduct.form1}" id="form1">
                        <div style="position: absolute; left: 0px; top: 0px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="position: absolute; left: 216px; top: 528px">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <table style="left: 240px; top: 120px; position: absolute">
                            <tr>
                                <td>Add Product</td>
                            </tr>
                            <tr>
                                <td>Gnome Name</td>
                                <td style="width: 147px">
                                    <webuijsf:textField binding="#{AddProduct.nameField}" id="nameField"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Gnome Price</td>
                                <td>
                                    <webuijsf:textField binding="#{AddProduct.priceField}" id="priceField"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="height: 36px">Gnome Description</td>
                                <td>
                                    <webuijsf:textArea binding="#{AddProduct.descriptionArea}" id="descriptionArea"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Quantity</td>
                                <td>
                                    <webuijsf:textField binding="#{AddProduct.quantityField}" id="quantityField"/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <h:commandButton action="#{AddProduct.addButton_action}" binding="#{AddProduct.addButton}" id="addButton" value="Add"/>
                                </td>
                            </tr>
                        </table>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
