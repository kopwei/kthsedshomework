/**
 *   PacketStatistician.cpp
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

#include "PacketStatistician.h"
#include "PacketDigest.h"
#include "locks.h"
#include "macro.h"
#include "ipheaderutil.h"

#include <netinet/in.h>
#include <pthread.h>
#include <iostream>

using namespace std;



/*
 * Re-definition
 */ 
map<unsigned int, CSubscriberStatistic> CPacketStatistician::m_mapSubscriberStat;
CResultRecorder CPacketStatistician::s_resultRecorder; 

CPacketStatistician::CPacketStatistician ( void )
{
}

CPacketStatistician::~CPacketStatistician ( void )
{
}

ResultEnum CPacketStatistician::AddNewPacketInfo ( const CPacketDigest* pPacketDigest )
{
	//m_subscriberStatistic.
	ResultEnum rs = eOK;
	rs = AddPacketToMap(pPacketDigest);
	EABASSERT ( rs );
	//m_mapSubscriberStat.insert

	m_totalPacketStatistic.AddPacketInfo ( pPacketDigest );
}

void CPacketStatistician::PrintStatisticResult()
{
	cout << "I received " << m_totalPacketStatistic.GetPacketNumber() << " Packets" << endl;
	cout << "The total volume number is " << m_totalPacketStatistic.GetTrafficVolume() << " Bytes" << endl;
	
	cout << "Totaly there are " << m_mapSubscriberStat.size() << " number of subscribers" << endl;
	
	map<unsigned int, CSubscriberStatistic>::iterator itor = m_mapSubscriberStat.begin();
	for( ; itor != m_mapSubscriberStat.end(); ++itor )
	{
		itor->second.PrintSummary();
	}
	
}

ResultEnum CPacketStatistician::AddPacketToMap ( const CPacketDigest* pPacketDigest )
{
	ResultEnum rs = eOK;
	// Create a new subscriber
	in_addr srcAddr = pPacketDigest->getSrcAddress();
	int src_key = CIPHeaderUtil::ConvertIPToInt ( &srcAddr );

	// First try to find if there is a match
	map<unsigned int, CSubscriberStatistic>::iterator itor = m_mapSubscriberStat.find ( src_key );
	bool bSrcFound = m_mapSubscriberStat.end() != itor ? true : false;

	// Lock the map and add specific values;
	while ( pthread_mutex_trylock ( &Locks::packetMap_lock ) != 0 )
		{}
	;
	if ( bSrcFound )
	{
		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()
	}
	else
	{
		CSubscriberStatistic* pSubscriber = new CSubscriberStatistic ( src_key );
		pSubscriber->AddNewPacket ( pPacketDigest );
		EABASSERT ( rs );
		m_mapSubscriberStat.insert ( pair<unsigned int, CSubscriberStatistic> ( src_key, *pSubscriber ) );
	}
	pthread_mutex_unlock ( &Locks::packetMap_lock );

	// Try to find if there is a destination match
	in_addr dstAddr = pPacketDigest->getDestAddress();
	unsigned int dst_key = CIPHeaderUtil::ConvertIPToInt ( &dstAddr );

	itor = m_mapSubscriberStat.find ( dst_key );
	if ( m_mapSubscriberStat.end() != itor )
	{
		// Lock the map and add specific values;
		while ( pthread_mutex_trylock ( &Locks::packetMap_lock ) != 0 )
			{}
		;

		rs = ( itor->second ).AddNewPacket ( pPacketDigest );
		EABASSERT ( rs ); //ON_ERROR_RETURN()
		pthread_mutex_unlock ( &Locks::packetMap_lock );
	}


	return rs;
}


void* CPacketStatistician::PacketStatisticTimeOut(void* pArg)
{
	// TODO: Need implementation here
	return NULL;
}
