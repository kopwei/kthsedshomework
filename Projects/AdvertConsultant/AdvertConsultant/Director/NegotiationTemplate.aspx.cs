using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

namespace AdvertConsultant.Director
{
    public partial class NegotiationTemplate : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            SqlDataSource dataSource = new SqlDataSource();
            dataSource.ConnectionString = ConfigurationManager.ConnectionStrings["ConnectionString"].ToString();
            dataSource.SelectCommandType = SqlDataSourceCommandType.Text;
            dataSource.SelectCommand = "SELECT MAX(CampaignID) FROM Campaigns";
            try
            {
                DataView view = (DataView)(dataSource.Select(DataSourceSelectArguments.Empty));
                DataRow dr = view.Table.Rows[0];
                int ID = (int)(dr.ItemArray[0]) + 1;
                CampaignID.Text = ID.ToString();
                
            }
            catch (System.Exception)
            {
            	
            }
            
            DirectorName.Text = Membership.GetUser().UserName;
            dataSource = null;
        }

        protected void CreateNegotiation_Click(object sender, EventArgs e)
        {
            SqlDataSource ASPNETDBDataSource = new SqlDataSource();
            ASPNETDBDataSource.ConnectionString = ConfigurationManager.ConnectionStrings["ConnectionString"].ToString();

            ASPNETDBDataSource.InsertCommandType = SqlDataSourceCommandType.Text;
            ASPNETDBDataSource.InsertCommand = "INSERT INTO Campaigns (CampaignID, CampaignName, DirectorName, ClientName, ClientContact, TypeOfCampaign, Budget, StartTime, EndTime, InNegotiation) VALUES (@CampaignID, @CampaignName, @DirectorName, @ClientName, @ClientContact, @TypeOfCampaign, @Budget, @StartTime, @EndTime, @InNegotiation)";

            ASPNETDBDataSource.InsertParameters.Add("CampaignID", CampaignID.Text);
            ASPNETDBDataSource.InsertParameters.Add("CampaignName", CampaignName.Text);
            ASPNETDBDataSource.InsertParameters.Add("DirectorName", DirectorName.Text);
            ASPNETDBDataSource.InsertParameters.Add("ClientName", ClientName.Text);
            ASPNETDBDataSource.InsertParameters.Add("ClientContact", ClientContact.Text);
            ASPNETDBDataSource.InsertParameters.Add("TypeOfCampaign", TypeOfCampaign.Text);
            ASPNETDBDataSource.InsertParameters.Add("Budget", Budget.Text);
            ASPNETDBDataSource.InsertParameters.Add("StartTime", StartTime.Text);
            ASPNETDBDataSource.InsertParameters.Add("EndTime", EndTime.Text);
            ASPNETDBDataSource.InsertParameters.Add("InNegotiation", "1");

            int rowsAffected = 0;

            try
            {
                rowsAffected = ASPNETDBDataSource.Insert();
            }
            catch (Exception)
            {
                //Server.Transfer("NegotiationTemplate_problem.aspx");
            }
            finally
            {
                ASPNETDBDataSource = null;
            }

            //if (rowsAffected != 1)
            //{
            //    Server.Transfer("NegotiationTemplate_problem.aspx");
            //}
            //else
            //{
            //    Server.Transfer("Negotiation_confirm.aspx");
            //}

            if (rowsAffected == 1)
            {
                Server.Transfer("CampAndNeg.aspx");
            }

        }
    }
}
