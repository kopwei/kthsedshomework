<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : AddingIntoCartFailure
    Created on : 2007-12-8, 1:09:35
    Author     : Ricky
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{AddingIntoCartFailure.page1}" id="page1">
            <webuijsf:html binding="#{AddingIntoCartFailure.html1}" id="html1">
                <webuijsf:head binding="#{AddingIntoCartFailure.head1}" id="head1">
                    <webuijsf:link binding="#{AddingIntoCartFailure.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{AddingIntoCartFailure.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{AddingIntoCartFailure.form1}" id="form1">
                        <div style="height: 60px; left: 0px; top: 0px; position: absolute; width: 800px">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="height: 190px; left: 264px; top: 192px; position: absolute; width: 526px">
                            <table>
                                <tr>
                                    <td colspan="2" style="background-color: #FFFFF; text-align: Center">
                                        <font style="font-size: 14pt; color: #00000; font-weight: bold">The action of adding into your shopping cart failed.</font>
                                        <p></p>
                                        <font style="font-size: 14pt; font-weight: bold">Please check the quantity available and try again<a></a>
                                        </font>
                                    </td>
                                </tr>
                                <br/>
                            </table>
                            <br/>
                        </div>
                        <div style="left: 216px; top: 528px; position: absolute">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
