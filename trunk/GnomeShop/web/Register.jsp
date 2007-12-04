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
                        <webuijsf:label binding="#{Register.userNameLabel}" id="userNameLabel" style="left: 48px; top: 24px; position: absolute; width: 70px" text="User Name"/>
                        <webuijsf:textField binding="#{Register.userNameField}" id="userNameField" style="left: 120px; top: 24px; position: absolute"/>
                        <webuijsf:label binding="#{Register.passwordLabel}" id="passwordLabel" style="left: 48px; top: 72px; position: absolute; width: 70px" text="Password"/>
                        <webuijsf:passwordField binding="#{Register.passwordField}" id="passwordField" style="left: 120px; top: 72px; position: absolute"/>
                        <webuijsf:label binding="#{Register.firstNameLabel}" id="firstNameLabel" style="left: 48px; top: 120px; position: absolute; width: 70px" text="First Name"/>
                        <webuijsf:textField binding="#{Register.firstNameField}" id="firstNameField" style="left: 120px; top: 120px; position: absolute"/>
                        <webuijsf:label binding="#{Register.lastNameLabel}" id="lastNameLabel" style="left: 48px; top: 168px; position: absolute; width: 70px" text="Last Name"/>
                        <webuijsf:textField binding="#{Register.lastNameField}" id="lastNameField" style="left: 120px; top: 168px; position: absolute"/>
                        <webuijsf:label binding="#{Register.emailLabel}" for="emailField" id="emailLabel"
                            style="left: 48px; top: 216px; position: absolute; width: 72px" text="E-Mail"/>
                        <webuijsf:textField binding="#{Register.emailField}" id="emailField" style="left: 120px; top: 216px; position: absolute"/>
                        <webuijsf:label binding="#{Register.phoneLabel}" id="phoneLabel" style="position: absolute; left: 48px; top: 264px" text="Phone No"/>
                        <webuijsf:textField binding="#{Register.phoneField}" id="phoneField" style="position: absolute; left: 120px; top: 264px"/>
                        <h:commandButton action="#{Register.submitButton_action}" binding="#{Register.submitButton}" id="submitButton"
                            style="left: 192px; top: 312px; position: absolute" value="Submit"/>
                        <f:validator/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
