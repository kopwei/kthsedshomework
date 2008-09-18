/**
 *   SubscriberStatistic.cpp
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

#include "SubscriberStatistic.h"
#include "PacketDigest.h"
#include "ipheaderutil.h"
#include "macro.h"

#include <iostream>

using namespace std;

CSubscriberStatistic::CSubscriberStatistic ( const unsigned long long llMacAddress, const unsigned int ipAddress ) :
		m_macAddress ( llMacAddress ),
		m_ipAddress ( ipAddress )
{
}

CSubscriberStatistic::~CSubscriberStatistic ( void )
{
}


CSubscriberStatistic::CSubscriberStatistic ( const CSubscriberStatistic& subStat )
		:m_macAddress ( subStat.m_macAddress ),
		m_ipAddress ( subStat.m_ipAddress ),
		m_uploadPacketStatistic ( subStat.m_uploadPacketStatistic ),
		m_downloadPacketStatistic ( subStat.m_downloadPacketStatistic )
{}


ResultEnum CSubscriberStatistic::AddNewPacket ( const CPacketDigest* pPacketDigest )
{
	ResultEnum rs = eOK;
	in_addr src_addr = pPacketDigest->getSrcAddress();
	bool bIsSrcPacket = CIPHeaderUtil::ConvertIPToInt ( &src_addr ) == m_ipAddress ? true : false;

	if ( bIsSrcPacket )
	{
		rs = m_uploadPacketStatistic.AddPacketInfo ( pPacketDigest );
		EABASSERT ( rs == eOK );
	}
	else
	{
		rs = m_downloadPacketStatistic.AddPacketInfo ( pPacketDigest );
		EABASSERT ( rs == eOK );
	}

	return rs;
}

const string CSubscriberStatistic::toString() const
{
	string indent = "   ";
	stringstream strStream;
	strStream << m_macAddress << indent << m_ipAddress << m_uploadPacketStatistic.toString() << indent << m_downloadPacketStatistic.toString();
	return strStream.str();
}


