/**
 *   PacketStatistic.cpp
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

#include "PacketStatistic.h"

#include "PacketDigest.h"
#include "classifier.h"
#include "macro.h"
#include <sstream>


CPacketStatistic::CPacketStatistic( void ) :
m_packetnumber(0), 
m_trafficvolume(0), 
//m_emptypacketnumber(0), 
m_tcppacketnumber(0),
m_tcptrafficvolume(0),
m_udppacketnumber(0),
m_udptrafficvolume(0)
//m_p2ppacketnumber(0),
//m_p2ptrafficvolume(0),
//m_httppacketnumber(0),
//m_httptrafficvolume(0),
//m_unidentifiedpacketnumber(0),
//m_unidentifiedtrafficvolume(0)
{}

CPacketStatistic::~CPacketStatistic( void )
{}

CPacketStatistic::CPacketStatistic(const CPacketStatistic& stat) :
m_packetnumber(stat.m_packetnumber), 
m_trafficvolume(stat.m_trafficvolume), 
//m_emptypacketnumber(stat.m_emptypacketnumber), 
m_tcppacketnumber(stat.m_tcppacketnumber),
m_tcptrafficvolume(stat.m_tcptrafficvolume),
m_udppacketnumber(stat.m_udppacketnumber),
m_udptrafficvolume(stat.m_udptrafficvolume),
m_trafficMap(stat.m_trafficMap)
//m_p2ppacketnumber(stat.m_p2ppacketnumber),
//m_p2ptrafficvolume(stat.m_p2ptrafficvolume),
//m_httppacketnumber(stat.m_httppacketnumber),
//m_httptrafficvolume(stat.m_httptrafficvolume),
//m_unidentifiedpacketnumber(stat.m_unidentifiedpacketnumber),
//m_unidentifiedtrafficvolume(stat.m_unidentifiedtrafficvolume)
{}

ResultEnum CPacketStatistic::AddPacketInfo(const CPacketDigest* pDigest)
{
	ResultEnum rs = eOK;
	unsigned int size = pDigest->getPacketSize();
	// Modify the total statistic
	++m_packetnumber;
	m_trafficvolume += size;

	u_short protocol = pDigest->getProtocol();
	rs = distributeByProtocol(protocol, size);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	u_short classfication = pDigest->getProtocolClassification();
	rs = distributedByClassification(classfication, size);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	// TODO: need more implementation here
	return rs;
}

ResultEnum CPacketStatistic::distributeByProtocol(const unsigned short sProtocolId, const unsigned int iPacketSize)
{
	ResultEnum rs = eOK;
	if (PROTO_ID_TCP == sProtocolId)
	{
		m_tcppacketnumber++;
		m_tcptrafficvolume += iPacketSize;
	}
	else if (PROTO_ID_UDP == sProtocolId)
	{
		m_udppacketnumber++;
		m_udptrafficvolume += iPacketSize;
	}	
	// TODO: May need more implementation here
	return rs;
} 

ResultEnum CPacketStatistic::distributedByClassification(const unsigned short sClassId, const unsigned int iPacketSize)
{
	ResultEnum rs = eOK;
	// If it is p2p packet 
	MetaTraffic meta;
	meta.packetnumber = 1;
	meta.packetnumber = iPacketSize;
	m_trafficMap.insert(sClassId, meta);
	
	return rs;
}

const string GetStatisticString()
{
	string indent = "   ";
	stringstream strStream;
	//strStream << 
	return strStream.str();
}

