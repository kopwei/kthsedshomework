/**
 *   PacketStatistic.h
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

#ifndef _PACKET_STATISTIC_
#define _PACKET_STATISTIC_

#include "resultenum.h"
#include "MetaTraffic.h"
#include "PacketStatisticMap.h"
#include <string>
#include <map>

using namespace std;

class CPacketDigest;

class CPacketStatistic
{
		typedef unsigned long long _uint_64;

	public:
		CPacketStatistic ( void );
		~CPacketStatistic ( void );

		CPacketStatistic ( const CPacketStatistic& );

		/*
		 *	This method is used to add a new packet digest into the info base
		 */
		ResultEnum		AddPacketInfo ( const CPacketDigest* pDigest );

		_uint_64 packetnumber() const {return m_packetnumber;}
		_uint_64 trafficvolume() const {return m_trafficvolume;}
//		_uint_64 emptypacketnumber() const {return m_emptypacketnumber;}
		_uint_64 tcppacketnumber() const {return m_tcppacketnumber;}
		_uint_64 tcptrafficvolume() const {return m_tcptrafficvolume;}
		_uint_64 udppacketnumber() const {return m_udppacketnumber;}
		_uint_64 udptrafficvolume() const {return m_udptrafficvolume;}
//		_uint_64 p2ppacketnumber() const {return m_p2ppacketnumber;}
//		_uint_64 p2ptrafficvolume() const {return m_p2ptrafficvolume;}
//		_uint_64 httppacketnumber() const {return m_httppacketnumber;}
//		_uint_64 httptrafficvolume() const {return m_httptrafficvolume;}
//		_uint_64 unidentifiedpacketnumber() const {return m_unidentifiedpacketnumber;}
//		_uint_64 unidentifiedtrafficvolume() const {return m_unidentifiedtrafficvolume;}
		
		const string GetStatisticString();


	private:

		/*
		 *	My own private functions
		 */
		/*
		 *	This method will distribute the packet by its protocol e.g. TCP, UDP, etc.
		 */
		ResultEnum distributeByProtocol ( const unsigned short sProtocolId, const unsigned int iPacketSize );

		/*
		 *	This method will distribute the packet by its classification, e.g. P2P, Web, etc.
		 */
		ResultEnum distributedByClassification ( const unsigned short sClassId, const unsigned int iPacketSize );


		_uint_64 m_packetnumber;
		_uint_64 m_trafficvolume;
//		_uint_64 m_emptypacketnumber;
		_uint_64 m_tcppacketnumber;
		_uint_64 m_tcptrafficvolume;
		_uint_64 m_udppacketnumber;
		_uint_64 m_udptrafficvolume;
//		_uint_64 m_p2ppacketnumber;
//		_uint_64 m_p2ptrafficvolume;
//		_uint_64 m_httppacketnumber;
//		_uint_64 m_httptrafficvolume;
//		_uint_64 m_unidentifiedpacketnumber;
//		_uint_64 m_unidentifiedtrafficvolume;
		
		// 
		CPacketStatisticMap m_trafficMap;
		
};

#endif
