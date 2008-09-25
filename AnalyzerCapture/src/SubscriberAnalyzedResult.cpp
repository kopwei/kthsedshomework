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
#include "UserUtil.h"
#include <netinet/in.h>
#include <fstream>

CSubscriberAnalyzedResult::CSubscriberAnalyzedResult()
		: CAnalyzedResult(), m_pSubscriberMap ( NULL )
{
	m_pSubscriberMap = &m_mapSubscriberStat;
}


CSubscriberAnalyzedResult::~CSubscriberAnalyzedResult()
{
}

ResultEnum CSubscriberAnalyzedResult::AddNewPacketInfo ( const CPacketDigest* pDigest )
{
	return AddPacketToMap ( pDigest );
}

ResultEnum CSubscriberAnalyzedResult::PrintResult()
{
	ResultEnum rs = eOK;
	string datestr = GetTimeStr ( false );
	if ( m_pSubscriberMap == &m_mapSubscriberStat )
	{
		m_pSubscriberMap = &m_map_tempSubscriberStat;
		rs = PrintInfoToFile ( &m_mapSubscriberStat, datestr);
		EABASSERT ( rs );
	}
	else
	{
		m_pSubscriberMap = &m_mapSubscriberStat;
		rs = PrintInfoToFile ( &m_map_tempSubscriberStat, datestr );
		EABASSERT ( rs );
	}
	return rs;
}


ResultEnum CSubscriberAnalyzedResult::AddPacketToMap ( const CPacketDigest* pPacketDigest )
{
	ResultEnum rs = eOK;

	// Create a new subscriber
	in_addr srcAddr = pPacketDigest->getSrcAddress();
	unsigned int srcIp = CIPHeaderUtil::ConvertIPToInt ( &srcAddr );
	ether_addr macSrcAddr = pPacketDigest->getSrcEtherAddress();
	unsigned long long src_MAC_key = CIPHeaderUtil::ConvertMacToInt64 ( &macSrcAddr );

	// Try to find if there is a destination match
	in_addr dstAddr = pPacketDigest->getDestAddress();
	unsigned int dstIp = CIPHeaderUtil::ConvertIPToInt ( &dstAddr );
	ether_addr macDestAddr = pPacketDigest->getDestEtherAddress();
	unsigned long long dst_MAC_key = CIPHeaderUtil::ConvertMacToInt64 ( &macDestAddr );

	if ( CUserUtil::IsUserUploaded ( src_MAC_key, dst_MAC_key, srcIp, dstIp ) )
	{
		DistributePacket(pPacketDigest, srcIp, src_MAC_key, false);
	}
	if ( CUserUtil::IsUserDownloaded(src_MAC_key, dst_MAC_key, srcIp, dstIp))
	{		
		DistributePacket(pPacketDigest, dstIp, dst_MAC_key, true);
	}
	return rs;
}

ResultEnum CSubscriberAnalyzedResult::DistributePacket(const CPacketDigest* pDigest, const uint ipAddr, const uint64 macAddr, const bool isDownload)
{
	ResultEnum rs = eOK;
	// Lock the map and add specific values;
	pthread_mutex_lock ( &Locks::packetMap_lock );
	rs = DistributePacketImpl(pDigest, ipAddr, macAddr, isDownload, m_pSubscriberMap);
	EABASSERT ( rs );
	pthread_mutex_unlock ( &Locks::packetMap_lock );
	
	pthread_mutex_lock(&Locks::total_subscriber_lock);
	rs = DistributePacketImpl(pDigest, ipAddr, macAddr, isDownload, &m_totalSubscriberStat);
	EABASSERT(rs);
	pthread_mutex_unlock(&Locks::total_subscriber_lock);
	return rs;
}

ResultEnum CSubscriberAnalyzedResult::DistributePacketImpl(const CPacketDigest* pDigest, const uint ipAddr, const uint64 macAddr, const bool isDownload, SubscriberStatisticMap* pStatisticMap)
{
	ResultEnum rs = eOK;
	SubscriberStatisticMap::iterator itor = pStatisticMap->find ( macAddr );
	bool bFound = pStatisticMap->end() != itor ? true : false;
	if ( bFound )
	{
		rs = ( itor->second ).AddNewPacket ( pDigest, isDownload);
		EABASSERT ( rs ); //ON_ERROR_RETURN()
	}
	else
	{
		CSubscriberStatistic pSubscriber ( macAddr , ipAddr);
		rs = pSubscriber.AddNewPacket ( pDigest , isDownload);
		EABASSERT ( rs );
		pStatisticMap->insert ( pair<uint64, CSubscriberStatistic> ( macAddr, pSubscriber ) );
	}
	return rs;
}

ResultEnum CSubscriberAnalyzedResult::PrintInfoToFile ( SubscriberStatisticMap* pStatisticMap, const string& fileName)
{
	string directory = "PacketResult/";
	
	string fileNameStr = directory.append ( fileName );
	string indent = "   ";
	ofstream ofile ( fileNameStr.c_str(), ios_base::trunc );
	SubscriberStatisticMap::const_iterator itor = pStatisticMap->begin();
	for ( ; itor != pStatisticMap->end() ; ++itor )
	{
		ofile << itor->second.toString() << endl;
	}
	ofile.close();
	pStatisticMap->clear();
	return eOK;
}

ResultEnum CSubscriberAnalyzedResult::PrintFinalResult()
{
	//TODO: 
	string fileName = "SubscriberStat.ret";
	ofstream ofile (fileName.c_str(), ios_base::trunc);
	SubscriberStatisticMap::const_iterator itor = m_totalSubscriberStat.begin();
	for (; itor != m_totalSubscriberStat.end(); ++itor)
	{
		ofile << itor->second.toString() << endl;
	}
	ofile.close();
	m_totalSubscriberStat.clear();
	
}

