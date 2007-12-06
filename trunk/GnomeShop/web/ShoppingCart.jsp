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
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
