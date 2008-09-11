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


//#include "ipheaderutil.h"
#include "PacketDigest.h"
#include "macro.h"
#include "hashtab.h"
#include "analyserpxAggreg.h"
#include "MetaTraffic.h"

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



ResultEnum CTrafficAnalyzedResult::AddNewPacketInfo( const CPacketDigest* pDigest )
{
	ResultEnum rs = eOK;// AddPacketToMap(pDigest);
	
	++m_pCounter->packetnumber;
	m_pCounter->trafficvolume += pDigest->getPacketSize();
	
	//if (IsSubscriber(src_key))
	//{
	//	++m_pCounter->user_number;
	//}		
	
	rs = m_totalPacketStatistic.AddPacketInfo(pDigest);
	return rs;
}

bool CTrafficAnalyzedResult::IsSubscriber( const int ip_addr ) const
{
	// TODO: Need implementation here
	return true;
}

ResultEnum CTrafficAnalyzedResult::PrintInfoToFile( MetaTraffic* pTraffic )
{
	string datestr = GetTimeStr(false);
	ofstream ofile ( "traffic.ret", ios_base::app );
	string indent = "  ";
	int iFlowNumber = HashTableUtil::num_hash_entries(CAnalyzerAggregator::test_table);
	ofile << datestr << indent;
	//cout << "date printed ..."<<endl;
	ofile << pTraffic->packetnumber << indent << pTraffic->trafficvolume << indent << iFlowNumber << endl;
	ofile.close();
	pTraffic->clear();
}


