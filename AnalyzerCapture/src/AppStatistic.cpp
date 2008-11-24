/**
 *   AppStatistic.cpp
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
#include "AppStatistic.h"
#include "PacketDigest.h"
#include "CommonUtil.h"

using namespace std;

CAppStatistic::CAppStatistic()
{
	for(int i = 0 ; i < APP_PAYLOAD_MAXSIZE; i++)
	{
		m_payloadLength[i] = 0;
	}
}


CAppStatistic::~CAppStatistic()
{
}


ResultEnum CAppStatistic::AddNewPacket(const CPacketDigest* pDigest)
{
	unsigned int packetSize = pDigest->getPacketSize();
	m_totalTraffic.AddNewTraffic(1, packetSize);
	if (pDigest->getLocality() == eLocal)
	{
		m_localTraffic.AddNewTraffic(1, packetSize);
	}
	if(packetSize < APP_PAYLOAD_MAXSIZE)
		m_payloadLength[packetSize] += 1;
}

const std::string CAppStatistic::toString() const
{
	string str = m_totalTraffic.toString();
	str.append(m_localTraffic.toString());
	string indent = "   ";
	for (int i = 0 ; i < APP_PAYLOAD_MAXSIZE; i++)
	{		
		str.append(CommonUtil::itoa(m_payloadLength[i], 10));
		str.append(indent);
	}
	
	
	return str;
}
