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
#include "Flow.h"
#include "analyserpxFlow.h"

#include <iostream>
#include <fstream>

CFlowAnalyzedResult::CFlowAnalyzedResult()
{
}


CFlowAnalyzedResult::~CFlowAnalyzedResult()
{
}

ResultEnum CFlowAnalyzedResult::AddNewFlowInfo(const flow_t* flow)
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
	
	m_totalStatisticMap.AddNewFlow(flow);
	
	return eOK;
}

ResultEnum CFlowAnalyzedResult::ProcessFlowMap(const FlowMap* pFlowMap)
{
	ResultEnum rs = eOK;
	//m_statisticMap.clear();
	FlowMap::const_iterator itor = pFlowMap->begin();
	for( ;itor != pFlowMap->end() ; ++itor)
	{
		m_statisticMap.AddNewFlow(itor->second);
		//AddNewFlowInfo(itor->second);
	}
	return rs;
}

ResultEnum CFlowAnalyzedResult::PrintResult()
{
	return PrintCurrentResultToFile();
}


ResultEnum CFlowAnalyzedResult::PrintTotalResult()
{
	string indent = "   ";
	string fileName = "FlowDigestResult.ret";
	ofstream ofile(fileName.c_str(), ios_base::trunc);
	unsigned long long totalPacket = 0;
	unsigned long long totalVolume = 0;
	FlowDigestMap::const_iterator const_itor;
	for (const_itor = m_digestMap.begin(); const_itor != m_digestMap.end(); ++const_itor)
	{
		totalPacket += const_itor->second.GetPacketNumber();
		totalVolume += const_itor->second.GetTrafficVolume();
	}
	cout << "Totally there are " << totalPacket << " frames" << endl;
	ofile << "Totally there are " << totalPacket << " frames" << endl;	
	cout << "Totally there are " << totalVolume << " bytes" << endl;
	ofile << "Totally there are " << totalVolume << " bytes" << endl;
	for (const_itor = m_digestMap.begin(); const_itor != m_digestMap.end(); ++const_itor)
	{
		double packetPercent = (double)(const_itor->second.GetPacketNumber()) / (double)totalPacket * 100;
		double volumePercent = (double)(const_itor->second.GetTrafficVolume()) / (double)totalVolume * 100;
		cout << const_itor->first << indent << CFlowUtil::get_protocolName(const_itor->first) << " : packet " 
				<< const_itor->second.GetPacketNumber() << indent <<packetPercent << "% "
				<< " : volume " << const_itor->second.GetTrafficVolume() << indent << volumePercent << "%" << endl;
		ofile << const_itor->first << indent << CFlowUtil::get_protocolName(const_itor->first) << " : packet " 
				<< const_itor->second.GetPacketNumber() << indent <<packetPercent << "% "
				<< " : volume " << const_itor->second.GetTrafficVolume() << indent << volumePercent << "%" << endl;
	}
	ofile.close();
	
	return PrintTotalResultToFile();
}

ResultEnum CFlowAnalyzedResult::PrintCurrentResultToFile()
{
	ResultEnum rs = eOK;
	string datestr = GetTimeStr(false);
	string dirName = "FlowResult/";
	string fileName = GetTimeStr(false);
	dirName.append(fileName);
	string indent = "   ";
	ofstream ofile(dirName.c_str(), ios_base::trunc);
	
	ofile << m_statisticMap.toString() << endl;
	ofile.close();
	m_statisticMap.clear();
	return rs;
}

ResultEnum CFlowAnalyzedResult::PrintTotalResultToFile()
{
	ResultEnum rs = eOK;
	string fileName = "FlowFinalResult.ret";
	ofstream ofile(fileName.c_str(), ios_base::trunc);
	ofile << m_totalStatisticMap.toString() << endl;
	ofile.close();
	m_totalStatisticMap.clear();
	return rs;
}

