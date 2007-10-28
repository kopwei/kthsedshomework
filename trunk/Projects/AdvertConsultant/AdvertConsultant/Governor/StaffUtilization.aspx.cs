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

namespace AdvertConsultant.Governor
{
    public partial class StaffUtilization : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (StaffUtilizationList.SelectedIndex == 0)
            {
                GridView1.DataSourceID = "InCampaignStaffDataSource";
            }
            if (StaffUtilizationList.SelectedIndex == 1)
            {
                GridView1.DataSourceID = "FreeStaffsDataSource";
            }
        }

        protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }              
    }
}
