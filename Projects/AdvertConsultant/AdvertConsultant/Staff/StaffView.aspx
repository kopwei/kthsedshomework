<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="StaffView.aspx.cs" Inherits="AdvertConsultant.Members.StaffView" MasterPageFile="~/SiteMaster.master"%>

<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
<br />
    <asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="False" DataKeyNames="ApplicationId"
        DataSourceID="SqlDataSource1">
        <Columns>
            <asp:BoundField DataField="ApplicationName" HeaderText="ApplicationName" SortExpression="ApplicationName" />
            <asp:BoundField DataField="ApplicationId" HeaderText="ApplicationId" ReadOnly="True"
                SortExpression="ApplicationId" />
            <asp:BoundField DataField="Description" HeaderText="Description" SortExpression="Description" />
        </Columns>
    </asp:GridView>
    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        SelectCommand="SELECT [ApplicationName], [ApplicationId], [Description] FROM [vw_aspnet_Applications]">
    </asp:SqlDataSource>

</asp:content>