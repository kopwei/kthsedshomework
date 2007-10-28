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
    public partial class StaffDetail : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // If the staff is already assigned to a campaign, then he/she cannot be assigned to another untill the campaign finishes
            DataView staffView = (DataView)(StaffDataSource.Select());
            DataRow staffRow = staffView.Table.Rows[0];
            if (DBNull.Value != staffRow.ItemArray[2] && null != staffRow.ItemArray[2])
            {
                String campaignId = staffRow.ItemArray[2].ToString();
                SqlDataSource campaignSrc = new SqlDataSource();
                campaignSrc.ConnectionString = ConfigurationManager.ConnectionStrings[1].ToString();
                
                campaignSrc.SelectCommandType = SqlDataSourceCommandType.Text;
                campaignSrc.SelectCommand = "SELECT EndTime FROM Campaigns WHERE (CampaignID = @CampaignID)";
                campaignSrc.SelectParameters.Add("CampaignID", campaignId);
                // Check the campaign time
                try
                {
                    DataView campaignView = (DataView)(campaignSrc.Select(DataSourceSelectArguments.Empty));
                   DataRow  campaignRow =    campaignView.Table.Rows[0];
                   DateTime time = (DateTime)(campaignRow.ItemArray[0]);
                    if (time.CompareTo(DateTime.Now) > 0)
                    {
                        assignStaffButton.Visible = false;
                    }
                }
                catch (System.Exception)
                {
                	
                }

                
            }
            else
            {
                assignStaffButton.Visible = true;
            }
        }


        protected void assignStaffButton_Click(object sender, EventArgs e)
        {
            assignStaffButton.Enabled = false;
            CampaignList.Visible = true;
            CampaignList.Enabled = true;
            buttonCommitAssignment.Visible = true;
            buttonCommitAssignment.Enabled = true;
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            DataView view = (DataView)(SqlDataSource1.Select(DataSourceSelectArguments.Empty));
            DataRow dr = view.Table.Rows[0];
            String strId = dr.ItemArray[0].ToString();


            SqlDataSource1.UpdateCommandType = SqlDataSourceCommandType.Text;
            SqlDataSource1.UpdateCommand = "UPDATE Staffs SET CampaignID = @CampaignID WHERE(StaffID = @StaffID)";
            SqlDataSource1.UpdateParameters.Add("CampaignID", strId);
            SqlDataSource1.UpdateParameters.Add("StaffID", StaffDetailsView.DataKey.Value.ToString());

            int rowsAffected1 = 0;

            try
            {
                rowsAffected1 = SqlDataSource1.Update();
            }
            catch (System.Exception)
            {

            }
            
            if (rowsAffected1 == 1)
            {
                Server.Transfer("AssignmentOK.aspx");
            }
        }

    }
}
