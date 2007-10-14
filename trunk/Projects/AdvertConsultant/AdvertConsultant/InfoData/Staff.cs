// ***************************************************************
//  Staff.cs   version:  1.0   ? date: 10/14/2007
//  -------------------------------------------------------------
//  
//  -------------------------------------------------------------
//  Created by Wei Zhenfang
//  -------------------------------------------------------------
//  Copyright (C) 2007 - All Rights Reserved
// ***************************************************************
// This is free software; you can redistribute it and/or modify it
// under the terms of the GNU Library General Public License as published by
// the Free Software Foundation; either version 2 of the License, or (at your
// option) any later version.
// ***************************************************************

using System;
using System.Diagnostics;
using System.Xml;

namespace AdvertConsultant.InfoData
{
    /// <summary>
    /// 
    /// </summary>
    public class Staff :  Employee
    {
        private Campaign currentCampaign;

        // Properties
        #region properties
        public Campaign CurrentCampaign
        {
            get
            {
                Debug.Assert(null != currentCampaign);
                return currentCampaign;
            }
            set
            {
                Debug.Assert(null != value);
                currentCampaign = value;
            }
        }
        #endregion

        /// <summary>
        /// Override the 
        /// </summary>
        /// <param name="writer"></param>
        //public override void WriteToXML(System.Xml.XmlWriter writer)
        //{
        //    throw new Exception("The method or operation is not implemented.");
        //}
    }
}
