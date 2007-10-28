<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" Inherits="Login_aspx" Title="Login" Codebehind="Login.aspx.cs" %>
<asp:content contentplaceholderid="PageContent" runat="Server">
<h2>Login</h2>

<asp:label id="MessageLabel" runat="Server" forecolor="Red" Font-Size=".9em" />

<asp:login id="Login1" runat="server" DestinationPageUrl="~/Home.aspx" TitleText="">
</asp:login>

</asp:content>