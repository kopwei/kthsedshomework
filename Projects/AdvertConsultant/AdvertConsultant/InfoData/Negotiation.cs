// ***************************************************************
//  Negotiation.cs   version:  1.0   ? date: 10/14/2007
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

namespace AdvertConsultant.InfoData
{
    /// <summary>
    /// Class Negotiation
    /// This class is used for storing the information of an in-progress negotiation
    /// </summary>
    public class Negotiation
    {
        // Fields
        private ClientInfo clientInfo;
        private uint budget;

        // Properties
        #region properties
        /// <summary>
        /// Client info property
        /// </summary>
        public ClientInfo ClientInformation
        {
            get
            {
                Debug.Assert(null != clientInfo);
                return clientInfo;
            }
        }

        /// <summary>
        /// Budget property
        /// </summary>
        public uint Budget
        {
            get
            {
                return budget;
            }
            set
            {
                budget = value;
            }
        }

        #endregion

        /// <summary>
        /// Default constructor
        /// </summary>
        public Negotiation()
        {
            clientInfo = null;
            budget = 0;
        }

        //public bool StoreData();
    }
}
