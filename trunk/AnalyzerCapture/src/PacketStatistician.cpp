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
#include "resultrecorder.h"
#include "analyzer.h"
#include "userinputparams.h"

#include <netinet/in.h>
#include <pthread.h>
#include <iostream>
#include <time.h>

#include <fstream>

using namespace std;


/*
 * Re-definition
 */ 
//StatisticMap CPacketStatistician::m_mapSubscriberStat;
//CResultRecorder CPacketStatistician::s_resultRecorder; 
//time_t	CPacketStatistician::s_recordingTime;
//CPayloadLengthResult		CPacketStatistician::s_payloadLengthResult;

CPacketStatistician::CPacketStatistician ( void )
{
	//time(&s_recordingTime);
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
	m_payloadLengthResult.AddNewPacketInfo(pPacketDigest);
	m_totalPacketStatistic.AddPacketInfo ( pPacketDigest );
}

void CPacketStatistician::SetInputParams(CUserInputParams* pParams)
{
	m_pInputParams = pParams;
}

void CPacketStatistician::PrintStatisticResult()
{
	cout << "I received " << m_totalPacketStatistic.packetnumber() << " Packets" << endl;
	cout << "The total volume number is " << m_totalPacketStatistic.trafficvolume() << " Bytes" << endl;
	
	cout << "Totally there are " << m_mapSubscriberStat.size() << " number of subscribers" << endl;
	
	map<unsigned int, CSubscriberStatistic>::iterator itor = m_mapSubscriberStat.begin();
	for( ; itor != m_mapSubscriberStat.end(); ++itor )
	{
		itor->second.PrintSummary();
	}
	m_payloadLengthResult.PrintResult();
	
	// Only for testing
	//RecordStatisticResult(NULL);
	
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
		CSubscriberStatistic pSubscriber( src_key );
		pSubscriber.AddNewPacket ( pPacketDigest );
		EABASSERT ( rs );
		m_mapSubscriberStat.insert ( pair<unsigned int, CSubscriberStatistic> ( src_key, pSubscriber ) );
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
//	// TODO: Need implementation here
//	CUserInputParams* pParams = (CUserInputParams *)(pArg);
//	EABASSERT(NULL != pParams); ON_ERROR_RETURN(NULL == pParams, NULL);
//	//CResultRecorder recorder;
//	// TODO: Here we have to sleep for a certain amount of time and then start to
//	// record something
//	while (CAnalyzer::tFlag)
//	{
//		sleep(30);
//		pthread_mutex_lock ( &Locks::packetMap_lock );
//		CResultRecorder recorder(m_mapSubscriberStat);
//		m_mapSubscriberStat.clear();
//		pthread_mutex_unlock ( &Locks::packetMap_lock );
//		
//		time_t currentTime;
//		time(&currentTime);
//		RecordParameter parameter(eRecordToDatabase, s_recordingTime, currentTime);				
//		ResultEnum rs = recorder.RecordTimeOutResult(&parameter);
//		EABASSERT(eOK == rs); ON_ERROR_RETURN(eOK != rs, NULL);		
//		s_recordingTime = currentTime;
//	}
//
//
//
	return NULL;
}

//ResultEnum CPacketStatistician::RecordStatisticResult( const CUserInputParams* pParams )
//{
//	ResultEnum rs = eNotImplemented;
//	//// TODO: Need implementation here 
//	//char* fileName = "packetLength";
//	//ofstream ostream(fileName);
//	//for (int i = 0; i < MAXSIZE; i++)
//	//{
//	//	ostream << s_packetLengthDist[i] << " ";
//	//}
//	return rs;
//}