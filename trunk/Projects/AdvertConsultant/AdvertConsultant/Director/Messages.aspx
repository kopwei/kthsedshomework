<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeBehind="Messages.aspx.cs" Inherits="AdvertConsultant.Director.Messages" Title="Untitled Page" %>
<asp:Content ID="Content1" ContentPlaceHolderID="PageContent" runat="server">
    <br />
    <asp:Label ID="labelRequest" runat="server" Text="Requests" Width="163px" Height="24px"></asp:Label><br />
    <asp:GridView ID="messageGridView" runat="server" AutoGenerateColumns="False" DataSourceID="messageSqlDataSource" AllowPaging="True" AllowSorting="True" CellPadding="4" ForeColor="#333333" GridLines="None">
        <Columns>
            <asp:BoundField DataField="RequiredStaffName" HeaderText="Staff" ReadOnly="True"
                SortExpression="RequiredStaffName">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="SourceDirectorName" HeaderText="Director" SortExpression="SourceDirectorName">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="CampaignName" HeaderText="Campaign Name" SortExpression="CampaignName">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="ShortMessage" HeaderText="Short Message" SortExpression="ShortMessage">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:HyperLinkField DataNavigateUrlFields="RequiredStaffName" DataNavigateUrlFormatString="ReplyMessage.aspx?StaffName={0}"
                HeaderText="Reply" Text="Reply" />
        </Columns>
        <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
        <EditRowStyle BackColor="#999999" />
        <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
        <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
        <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
        <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
    </asp:GridView>
    <asp:SqlDataSource ID="messageSqlDataSource" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        SelectCommand="SELECT [RequiredStaffName], [SourceDirectorName], [TargetDirectorName], [CampaignName], [ShortMessage], [MessageType] FROM [DirectorMessages]">
    </asp:SqlDataSource>
    <br />
    <br />
    <br />
    <asp:Label ID="labelReplied" runat="server" Text="Replies" Width="140px" Height="20px"></asp:Label><br />
    <asp:GridView ID="GridView1" runat="server" AllowPaging="True" AllowSorting="True"
        AutoGenerateColumns="False" CellPadding="4" DataSourceID="replySqlDataSource"
        ForeColor="#333333" GridLines="None">
        <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
        <Columns>
            <asp:BoundField DataField="RequiredStaffName" HeaderText="Staff" ReadOnly="True"
                SortExpression="RequiredStaffName">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="SourceDirectorName" HeaderText="Director" SortExpression="SourceDirectorName">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="CampaignName" HeaderText="Campaign Name" SortExpression="CampaignName">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
            <asp:BoundField DataField="ShortMessage" HeaderText="Short Message" SortExpression="ShortMessage">
                <ItemStyle HorizontalAlign="Center" />
            </asp:BoundField>
        </Columns>
        <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
        <EditRowStyle BackColor="#999999" />
        <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
        <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
        <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
        <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
    </asp:GridView>
    <asp:SqlDataSource ID="replySqlDataSource" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        SelectCommand="SELECT [RequiredStaffName], [SourceDirectorName], [TargetDirectorName], [CampaignName], [ShortMessage], [MessageType] FROM [DirectorMessages]">
    </asp:SqlDataSource>
    <br />
</asp:Content>
