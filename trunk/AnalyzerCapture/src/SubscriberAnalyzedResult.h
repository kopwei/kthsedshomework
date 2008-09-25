/**
 *   SubscriberAnalyzedResult.h
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
#ifndef SUBSCRIBERANALYZEDRESULT_H
#define SUBSCRIBERANALYZEDRESULT_H

#include <AnalyzedResult.h>

#include "SubscriberStatistic.h"
#include <map>

using namespace std;

typedef unsigned long long uint64;
typedef map<uint64, CSubscriberStatistic> SubscriberStatisticMap;

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CSubscriberAnalyzedResult : public CAnalyzedResult
{
public:
    CSubscriberAnalyzedResult();

    ~CSubscriberAnalyzedResult();
	
	ResultEnum AddNewPacketInfo(const CPacketDigest* pDigest);

	ResultEnum PrintResult();
	
	ResultEnum PrintFinalResult();
	
private:
	/**
	 * Avoid bitwise copy
	 */
	CSubscriberAnalyzedResult(const CSubscriberAnalyzedResult&);
	const CSubscriberAnalyzedResult& operator=(const CSubscriberAnalyzedResult&);
	
	/**
 	 *	This method is used to store the information of new packet
	 */
	ResultEnum				AddPacketToMap(const CPacketDigest* pPacketDigest);
	
	ResultEnum 				DistributePacket(const CPacketDigest* pDigest, const uint ipAddr, const uint64 macAddr, const bool isDownload);
	
	ResultEnum				DistributePacketImpl(const CPacketDigest* pDigest, const uint ipAddr, const uint64 macAddr, const bool isDownload, SubscriberStatisticMap* pStatisticMap);
	
	ResultEnum				PrintInfoToFile(SubscriberStatisticMap* pStatisticMap, const string& fileName);
	
	
	SubscriberStatisticMap			m_mapSubscriberStat;
	SubscriberStatisticMap			m_map_tempSubscriberStat;
	
	SubscriberStatisticMap*			m_pSubscriberMap;
	
	SubscriberStatisticMap			m_totalSubscriberStat;

};

#endif
