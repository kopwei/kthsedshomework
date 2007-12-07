<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : ProductDetail
    Created on : Dec 7, 2007, 10:14:33 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{ProductDetail.page1}" id="page1">
            <webuijsf:html binding="#{ProductDetail.html1}" id="html1">
                <webuijsf:head binding="#{ProductDetail.head1}" id="head1">
                    <webuijsf:link binding="#{ProductDetail.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{ProductDetail.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{ProductDetail.form1}" id="form1">
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
