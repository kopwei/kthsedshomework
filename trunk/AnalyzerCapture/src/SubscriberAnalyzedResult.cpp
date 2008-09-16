/**
 *   SubscriberAnalyzedResult.cpp
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
#include "SubscriberAnalyzedResult.h"

#include "ipheaderutil.h"
#include "PacketDigest.h"
#include "locks.h"
#include "macro.h"
#include <netinet/in.h>
#include <fstream>

CSubscriberAnalyzedResult::CSubscriberAnalyzedResult()
	: CAnalyzedResult(), m_pSubscriberMap(NULL)
{
	m_pSubscriberMap = &m_mapSubscriberStat;
}


CSubscriberAnalyzedResult::~CSubscriberAnalyzedResult()
{
}

ResultEnum CSubscriberAnalyzedResult::AddNewPacketInfo(const CPacketDigest* pDigest)
{
	return AddPacketToMap(pDigest);
}

ResultEnum CSubscriberAnalyzedResult::PrintResult()
{
	ResultEnum rs = eOK;
	if (m_pSubscriberMap == &m_mapSubscriberStat)
	{
		m_pSubscriberMap = &m_map_tempSubscriberStat;
		rs = PrintInfoToFile(&m_mapSubscriberStat);
		EABASSERT ( rs );
	}
	else
	{
		m_pSubscriberMap = &m_mapSubscriberStat;
		rs = PrintInfoToFile(&m_map_tempSubscriberStat);
		EABASSERT ( rs );
	}
	return rs;
}


ResultEnum CSubscriberAnalyzedResult::AddPacketToMap( const CPacketDigest* pPacketDigest )
{
	ResultEnum rs = eOK;
	
	// Create a new subscriber
	in_addr srcAddr = pPacketDigest->getSrcAddress();
	unsigned int srcIp = CIPHeaderUtil::ConvertIPToInt ( &srcAddr );
	ether_addr macSrcAddr = pPacketDigest->getSrcEtherAddress();
	unsigned long long src_key = CIPHeaderUtil::ConvertMacToInt64(&macSrcAddr);

	// First try to find if there is a match
	// Lock the map and add specific values;
	pthread_mutex_lock ( &Locks::packetMap_lock );
	
	SubscriberStatisticMap::iterator itor = m_pSubscriberMap->find ( srcIp );
	bool bSrcFound = m_pSubscriberMap->end() != itor ? true : false;
	
	if ( bSrcFound )
	{
		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()
	}
	else
	{
		CSubscriberStatistic pSubscriber( srcIp, src_key);
		pSubscriber.AddNewPacket ( pPacketDigest );
		EABASSERT ( rs );
		m_pSubscriberMap->insert ( pair<unsigned long long, CSubscriberStatistic> ( srcIp, pSubscriber ) );

	}
	pthread_mutex_unlock ( &Locks::packetMap_lock );

	// Try to find if there is a destination match
	in_addr dstAddr = pPacketDigest->getDestAddress();
	unsigned int dstIp = CIPHeaderUtil::ConvertIPToInt ( &dstAddr );
	ether_addr macDestAddr = pPacketDigest->getSrcEtherAddress();
	unsigned long long dst_key = CIPHeaderUtil::ConvertMacToInt64(&macDestAddr);
	
	// Lock the map and add specific values;
	pthread_mutex_lock ( &Locks::packetMap_lock );
	itor = m_pSubscriberMap->find ( dstIp );
	if ( m_pSubscriberMap->end() != itor )
	{		
		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()		
	}
	pthread_mutex_unlock ( &Locks::packetMap_lock );
	return rs;
}

ResultEnum CSubscriberAnalyzedResult::PrintInfoToFile(SubscriberStatisticMap* pStatisticMap)
{
	string directory = "TrafficResult/";
	string datestr = GetTimeStr(false);
	string fileName = directory.append(datestr);
	
	ofstream ofile(fileName.c_str(), ios_base::trunc);
	SubscriberStatisticMap::const_iterator itor = pStatisticMap->begin();
	for( ; itor != pStatisticMap->end() ; ++itor)
	{
		ofile << itor->second.toString() << endl;
	}
	ofile.close();
	pStatisticMap->clear();
	return eOK;
}

