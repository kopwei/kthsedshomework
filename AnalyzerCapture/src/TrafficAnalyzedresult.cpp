/**
 *   TrafficAnalyzedresult.cpp
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
#include "TrafficAnalyzedresult.h"
#include "locks.h"

#include "ipheaderutil.h"
#include "PacketDigest.h"
#include "macro.h"
#include "hashtab.h"
#include "analyserpxAggreg.h"

#include <netinet/in.h>
#include <fstream>


CTrafficAnalyzedResult::CTrafficAnalyzedResult()
 : CAnalyzedResult()
{
	m_pCounter = &m_traffic_counter;
}


CTrafficAnalyzedResult::~CTrafficAnalyzedResult()
{
}

ResultEnum CTrafficAnalyzedResult::PrintResult()
{
	if (&m_traffic_counter == m_pCounter)
	{
		m_pCounter = &m_temp_traffic_counter;
		PrintInfoToFile(&m_traffic_counter);
	}
	else
	{
		m_pCounter = &m_traffic_counter;
		PrintInfoToFile(&m_temp_traffic_counter);
	}
}

ResultEnum CTrafficAnalyzedResult::AddPacketToMap( const CPacketDigest* pPacketDigest )
{
	ResultEnum rs = eOK;
	++m_pCounter->frame_number;
	m_pCounter->volume += pPacketDigest->getPacketSize();
	// Create a new subscriber
	in_addr srcAddr = pPacketDigest->getSrcAddress();
	int src_key = CIPHeaderUtil::ConvertIPToInt ( &srcAddr );

	// First try to find if there is a match
	StatisticMap::iterator itor = m_mapSubscriberStat.find ( src_key );
	bool bSrcFound = m_mapSubscriberStat.end() != itor ? true : false;

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
		m_mapSubscriberStat.insert ( pair<unsigned int, CSubscriberStatistic> ( src_key, pSubscriber ) );
		if (IsSubscriber(src_key))
		{
			++m_pCounter->user_number;
		}		
	}
	pthread_mutex_unlock ( &Locks::packetMap_lock );

	// Try to find if there is a destination match
	in_addr dstAddr = pPacketDigest->getDestAddress();
	unsigned int dst_key = CIPHeaderUtil::ConvertIPToInt ( &dstAddr );

	itor = m_mapSubscriberStat.find ( dst_key );
	if ( m_mapSubscriberStat.end() != itor )
	{
		// Lock the map and add specific values;
		pthread_mutex_lock ( &Locks::packetMap_lock );
		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()
		pthread_mutex_unlock ( &Locks::packetMap_lock );
	}
	return rs;
}

ResultEnum CTrafficAnalyzedResult::AddNewPacketInfo( const CPacketDigest* pDigest )
{
	ResultEnum rs = AddPacketToMap(pDigest);
	rs = m_totalPacketStatistic.AddPacketInfo(pDigest);
	return rs;
}

bool CTrafficAnalyzedResult::IsSubscriber( const int ip_addr ) const
{
	// TODO: Need implementation here
	return true;
}

ResultEnum CTrafficAnalyzedResult::PrintInfoToFile( TrafficCounter* pCounter )
{
	string datestr = GetTimeStr(false);
	ofstream ofile ( "traffic.ret", ios::binary | ios::app );
	string indent = "  ";
	int iFlowNumber = HashTableUtil::num_hash_entries(CAnalyzerAggregator::test_table);
	ofile << datestr << indent;
	//cout << "date printed ..."<<endl;
	ofile << pCounter->frame_number << indent << pCounter->volume << indent << pCounter->user_number << indent << iFlowNumber << endl;
	pCounter->clear();
}

ResultEnum CTrafficAnalyzedResult::PrintDailyResult()
{
	//m_totalPacketStatistic.
}


