// ***************************************************************
//  ClientInfo.cs   version:  1.0   ? date: 10/14/2007
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
using System.Xml;

namespace AdvertConsultant.InfoData
{
    public class ClientInfo
    {
        // Fields
        private string clientName;

        // Properties
        #region properties
        /// <summary>
        /// Client name property
        /// </summary>
        public string  ClientName
        {
            get
            {
                return clientName;
            }
            set
            {
                clientName = value;
            }
        }
        #endregion

        /// <summary>
        /// Default constructor
        /// </summary>
        public ClientInfo()
        {
            clientName = "";
        }
    }
}
