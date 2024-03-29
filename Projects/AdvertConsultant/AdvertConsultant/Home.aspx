<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" Inherits="Home_aspx" Title="Start Page" Codebehind="Home.aspx.cs" %>
<asp:content contentplaceholderid="PageContent" runat="Server">
<h2>Sample Site Home</h2>

<p>This is an unsecure home page, accessible to all users.</p>

<asp:loginview id="LoginView1" runat="server">
    <anonymoustemplate>
        <p>The secure navigation menu is not visible until you <a href=login.aspx>log in</a>. 
        <a href="members/securepage.aspx">Click here</a> to see what happens when you try to access a secure page without being logged in.</p>
    </anonymoustemplate>   
</asp:loginview>
<br />
<br />
<br />
<br />
</asp:content>