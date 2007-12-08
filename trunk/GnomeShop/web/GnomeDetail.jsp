<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : GnomeDetail
    Created on : 2007-12-6, 20:11:44
    Author     : Ricky
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{GnomeDetail.page1}" id="page1">
            <webuijsf:html binding="#{GnomeDetail.html1}" id="html1">
                <webuijsf:head binding="#{GnomeDetail.head1}" id="head1">
                    <webuijsf:link binding="#{GnomeDetail.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{GnomeDetail.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{GnomeDetail.form1}" id="form1">
                        <div style="left: 72px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <div style="height: 430px; left: 0px; top: 72px; position: absolute; width: 800px">
                            <table>
                                <tr>
                                    <td colspan="2" style="background-color: #336699; text-align: Center; border-style: outset; border-width: 1">
                                        <a name="Details">
                                            <font style="font-size: 12pt; color: #FFFFFF; font-weight: bold">Gnome Detail</font>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color: #FFFFFF; border-style: inset; border-width: 0">
                                        <font style="font-size: 10pt; color: #CE7E00; font-weight: bold">Product Name</font>
                                    </td>
                                    <td style="background-color: #FFFFFF; border-width: 1">
                                        <h:outputText style="font-size: 10pt; color: #00000" value="#{GnomeDetail.productBean.name}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color: #FFFFFF; border-style: inset; border-width: 0">
                                        <font style="font-size: 10pt; color: #CE7E00; font-weight: bold">Price</font>
                                    </td>
                                    <td style="background-color: #FFFFFF; border-width: 1">
                                        <h:outputText style="font-size: 10pt; color: #00000" value="#{GnomeDetail.productBean.price}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color: #FFFFFF; border-style: inset; border-width: 0">
                                        <font style="font-size: 10pt; color: #CE7E00; font-weight: bold">Quantity</font>
                                    </td>
                                    <td style="background-color: #FFFFFF; border-width: 1">
                                        <h:outputText style="font-size: 10pt; color: #00000" value="#{GnomeDetail.productBean.quantity}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color: #FFFFFF; border-style: inset; border-width: 0">
                                        <font style="font-size: 10pt; color: #CE7E00; font-weight: bold">Description</font>
                                    </td>
                                    <td style="background-color: #FFFFFF; border-width: 1">
                                        <h:outputText style="font-size: 10pt; color: #00000" value="#{GnomeDetail.productBean.description}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align: Center">
                                        <a name="Previous">
                                            <font style="font-size: 10pt; color: #CCCCC; font-weight: bold">Previous</font>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                            <h:commandButton action="#{GnomeDetail.addIntoCartButton_action}" binding="#{GnomeDetail.addIntoCartButton}" id="addIntoCartButton"
                                style="color: blue; font-family: 'Times New Roman',Times,serif; font-size: 12px; font-style: normal; height: 24px; text-align: center; text-decoration: underline; width: 119px"
                                text="Add into My Cart" value="Check out"/>
                        </div>
                        <div style="height: 60px; left: 1px; top: 0px; position: absolute; width: 800px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
