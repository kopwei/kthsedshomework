/**
 *   PacketDigest.h
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


#ifndef _PACKET_DIGEST_
#define _PACKET_DIGEST_

#include "pcap.h"
#include <net/if_arp.h>
#include <netinet/in.h>
#include <netinet/if_ether.h>
#include <net/ethernet.h>
//#include "net/socket.h"

class flow_t;

//////////////////////////////////////////////////////////////////////////

/**
 *	This class represent a digest of an IP packet
 */
class CPacketDigest
{
public:
    CPacketDigest ( const struct pcap_pkthdr* header, const u_char* packet, flow_t* flow );
    ~CPacketDigest ( void );

    const time_t		getTimeStamp() const;
	ether_addr			getSrcEtherAddress() const;
	ether_addr			getDestEtherAddress() const;
    in_addr				getSrcAddress() const;
    in_addr				getDestAddress() const;
    unsigned int		getPacketSize() const;
    unsigned short		getProtocol() const;
    unsigned short		getProtocolClassification() const;

private:
    time_t				m_timeStamp;		// Time
	ether_addr			m_srcMacAddr;		// Source Mac Address
	ether_addr			m_destMacAddr;		// Destination Mac Address
    in_addr				m_srcIPAddr;		// Source IP Address
    in_addr				m_destIPAddr;		// Destination IP Address
    unsigned int		m_sPacketSize;		// Packet size
    unsigned short		m_sProtocol;		// Protocol
    unsigned short		m_sClass;		// Protocol classification
    //flow_t*				m_pFlow;			// Flow which the packet belongs to
};

#endif
