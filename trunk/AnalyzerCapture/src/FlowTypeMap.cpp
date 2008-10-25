/**
 *   FlowTypeMap.cpp
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
#include "FlowTypeMap.h"
#include "classifier.h"
#include "Flow.h"
#include "CommonUtil.h"

using namespace std;

typedef unsigned long long uint64;
typedef std::map<ushort, uint64> TypeMap;

CFlowTypeMap::CFlowTypeMap()
{
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_TCP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_EDONKEY, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_BITTORRENT, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_KAZAA, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_GNU, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_GOOBOGY, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SORIBADA, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SOULSEEK, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_ARES, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_WINMX, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_MUTE, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_NAP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_XDCC, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_DIRECTCONNECT, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_APPLEJUICE, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_WASTE, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_UDP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_ICMP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_OTHER, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_UNKNOWN, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_NONPAYLOAD, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HTTP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERHTTP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_AIM, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_IRC, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_MSN, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_YAHOOMESS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERCHAT, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HLCS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HL2DEATH, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HL2CS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HLDEATH, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_QUAKE3, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_DNS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_NETBIOS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_NBNS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_NBDS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_BOOTSTRAP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERDNS, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_RTSP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HTTPQUICKTIME, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HTTPVIDEO, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_HTTPAUDIO, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_GOOGLEVIDEO, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_YOUTUBE, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_ZIPPYVIDEO, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_VEOH, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_VIDILIFE, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERVIDEO, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_POP3, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SMTP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_OTHERMAIL, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_FTP, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_MYSQL, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SKYPETOSKYPE, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SKYPEOUT, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SSH, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_SSL, 0LL));
	m_typeMap.insert(pair<ushort, uint>(PROTO_ID_VALIDCERTSSL, 0LL));
}


CFlowTypeMap::~CFlowTypeMap()
{
}

void CFlowTypeMap::AddNewFlow(const flow_t* pFlow)
{
	if (NULL == pFlow)
		return;
	TypeMap::iterator itor = m_typeMap.find(pFlow->class_proto());
	if (itor != m_typeMap.end())
		itor->second += 1;
}

const string CFlowTypeMap::toString() const
{
	string strStream;
	string indent = "   ";
	TypeMap::const_iterator typeItor = m_typeMap.begin();
	for (; typeItor != m_typeMap.end(); ++typeItor)
	{
		strStream.append(CommonUtil::itoa(typeItor->first, 10));
		strStream.append(indent);
		strStream.append(CommonUtil::itoa( typeItor->second, 10 ));
		strStream.append(indent);
	}
	return strStream;
}


