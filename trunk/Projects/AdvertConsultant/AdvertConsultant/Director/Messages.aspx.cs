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
    public partial class Messages : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                messageSqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
                messageSqlDataSource.SelectParameters.Clear();
                messageSqlDataSource.SelectCommand = "SELECT * FROM DirectorMessages WHERE (TargetDirectorName = @DirectorName AND MessageType = @Request)";
                messageSqlDataSource.SelectParameters.Add("DirectorName", Membership.GetUser().UserName);
                messageSqlDataSource.SelectParameters.Add("Request", "Request");


                DataView dv = (DataView)(messageSqlDataSource.Select(DataSourceSelectArguments.Empty));
                //messageGridView.Sort("RequestStaffName", SortDirection.Ascending);

                replySqlDataSource.SelectCommandType = SqlDataSourceCommandType.Text;
                replySqlDataSource.SelectParameters.Clear();
                replySqlDataSource.SelectCommand = "SELECT * FROM DirectorMessages WHERE (TargetDirectorName = @DirectorName AND MessageType = @Reply)";
                replySqlDataSource.SelectParameters.Add("DirectorName", Membership.GetUser().UserName);
                replySqlDataSource.SelectParameters.Add("Reply", "Reply");

                dv = (DataView)(messageSqlDataSource.Select(DataSourceSelectArguments.Empty));
            }
            catch (System.Exception)
            {

            }
            
        }
    }
}


//directorSqlDataSource.InsertCommand = "INSERT INTO DirectorMessages (SourceDirectorName, RequiredStaffName, TargetDirectorName, CampaignName, ShortMessage, MessageType) VALUES(@SourceDirectorName, @RequiredStaffName, @TargetDirectorName, @CampaignName, @ShortMessage, @MessageType)";
//            directorSqlDataSource.InsertParameters.Add("SourceDirectorName", selfName);
//            directorSqlDataSource.InsertParameters.Add("RequiredStaffName", staffName);
//            directorSqlDataSource.InsertParameters.Add("TargetDirectorName", directorName);
//            directorSqlDataSource.InsertParameters.Add("CampaignName", campaignName);
//            directorSqlDataSource.InsertParameters.Add("ShortMessage", "Request");
//            directorSqlDataSource.InsertParameters.Add("MessageType", "Request");