<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeBehind="StaffDetail.aspx.cs" Inherits="AdvertConsultant.Director.StaffDetail" Title="Untitled Page" %>
<asp:Content ID="Content1" ContentPlaceHolderID="PageContent" runat="server">
    <br />
    <br />
    <asp:DetailsView ID="StaffDetailsView" runat="server" AutoGenerateRows="False" CellPadding="4"
        DataKeyNames="StaffID" DataSourceID="StaffDataSource" ForeColor="#333333" GridLines="None"
        Height="50px" Width="223px">
        <FooterStyle BackColor="#990000" Font-Bold="True" ForeColor="White" />
        <CommandRowStyle BackColor="#FFFFC0" Font-Bold="True" />
        <RowStyle BackColor="#FFFBD6" ForeColor="#333333" />
        <PagerStyle BackColor="#FFCC66" ForeColor="#333333" HorizontalAlign="Center" />
        <Fields>
            <asp:BoundField DataField="StaffID" HeaderText="StaffID" ReadOnly="True" SortExpression="StaffID" />
            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" />
            <asp:BoundField DataField="CampaignID" HeaderText="CampaignID" SortExpression="CampaignID" />
            <asp:BoundField DataField="OutofOfficeHour" HeaderText="OutofOfficeHour" SortExpression="OutofOfficeHour" />
            <asp:BoundField DataField="DepartmentName" HeaderText="DepartmentName" SortExpression="DepartmentName" />
        </Fields>
        <FieldHeaderStyle BackColor="#FFFF99" Font-Bold="True" />
        <HeaderStyle BackColor="#990000" Font-Bold="True" ForeColor="White" />
        <AlternatingRowStyle BackColor="White" />
    </asp:DetailsView>
    &nbsp;&nbsp;
    <asp:ObjectDataSource ID="StaffDataSource" runat="server" DeleteMethod="Delete" InsertMethod="Insert"
        OldValuesParameterFormatString="original_{0}" SelectMethod="GetStaffDataByID"
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
        <SelectParameters>
            <asp:QueryStringParameter Name="StaffID" QueryStringField="StaffID" Type="Int32" />
        </SelectParameters>
    </asp:ObjectDataSource>
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<br />
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;<br />
    Staff Skills<br />
    <br />
    <asp:ListBox ID="StaffSkillBox" runat="server" Width="220px"></asp:ListBox><br />
    <br />
    <asp:Button ID="assignStaffButton" runat="server" OnClick="assignStaffButton_Click"
        Text="Assign the Staff to.." /><br />
    <br />
    <asp:DropDownList ID="CampaignList" runat="server" DataSourceID="CampaignData"
        DataTextField="CampaignName" DataValueField="CampaignName" Width="172px" Visible="False">
        <asp:ListItem>None</asp:ListItem>
    </asp:DropDownList>&nbsp;
    <asp:Button ID="buttonCommitAssignment" runat="server" Enabled="False" OnClick="Button1_Click"
        Text="OK" Visible="False" /><br />
    <br />
    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        SelectCommand="SELECT [CampaignID] FROM [Campaigns] WHERE ([CampaignName] = @CampaignName)">
        <SelectParameters>
            <asp:ControlParameter ControlID="CampaignList" Name="CampaignName" PropertyName="SelectedValue"
                Type="String" />
        </SelectParameters>
    </asp:SqlDataSource>
    &nbsp;
    <asp:ListBox ID="ListBox1" runat="server" DataSourceID="SqlDataSource1" DataTextField="CampaignID"
        DataValueField="CampaignID" Height="25px" Visible="False" Width="184px"></asp:ListBox><br />
    <asp:ObjectDataSource ID="CampaignData"
        runat="server" OldValuesParameterFormatString="original_{0}"
        SelectMethod="GetCampaignNamesByState" TypeName="AdvertConsultant.DataSets.CampaignTableAdapters.CampaignNamesTableAdapter">
    </asp:ObjectDataSource>
    <br />
</asp:Content>
