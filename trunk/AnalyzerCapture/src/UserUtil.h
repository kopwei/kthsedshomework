/**
 *   UserUtil.h
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
#ifndef USERUTIL_H
#define USERUTIL_H

#include <set>

using namespace std;

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CUserUtil{
	typedef unsigned long long uint64;
public:
    CUserUtil();

    ~CUserUtil();
	
	static void InitISPMacSet();

	static const bool IsUserUploaded(const uint64 srcMac, const uint64 dstMac, const uint srcIp, const uint dstIp);
	
	static const bool IsUserDownloaded(const uint64 srcMac, const uint64 dstMac, const uint srcIp, const uint dstIp);
	
	static const bool IsUserIP(const uint ipAddr);
	
	static const bool IsUserMac(const uint64 macAddr);
	
	static void PrintUsers();

private:
	
	static set<uint64> s_ispMacSet;	
	static set<uint64> s_userMacSet;

	static set<uint>   s_userIPSet;
};

#endif
