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

	unsigned int			getPacketNumber();
	unsigned long long		getTrafficVolume();

	void					addPacketInfo(const CPacketDigest* pDigest);


private:
	unsigned int			m_iPacketNumber;	// Total Packet number between starting and ending time
	unsigned long long		m_llTrafficVolume;	// Total traffic volume generated between starting and ending time


};

#endif
