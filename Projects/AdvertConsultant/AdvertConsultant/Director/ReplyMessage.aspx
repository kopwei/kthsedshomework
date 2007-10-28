<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeBehind="ReplyMessage.aspx.cs" Inherits="AdvertConsultant.Director.ReplyMessage" Title="Untitled Page" %>
<asp:Content ID="Content1" ContentPlaceHolderID="PageContent" runat="server">
    <br />
    <br />
    <asp:DetailsView ID="staffDetailsView" runat="server" AutoGenerateRows="False" BackColor="White"
        BorderColor="#999999" BorderStyle="None" BorderWidth="1px" CellPadding="3" DataKeyNames="StaffID"
        DataSourceID="staffDataSource" GridLines="Vertical" Height="50px" Width="125px">
        <FooterStyle BackColor="#CCCCCC" ForeColor="Black" />
        <EditRowStyle BackColor="#008A8C" Font-Bold="True" ForeColor="White" />
        <RowStyle BackColor="#EEEEEE" ForeColor="Black" />
        <PagerStyle BackColor="#999999" ForeColor="Black" HorizontalAlign="Center" />
        <Fields>
            <asp:BoundField DataField="StaffID" HeaderText="StaffID" ReadOnly="True" SortExpression="StaffID" />
            <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" />
            <asp:BoundField DataField="CampaignID" HeaderText="CampaignID" SortExpression="CampaignID" />
            <asp:BoundField DataField="OutofOfficeHour" HeaderText="OutofOfficeHour" SortExpression="OutofOfficeHour" />
            <asp:BoundField DataField="DepartmentName" HeaderText="DepartmentName" SortExpression="DepartmentName" />
            <asp:CheckBoxField DataField="PendingAssignment" HeaderText="PendingAssignment" SortExpression="PendingAssignment" />
        </Fields>
        <HeaderStyle BackColor="#000084" Font-Bold="True" ForeColor="White" />
        <AlternatingRowStyle BackColor="Gainsboro" />
    </asp:DetailsView>
    <br />
    <asp:ObjectDataSource ID="staffDataSource" runat="server" DeleteMethod="Delete" InsertMethod="Insert"
        OldValuesParameterFormatString="original_{0}" SelectMethod="GetStaffDataByName"
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
        <SelectParameters>
            <asp:QueryStringParameter Name="Name" QueryStringField="StaffName" Type="String" />
        </SelectParameters>
        <InsertParameters>
            <asp:Parameter Name="StaffID" Type="Int32" />
            <asp:Parameter Name="Name" Type="String" />
            <asp:Parameter Name="CampaignID" Type="Int32" />
            <asp:Parameter Name="OutofOfficeHour" Type="Int32" />
            <asp:Parameter Name="DepartmentName" Type="String" />
        </InsertParameters>
    </asp:ObjectDataSource>
    <asp:SqlDataSource ID="sqlDataSource" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="SELECT DirectorMessages.* FROM DirectorMessages"></asp:SqlDataSource>
    <br />
    <br />
    <asp:Label ID="labelMessage" runat="server" Text="Enter reply message" Width="169px"></asp:Label><br />
    <br />
    <asp:TextBox ID="textBoxMessage" runat="server" Height="42px" Width="170px"></asp:TextBox>&nbsp;<br />
    &nbsp;<asp:Button ID="buttonAccept" runat="server" OnClick="buttonAccept_Click" Text="Accept" />
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<asp:Button ID="buttonReject" runat="server"
        Text="Reject" OnClick="buttonReject_Click" />
</asp:Content>
