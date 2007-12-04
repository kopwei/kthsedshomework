<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Login
    Created on : Dec 4, 2007, 11:15:17 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{Login.page1}" id="page1">
            <webuijsf:html binding="#{Login.html1}" id="html1">
                <webuijsf:head binding="#{Login.head1}" id="head1">
                    <webuijsf:link binding="#{Login.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Login.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Login.form1}" id="form1">
                        <webuijsf:label binding="#{Login.userNameLabel}" id="userNameLabel" style="position: absolute; left: 24px; top: 24px" text="User Name"/>
                        <webuijsf:textField binding="#{Login.userNameField}" id="userNameField" style="left: 96px; top: 24px; position: absolute"/>
                        <webuijsf:label binding="#{Login.passwordLabel}" id="passwordLabel" style="position: absolute; left: 24px; top: 72px" text="Password"/>
                        <webuijsf:passwordField binding="#{Login.passwordField}" id="passwordField" style="position: absolute; left: 96px; top: 72px"/>
                        <webuijsf:button binding="#{Login.button1}" id="button1" style="left: 167px; top: 120px; position: absolute" text="Login"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
