/**
 *   PacketDigest.cpp	
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

#include "../include/PacketDigest.h"
#include "netinet/ip.h"

//const int ETHER_HDR_LEN = 14;

CPacketDigest::CPacketDigest(pcap_pkthdr* header, char* packet)
{
	m_timeStamp = header->ts.tv_usec + header->ts.tv_sec * (1e6);
	m_sPacketSize = header->len;
	ip* pIpHeader =reinterpret_cast<ip *>(packet + ETHER_HDR_LEN);
	m_pSrcIPAddr = &pIpHeader->ip_src;
	m_pDestIPAddr = &pIpHeader->ip_dst;
	m_sProtocol = pIpHeader->ip_p;
	m_sClass = pIpHeader->ip_p;

}

CPacketDigest::~CPacketDigest(void)
{
} 

/**
 *	This method returns the time stamp of the packet
 */
const time_t CPacketDigest::getTimeStamp() const
{
	return m_timeStamp;
}

/**
 *	This method returns the Source IP Address of the packet
 */
in_addr* CPacketDigest::getSrcAddress() const
{
	return m_pSrcIPAddr;
}

in_addr* CPacketDigest::getDestAddress() const
{
	return m_pDestIPAddr;
}

unsigned int CPacketDigest::getPacketSize() const
{
	return m_sPacketSize;
}

unsigned short CPacketDigest::getProtocol() const
{
	return m_sProtocol;
}

unsigned short CPacketDigest::getProtocolClassification() const
{
	return m_sClass;
}