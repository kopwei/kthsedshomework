<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Browse
    Created on : Dec 9, 2007, 4:36:27 AM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{Browse.page1}" id="page1">
            <webuijsf:html binding="#{Browse.html1}" id="html1">
                <webuijsf:head binding="#{Browse.head1}" id="head1">
                    <webuijsf:link binding="#{Browse.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Browse.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Browse.form1}" id="form1">
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
                                            <font style="font-size: 12pt; color: #FFFFFF; font-weight: bold">All Products</font>
                                        </a>
                                    </td>
                                </tr>
                                <br/>
                                <h:form>
                                    <h:dataTable border="1" value="#{Browse.products}" var="productBean">
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Product Name</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{productBean.name}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Price</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{productBean.price}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Detail</f:verbatim>
                                            </f:facet>
                                            <webuijsf:hyperlink text="Detail" url="/faces/GnomeDetail.jsp">
                                                <f:param name="productid" value="#{productBean.id}"/>
                                            </webuijsf:hyperlink>
                                        </h:column>
                                    </h:dataTable>
                                </h:form>
                            </table>
                            <br/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
