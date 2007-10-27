<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="CampAndNeg.aspx.cs" Inherits="AdvertConsultant.Director.CampAndNeg" MasterPageFile="../SiteMaster.master"%>

<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
<h2>CampAndNeg</h2>
<p>
    <asp:GridView ID="CampaignGridView" runat="server" AutoGenerateColumns="False" CellPadding="4"
        DataSourceID="ObjectDataSource1" GridLines="Horizontal" BackColor="White" BorderColor="#336666" BorderStyle="Double" BorderWidth="3px" AllowPaging="True" AllowSorting="True">
        <FooterStyle BackColor="White" ForeColor="#333333" />
        <Columns>
            <asp:BoundField DataField="CampaignName" HeaderText="CampaignName" SortExpression="CampaignName" />
            <asp:BoundField DataField="DirectorName" HeaderText="DirectorName" SortExpression="DirectorName" />
            <asp:BoundField DataField="ClientName" HeaderText="ClientName" SortExpression="ClientName" />
            <asp:BoundField DataField="TypeOfCampaign" HeaderText="TypeOfCampaign" SortExpression="TypeOfCampaign" />
            <asp:BoundField DataField="Budget" HeaderText="Budget" SortExpression="Budget" />
            <asp:BoundField DataField="StartTime" HeaderText="StartTime" SortExpression="StartTime" />
            <asp:BoundField DataField="EndTime" HeaderText="EndTime" SortExpression="EndTime" />
            <asp:CheckBoxField DataField="InNegotiation" HeaderText="InNegotiation" ReadOnly="True"
                SortExpression="InNegotiation" />
            <asp:HyperLinkField HeaderText="Details" NavigateUrl="/Director/CampaignDetail.aspx"
                Text="Details" />
        </Columns>
        <RowStyle BackColor="White" ForeColor="#333333" />
        <SelectedRowStyle BackColor="#339966" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="#336666" ForeColor="White" HorizontalAlign="Center" />
        <HeaderStyle BackColor="#336666" Font-Bold="True" ForeColor="White" />
    </asp:GridView>
    <asp:ObjectDataSource ID="ObjectDataSource1" runat="server" OldValuesParameterFormatString="original_{0}"
        SelectMethod="GetAllCampaignData" TypeName="AdvertConsultant.DataSets.CampaignTableAdapters.CampaignsTableAdapter">
    </asp:ObjectDataSource>
    &nbsp;</p>

<br />
<br />
<br />
<br />
<br />
</asp:content>