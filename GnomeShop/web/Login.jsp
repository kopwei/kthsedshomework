<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Login
    Created on : Dec 4, 2007, 11:15:17 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html"
    xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />
    <f:view>
        <webuijsf:page binding="#{Login.page1}" id="page1">
            <webuijsf:html binding="#{Login.html1}" id="html1">
                <webuijsf:head binding="#{Login.head1}" id="head1">
                    <webuijsf:link binding="#{Login.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Login.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Login.form1}" id="form1" style="color: #3366ff">
                        <h:commandButton action="#{Login.loginButton_action}" binding="#{Login.loginButton}" id="loginButton"
                            style="left: 384px; top: 192px; position: absolute; width: 50px" value="Login"/>
                        <div style="left: 0px; top: 0px; position: absolute">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="left: 0px; top: 72px; position: absolute">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="left: 216px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <webuijsf:textField binding="#{Login.userNameField}" id="userNameField" label="User Name" required="true" style="left: 240px; top: 96px; position: absolute"/>
                        <webuijsf:passwordField binding="#{Login.passwordField}" id="passwordField" label="Password" required="true" style="left: 240px; top: 144px; position: absolute"/>
                        <h:commandButton binding="#{Login.logoutButton}" id="logoutButton" style="left: 288px; top: 192px; position: absolute; width: 72px" value="Logout"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
