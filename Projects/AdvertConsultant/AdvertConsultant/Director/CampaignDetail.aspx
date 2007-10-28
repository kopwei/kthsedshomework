<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeBehind="CampaignDetail.aspx.cs" Inherits="AdvertConsultant.Director.CampaignDetail" Title="Untitled Page" %>

<%@ Register Assembly="Microsoft.ReportViewer.WebForms, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a"
    Namespace="Microsoft.Reporting.WebForms" TagPrefix="rsweb" %>

<%@ Register Assembly="Microsoft.ReportViewer.WebForms, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a"
    Namespace="Microsoft.Reporting.WebForms" TagPrefix="rsweb" %>

<%@ Register Assembly="Microsoft.ReportViewer.WebForms, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a"
    Namespace="Microsoft.Reporting.WebForms" TagPrefix="rsweb" %>
<asp:Content ID="Content1" ContentPlaceHolderID="PageContent" runat="server">
    &nbsp;<br />
    <br />
    <asp:DetailsView ID="CampaignDetailsView" runat="server" AutoGenerateRows="False" BackColor="White"
        BorderColor="#336666" BorderStyle="Double" BorderWidth="3px" CellPadding="4"
        DataKeyNames="CampaignID" DataSourceID="CampaignDataSource" GridLines="Horizontal"
        Height="50px" Width="344px">
        <FooterStyle BackColor="White" ForeColor="#333333" />
        <EditRowStyle BackColor="#339966" Font-Bold="True" ForeColor="White" />
        <RowStyle BackColor="White" ForeColor="#333333" />
        <PagerStyle BackColor="#336666" ForeColor="White" HorizontalAlign="Center" />
        <Fields>
            <asp:BoundField DataField="CampaignID" HeaderText="CampaignID" ReadOnly="True" SortExpression="CampaignID" />
            <asp:BoundField DataField="CampaignName" HeaderText="CampaignName" SortExpression="CampaignName" />
            <asp:BoundField DataField="DirectorName" HeaderText="DirectorName" SortExpression="DirectorName" />
            <asp:BoundField DataField="ClientName" HeaderText="ClientName" SortExpression="ClientName" />
            <asp:BoundField DataField="ClientContact" HeaderText="ClientContact" SortExpression="ClientContact" />
            <asp:BoundField DataField="TypeOfCampaign" HeaderText="TypeOfCampaign" SortExpression="TypeOfCampaign" />
            <asp:BoundField DataField="Budget" HeaderText="Budget" SortExpression="Budget" />
            <asp:BoundField DataField="StartTime" HeaderText="StartTime" SortExpression="StartTime" />
            <asp:BoundField DataField="EndTime" HeaderText="EndTime" SortExpression="EndTime" />
            <asp:CheckBoxField DataField="InNegotiation" HeaderText="InNegotiation" SortExpression="InNegotiation" />
            <asp:CommandField ShowDeleteButton="True" ShowEditButton="True" />
        </Fields>
        <HeaderStyle BackColor="#336666" Font-Bold="True" ForeColor="White" />
    </asp:DetailsView>
    <asp:ObjectDataSource ID="CampaignDataSource" runat="server" DeleteMethod="Delete" OldValuesParameterFormatString="original_{0}" SelectMethod="GetCampaignDataByID"
        TypeName="AdvertConsultant.DataSets.CampaignTableAdapters.CampaignsTableAdapter"
        UpdateMethod="Update">
        <DeleteParameters>
            <asp:Parameter Name="Original_CampaignID" Type="Int32" />
        </DeleteParameters>
        <UpdateParameters>
            <asp:Parameter Name="CampaignName" Type="String" />
            <asp:Parameter Name="DirectorName" Type="String" />
            <asp:Parameter Name="ClientName" Type="String" />
            <asp:Parameter Name="ClientContact" Type="Decimal" />
            <asp:Parameter Name="TypeOfCampaign" Type="String" />
            <asp:Parameter Name="Budget" Type="Decimal" />
            <asp:Parameter Name="StartTime" Type="DateTime" />
            <asp:Parameter Name="EndTime" Type="DateTime" />
            <asp:Parameter Name="InNegotiation" Type="Boolean" />
            <asp:Parameter Name="Original_CampaignID" Type="Int32" />
        </UpdateParameters>
        <SelectParameters>
            <asp:QueryStringParameter Name="CampaignID" QueryStringField="CampaignID" Type="Int32" />
        </SelectParameters>
    </asp:ObjectDataSource>
    &nbsp;&nbsp;
    <br />
    <br />
    Staffs in this campaign<br />
    <br />
    &nbsp;
    <asp:ListBox ID="ListBox1" runat="server" DataSourceID="CampaignStaffDataSource"
        DataTextField="Name" DataValueField="Name" Width="330px"></asp:ListBox>
    <asp:ObjectDataSource ID="CampaignStaffDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
        SelectMethod="GetStaffNameByCampaign" TypeName="AdvertConsultant.DataSets.StaffTableAdapters.StaffNameTableAdapter">
        <SelectParameters>
            <asp:QueryStringParameter Name="CampaignID" QueryStringField="CampaignID" Type="Int32" />
        </SelectParameters>
    </asp:ObjectDataSource>
</asp:Content>
