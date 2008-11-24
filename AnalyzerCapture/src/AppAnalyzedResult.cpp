/**
 *   AppAnalyzedResult.cpp
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
#include "AppAnalyzedResult.h"
#include "PacketDigest.h"

#include <fstream>

using namespace std;

CAppAnalyzedResult::CAppAnalyzedResult()
 : CAnalyzedResult(), m_pAppMap(NULL)
{
	m_pAppMap = &m_appMap;
}


CAppAnalyzedResult::~CAppAnalyzedResult()
{
}

ResultEnum CAppAnalyzedResult::AddNewPacketInfo(const CPacketDigest* pDigest )
{
	//TODO
	unsigned short appId = pDigest->getProtocolClassification();
	map<unsigned short, CAppStatistic>::iterator itor = m_pAppMap->find(appId);
	map<unsigned short, CAppStatistic>::iterator totalitor = m_totalAppMap.find(appId);
	if ( itor != m_pAppMap->end())
	{
		itor->second.AddNewPacket(pDigest);
		totalitor->second.AddNewPacket(pDigest);
	}
	else
	{
		CAppStatistic appStat;
		appStat.AddNewPacket(pDigest);
		m_pAppMap->insert(pair<unsigned short, CAppStatistic>(appId, appStat) );
		if (totalitor != m_totalAppMap.end())
		{
			totalitor->second.AddNewPacket(pDigest);
		}
		else
		{
			m_totalAppMap.insert(pair<unsigned short, CAppStatistic>(appId, appStat) );
		}
	}
	return eOK;
}

ResultEnum CAppAnalyzedResult::PrintResult()
{
	//TODO
	if(m_pAppMap == &m_appMap)
	{
		m_pAppMap = &m_tempAppMap;
		return PrintInfoToFile(&m_appMap);
	}
	else
	{
		m_pAppMap = &m_appMap;
		return PrintInfoToFile(&m_tempAppMap);
	}
}

ResultEnum CAppAnalyzedResult::PrintInfoToFile(std::map<unsigned short, CAppStatistic>* pAppMap)
{
	string directory = "AppResult/";
	string datestr = GetTimeStr ( false );
	string fileNameStr = directory.append ( datestr );
	string indent = "   ";
	ofstream ofile ( fileNameStr.c_str(), ios_base::trunc );
	map<unsigned short, CAppStatistic>::const_iterator itor = pAppMap->begin();
	for ( ; itor != pAppMap->end() ; ++itor )
	{
		ofile<< itor->first<< indent << itor->second.toString() << endl;
	}
	ofile.close();
	pAppMap->clear();
	return eOK;
}


ResultEnum CAppAnalyzedResult::PrintFinalResult()
{
	string fileName = "appresult.ret";
	string indent = "   ";
	ofstream ofile ( fileName.c_str(), ios_base::trunc );
	map<unsigned short, CAppStatistic>::const_iterator itor = m_totalAppMap.begin();
	for ( ; itor != m_totalAppMap.end() ; ++itor )
	{
		ofile<< itor->first<< indent << itor->second.toString() << endl;
	}
	ofile.close();
	m_totalAppMap.clear();
	return eOK;
}

