/**
 *   PacketStatistician.h	
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

#ifndef _PACKET_STATISTICIAN_
#define _PACKET_STATISTICIAN_


#include <map>
#include "analyserpxAggreg.h"
#include "PacketStatistic.h"
#include "SubscriberStatistic.h"
#include "PayloadLengthResult.h"
#include "resultrecorder.h"
#include "resultenum.h"


using namespace std;

class CUserInputParams;
struct tm;

//////////////////////////////////////////////////////////////////////////
/**
 *	This class is a class used for analyzing the packets
 */
class CPacketStatistician
{
	

public:
	CPacketStatistician(void);
	~CPacketStatistician(void);

	/**
	 *	This method will be called 
	 */
	ResultEnum			AddNewPacketInfo(const CPacketDigest* pPacketDigest);
	
	static void*		PacketStatisticTimeOut(void* pArg);
	
	void				PrintStatisticResult(const tm* t);

	//StatisticMap		GetStatisticMap() const {return s_mapSubscriberStat;}

	//void				SetInputParams(CUserInputParams* pParams);

private:
	
	/**
	 *	This method is used to record current info 
	 */
	//static ResultEnum	RecordStatisticResult(const CUserInputParams* pParams);
	
	/*
	 *	Avoid bitwise copy
	 */
	CPacketStatistician(const CPacketStatistician&);
	CPacketStatistician& operator = (const CPacketStatistician&);


//	CPacketStatistic		m_totalPacketStatistic;
//	StatisticMap			m_mapSubscriberStat;
	
	//static CResultRecorder		s_resultRecorder;
	//static time_t			s_recordingTime;
	
	CPayloadLengthResult	m_payloadLengthResult;

	CTrafficAnalyzedResult  m_trafficResult;

	CUserInputParams*		m_pInputParams;
};

#endif
