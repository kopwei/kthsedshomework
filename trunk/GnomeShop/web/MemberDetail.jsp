<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : MemberDetail
    Created on : Dec 6, 2007, 10:27:13 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{MemberDetail.page1}" id="page1">
            <webuijsf:html binding="#{MemberDetail.html1}" id="html1">
                <webuijsf:head binding="#{MemberDetail.head1}" id="head1">
                    <webuijsf:link binding="#{MemberDetail.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{MemberDetail.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{MemberDetail.form1}" id="form1">
                        <div style="position: absolute; left: 0px; top: 0px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="left: 216px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <webuijsf:staticText binding="#{MemberDetail.userNameText}" id="userNameText" style="left: 336px; top: 96px; position: absolute" text="#{MemberDetail.member.userName}"/>
                        <webuijsf:staticText binding="#{MemberDetail.firstNameText}" id="firstNameText" style="left: 336px; top: 120px; position: absolute" text="#{MemberDetail.member.firstName}"/>
                        <webuijsf:label binding="#{MemberDetail.label1}" id="label1" style="left: 240px; top: 96px; position: absolute" text="User Name"/>
                        <webuijsf:label binding="#{MemberDetail.label2}" id="label2" style="position: absolute; left: 240px; top: 120px" text="First Name"/>
                        <webuijsf:label binding="#{MemberDetail.label3}" id="label3" style="position: absolute; left: 240px; top: 144px" text="Last Name"/>
                        <webuijsf:staticText binding="#{MemberDetail.lastNameText}" id="lastNameText" style="position: absolute; left: 336px; top: 144px" text="#{MemberDetail.member.lastName}"/>
                        <webuijsf:label binding="#{MemberDetail.label4}" id="label4" style="position: absolute; left: 240px; top: 168px" text="Email Address"/>
                        <webuijsf:staticText binding="#{MemberDetail.emailText}" id="emailText" style="position: absolute; left: 336px; top: 168px" text="#{MemberDetail.member.email}"/>
                        <webuijsf:label binding="#{MemberDetail.label5}" id="label5" style="position: absolute; left: 240px; top: 192px" text="Telephone"/>
                        <webuijsf:staticText binding="#{MemberDetail.telephoneText}" id="telephoneText" style="position: absolute; left: 336px; top: 192px" text="#{MemberDetail.member.telephone}"/>
                        <webuijsf:label binding="#{MemberDetail.label6}" id="label6" style="position: absolute; left: 240px; top: 216px" text="Blocked"/>
                        <webuijsf:checkbox binding="#{MemberDetail.blockCheckbox}" id="blockCheckbox" readOnly="true" selected="#{MemberDetail.member.blocked}" style="position: absolute; left: 336px; top: 216px"/>
                        <webuijsf:button actionExpression="#{MemberDetail.blockButton_action}" binding="#{MemberDetail.blockButton}" id="blockButton"
                            rendered="#{!MemberDetail.member.blocked}" style="left: 336px; top: 264px; position: absolute; width: 50px" text="Block"/>
                        <webuijsf:button actionExpression="#{MemberDetail.unblockButton_action}" binding="#{MemberDetail.unblockButton}" id="unblockButton"
                            rendered="#{MemberDetail.member.blocked}" style="left: 336px; top: 264px; position: absolute; width: 50px" text="Unblock"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
