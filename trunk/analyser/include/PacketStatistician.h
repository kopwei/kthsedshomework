/**
 *   PacketStatistician.h	
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

#pragma once;


#include <map>
#include "analyserpxAggreg.h"
#include "PacketStatistic.h"
#include "SubscriberStatistic.h"

using namespace std;
//////////////////////////////////////////////////////////////////////////
/**
 *	This class is a class used for analyzing the packets
 */
class CPacketStatistician
{
	

public:
	CPacketStatistician(void);
	~CPacketStatistician(void);

	void processNewPacket(unsigned char *args, const struct pcap_pkthdr *header, const u_char *packet, ThreadParams *tp);

private:
	
	CPacketStatistic		m_totalPacketStatistic;
	CSubscriberStatistic	m_subscriberStatistic;
	//map<__int6>

};

