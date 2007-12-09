<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : AdminPage
    Created on : Dec 6, 2007, 8:30:35 PM
    Author     : Kop
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page binding="#{AdminPage.page1}" id="page1">
            <webuijsf:html binding="#{AdminPage.html1}" id="html1">
                <webuijsf:head binding="#{AdminPage.head1}" id="head1">
                    <webuijsf:link binding="#{AdminPage.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{AdminPage.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{AdminPage.form1}" id="form1">
                        <div style="left: 0px; top: 0px; position: absolute">
                            <jsp:directive.include file="Header.jspf"/>
                        </div>
                        <div style="position: absolute; left: 0px; top: 72px">
                            <jsp:directive.include file="Search.jspf"/>
                        </div>
                        <div style="position: absolute; left: 216px; top: 528px">
                            <jsp:directive.include file="Footer.jspf"/>
                        </div>
                        <!--
                         <webuijsf:table augmentTitle="false" binding="#{AdminPage.memberTable}" id="memberTable"
                            style="height: 173px; left: 216px; top: 96px; position: absolute; width: 504px" title="Members" width="504">
                            <webuijsf:tableRowGroup binding="#{AdminPage.tableRowGroup1}" id="tableRowGroup1" rows="10"
                                sourceData="#{AdminPage.membersDataProvider}" sourceVar="currentRow">
                                <webuijsf:tableColumn binding="#{AdminPage.tableColumn4}" headerText="member_id" id="tableColumn4" rendered="false"
                                    sort="members.member_id" width="132">
                                    <webuijsf:staticText binding="#{AdminPage.staticText4}" id="staticText4" text="#{currentRow.value['members.member_id']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{AdminPage.userNameColumn}" headerText="User Name" id="userNameColumn" sort="members.username" width="127">
                                    <webuijsf:staticText binding="#{AdminPage.staticText5}" id="staticText5" text="#{currentRow.value['members.username']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{AdminPage.firstNameColumn}" headerText="First Name" id="firstNameColumn"
                                    sort="members.first_name" width="127">
                                    <webuijsf:staticText binding="#{AdminPage.staticText8}" id="staticText8" text="#{currentRow.value['members.first_name']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{AdminPage.lastNameColumn}" headerText="Last Name" id="lastNameColumn" sort="members.last_name" width="127">
                                    <webuijsf:staticText binding="#{AdminPage.staticText9}" id="staticText9" text="#{currentRow.value['members.last_name']}"/>
                                </webuijsf:tableColumn>
                                <webuijsf:tableColumn binding="#{AdminPage.detailColumn}" headerText="Details" id="detailColumn" width="80">
                                    <webuijsf:hyperlink actionExpression="MemberDetails" text="Details" url="/faces/MemberDetail.jsp">
                                        <f:param name="memberid" value="#{currentRow.value['members.member_id']}"/>
                                    </webuijsf:hyperlink>
                                </webuijsf:tableColumn>
                            </webuijsf:tableRowGroup>
                        </webuijsf:table> -->
                        <div style="height: 310px; left: 216px; top: 96px; position: absolute; width: 430px">
                            <table>
                                <tr>
                                    <td colspan="2" style="background-color: #336699; text-align: Center; border-style: outset; border-width: 1">
                                        <a name="Results">
                                            <font style="font-size: 12pt; color: #FFFFFF; font-weight: bold">All Members</font>
                                        </a>
                                    </td>
                                </tr>
                                <br/>
                                <h:form>
                                    <h:dataTable border="1" value="#{AdminPage.users}" var="member">
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>User Name</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{member.userName}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>First name</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{member.firstName}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Last Name</f:verbatim>
                                            </f:facet>
                                            <h:outputText value="#{member.lastName}"/>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <f:verbatim>Details</f:verbatim>
                                            </f:facet>
                                            <webuijsf:hyperlink binding="#{MemberDetail.unblockHyperlink}" id="removeHyperlink" text="Detail" url="MemberDetail.jsp">
                                                <f:param name="memberid" value="#{member.memberId}"/>
                                            </webuijsf:hyperlink>
                                        </h:column>
                                    </h:dataTable>
                                </h:form>
                            </table>
                        </div>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
