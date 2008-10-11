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

//#include "locks.h"
#include "macro.h"
#include "ipheaderutil.h"
#include "resultrecorder.h"
#include "analyzer.h"
#include "userinputparams.h"
#include "locks.h"
//#include <netinet/in.h>
//#include <pthread.h>
#include <iostream>
#include <time.h>

//#include <fstream>

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
	if (NULL == pPacketDigest)
		return eEmptyPointer;
	ResultEnum rs = eOK;
	
	pthread_mutex_lock(&Locks::traffic_result_lock);
	rs = m_trafficResult.AddNewPacketInfo(pPacketDigest);
	pthread_mutex_unlock(&Locks::traffic_result_lock);
	EABASSERT ( rs );
	
	
	pthread_mutex_lock(&Locks::subscriber_result_lock);
	rs = m_subscriberResult.AddNewPacketInfo(pPacketDigest);
	pthread_mutex_unlock(&Locks::subscriber_result_lock);
	EABASSERT ( rs );
	
	//m_mapSubscriberStat.insert
	pthread_mutex_lock(&Locks::payload_result_lock);
	rs = m_payloadLengthResult.AddNewPacketInfo(pPacketDigest);
	pthread_mutex_unlock(&Locks::payload_result_lock);
	EABASSERT ( rs );
	
	
	//m_totalPacketStatistic.AddPacketInfo ( pPacketDigest );
}

//void CPacketStatistician::SetInputParams(CUserInputParams* pParams)
//{
//	m_pInputParams = pParams;
//}

void CPacketStatistician::PrintStatisticResult(const tm* t)
{
//	cout << "I received " << m_totalPacketStatistic.packetnumber() << " Packets" << endl;
//	cout << "The total volume number is " << m_totalPacketStatistic.trafficvolume() << " Bytes" << endl;
	
//	cout << "Totally there are " << m_mapSubscriberStat.size() << " number of subscribers" << endl;
	
//	map<unsigned int, CSubscriberStatistic>::iterator itor = m_mapSubscriberStat.begin();
//	for( ; itor != m_mapSubscriberStat.end(); ++itor )
//	{
//		itor->second.PrintSummary();
//	}
	//pthread_mutex_lock(&Locks::statistician_lock);
	m_payloadLengthResult.setEndTime(*t);
	m_payloadLengthResult.PrintResult();
	//pthread_mutex_unlock(&Locks::statistician_lock);
	
	//pthread_mutex_lock(&Locks::statistician_lock);
	m_trafficResult.setEndTime(*t);
	m_trafficResult.PrintResult();
	//pthread_mutex_unlock(&Locks::statistician_lock);
	
	//pthread_mutex_lock(&Locks::statistician_lock);
	m_subscriberResult.setEndTime(*t);
	m_subscriberResult.PrintResult();
	//pthread_mutex_unlock(&Locks::statistician_lock);
	
	// Only for testing
	//RecordStatisticResult(NULL);
	
}

void CPacketStatistician::PrintFinalResult()
{
	m_subscriberResult.PrintFinalResult();
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
