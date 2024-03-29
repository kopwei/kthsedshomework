<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : RegisterFailed
    Created on : Dec 8, 2007, 9:30:30 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{RegisterFailed.page1}" id="page1">
            <webuijsf:html binding="#{RegisterFailed.html1}" id="html1">
                <webuijsf:head binding="#{RegisterFailed.head1}" id="head1">
                    <webuijsf:link binding="#{RegisterFailed.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{RegisterFailed.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{RegisterFailed.form1}" id="form1">
                        <div style="left: 216px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <webuijsf:staticText binding="#{RegisterFailed.staticText1}" id="staticText1"
                            style="height: 22px; left: 264px; top: 168px; position: absolute; width: 94px" text="Register Failed"/>
                        <div style="left: 0px; top: 72px; position: absolute">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="left: 0px; top: 0px; position: absolute">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
