// ***************************************************************
//  Employee.cs   version:  1.0   ? date: 10/14/2007
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
    enum EmployeeType{eAdmin, eGovernor, eDirector, eStaff};
    /// <summary>
    /// Class employee
    /// This class is the base class of all the employees in the company. This 
    /// </summary>
    public abstract class Employee
    {
        // Fields
        protected uint        employeeId;
        protected string      firstName;
        protected string      lastName;

        // Properties
        #region properties
        /// <summary>
        /// Employee id property
        /// </summary>
        public uint EmployeeID
        {
            get 
            {
                Debug.Assert(employeeId > 0);
                return employeeId;
            }
            set
            {
                Debug.Assert(value > 0);
                employeeId = value;
            }
        }

        /// <summary>
        /// Employee first name property
        /// </summary>
        public string FirstName
        {
            get
            {
                Debug.Assert(firstName.Length > 0);
                return firstName;
            }
            set
            {
                Debug.Assert(value.Length > 0);
                firstName = value;
            }
        }

        /// <summary>
        /// Employee last name property
        /// </summary>
        public string LastName
        {
            get
            {
                Debug.Assert(lastName.Length > 0);
                return firstName;
            }
            set
            {
                Debug.Assert(value.Length > 0);
                lastName = value;
            }
        }

        #endregion

        /// <summary>
        /// Constructor to initialize all basic information
        /// </summary>
        /// <param name="id"></param>
        /// <param name="firstName"></param>
        /// <param name="lastName"></param>
        public Employee(uint id, string firstName, string lastName) 
        {
            Debug.Assert(id > 0);
            employeeId = id;
            Debug.Assert(firstName.Length > 0);
            this.firstName = firstName;
            Debug.Assert(lastName.Length > 0);
            this.lastName = lastName;
        }

        /// <summary>
        /// Default constructor without input parameters
        /// </summary>
        public Employee()
        {
            employeeId = 0;
            firstName = "";
            lastName = "";
        }

        //public abstract bool StoreData();
        //public abstract bool RetrieveData();
    }
}
