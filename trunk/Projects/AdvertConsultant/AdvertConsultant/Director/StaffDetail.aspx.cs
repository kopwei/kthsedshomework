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
            // If the staff is already assigned to a campaign, then he/she cannot be assigned to another until the campaign finishes
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
            // If the staff is pending ,then he/she can not be assigned to another
            
            else if (DBNull.Value != staffRow.ItemArray[5] )
            {
                string pending = staffRow.ItemArray[5].ToString();
                if (pending == "True")
                {
                    assignStaffButton.Visible = false;
                }
                else
                {
                    assignStaffButton.Visible = true;
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

        protected void buttonCommitAssignment_Click(object sender, EventArgs e)
        {
            // Step 1) Get the directors department name
            String directorName = Membership.GetUser().UserName;
            string departmentName = GetDepartmentNameByDirector(directorName);

            // Step 2) Get the staffs department name
            string staffId = StaffDetailsView.DataKey.Value.ToString();
            string staffDepartmentName = GetStaffDepartmentName(staffId);

            if (departmentName.Equals(staffDepartmentName))
            {
                DataView view = (DataView)(SqlDataSource1.Select(DataSourceSelectArguments.Empty));
                DataRow dr = view.Table.Rows[0];
                String campaignId = dr.ItemArray[0].ToString();

                SqlDataSource1.UpdateParameters.Clear();
                SqlDataSource1.UpdateCommandType = SqlDataSourceCommandType.Text;
                SqlDataSource1.UpdateCommand = "UPDATE Staffs SET CampaignID = @CampaignID WHERE(StaffID = @StaffID)";
                SqlDataSource1.UpdateParameters.Add("CampaignID", campaignId);
                SqlDataSource1.UpdateParameters.Add("StaffID", staffId);

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
            else
            {
                SendMessageToDepartment(staffId, staffDepartmentName, directorName);

                DataView view = (DataView)(SqlDataSource1.Select(DataSourceSelectArguments.Empty));
                DataRow dr = view.Table.Rows[0];
                String campaignId = dr.ItemArray[0].ToString();

                SqlDataSource1.UpdateParameters.Clear();
                SqlDataSource1.UpdateCommandType = SqlDataSourceCommandType.Text;
                SqlDataSource1.UpdateCommand = "UPDATE Staffs SET PendingAssignment = 1 WHERE(StaffID = @StaffID)";
                SqlDataSource1.UpdateParameters.Add("StaffID", staffId);

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
                    Server.Transfer("AssignmentPending.aspx");
                }
            }

            
        }

        private string GetDepartmentNameByDirector(string directorName)
        {
            directorSqlDataSource.SelectParameters.Clear();
            directorSqlDataSource.SelectCommand = "SELECT DepartmentName FROM Directors WHERE(DirectorName = @DirectorName)";
            directorSqlDataSource.SelectParameters.Add("DirectorName", directorName);
            try
            {
                DataView directorView = (DataView)(directorSqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow directorRow = directorView.Table.Rows[0];
                string departmentName = directorRow.ItemArray[0].ToString();
                return departmentName;
            }
            catch (System.Exception)
            {
            	return "";
            }
                       
        }

        private string GetStaffDepartmentName(string staffId)
        {
            directorSqlDataSource.SelectParameters.Clear();
            directorSqlDataSource.SelectCommand = "SELECT DepartmentName FROM Staffs WHERE(StaffID = @StaffID)";
            directorSqlDataSource.SelectParameters.Add("StaffID", staffId);
            try
            {
                DataView directorView = (DataView)(directorSqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow directorRow = directorView.Table.Rows[0];
                string departmentName = directorRow.ItemArray[0].ToString();
                return departmentName;
            }
            catch (System.Exception)
            {
                return "";
            }
        }

        private void SendMessageToDepartment(string staffId, string departmentName, string selfName)
        {
            // Step 1) Get destination director name
            string directorName = GetDirectorNameByDepartment(departmentName);

            // Step 2) Get Staff Name
            string staffName = GetStaffNameByID(staffId);

            string campaignName = CampaignList.Text;

            //INSERT INTO Campaigns (CampaignID, CampaignName, DirectorName, ClientName, ClientContact, 
            // TypeOfCampaign, Budget, StartTime, EndTime, InNegotiation) VALUES (@CampaignID, @CampaignName, @DirectorName, @ClientName, @ClientContact, @TypeOfCampaign, @Budget, @StartTime, @EndTime, @InNegotiation)";
            //directorSqlDataSource.Insert.Clear();
            directorSqlDataSource.InsertCommandType = SqlDataSourceCommandType.Text;
            directorSqlDataSource.InsertCommand = "INSERT INTO DirectorMessages (SourceDirectorName, RequiredStaffName, TargetDirectorName, CampaignName, ShortMessage, MessageType) VALUES(@SourceDirectorName, @RequiredStaffName, @TargetDirectorName, @CampaignName, @ShortMessage, @MessageType)";
            directorSqlDataSource.InsertParameters.Add("SourceDirectorName", selfName);
            directorSqlDataSource.InsertParameters.Add("RequiredStaffName", staffName);
            directorSqlDataSource.InsertParameters.Add("TargetDirectorName", directorName);
            directorSqlDataSource.InsertParameters.Add("CampaignName", campaignName);
            directorSqlDataSource.InsertParameters.Add("ShortMessage", "Request");
            directorSqlDataSource.InsertParameters.Add("MessageType", "Request");

            try
            {
                directorSqlDataSource.Insert();
            }
            catch (System.Exception)
            {
                return;
            }
        }

        private string GetDirectorNameByDepartment(string departmentName)
        {
            directorSqlDataSource.SelectParameters.Clear();
            directorSqlDataSource.SelectCommand = "SELECT DirectorName FROM Directors WHERE(DepartmentName = @DepartmentName)";
            directorSqlDataSource.SelectParameters.Add("DepartmentName", departmentName);
            try
            {
                DataView directorView = (DataView)(directorSqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow directorRow = directorView.Table.Rows[0];
                string directorName = directorRow.ItemArray[0].ToString();
                return directorName;
            }
            catch (System.Exception)
            {
                return "";
            }
        }

        private string GetStaffNameByID(string staffId)
        {
            directorSqlDataSource.SelectParameters.Clear();
            directorSqlDataSource.SelectCommand = "SELECT Name FROM Staffs WHERE(StaffID = @StaffID)";
            directorSqlDataSource.SelectParameters.Add("StaffID", staffId);
            try
            {
                DataView staffView = (DataView)(directorSqlDataSource.Select(DataSourceSelectArguments.Empty));
                DataRow staffRow = staffView.Table.Rows[0];
                string staffName = staffRow.ItemArray[0].ToString();
                return staffName;
            }
            catch (System.Exception)
            {
                return "";
            }
        }

    }
}
