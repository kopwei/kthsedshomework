/**
 *   FlowAnalyzedResult.cpp
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
#include "FlowAnalyzedResult.h"
#include "classifier.h"
#include "flow.pb.h"

#include <fstream>

CFlowAnalyzedResult::CFlowAnalyzedResult()
{
}


CFlowAnalyzedResult::~CFlowAnalyzedResult()
{
}

ResultEnum CFlowAnalyzedResult::AddNewFlowInfo(flow_t* flow)
{
	u_short proto = flow->class_proto();
	// add the distribution
	//m_distributionMap.find(proto)->second += 1;
	
	// add the total 
	FlowDigestMap::iterator itor = m_digestMap.find ( proto );
	if ( m_digestMap.end() != itor )
	{
		itor->second.AddNewTraffic(flow->n_frames(), flow->n_bytes());
	}
	else
	{
		MetaTraffic meta;
		meta.AddNewTraffic(flow->n_frames(), flow->n_bytes());
		m_digestMap.insert ( pair<u_short, MetaTraffic> ( proto, meta ) );
	}
	
	return eOK;
}

ResultEnum CFlowAnalyzedResult::ProcessFlowMap(const FlowMap* pFlowMap)
{
	ResultEnum rs = eOK;
	//TODO
	return rs;
}

ResultEnum CFlowAnalyzedResult::PrintResult()
{
	string directory = "FlowResult/";
	string datestr = GetTimeStr(false);
	string fileNameStr = directory.append(datestr);
	string indent = "   ";
	ofstream ofile(fileNameStr.c_str(), ios_base::trunc);
//	FlowTypeDistributionMap::iterator itor = m_distributionMap.begin();
//	for( ;itor != m_distributionMap.end() ;++itor )
	{
//		ofile << itor->first << indent << itor ->second << indent;
	}
	ofile.close();
}


ResultEnum CFlowAnalyzedResult::PrintTotalResult()
{
	
}

