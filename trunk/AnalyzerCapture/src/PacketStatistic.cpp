/**
 *   PacketStatistic.cpp	
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
 *   along with this program; if not, write to the                         
 *   Copyright (C) 2008 by Ericsson                                        
 */

#include "PacketStatistic.h"
//#include "PacketDigest.h"
#include "classifier.h"

CPacketStatistic::CPacketStatistic(void) :
		m_llTrafficVolume(0), m_iPacketNumber(0)
{
}

CPacketStatistic::~CPacketStatistic(void)
{
}

unsigned int CPacketStatistic::GetPacketNumber() const
{
	return m_iPacketNumber;
}

unsigned long long CPacketStatistic::GetTrafficVolume() const
{
	return m_llTrafficVolume;
}

ResultEnum CPacketStatistic::AddPacketInfo(const CPacketDigest* pDigest )
{
	ResultEnum rs = eOK;
	++m_iPacketNumber;
	m_llTrafficVolume += pDigest->getPacketSize();
	if (CClassifier::IsP2P(pDigest->getProtocolClassification()))
	{
		++m_iP2PPacketNumber;
		m_llP2PTrafficVolume += pDigest->getPacketSize();
	}
	return rs;
}

unsigned int CPacketStatistic::GetP2PPacketNumber() const
{
	return m_iP2PPacketNumber;
}

unsigned long long CPacketStatistic::GetP2PTrafficVolume() const
{
	return m_llP2PTrafficVolume;
}
