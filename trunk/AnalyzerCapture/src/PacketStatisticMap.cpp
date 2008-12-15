/**
 *   PacketStatisticMap.cpp
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
#include "PacketStatisticMap.h"

#include "classifier.h"
#include "CommonUtil.h"

CPacketStatisticMap::CPacketStatisticMap()
{
	// Initialize all the traffic data
	MetaTraffic meta;
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_TCP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_EDONKEY, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_BITTORRENT, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_KAZAA, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_GNU, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_GOOBOGY, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SORIBADA, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SOULSEEK, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_ARES, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_WINMX, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_MUTE, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_NAP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_DHT, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_DIRECTCONNECT, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_APPLEJUICE, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_WASTE, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_UDP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_ICMP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_OTHER, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_UNKNOWN, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_NONPAYLOAD, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HTTP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_OTHERHTTP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_AIM, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_IRC, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_MSN, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_YAHOOMESS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_OTHERCHAT, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HLCS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HL2DEATH, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HL2CS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HLDEATH, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_QUAKE3, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_DNS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_NETBIOS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_NBNS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_NBDS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_BOOTSTRAP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_OTHERDNS, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_RTSP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HTTPQUICKTIME, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HTTPVIDEO, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_HTTPAUDIO, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_GOOGLEVIDEO, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_YOUTUBE, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_ZIPPYVIDEO, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_VEOH, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_VIDILIFE, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_OTHERVIDEO, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_POP3, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SMTP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_OTHERMAIL, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_FTP, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_MYSQL, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SKYPETOSKYPE, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SKYPEOUT, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SSH, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_SSL, meta));
	m_statisticMap.insert(pair<ushort, MetaTraffic>(PROTO_ID_VALIDCERTSSL, meta));	
}


CPacketStatisticMap::~CPacketStatisticMap()
{
}


void CPacketStatisticMap::insert(const ushort classifier, const MetaTraffic data)
{
	map<ushort, MetaTraffic>::iterator itor = m_statisticMap.find(classifier);
	if (itor != m_statisticMap.end())
	{
		itor->second.AddNewPacket(data.GetTrafficVolume());
	}
}

void CPacketStatisticMap::clear()
{
	map<ushort, MetaTraffic>::iterator itor;
	for( itor = m_statisticMap.begin(); itor != m_statisticMap.end(); ++itor)
	{
		itor->second.clear();
	}
}

const string CPacketStatisticMap::toString() const
{
	string strStream;
	string indent = "   ";
	map<ushort, MetaTraffic>::const_iterator itor = m_statisticMap.begin();
	for( ; itor != m_statisticMap.end(); ++itor)
	{
		strStream.append(CommonUtil::itoa(itor->first));
		strStream.append(indent);
		strStream.append(itor->second.toString());
	}
	return strStream;
}

