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
 *   along with this program; if not, write to the                         
 *   Copyright (C) 2008 by Ericsson                                        
 */


#ifndef _PACKET_STATISTIC_
#define _PACKET_STATISTIC_

#include "PacketDigest.h"
//class CPacketDigest;
//#include <WinSock.h>
//////////////////////////////////////////////////////////////////////////
/**
 *	This class is the 
 */
class CPacketStatistic
{
public:
	CPacketStatistic(void);
	~CPacketStatistic(void);

	/**
	 *	This method is used to add a new packet digest into the info base
	 */
	void					AddPacketInfo(const CPacketDigest* pDigest);


	/**
	 *	Following methods are the get methods
	 */
	unsigned int			GetPacketNumber() const;
	unsigned long long		GetTrafficVolume() const;
	 

	unsigned int			GetP2PPacketNumber() const;
	unsigned long long		GetP2PTrafficVolume() const;

	


private:
	unsigned int			m_iPacketNumber;	// Total Packet number between starting and ending time
	unsigned long long		m_llTrafficVolume;	// Total traffic volume generated between starting and ending time

	unsigned int			m_iEmptyPacketNumber;

	unsigned int			m_iTCPPacketNumber;
	unsigned long long		m_llTCPTrafficVolume;

	unsigned int			m_iUDPPacketNumber;
	unsigned long long		m_llUDPTrafficVolume;

	unsigned int			m_iP2PPacketNumber;
	unsigned long long		m_llP2PTrafficVolume;

	unsigned int			m_iHTTPPacketNumber;
	unsigned long long		m_llHTTPTrafficVolume;


	unsigned int			m_iUnidentifiedPacketNumber;
	unsigned long long		m_llUnidentifiedTrafficVolume;

	//unsigned int			m_i



};

#endif
