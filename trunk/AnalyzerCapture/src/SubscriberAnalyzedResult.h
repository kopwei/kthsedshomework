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

typedef  map<unsigned long long, CSubscriberStatistic> SubscriberStatisticMap;

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
	
	ResultEnum				PrintInfoToFile(SubscriberStatisticMap* pStatisticMap);
	
	
	SubscriberStatisticMap			m_mapSubscriberStat;
	SubscriberStatisticMap			m_map_tempSubscriberStat;
	
	SubscriberStatisticMap*			m_pSubscriberMap;

};

#endif
