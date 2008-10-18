/**
 *   IPRangeLocator.h
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

#pragma once

#include <list>
#include <map>
#include <string>

enum AreaCodeEnum
{
	eUnknown = -1,
	eUSA = 1,
	eUK = 44,
	eSweden = 46,
	eChina = 86
};

class IPRange 
{
public:
	IPRange(const std::string& upperBound, const std::string& lowerBound)
	{
		m_upperBound = ConvertIPStringToInt(upperBound);
		m_lowerBound = ConvertIPStringToInt(lowerBound);
	}

	const bool IsInRange(const unsigned int ipAddr) const;

private:
	const unsigned int ConvertIPStringToInt(const std::string& ipStr) const;


	unsigned int m_upperBound;
	unsigned int m_lowerBound;
};

class CIPRangeLocator
{
public:
	/*
	 *	
	 */
	static void InitIPData(const AreaCodeEnum country_code);

	/*
	 *	
	 */
	static const bool IsIPInRange(const unsigned int ipAddr, const AreaCodeEnum country_code);

	/*
	 *	
	 */
	static const AreaCodeEnum GetIPLocation(const unsigned int ipAddr);

private:

	



	static std::map<AreaCodeEnum, std::list<IPRange> > s_ipMap;
};
