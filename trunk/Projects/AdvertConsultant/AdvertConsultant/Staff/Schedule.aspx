<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeBehind="Schedule.aspx.cs" Inherits="AdvertConsultant.Staff.Schedule" Title="Untitled Page" %>
<asp:Content ID="Content1" ContentPlaceHolderID="PageContent" runat="server">
    <br />
    <br />
    <asp:Calendar ID="ScheduleCalendar" runat="server" BackColor="#FFFFCC" BorderColor="#FFCC66"
        BorderWidth="1px" DayNameFormat="Shortest" Font-Names="Verdana" Font-Size="8pt"
        ForeColor="#663399" Height="200px" ShowGridLines="True" Width="361px" OnSelectionChanged="ScheduleCalendar_SelectionChanged">
        <SelectedDayStyle BackColor="#CCCCFF" Font-Bold="True" />
        <TodayDayStyle BackColor="#FFCC66" ForeColor="White" />
        <SelectorStyle BackColor="#FFCC66" />
        <OtherMonthDayStyle ForeColor="#CC9966" />
        <NextPrevStyle Font-Size="9pt" ForeColor="#FFFFCC" />
        <DayHeaderStyle BackColor="#FFCC66" Font-Bold="True" Height="1px" />
        <TitleStyle BackColor="#990000" Font-Bold="True" Font-Size="9pt" ForeColor="#FFFFCC" />
    </asp:Calendar>
    <br />
    <asp:SqlDataSource ID="staffSqlDataSource" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        SelectCommand="SELECT CampaignID FROM Staffs"></asp:SqlDataSource>
    <asp:SqlDataSource ID="campaignSqlDataSource" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        SelectCommand="SELECT CampaignName, StartTime, EndTime FROM Campaigns"></asp:SqlDataSource>
    <br />
    <br />
    &nbsp;<asp:Label ID="WorkLabel" runat="server" Height="29px" Width="373px"></asp:Label><br />
    <br />
    <br />
    <br />
    <br />
</asp:Content>
