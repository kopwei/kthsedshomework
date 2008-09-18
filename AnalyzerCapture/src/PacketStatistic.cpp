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
#include "ipheaderutil.h"
#include "classifier.h"
#include "macro.h"
#include "PacketDigest.h"


CPacketStatistic::CPacketStatistic( void ) 
{}

CPacketStatistic::~CPacketStatistic( void )
{}


ResultEnum CPacketStatistic::AddPacketInfo(const CPacketDigest* pDigest)
{
	ResultEnum rs = eOK;
	// Modify the total statistic
	m_totalTraffic.AddNewPacket(pDigest->getPacketSize());
	
	rs = distributeByProtocol(pDigest);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	rs = distributeByClassification(pDigest);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);
	
	rs = distributeByLocality(pDigest);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	// TODO: need more implementation here
	return rs;
}

ResultEnum CPacketStatistic::distributeByProtocol(const CPacketDigest* pDigest)
{
	ResultEnum rs = eOK;
	if (PROTO_ID_TCP == pDigest->getProtocol())
	{
		m_tcpTraffic.AddNewPacket(pDigest->getPacketSize());
	}
	else if (PROTO_ID_UDP == pDigest->getProtocol())
	{
		m_udpTraffic.AddNewPacket(pDigest->getPacketSize());
	}	
	// TODO: May need more implementation here
	return rs;
} 

ResultEnum CPacketStatistic::distributeByClassification(const CPacketDigest* pDigest)
{
	ResultEnum rs = eOK;
	// If it is p2p packet 
	MetaTraffic meta;
	meta.AddNewPacket(pDigest->getPacketSize());
	m_trafficMap.insert(pDigest->getProtocolClassification(), meta);
	
	return rs;
}

ResultEnum CPacketStatistic::distributeByLocality(const CPacketDigest* pDigest)
{
	in_addr src_addr = pDigest->getSrcAddress();
	in_addr dst_addr = pDigest->getDestAddress();
	uint srcAddr = CIPHeaderUtil::ConvertIPToInt(&src_addr);
	uint dstAddr = CIPHeaderUtil::ConvertIPToInt(&dst_addr);
	
	if (isUser(srcAddr) && isUser(dstAddr))
	{
		m_localTraffic.AddNewPacket(pDigest->getPacketSize());
	}
	else
	{
		m_illocalTraffic.AddNewPacket(pDigest->getPacketSize());
	}
}

bool CPacketStatistic::isUser(const uint ipAddress)
{
	//TODO: need implementation here
	return true;
}


const string CPacketStatistic::toString() const
{
	string indent = "   ";
	stringstream strStream;
	strStream << m_totalTraffic.toString() << m_localTraffic.toString() << m_trafficMap.toString();
	return strStream.str();
}

void CPacketStatistic::clear()
{
	m_totalTraffic.clear();
	m_tcpTraffic.clear();
	m_udpTraffic.clear();
	m_localTraffic.clear();
	m_illocalTraffic.clear();
	m_trafficMap.clear();
}

