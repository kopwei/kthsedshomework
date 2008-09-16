/**
 *   AnalyzedResult.cpp
 *   Author: Wei Zhenfang                                                  
 *                                                                         
 *   This program is free software; you can redistribute it and/or modify  
 *   it under the terms of the GNU General Public License as published by  
 *   the Free Software Foundation; either version 2 of the License, or     
 *   (at your option) any later version.                                   
 *                                                                         
 *   This program is distributed in the hope that it will be useful,       
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         
 *   GNU General Public License for more details.                          
 *                                                                         
 *   You should have received a copy of the GNU General Public License     
 *   along with this program; if not, write to Ericsson AB                        
 *   Copyright (C) 2008 by Ericsson AB                                    
 */

#include "AnalyzedResult.h"
//#include "CommonUtil.h"
#include <sstream>

CAnalyzedResult::CAnalyzedResult(void)
{

}

CAnalyzedResult::~CAnalyzedResult(void)
{
}

const string CAnalyzedResult::GetTimeStr( const bool bIsStart )
{
	//string strZero = CommonUtil::itoa(0, 10);
	tm refTime;
	if (bIsStart)
	{
		refTime = m_startTime;
	}
	else
	{
		refTime = m_endTime;
	}
	stringstream datestr;
	// year
	datestr << refTime.tm_year + 1900;
	// month
	if (refTime.tm_mon < 11)
	{
		datestr << 0;
	}
	datestr << refTime.tm_mon + 1;
	// day
	if (refTime.tm_mday < 10) 
	{
		datestr << 0;
	}
	datestr << refTime.tm_mday;
	// hour
	if (refTime.tm_hour < 10)
	{
		datestr << 0;
		if (refTime.tm_hour == 0)
		{
			datestr << 0;
		}
	}
	datestr << refTime.tm_hour;
	// minute
	if (refTime.tm_min < 10)
	{
		datestr << 0;
		if (refTime.tm_min == 0)
		{
			datestr << 0;
		}
	}
	datestr << refTime.tm_min;
	// seconds
	if (refTime.tm_sec < 10)
	{
		datestr << 0;
		if (refTime.tm_sec == 0)
		{
			datestr << 0;
		}
	}
	datestr << refTime.tm_sec;
	return datestr.str();

/*
	// Year
	string year = CommonUtil::itoa(refTime.tm_year + 1900, 10);
	// Month
	string month;
	if (refTime.tm_mon  <  9)
	{
		month.append(strZero);
	}
	month.append(CommonUtil::itoa(refTime.tm_mon + 1, 10));
	// Day
	string day;
	if (refTime.tm_mday < 9)
	{
		day.append(strZero);
	}
	day.append(CommonUtil::itoa(refTime.tm_mday, 10));
	// Hour
	string hour;
	if (refTime.tm_hour < 10)
	{
		hour.append(strZero);
		if (refTime.tm_hour = 0)
		{
			hour.append(strZero);
		}
		else 
		{
			hour.append(CommonUtil::itoa(refTime.tm_hour, 10));
		}
	}
	else 
	{
		hour.append(CommonUtil::itoa(refTime.tm_hour, 10));
	}
	// Minute
	string minute;
	if (refTime.tm_min < 10)
	{
		minute.append(strZero);
		if (refTime.tm_min = 0)
		{
			minute.append(strZero);;
		}
		else 
		{
			minute.append(CommonUtil::itoa(refTime.tm_min, 10));
		}
	}
	else 
	{
		minute.append(CommonUtil::itoa(refTime.tm_min, 10));
	}
	string datestr = year + month + day + hour + minute;
	return datestr;
	*/
}

