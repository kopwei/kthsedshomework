/**
 *   SubscriberStatistic.h	
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


#ifndef _SUBSCRIBER_STATISTIC_
#define  _SUBSCRIBER_STATISTIC_

#include "PacketStatistic.h"
#include "resultenum.h"

class CPacketDigest;
//////////////////////////////////////////////////////////////////////////

class CSubscriberStatistic
{
public:
	CSubscriberStatistic(const unsigned int ipAddress);
	~CSubscriberStatistic(void);
	
	CSubscriberStatistic(const CSubscriberStatistic& subStat);
	
	//const CSubscriberStatistic& operator= (const CSubscriberStatistic& subStat);	
	
	ResultEnum AddNewPacket(const CPacketDigest* pPacketDigest);
	
	void PrintSummary();

	unsigned int GetIPAddress() const {return m_ipAddress;}

	const CPacketStatistic& GetUploadStatistic() const {return *(&m_uploadPacketStatistic);}

	const CPacketStatistic& GetDownloadStatistic() const {return *(&m_downloadPacketStatistic);}
	

private: 
	unsigned long long		m_macAddress;
	unsigned int 			m_ipAddress;
	
	CPacketStatistic		m_uploadPacketStatistic;				// Upload Packet Statistic
	CPacketStatistic		m_downloadPacketStatistic;				// Download packet statistic
	
};

#endif
