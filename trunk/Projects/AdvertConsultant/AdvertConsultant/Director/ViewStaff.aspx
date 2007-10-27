<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="ViewStaff.aspx.cs" Inherits="AdvertConsultant.Director.ViewStaff" MasterPageFile="../SiteMaster.master"%>

<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
<h2>Staffs</h2>
    <p>
        <asp:DropDownList ID="staffGroupList" runat="server" AutoPostBack="True" OnSelectedIndexChanged="staffGroupList_SelectedIndexChanged">
            <asp:ListItem>AllStaffs</asp:ListItem>
            <asp:ListItem>Available Staffs</asp:ListItem>
        </asp:DropDownList>&nbsp;</p>
    <asp:GridView ID="staffGridView" runat="server" AllowPaging="True" AllowSorting="True"
        AutoGenerateColumns="False" BackColor="LightGoldenrodYellow" BorderColor="Tan"
        BorderWidth="1px" CellPadding="2" DataKeyNames="StaffID" DataSourceID="StaffDataSource"
        ForeColor="Black" GridLines="None">
        <FooterStyle BackColor="Tan" />
        <Columns>
            <asp:BoundField DataField="StaffID" HeaderText="StaffID" ReadOnly="True" SortExpression="StaffID" >
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" >
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="DepartmentName" HeaderText="Department" SortExpression="Department" >
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:HyperLinkField DataNavigateUrlFields="StaffID" DataNavigateUrlFormatString="StaffDetail.aspx?StaffID={0}"
                HeaderText="Details" Text="Details" >
                <ItemStyle HorizontalAlign="Center" />
            </asp:HyperLinkField>
        </Columns>
        <SelectedRowStyle BackColor="DarkSlateBlue" ForeColor="GhostWhite" />
        <PagerStyle BackColor="PaleGoldenrod" ForeColor="DarkSlateBlue" HorizontalAlign="Center" />
        <HeaderStyle BackColor="Tan" Font-Bold="True" />
        <AlternatingRowStyle BackColor="PaleGoldenrod" />
    </asp:GridView>
    <asp:ObjectDataSource ID="StaffDataSource" runat="server" DeleteMethod="Delete" InsertMethod="Insert"
        OldValuesParameterFormatString="original_{0}" SelectMethod="GetAllStaffData"
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
    <asp:ObjectDataSource ID="AvailableStaffData" runat="server" DeleteMethod="Delete"
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
<br />
<br />
<br />
</asp:content>