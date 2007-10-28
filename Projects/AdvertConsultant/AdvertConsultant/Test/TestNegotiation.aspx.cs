using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Threading;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

namespace AdvertConsultant.Test
{
    public partial class TestNegotiation : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            //Thread myThread = new Thread(new ThreadStart(ThreadMethod));
            ////myThread.SetApartmentState(ApartmentState.STA);
            //myThread.Start();
            //try
            //{
            //    //myThread.SetApartmentState(ApartmentState.MTA);
            //}
            //catch (System.Exception)
            //{
            	
            //}


            CampaignID.Text = "800";
            CampaignName.Text = "TestNegotiation";
            DirectorName.Text = "Scott";
            ClientName.Text = "TestClient";
            ClientContact.Text = "12345678";
            TypeOfCampaign.Text = "TV";
            Budget.Text = "45000";
            StartTime.Text = "2007-11-12";
            EndTime.Text = "2007-12-20";
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


        //protected void ThreadMethod()
        //{
        //    Thread.Sleep(3000);
        //    CampaignID.Text = "800";
        //    CampaignName.Text = "TestNegotiation";
        //    Thread.Sleep(3000);
        //    DirectorName.Text = "Scott";
        //    ClientName.Text = "TestClient";
        //    Thread.Sleep(3000);
        //    ClientContact.Text = "12345678";
        //    TypeOfCampaign.Text = "TV";
        //    Thread.Sleep(3000);
        //    Budget.Text = "45000";
        //    StartTime.Text = "2007-11-12";
        //    EndTime.Text = "2007-12-20";
        //    Thread.Sleep(3000);
        //    this.CreateNegotiation_Click(null, null);
        //}
    }
}
