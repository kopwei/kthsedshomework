<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="CampAndNeg.aspx.cs" Inherits="AdvertConsultant.Director.CampAndNeg" MasterPageFile="../SiteMaster.master"%>

<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
<h2>CampAndNeg</h2>
<p>
    <asp:GridView ID="CampaignGridView" runat="server" AutoGenerateColumns="False" CellPadding="4"
        DataSourceID="CampaignData" GridLines="Horizontal" BackColor="White" BorderColor="#336666" BorderStyle="Double" BorderWidth="3px" AllowPaging="True" AllowSorting="True" DataKeyNames="CampaignID">
        <FooterStyle BackColor="White" ForeColor="#333333" />
        <Columns>
            <asp:BoundField DataField="CampaignName" HeaderText="CampaignName" SortExpression="CampaignName" />
            <asp:BoundField DataField="DirectorName" HeaderText="DirectorName" SortExpression="DirectorName" />
            <asp:BoundField DataField="ClientName" HeaderText="ClientName" SortExpression="ClientName" />
            <asp:CheckBoxField DataField="InNegotiation" HeaderText="InNegotiation" SortExpression="InNegotiation" />
            <asp:HyperLinkField DataNavigateUrlFields="CampaignID" DataNavigateUrlFormatString="CampaignDetail.aspx?CampaignID={0}"
                HeaderText="Details" Text="Details" />
        </Columns>
        <RowStyle BackColor="White" ForeColor="#333333" />
        <SelectedRowStyle BackColor="#339966" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="#336666" ForeColor="White" HorizontalAlign="Center" />
        <HeaderStyle BackColor="#336666" Font-Bold="True" ForeColor="White" />
    </asp:GridView>
    <asp:ObjectDataSource ID="CampaignData" runat="server" OldValuesParameterFormatString="original_{0}"
        SelectMethod="GetAllCampaignData" TypeName="AdvertConsultant.DataSets.CampaignTableAdapters.CampaignsTableAdapter" DeleteMethod="Delete" InsertMethod="Insert" UpdateMethod="Update">
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
    </p>
    <p>
    &nbsp;<br />
<br />
<br />
<br />
<br />
    </p>
</asp:content>