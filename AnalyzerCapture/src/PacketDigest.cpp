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

#include "PacketDigest.h"
#include "netinet/ip.h"
#include "netinet/if_ether.h"
#include "Flow.h"
//const int ETHER_HDR_LEN = 14;

CPacketDigest::CPacketDigest ( const pcap_pkthdr* header, const u_char* packet, flow_t* flow ) :
		m_eLocality(eUnknownLocality)
{
    m_timeStamp = header->ts.tv_usec + header->ts.tv_sec * ( 1e6 );
    m_sPacketSize = header->len;
	ethhdr* pEtherHeader = (ethhdr *)packet;
	m_srcMacAddr = *((ether_addr*) pEtherHeader->h_source);
	m_destMacAddr = *((ether_addr*) pEtherHeader->h_dest);
	
    ip* pIpHeader = ( ip * ) ( packet + ETHER_HDR_LEN );
	//uint srcIp = ntohl(pIpHeader->ip_src.s_addr);
	//uint dstIp = ntohl(pIpHeader->ip_dst.s_addr);
	m_srcIPAddr = pIpHeader->ip_src;
	m_destIPAddr= pIpHeader->ip_dst;
    m_sProtocol = pIpHeader->ip_p;
    //m_pFlow = flow; 
    m_sClass = flow->class_proto();

}

CPacketDigest::~CPacketDigest ( void )
{
}

/**
 *	This method returns the time stamp of the packet
 */
const time_t CPacketDigest::getTimeStamp() const
{
    return m_timeStamp;
}

const ether_addr CPacketDigest::getSrcEtherAddress() const
{
	return m_srcMacAddr;
}

const ether_addr CPacketDigest::getDestEtherAddress() const
{
	return m_destMacAddr;
}

/**
 *	This method returns the Source IP Address of the packet
 */
const in_addr CPacketDigest::getSrcAddress() const
{
    return m_srcIPAddr;
}

const in_addr CPacketDigest::getDestAddress() const
{
    return m_destIPAddr;
}

const unsigned int CPacketDigest::getPacketSize() const
{
    return m_sPacketSize;
}

const unsigned short CPacketDigest::getProtocol() const
{
    return m_sProtocol;
}

const unsigned short CPacketDigest::getProtocolClassification() const
{
//    if (NULL != m_pFlow)
//        return m_pFlow->class_proto();
//    else
		return m_sClass;		

}
