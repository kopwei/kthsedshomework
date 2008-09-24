/**
 *   UserUtil.cpp
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
#include "UserUtil.h"

// Re-declaration here
typedef unsigned long long uint64;
set<uint64> CUserUtil::s_ispMacSet;
set<uint64> CUserUtil::s_userMacSet;
set<uint> CUserUtil::s_userIPSet;

CUserUtil::CUserUtil()
{
}


CUserUtil::~CUserUtil()
{
}

void CUserUtil::InitISPMacSet()
{
	// TODO: need implementation here

	s_ispMacSet.insert(0x1b2ba3c1c9LL);
	s_ispMacSet.insert(0x1b2ba3c1ccLL);
	s_ispMacSet.insert(0x1b2ba3c1cdLL);
}

const bool CUserUtil::IsUserUploaded(const uint64 srcMac, const uint64 dstMac, const uint srcIp, const uint dstIp)
{
	// If the ip and mac address belongs to a user mac list or
	// user ip list , then it is uploaded by user
	if (IsUserMac(srcMac))
		return true;
	else if (IsUserIP(srcIp))
		return true;
	// If the destination mac addr is ISP mac addr, it is uploaded by user and we have to add
	// related info into user list
	else
	{
		set<uint64>::iterator itor = s_ispMacSet.find(dstMac);
		if (itor != s_ispMacSet.end())
		{
			s_userMacSet.insert(srcMac);
			s_userIPSet.insert(srcIp);
			return true;
		}
	}
	return false;
}

const bool CUserUtil::IsUserDownloaded(const uint64 srcMac, const uint64 dstMac, const uint srcIp, const uint dstIp)
{
	// TODO: need implementation here
	if (IsUserMac(dstMac))
		return true;
	else if (IsUserIP(dstIp))
		return true;
	else
	{
		set<uint64>::iterator itor = s_ispMacSet.find(srcMac);
		if (itor != s_ispMacSet.end())
		{
			s_userMacSet.insert(srcMac);
			s_userIPSet.insert(srcIp);
			return true;
		}
	}
	return false;	
}

const bool CUserUtil::IsUserIP(const uint ipAddr)
{
	set<uint>::const_iterator itor = s_userIPSet.find(ipAddr);
	return itor != s_userIPSet.end();
}

const bool CUserUtil::IsUserMac(const uint64 macAddr)
{
	set<uint64>::const_iterator itor = s_userMacSet.find(macAddr);
	return itor != s_userMacSet.end();
}


