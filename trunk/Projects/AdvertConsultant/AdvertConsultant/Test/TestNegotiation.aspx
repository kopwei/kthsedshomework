<%@ Page Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeBehind="TestNegotiation.aspx.cs" Inherits="AdvertConsultant.Test.TestNegotiation" Title="Untitled Page" %>
<asp:content ID="Content1" contentplaceholderid="PageContent" runat="Server">
    <br />
    Test Negotiation<br />
<br />
    <asp:Panel ID="negotiationPanel" runat="server" DefaultButton="buttonCreateNegotiation" Height="111px"
        Width="642px" Wrap="False">
        <br />
        &nbsp;<asp:Label ID="Label9" runat="server" Text="Campaign ID" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
        <asp:TextBox ID="CampaignID" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator1" runat="server" ControlToValidate="CampaignID"
            ErrorMessage="Please input a Campaign ID.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label1" runat="server" Text="Campaign Name" Width="124px"></asp:Label>
        &nbsp; &nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;
        <asp:TextBox ID="CampaignName" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator2" runat="server" ControlToValidate="CampaignName"
            ErrorMessage="Please input a Campaign Name.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label2" runat="server" Text="Director Name" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<asp:TextBox ID="DirectorName" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator3" runat="server" ControlToValidate="DirectorName"
            ErrorMessage="Please input a Director Name.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label3" runat="server" Text="Client Name" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
        <asp:TextBox ID="ClientName" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator4" runat="server" ControlToValidate="ClientName"
            ErrorMessage="Please input a Client Name.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label4" runat="server" Text="Client Contact" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<asp:TextBox ID="ClientContact" runat="server"
            Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator5" runat="server" ControlToValidate="ClientContact"
            ErrorMessage="Please input a Contact information of client.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label5" runat="server" Text="Type of Campaign" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<asp:TextBox ID="TypeOfCampaign" runat="server"
            Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator6" runat="server" ControlToValidate="TypeOfCampaign"
            ErrorMessage="Please input a Type of Campaign.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label6" runat="server" Text="Budget" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<asp:TextBox ID="Budget" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator7" runat="server" ControlToValidate="Budget"
            ErrorMessage="Please input a Budget.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label7" runat="server" Text="Start Time" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
        <asp:TextBox ID="StartTime" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator8" runat="server" ControlToValidate="StartTime"
            ErrorMessage="Please input a Start Time.">*</asp:RequiredFieldValidator><br />
        <br />
        &nbsp;<asp:Label ID="Label8" runat="server" Text="End Time" Width="124px"></asp:Label>
        &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
        <asp:TextBox ID="EndTime" runat="server" Width="130px"></asp:TextBox>
        <asp:RequiredFieldValidator ID="reFieValidator9" runat="server" ControlToValidate="EndTime"
            ErrorMessage="Please input a End Time.">*</asp:RequiredFieldValidator><br />
        <asp:ValidationSummary ID="NegotiationTemplateValidationSummary" runat="server" />
        <br />
        <br />
        <asp:Button ID="buttonCreateNegotiation" runat="server"
            Text="Create Negotiation" OnClick="CreateNegotiation_Click" /></asp:Panel>
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
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
</asp:content>