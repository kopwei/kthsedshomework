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
	unsigned int src_key = CIPHeaderUtil::ConvertIPToInt ( &srcAddr );
	//unsigned long long newkey = CIPHeaderUtil::ConvertMacToInt64(&(pPacketDigest->getDestEtherAddress()));

	// First try to find if there is a match
	StatisticMap::iterator itor = m_pSubscriberMap->find ( src_key );
	bool bSrcFound = m_pSubscriberMap->end() != itor ? true : false;

	// Lock the map and add specific values;
	pthread_mutex_lock ( &Locks::packetMap_lock );
	if ( bSrcFound )
	{
		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()
	}
	else
	{
		CSubscriberStatistic pSubscriber( src_key );
		pSubscriber.AddNewPacket ( pPacketDigest );
		EABASSERT ( rs );
		m_pSubscriberMap->insert ( pair<unsigned int, CSubscriberStatistic> ( src_key, pSubscriber ) );

	}
	pthread_mutex_unlock ( &Locks::packetMap_lock );

	// Try to find if there is a destination match
	in_addr dstAddr = pPacketDigest->getDestAddress();
	unsigned int dst_key = CIPHeaderUtil::ConvertIPToInt ( &dstAddr );

	itor = m_pSubscriberMap->find ( dst_key );
	if ( m_pSubscriberMap->end() != itor )
	{
		// Lock the map and add specific values;
		pthread_mutex_lock ( &Locks::packetMap_lock );
		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()
		pthread_mutex_unlock ( &Locks::packetMap_lock );
	}
	return rs;
}

ResultEnum CSubscriberAnalyzedResult::PrintInfoToFile(StatisticMap* pStatisticMap)
{
	string directory = "trafficresult\\";
	string datestr = GetTimeStr(false);
	string fileName = directory.append(datestr);
	
	return eNotImplemented;
}

