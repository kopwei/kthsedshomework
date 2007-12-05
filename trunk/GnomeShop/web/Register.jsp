<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Register
    Created on : Dec 4, 2007, 11:01:53 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{Register.page1}" id="page1">
            <webuijsf:html binding="#{Register.html1}" id="html1">
                <webuijsf:head binding="#{Register.head1}" id="head1">
                    <webuijsf:link binding="#{Register.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{Register.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{Register.form1}" id="form1">
                        <webuijsf:label binding="#{Register.userNameLabel}" id="userNameLabel" style="left: 264px; top: 96px; position: absolute; width: 70px" text="User Name"/>
                        <webuijsf:textField binding="#{Register.userNameField}" id="userNameField" style="left: 336px; top: 96px; position: absolute"/>
                        <webuijsf:label binding="#{Register.passwordLabel}" id="passwordLabel" style="left: 264px; top: 144px; position: absolute; width: 70px" text="Password"/>
                        <webuijsf:passwordField binding="#{Register.passwordField}" id="passwordField" style="left: 336px; top: 144px; position: absolute"/>
                        <webuijsf:label binding="#{Register.firstNameLabel}" id="firstNameLabel"
                            style="left: 264px; top: 192px; position: absolute; width: 70px" text="First Name"/>
                        <webuijsf:textField binding="#{Register.firstNameField}" id="firstNameField" style="left: 336px; top: 192px; position: absolute"/>
                        <webuijsf:label binding="#{Register.lastNameLabel}" id="lastNameLabel" style="left: 264px; top: 240px; position: absolute; width: 70px" text="Last Name"/>
                        <webuijsf:textField binding="#{Register.lastNameField}" id="lastNameField" style="left: 336px; top: 240px; position: absolute"/>
                        <webuijsf:label binding="#{Register.emailLabel}" for="emailField" id="emailLabel"
                            style="left: 264px; top: 288px; position: absolute; width: 72px" text="E-Mail"/>
                        <webuijsf:textField binding="#{Register.emailField}" id="emailField" style="left: 336px; top: 288px; position: absolute"/>
                        <webuijsf:label binding="#{Register.phoneLabel}" id="phoneLabel" style="left: 264px; top: 336px; position: absolute" text="Phone No"/>
                        <webuijsf:textField binding="#{Register.phoneField}" id="phoneField" style="left: 336px; top: 336px; position: absolute"/>
                        <h:commandButton action="#{Register.submitButton_action}" binding="#{Register.submitButton}" id="submitButton"
                            style="left: 408px; top: 384px; position: absolute" value="Submit"/>
                        <f:validator/>
                        <div style="left: 0px; top: 0px; position: absolute">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="left: 0px; top: 72px; position: absolute">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="left: 216px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
