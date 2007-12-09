<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : SubmissionFail
    Created on : 2007-12-8, 18:54:07
    Author     : Ricky
-->

<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{SubmissionFail.page1}" id="page1">
            <webuijsf:html binding="#{SubmissionFail.html1}" id="html1">
                <webuijsf:head binding="#{SubmissionFail.head1}" id="head1">
                    <webuijsf:link binding="#{SubmissionFail.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{SubmissionFail.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{SubmissionFail.form1}" id="form1">
                        <div style="height: 60px; left: 0px; top: 0px; position: absolute; width: 800px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="height: 430px; left: 0px; top: 72px; position: absolute; width: 800px">
                            <table>
                                <tr>
                                    <td colspan="2" style="background-color: #FFFFF; text-align: Center">
                                        <font style="font-size: 14pt; color: #00000; font-weight: bold">The action of submission failed.</font>
                                        <p/>
                                        <font style="font-size: 14pt; font-weight: bold">Please <a href="Checkout.faces">try again</a></font>
                                    </td>
                                </tr>
                                <br/>
                            </table>
                            <br/>
                        </div>
                        <div style="left: 48px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>