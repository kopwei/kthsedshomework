<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="StaffUtilization.aspx.cs" Inherits="AdvertConsultant.Governor.StaffUtilization" MasterPageFile="../SiteMaster.master"%>

<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
    <br />
    &nbsp;Staff Utilization<br />
    <br />
    &nbsp;<asp:DropDownList ID="StaffUtilizationList" runat="server" OnSelectedIndexChanged="DropDownList1_SelectedIndexChanged" Width="105px" AutoPostBack="True">
            <asp:ListItem>In Campaigns</asp:ListItem>
            <asp:ListItem>Free Staff</asp:ListItem>
        </asp:DropDownList><br />
<br />
    <asp:GridView ID="GridView1" runat="server" DataKeyNames="StaffID"
        DataSourceID="FreeStaffsDataSource" AutoGenerateColumns="False">
        <Columns>
            <asp:BoundField DataField="StaffID" HeaderText="StaffID" ReadOnly="True" SortExpression="StaffID" />
            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" />
            <asp:BoundField DataField="CampaignID" HeaderText="CampaignID" SortExpression="CampaignID" />
            <asp:BoundField DataField="OutofOfficeHour" HeaderText="OutofOfficeHour" SortExpression="OutofOfficeHour" />
            <asp:BoundField DataField="DepartmentName" HeaderText="DepartmentName" SortExpression="DepartmentName" />
        </Columns>
    </asp:GridView>
    <asp:ObjectDataSource ID="FreeStaffsDataSource" runat="server" DeleteMethod="Delete"
        InsertMethod="Insert" OldValuesParameterFormatString="original_{0}" SelectMethod="GetAvailableStaff"
        TypeName="AdvertConsultant.DataSets.StaffTableAdapters.StaffsTableAdapter" UpdateMethod="Update">
        <DeleteParameters>
            <asp:Parameter Name="Original_StaffID" Type="Int32" />
        </DeleteParameters>
        <UpdateParameters>
            <asp:Parameter Name="StaffID" Type="Int32" />
            <asp:Parameter Name="Name" Type="String" />
            <asp:Parameter Name="CampaignID" Type="Int32" />
            <asp:Parameter Name="OutofOfficeHour" Type="Int32" />
            <asp:Parameter Name="DepartmentName" Type="String" />
            <asp:Parameter Name="Original_StaffID" Type="Int32" />
        </UpdateParameters>
        <InsertParameters>
            <asp:Parameter Name="StaffID" Type="Int32" />
            <asp:Parameter Name="Name" Type="String" />
            <asp:Parameter Name="CampaignID" Type="Int32" />
            <asp:Parameter Name="OutofOfficeHour" Type="Int32" />
            <asp:Parameter Name="DepartmentName" Type="String" />
        </InsertParameters>
    </asp:ObjectDataSource>
    <asp:ObjectDataSource ID="InCampaignStaffDataSource" runat="server" DeleteMethod="Delete"
        InsertMethod="Insert" OldValuesParameterFormatString="original_{0}" SelectMethod="GetInCampaignStaffsDataByCampaignID"
        TypeName="AdvertConsultant.DataSets.StaffTableAdapters.StaffsTableAdapter" UpdateMethod="Update">
        <DeleteParameters>
            <asp:Parameter Name="Original_StaffID" Type="Int32" />
        </DeleteParameters>
        <UpdateParameters>
            <asp:Parameter Name="StaffID" Type="Int32" />
            <asp:Parameter Name="Name" Type="String" />
            <asp:Parameter Name="CampaignID" Type="Int32" />
            <asp:Parameter Name="OutofOfficeHour" Type="Int32" />
            <asp:Parameter Name="DepartmentName" Type="String" />
            <asp:Parameter Name="Original_StaffID" Type="Int32" />
        </UpdateParameters>
        <InsertParameters>
            <asp:Parameter Name="StaffID" Type="Int32" />
            <asp:Parameter Name="Name" Type="String" />
            <asp:Parameter Name="CampaignID" Type="Int32" />
            <asp:Parameter Name="OutofOfficeHour" Type="Int32" />
            <asp:Parameter Name="DepartmentName" Type="String" />
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
    <br />
</asp:content>