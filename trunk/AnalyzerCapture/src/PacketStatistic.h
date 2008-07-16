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

#include "resultenum.h"

class CPacketDigest;

class CPacketStatistic
{
typedef unsigned long long _uint_64;

public:
	CPacketStatistic(void);
	~CPacketStatistic(void);

	/*
	 *	This method is used to add a new packet digest into the info base
	 */
	ResultEnum		AddPacketInfo(const CPacketDigest* pDigest);

	_uint_64 packetnumber() {return m_packetnumber;}
	_uint_64 trafficvolume() {return m_trafficvolume;}
	_uint_64 emptypacketnumber() {return m_emptypacketnumber;}
	_uint_64 tcppacketnumber() {return m_tcppacketnumber;}
	_uint_64 tcptrafficvolume() {return m_tcptrafficvolume;}
	_uint_64 udppacketnumber() {return m_udppacketnumber;}
	_uint_64 udptrafficvolume() {return m_udptrafficvolume;}
	_uint_64 p2ppacketnumber() {return m_p2ppacketnumber;}
	_uint_64 p2ptrafficvolume() {return m_p2ptrafficvolume;}
	_uint_64 httppacketnumber() {return m_httppacketnumber;}
	_uint_64 httptrafficvolume() {return m_httptrafficvolume;}
	_uint_64 unidentifiedpacketnumber() {return m_unidentifiedpacketnumber;}
	_uint_64 unidentifiedtrafficvolume() {return m_unidentifiedtrafficvolume;}


private:

	/*
	 *	My own private functions
	 */
	/*
	 *	This method will distribute the packet by its protocol e.g. TCP, UDP, etc.
	 */
	ResultEnum distributeByProtocol(const unsigned short sProtocolId, const unsigned int iPacketSize);

	/*
	 *	This method will distribute the packet by its classification, e.g. P2P, Web, etc.
	 */
	ResultEnum distributedByClassification(const unsigned short sClassId, const unsigned int iPacketSize);
	

	_uint_64 m_packetnumber;
	_uint_64 m_trafficvolume;
	_uint_64 m_emptypacketnumber;
	_uint_64 m_tcppacketnumber;
	_uint_64 m_tcptrafficvolume;
	_uint_64 m_udppacketnumber;
	_uint_64 m_udptrafficvolume;
	_uint_64 m_p2ppacketnumber;
	_uint_64 m_p2ptrafficvolume;
	_uint_64 m_httppacketnumber;
	_uint_64 m_httptrafficvolume;
	_uint_64 m_unidentifiedpacketnumber;
	_uint_64 m_unidentifiedtrafficvolume;
};