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
#include <net/ethernet.h>
#include <sstream>
#include "CommonUtil.h"

using namespace std;


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
		{
			tcphdr* pTcphead = (tcphdr *) (packet + size_ethernet + size_ip);
			*src_port = 
					htons(pTcphead->th_sport);
			*dst_port = 
					htons(pTcphead->th_dport);
			break;
		}
		case IPPROTO_UDP:
		{
			*src_port =
					((udphdr *) (packet + size_ethernet +
					size_ip))->uh_sport;
			*dst_port =
					((udphdr *) (packet + size_ethernet +
					size_ip))->uh_dport;
			break;
		}
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


const in_addr CIPHeaderUtil::ConvertIntToIP ( const unsigned int pIntIP )
{
	// TODO: need implemented here
	in_addr ip;
	ip.s_addr = htonl(pIntIP);
	return ip;
}

const unsigned long long CIPHeaderUtil::ConvertMacToInt64(const ether_addr* pMacAddr)
{
	unsigned long long retVal = 0;
	for (int i = 0; i < ETH_ALEN; ++i)
	{
		unsigned long long bitVal = (unsigned long long)pMacAddr->ether_addr_octet[i];
		unsigned long long shiftVal = bitVal << (8 * (ETH_ALEN - i - 1));
		retVal += shiftVal;
	}
	return retVal;
}



const std::string CIPHeaderUtil::ConvertIPToString(const in_addr* pIPAddr)
{
	uint ip = ntohl(pIPAddr->s_addr);
	
	return ConvertIPToString(ip);
}

const std::string CIPHeaderUtil::ConvertIPToString(const uint ip)
{
	ushort s1, s2, s3, s4;
	s4 = ip % 256;
	s3 = (ip >> 8) % 256;
	s2 = (ip >> 16) % 256;
	s1 = (ip >> 24) % 256;
	string indent = ".";
	stringstream strStream;
	strStream << s1 << indent << s2 << indent << s3 << indent << s4;
	return strStream.str();
}

const std::string CIPHeaderUtil::ConvertMacToString(const ether_addr* pMacAddr)
{
	unsigned long long mac = ConvertMacToInt64(pMacAddr);
	return ConvertMacToString(mac);
}

const std::string CIPHeaderUtil::ConvertMacToString(const unsigned long long mac)
{
	stringstream strStream;
	//string s1, s2, s3, s4, s5, s6;
	string indent = ":";
	//int i[6];
	//string s[6];
	for (int i = 0; i < 5; i++)
	{
		int seg = (mac >> 8 * (5 - i)) % 256;
		if (seg == 0)
		{
			strStream << "0";
		}
		else if (seg < 16)
		{
			strStream << "0";
		}
		strStream << CommonUtil::itoa(seg, 16);
		if (i < 4)
		{
			strStream << indent;
		}
	}
/*	s6 = CommonUtil::itoa(mac % 256, 16);
	s5 = CommonUtil::itoa((mac >> 8) % 256, 16);
	s4 = CommonUtil::itoa((mac >> 16) % 256, 16);
	s3 = CommonUtil::itoa((mac >> 24) % 256, 16);
	s2 = CommonUtil::itoa((mac >> 32) % 256, 16);
	s1 = CommonUtil::itoa((mac >> 40) % 256, 16);
	
	strStream << s1 << indent << s2 << indent << s3 << indent << s4 << indent << s5 << indent << s6;
*/
	return strStream.str();
}

