/**
 *   ipheaderutil.cpp
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

#include "ipheaderutil.h"

#include <netinet/in.h>


ResultEnum CIPHeaderUtil::GetIPHeader ( const u_char* packet, ip*& pIPHeader )
{
	short size_ethernet = ETHER_HDR_LEN;
	pIPHeader = ( ip* ) ( packet + size_ethernet );
	return eOK;
}

ResultEnum CIPHeaderUtil::GetSrcAndDstPort ( const u_char* packet, u_int16_t* src_port, u_int16_t* dst_port )
{
	/* ethernet headers are always exactly (time_t)(header->ts.tv_sec)14 bytes */
	short size_ethernet = ETHER_HDR_LEN;
	ip* pIPHeader = ( ip* ) ( packet + size_ethernet );
	u_int size_ip = pIPHeader->ip_hl * 4;
	if ( size_ip < 20 )
	{
		return eCommonError;
	}
	/* determine protocol */
	switch (pIPHeader->ip_p)
	{
		case IPPROTO_TCP:
			*src_port =
					((struct tcphdr *) (packet + size_ethernet +
					size_ip))->th_sport;
			*dst_port =
					((struct tcphdr *) (packet + size_ethernet +
					size_ip))->th_dport;
			break;
		case IPPROTO_UDP:
			*src_port =
					((struct udphdr *) (packet + size_ethernet +
					size_ip))->uh_sport;
			*dst_port =
					((struct udphdr *) (packet + size_ethernet +
					size_ip))->uh_dport;
			break;
		case IPPROTO_ICMP:
			*src_port = htons(0);
			*dst_port = htons(0);
			break;
		default:
			*src_port = htons(0);
			*dst_port = htons(0);
			break;
	}
	return eOK;
}

const unsigned int CIPHeaderUtil::ConvertIPToInt ( const in_addr* pIPAddr )
{

	// TODO: need implemented here
	return pIPAddr->s_addr;
	//return add.S_addr;
}

const in_addr* CIPHeaderUtil::ConvertIntToIP ( const unsigned int pIntIP )
{
	// TODO: need implemented here
	return NULL;
}


