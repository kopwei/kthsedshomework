/**
 *   IPRangeLocator.cpp
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

#include "StdAfx.h"
#include "IPRangeLocator.h"

#include <fstream>

using namespace std;

map<AreaCodeEnum, std::list<IPRange> > CIPRangeLocator::s_ipMap;


const bool CIPRangeLocator::IsIPInRange( const unsigned int ipAddr, const AreaCodeEnum country_code )
{
	// First find the range list from map
	map<AreaCodeEnum, list<IPRange> >::const_iterator itor = s_ipMap.find(country_code);
	if (itor != s_ipMap.end())
	{
		// Iterate the range list to see if the IP is in range
		list<IPRange>::const_iterator range_itor = itor->second.begin();
		for (; range_itor != itor->second.end(); ++range_itor)
		{
			if (range_itor->IsInRange(ipAddr))
			{
				return true;
			}
		}
	}
	return false;
}

const AreaCodeEnum CIPRangeLocator::GetIPLocation( const unsigned int ipAddr )
{
	// Iterate the whole map
	map<AreaCodeEnum, list<IPRange> >::const_iterator itor = s_ipMap.begin();
	for (; itor != s_ipMap.end(); ++itor)
	{
		// Iterate the range list to see if the IP is in range
		list<IPRange>::const_iterator range_itor = itor->second.begin();
		for (; range_itor != itor->second.end(); ++range_itor)
		{
			if (range_itor->IsInRange(ipAddr))
			{
				return itor->first;
			}
		}
	}
	return eUnknown;
}

/*
 *	This function will detect if the ip address is in a certain range;
 */
const bool IPRange::IsInRange( const unsigned int ipAddr) const
{
	if (ipAddr >= m_lowerBound && ipAddr <= m_upperBound)
	{
		return true;
	}
	return false;
}

/*
 *	This method is to initialize the IP data
 */
void CIPRangeLocator::InitIPData(const AreaCodeEnum country_code)
{
	list<IPRange> rangeList;
	if (country_code == eSweden)
	{
		ifstream infile("sweden.rng");
		while (!infile.eof())
		{
			string bounds;
			getline(infile, bounds);
			string::size_type idx = bounds.find_first_of(' ');
			if (idx != string::npos)
			{
				string lowerStr = bounds.substr(0, idx);
				string upperStr = bounds.substr(idx + 1);
				IPRange range(upperStr, lowerStr);
				rangeList.push_back(range);
			}
		}
	}
	//IPRange range(;
}

/*
 *	This method is used to convert the ip from string format to integer value
 */
const unsigned int IPRange::ConvertIPStringToInt( const std::string& ipStr ) const 
{
	string::size_type idx = 0;
	string seg[4];
	string following = ipStr;
	for (int i = 0; i < 4; ++i)
	{
		idx = following.find_first_of('.');
		if (idx != string::npos)
		{
			seg[i] = following.substr(0, idx);
			following = following.substr(idx + 1);
		}
		else
		{
			seg[i] = following;
		}
	}
	unsigned int returnVal = 0;
	for (int i = 0; i < 4; ++i)
	{
		returnVal += atoi(seg[i].c_str()) << 8 * (3 - i);
	}
	return returnVal;
}

