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
    public enum EnumReply
    {
        eAccept,
        eReject
    }
    public partial class ReplyMessage : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void buttonAccept_Click(object sender, EventArgs e)
        {
            
            UpdateStaffState(EnumReply.eAccept);
            Server.Transfer("Replied.aspx");
            //sqlDataSource.Select
        }

        protected void buttonReject_Click(object sender, EventArgs e)
        {
            UpdateStaffState(EnumReply.eReject);
            Server.Transfer("Replied.aspx");      
        }

        private void UpdateStaffState(EnumReply replyType)
        {
            string staffID = staffDetailsView.DataKey.Value.ToString();
            string staffName = GetStaffNameByID(staffID);
            string campaignName = GetCampaignNameByStaffName(staffName);
            string campaignId = GetCampaignIDByName(campaignName);
            string sourceDirectorName = GetDirectorNameByStaffname(staffName);

            // Set the pending status to false
            sqlDataSource.UpdateParameters.Clear();
            sqlDataSource.UpdateCommandType = SqlDataSourceCommandType.Text;
            sqlDataSource.UpdateCommand = "UPDATE Staffs SET PendingAssignment = @Pending WHERE(Name = @StaffName)";
            sqlDataSource.UpdateParameters.Add("Pending", "0");
            sqlDataSource.UpdateParameters.Add("StaffName", staffName);

            try
            {
                sqlDataSource.Update();
            }
            catch (System.Exception)
            {
            	
            }
            // Delete the corresponding message
            DeleteMessage(staffName);   
            // If accepted, then the staff's campaign is set
            if (replyType == EnumReply.eAccept)
            {
                sqlDataSource.UpdateParameters.Clear();
                sqlDataSource.UpdateCommandType = SqlDataSourceCommandType.Text;
                sqlDataSource.UpdateCommand = "UPDATE Staffs SET CampaignID = @CampaignID WHERE(Name = @StaffName)";
                sqlDataSource.UpdateParameters.Add("CampaignID", campaignId);
                sqlDataSource.UpdateParameters.Add("StaffName", staffName);

                try
                {
                    sqlDataSource.Update();
                }
                catch (System.Exception)
                {
                }
            }
            // Else we will reply a message back
            else if (replyType == EnumReply.eReject)
            {
                string repliedMessage = textBoxMessage.Text;
                sqlDataSource.InsertParameters.Clear();
                sqlDataSource.InsertCommandType = SqlDataSourceCommandType.Text;
                sqlDataSource.InsertCommand = "INSERT INTO DirectorMessages (SourceDirectorName, RequiredStaffName, TargetDirectorName, CampaignName, ShortMessage, MessageType) VALUES(@SourceDirectorName, @RequiredStaffName, @TargetDirectorName, @CampaignName, @ShortMessage, @Reply)";
                sqlDataSource.InsertParameters.Add("SourceDirectorName", Membership.GetUser().UserName);
                sqlDataSource.InsertParameters.Add("RequiredStaffName", staffName);
                sqlDataSource.InsertParameters.Add("TargetDirectorName", sourceDirectorName);
                sqlDataSource.InsertParameters.Add("CampaignName", campaignName);
                sqlDataSource.InsertParameters.Add("ShortMessage", repliedMessage);
                sqlDataSource.InsertParameters.Add("Reply", "Reply");

                try
                {
                    sqlDataSource.Insert();
                }
                catch (System.Exception)
                {
                }
            }
                  
        }

        private string GetCampaignNameByStaffName(String staffName)
        {
            sqlDataSource.SelectParameters.Clear();
            sqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
            sqlDataSource.SelectCommand = "SELECT CampaignName FROM DirectorMessages WHERE (RequiredStaffName = @StaffName)";
            sqlDataSource.SelectParameters.Add("StaffName", staffName);

            try
            {
                DataView view = (DataView)(sqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow dr = view.Table.Rows[0];
                String campaignName = dr.ItemArray[0].ToString();
                return campaignName;
            }
            catch (System.Exception)
            {
                return "";
            }            
        }

        private string GetCampaignIDByName(string campaignName)
        {
            sqlDataSource.SelectParameters.Clear();
            sqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
            sqlDataSource.SelectCommand = "SELECT CampaignID FROM Campaigns WHERE (CampaignName = @Name)";
            sqlDataSource.SelectParameters.Add("Name", campaignName);

            try
            {
                DataView view = (DataView)(sqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow dr = view.Table.Rows[0];
                String campaignId = dr.ItemArray[0].ToString();
                return campaignId;
            }
            catch (System.Exception)
            {
                return "";
            }          
        }

        private string GetDirectorNameByStaffname(string staffName)
        {
            sqlDataSource.SelectParameters.Clear();
            sqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
            sqlDataSource.SelectCommand = "SELECT SourceDirectorName FROM DirectorMessages WHERE (RequiredStaffName = @StaffName)";
            sqlDataSource.SelectParameters.Add("StaffName", staffName);

            try
            {
                DataView view = (DataView)(sqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow dr = view.Table.Rows[0];
                String directorName = dr.ItemArray[0].ToString();
                return directorName;
            }
            catch (System.Exception)
            {
                return "";
            }          
        }

        private string GetStaffNameByID(string staffId)
        {
            sqlDataSource.SelectParameters.Clear();
            sqlDataSource.SelectCommand = "SELECT Name FROM Staffs WHERE(StaffID = @StaffID)";
            sqlDataSource.SelectParameters.Add("StaffID", staffId);
            try
            {
                DataView staffView = (DataView)(sqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow staffRow = staffView.Table.Rows[0];
                string staffName = staffRow.ItemArray[0].ToString();
                return staffName;
            }
            catch (System.Exception)
            {
                return "";
            }
        }

        private void DeleteMessage(string staffName)
        {
            sqlDataSource.DeleteParameters.Clear();
            sqlDataSource.DeleteCommandType = SqlDataSourceCommandType.Text;
            sqlDataSource.DeleteCommand = "DELETE FROM DirectorMessages WHERE (RequiredStaffName = @StaffName)";
            sqlDataSource.DeleteParameters.Add("StaffName", staffName);

            try
            {
                sqlDataSource.Delete();
            }
            catch (System.Exception)
            {
            	
            }
        }
    }
}
