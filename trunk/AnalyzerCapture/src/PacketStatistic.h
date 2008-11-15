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
#include <map>


class CPacketDigest;

using namespace std;


class CPacketStatistic
{
		typedef unsigned long long _uint_64;

	public:
		CPacketStatistic ( void );
		~CPacketStatistic ( void );

		/*
		 *	This method is used to add a new packet digest into the info base
		 */
		ResultEnum		AddPacketInfo ( const CPacketDigest* pDigest );

		const string toString() const;

		void clear();


	private:

		/*
		 *	My own private functions
		 */
		/**
		 *	This method will distribute the packet by its protocol e.g. TCP, UDP, etc.
		 */
		ResultEnum distributeByProtocol ( const CPacketDigest* pDigest );

		/**
		 *	This method will distribute the packet by its classification, e.g. P2P, Web, etc.
		 */
		ResultEnum distributeByClassification ( const CPacketDigest* pDigest );
		
		/**
		 * This method will distribute the packet by its locality, e.g. user to user or user to other
		 */
		ResultEnum distributeByLocality( const CPacketDigest* pDigest );
		
		/**
		 * This method will distribute the traffic by natinality e.g. sweden to other countries or sweden to sweden
		 */ 
		ResultEnum distributedByInternationality( const CPacketDigest* pDigest);
		

		MetaTraffic m_totalTraffic;
		MetaTraffic m_tcpTraffic;
		MetaTraffic m_udpTraffic;
		MetaTraffic m_localTraffic;
		MetaTraffic m_illocalTraffic;
		MetaTraffic m_domesticTraffic;
		MetaTraffic m_intlTraffic;


		//
		CPacketStatisticMap m_trafficMap;

};

#endif
