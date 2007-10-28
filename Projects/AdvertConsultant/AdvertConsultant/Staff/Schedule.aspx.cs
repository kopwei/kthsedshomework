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

namespace AdvertConsultant.Staff
{
    public partial class Schedule : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void ScheduleCalendar_SelectionChanged(object sender, EventArgs e)
        {
            MembershipUser user = Membership.GetUser();
            staffSqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
            staffSqlDataSource.SelectCommand = "SELECT CampaignID FROM Staffs WHERE (Name = @Name)";
            staffSqlDataSource.SelectParameters.Clear();
            staffSqlDataSource.SelectParameters.Add("Name", user.UserName);
            try
            {
                // Query the staff table to get the campaign ID
                DataView staffView = (DataView)(staffSqlDataSource.Select(DataSourceSelectArguments.Empty));
                if (null == staffView)
                {
                    return;
                }
                DataRow staffDataRow = staffView.Table.Rows[0];
                if (null == staffDataRow)
                {
                    return;
                }
                if (DBNull.Value == staffDataRow.ItemArray[0])
                {
                    this.WorkLabel.Text = "You have nothing to do in this day";
                    return;
                }
                string campaignID = staffDataRow.ItemArray[0].ToString();

                // Query the start and end time from campaign table
                campaignSqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
                campaignSqlDataSource.SelectCommand = "SELECT CampaignName, StartTime, EndTime FROM Campaigns WHERE (CampaignID = @CampaignID)";
                campaignSqlDataSource.SelectParameters.Clear();
                campaignSqlDataSource.SelectParameters.Add("CampaignID", campaignID);


                DataView campaignView = (DataView)(campaignSqlDataSource.Select(DataSourceSelectArguments.Empty));
                if (null == campaignView)
                {
                    return;
                }
                DataRow campaignRow = campaignView.Table.Rows[0];
                if (null == campaignRow)
                {
                    return;
                }
                // Judge the work
                string campaignName = campaignRow.ItemArray[0].ToString();
                DateTime startTime = (DateTime)(campaignRow.ItemArray[1]);
                DateTime endTime = (DateTime)(campaignRow.ItemArray[2]);
                DateTime selectDate = ScheduleCalendar.SelectedDate;
                if (startTime.CompareTo(selectDate) < 0 && endTime.CompareTo(selectDate) > 0)
                {
                    this.WorkLabel.Text = "This day you have to work in the campaign " +campaignName;
                }
                else
                {
                    this.WorkLabel.Text = "You have nothing to do in this day";
                }

            }
            catch (System.Exception)
            {
            	
            }   
        }
    }
}
