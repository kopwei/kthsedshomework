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
    public partial class ViewStaff : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // The following code is used to switch the grid's data source while user changes the list
            // index
            if (0 == staffGroupList.SelectedIndex)
            {
                staffGridView.DataSourceID = "StaffDataSource";
            }
            if (1 == staffGroupList.SelectedIndex)
            {
                staffGridView.DataSourceID = "AvailableStaffData";
            }
        }

        protected void staffGroupList_SelectedIndexChanged(object sender, EventArgs e)
        {
            
            //staffGridView.Upda
        }
    }
}
