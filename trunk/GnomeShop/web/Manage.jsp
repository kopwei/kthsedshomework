<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : ManageGnomePage
    Created on : Dec 6, 2007, 8:31:27 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    
    <f:view>
        <webuijsf:page binding="#{Manage.page1}" id="page1">
            <webuijsf:html binding="#{Manage.html1}" id="html1">
                <webuijsf:head binding="#{Manage.head1}" id="head1">
                    <webuijsf:link binding="#{Manage.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Manage.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Manage.form1}" id="form1">
                        <div style="left: 0px; top: 0px; position: absolute">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="left: 216px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <table style="left: 240px; top: 96px; position: absolute">
                            <tr>
                                <td>Gnome Name</td>
                                <td>
                                    <webuijsf:textField binding="#{Manage.nameField}" id="nameField"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Gnome Price</td>
                                <td>
                                    <webuijsf:textField binding="#{Manage.priceField}" id="priceField"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Gnome Description</td>
                                <td>
                                    <webuijsf:textArea binding="#{Manage.descriptionArea}" id="descriptionArea"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Quantity</td>
                                <td>
                                    <webuijsf:textField binding="#{Manage.quantityField}" id="quantityField"/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <webuijsf:hyperlink actionExpression="#{Manage.addHyperlink_action}" binding="#{Manage.addHyperlink}" id="addHyperlink"
                                        style="color: rgb(0, 0, 204);text-decoration: underline" text="Add"/>
                                </td>
                            </tr>
                        </table>
                        <webuijsf:table augmentTitle="false" binding="#{Manage.table1}" id="table1"
                            style="height: 101px; left: 240px; top: 240px; position: absolute; width: 408px" title="Gnomes" width="408">
                            <webuijsf:tableRowGroup binding="#{Manage.tableRowGroup1}" id="tableRowGroup1" rows="10" sourceData="#{Manage.productsDataProvider}" sourceVar="currentRow">
                                <webuijsf:tableColumn binding="#{Manage.tableColumn6}" headerText="name" id="tableColumn6" sort="products.name">
                                    <webuijsf:staticText binding="#{Manage.staticText7}" id="staticText7" text="#{currentRow.value['products.name']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{Manage.tableColumn8}" headerText="Price" id="tableColumn8" sort="products.Price">
                                    <webuijsf:staticText binding="#{Manage.staticText9}" id="staticText9" text="#{currentRow.value['products.Price']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{Manage.tableColumn9}" headerText="Quantity" id="tableColumn9" sort="products.Quantity" width="43">
                                    <webuijsf:staticText binding="#{Manage.staticText10}" id="staticText10" text="#{currentRow.value['products.Quantity']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{Manage.removeColumn}" headerText="Remove" id="removeColumn" width="64">
                                    <webuijsf:hyperlink binding="#{MemberDetail.unblockHyperlink}" id="removeHyperlink" text="Remove" url="/faces/Manage.jsp">
                                        <f:param name="productid" value="#{currentRow.value['products.ProductId']}"/>
                                        <f:param name="remove" value="true"/>
                                    </webuijsf:hyperlink>
                                </webuijsf:tableColumn>
                            </webuijsf:tableRowGroup>
                        </webuijsf:table>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
