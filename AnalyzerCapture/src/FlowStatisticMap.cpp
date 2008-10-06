/**
 *   FlowStatisticMap.cpp
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
#include "FlowStatisticMap.h"
#include "classifier.h"
#include "flow.pb.h"
#include "UserUtil.h"
#include "ipheaderutil.h"
#include <sstream>

typedef unsigned long long uint64;
typedef std::map<ushort, uint64> FlowTypeMap;
typedef std::map<uint, FlowTypeMap> UserFlowStatMap;

CFlowStatisticMap::CFlowStatisticMap()
{
	
}


CFlowStatisticMap::~CFlowStatisticMap()
{
}

void CFlowStatisticMap::AddNewFlow(const flow_t* pFlow)
{
	uint userIp = ntohl(pFlow->src_ip());
	if (CUserUtil::IsUserIP(userIp))
	{
		UserFlowStatMap::iterator itor =  m_statMap.find(userIp);
		if (itor != m_statMap.end())
		{
			(itor->second.find(pFlow->class_proto()))->second += 1;
		}
		else
		{
			FlowTypeMap typeMap;
			InitFlowTypeMap(typeMap);
			(typeMap.find(pFlow->class_proto()))->second += 1;
			m_statMap.insert(pair<uint, FlowTypeMap>(userIp, typeMap));
		}
	}
}


void CFlowStatisticMap::InitFlowTypeMap(FlowTypeMap& flowTypeMap)
{
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_TCP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_EDONKEY, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_BITTORRENT, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_KAZAA, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_GNU, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_GOOBOGY, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SORIBADA, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SOULSEEK, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_ARES, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_WINMX, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_MUTE, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_NAP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_XDCC, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_DIRECTCONNECT, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_APPLEJUICE, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_WASTE, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_UDP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_ICMP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_OTHER, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_UNKNOWN, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_NONPAYLOAD, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HTTP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERHTTP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_AIM, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_IRC, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_MSN, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_YAHOOMESS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERCHAT, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HLCS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HL2DEATH, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HL2CS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HLDEATH, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_QUAKE3, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_DNS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_NETBIOS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_NBNS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_NBDS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_BOOTSTRAP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERDNS, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_RTSP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HTTPQUICKTIME, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HTTPVIDEO, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_HTTPAUDIO, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_GOOGLEVIDEO, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_YOUTUBE, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_ZIPPYVIDEO, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_VEOH, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_VIDILIFE, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERVIDEO, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_POP3, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SMTP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERMAIL, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_FTP, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_MYSQL, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SKYPETOSKYPE, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SKYPEOUT, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SSH, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_SSL, 0));
	flowTypeMap.insert(pair<ushort, uint>(PROTO_ID_VALIDCERTSSL, 0));	
}

const std::string CFlowStatisticMap::toString()
{
	std::stringstream strStream;
	std::string indent = "   ";
	UserFlowStatMap::const_iterator itor = m_statMap.begin();
	for (; itor != m_statMap.end(); ++itor)
	{
		strStream << CIPHeaderUtil::ConvertIPToString(itor->first) << indent;
		FlowTypeMap::const_iterator typeItor = itor->second.begin();
		for (; typeItor != itor->second.end(); ++typeItor)
		{
			strStream << typeItor->first << indent << typeItor->second << indent;
		}
		strStream << endl;
	}
	m_statMap.clear();
	return strStream.str();
}

void CFlowStatisticMap::clear()
{
	m_statMap.clear();
}


