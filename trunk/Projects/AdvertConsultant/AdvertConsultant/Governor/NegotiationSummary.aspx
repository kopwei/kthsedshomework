<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="NegotiationSummary.aspx.cs" Inherits="AdvertConsultant.Governor.NegotiationSummary" MasterPageFile="../SiteMaster.master"%>

<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
<br />
    &nbsp;Negotiation Summary<br />
<br />
    <asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="False" BackColor="White"
        BorderColor="#CC9966" BorderStyle="None" BorderWidth="1px" CellPadding="4" DataKeyNames="CampaignID"
        DataSourceID="NegotiationDataSource1">
        <FooterStyle BackColor="#FFFFCC" ForeColor="#330099" />
        <Columns>
            <asp:BoundField DataField="CampaignID" HeaderText="CampaignID" ReadOnly="True" SortExpression="CampaignID" />
            <asp:BoundField DataField="CampaignName" HeaderText="CampaignName" SortExpression="CampaignName" />
            <asp:BoundField DataField="DirectorName" HeaderText="DirectorName" SortExpression="DirectorName" />
            <asp:BoundField DataField="ClientName" HeaderText="ClientName" SortExpression="ClientName" />
            <asp:BoundField DataField="ClientContact" HeaderText="ClientContact" SortExpression="ClientContact" />
            <asp:BoundField DataField="TypeOfCampaign" HeaderText="TypeOfCampaign" SortExpression="TypeOfCampaign" />
            <asp:BoundField DataField="Budget" HeaderText="Budget" SortExpression="Budget" />
            <asp:BoundField DataField="StartTime" HeaderText="StartTime" SortExpression="StartTime" />
            <asp:BoundField DataField="EndTime" HeaderText="EndTime" SortExpression="EndTime" />
        </Columns>
        <RowStyle BackColor="White" ForeColor="#330099" />
        <SelectedRowStyle BackColor="#FFCC66" Font-Bold="True" ForeColor="#663399" />
        <PagerStyle BackColor="#FFFFCC" ForeColor="#330099" HorizontalAlign="Center" />
        <HeaderStyle BackColor="#990000" Font-Bold="True" ForeColor="#FFFFCC" />
    </asp:GridView>
    <asp:ObjectDataSource ID="NegotiationDataSource1" runat="server" DeleteMethod="Delete"
        InsertMethod="Insert" OldValuesParameterFormatString="original_{0}" SelectMethod="GetNegotiationSummaryDataByInNegotiation"
        TypeName="AdvertConsultant.DataSets.CampaignTableAdapters.CampaignsTableAdapter"
        UpdateMethod="Update">
        <DeleteParameters>
            <asp:Parameter Name="Original_CampaignID" Type="Int32" />
        </DeleteParameters>
        <UpdateParameters>
            <asp:Parameter Name="CampaignID" Type="Int32" />
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
        <InsertParameters>
            <asp:Parameter Name="CampaignID" Type="Int32" />
            <asp:Parameter Name="CampaignName" Type="String" />
            <asp:Parameter Name="DirectorName" Type="String" />
            <asp:Parameter Name="ClientName" Type="String" />
            <asp:Parameter Name="ClientContact" Type="Decimal" />
            <asp:Parameter Name="TypeOfCampaign" Type="String" />
            <asp:Parameter Name="Budget" Type="Decimal" />
            <asp:Parameter Name="StartTime" Type="DateTime" />
            <asp:Parameter Name="EndTime" Type="DateTime" />
            <asp:Parameter Name="InNegotiation" Type="Boolean" />
        </InsertParameters>
    </asp:ObjectDataSource>
<br />
<br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
</asp:content>