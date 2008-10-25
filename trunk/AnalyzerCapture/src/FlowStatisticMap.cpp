/**
 *   FlowStatisticMap.cpp
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
#include "FlowStatisticMap.h"
#include "classifier.h"
#include "Flow.h"
#include "UserUtil.h"
#include "ipheaderutil.h"
//#include <sstream>



CFlowStatisticMap::CFlowStatisticMap()
{
	
}


CFlowStatisticMap::~CFlowStatisticMap()
{
}

void CFlowStatisticMap::AddNewFlow(const flow_t* pFlow)
{
	in_addr Ip = pFlow->src_ip();
	uint userIp = CIPHeaderUtil::ConvertIPToInt(&Ip);
	if (CUserUtil::IsUserIP(userIp))
	{
		UserFlowStatMap::iterator itor =  m_statMap.find(userIp);
		if (itor != m_statMap.end())
		{
			itor->second.AddNewFlow(pFlow);
		}
		else
		{
			CFlowTypeMap typeMap;
			typeMap.AddNewFlow(pFlow);
			m_statMap.insert(pair<uint, CFlowTypeMap>(userIp, typeMap));
		}
	}
}



const std::string CFlowStatisticMap::toString()
{
	std::string strStream;
	std::string indent = "   ";
	UserFlowStatMap::const_iterator itor = m_statMap.begin();
	for (; itor != m_statMap.end(); ++itor)
	{
		strStream.append(CIPHeaderUtil::ConvertIPToString(itor->first));
		strStream.append(indent);
		strStream.append(itor->second.toString());
		strStream.append("\n");
	}
//	m_statMap.clear();
	return strStream;
}

void CFlowStatisticMap::clear()
{	
	static int count = 0;
	count ++;
	if (count == 4)
	{
		int i = 0;
	}
	m_statMap.clear();
}


