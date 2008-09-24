/**
 *   FlowAnalyzedResult.cpp
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
#include "FlowAnalyzedResult.h"
#include "classifier.h"
#include "flow.pb.h"

#include <fstream>

CFlowAnalyzedResult::CFlowAnalyzedResult()
{
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_TCP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_EDONKEY, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_BITTORRENT, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_KAZAA, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_GNU, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_GOOBOGY, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SORIBADA, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SOULSEEK, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_ARES, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_WINMX, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_MUTE, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_NAP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_XDCC, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_DIRECTCONNECT, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_APPLEJUICE, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_WASTE, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_UDP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_ICMP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_OTHER, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_UNKNOWN, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_NONPAYLOAD, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HTTP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_OTHERHTTP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_AIM, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_IRC, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_MSN, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_YAHOOMESS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_OTHERCHAT, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HLCS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HL2DEATH, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HL2CS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HLDEATH, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_QUAKE3, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_DNS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_NETBIOS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_NBNS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_NBDS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_BOOTSTRAP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_OTHERDNS, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_RTSP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HTTPQUICKTIME, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HTTPVIDEO, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_HTTPAUDIO, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_GOOGLEVIDEO, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_YOUTUBE, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_ZIPPYVIDEO, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_VEOH, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_VIDILIFE, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_OTHERVIDEO, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_POP3, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SMTP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_OTHERMAIL, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_FTP, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_MYSQL, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SKYPETOSKYPE, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SKYPEOUT, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SSH, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_SSL, 0));
	m_distributionMap.insert(pair<ushort, uint>(PROTO_ID_VALIDCERTSSL, 0));	
}


CFlowAnalyzedResult::~CFlowAnalyzedResult()
{
}

ResultEnum CFlowAnalyzedResult::AddNewFlowInfo(flow_t* flow)
{
	u_short proto = flow->class_proto();
	// add the distribution
	m_distributionMap.find(proto)->second += 1;
	
	// add the total 
	FlowDigestMap::iterator itor = m_digestMap.find ( proto );
	if ( m_digestMap.end() != itor )
	{
		itor->second.AddNewTraffic(flow->n_frames(), flow->n_bytes());
	}
	else
	{
		MetaTraffic meta;
		meta.AddNewTraffic(flow->n_frames(), flow->n_bytes());
		m_digestMap.insert ( pair<u_short, MetaTraffic> ( proto, meta ) );
	}
	return eOK;
}

ResultEnum CFlowAnalyzedResult::PrintResult()
{
	string directory = "FlowResult/";
	string datestr = GetTimeStr(false);
	string fileNameStr = directory.append(datestr);
	string indent = "   ";
	ofstream ofile(fileNameStr.c_str(), ios_base::trunc);
	FlowTypeDistributionMap::iterator itor = m_distributionMap.begin();
	for( ;itor != m_distributionMap.end() ;++itor )
	{
		ofile << itor->first << indent << itor ->second << indent;
	}
	ofile.close();
}


ResultEnum CFlowAnalyzedResult::PrintTotalResult()
{
	
}