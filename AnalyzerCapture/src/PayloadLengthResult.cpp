/**
 *   PayloadLengthResult.cpp
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

#include "PayloadLengthResult.h"
#include "PacketDigest.h"
#include "analyserpxFlow.h"
#include <sstream>
#include <string>
#include <fstream>


using namespace std;

CPayloadLengthResult::CPayloadLengthResult(void)
{
	for(int i = 0 ; i < PAYLOAD_MAXSIZE; i++)
	{
		m_packetLength[i] = 0;
		m_tempPacketLength[i]=0;
	}
	m_pCurrentArray = m_packetLength;
}

CPayloadLengthResult::~CPayloadLengthResult(void)
{
}

ResultEnum CPayloadLengthResult::AddNewPacketInfo( const CPacketDigest* pDigest )
{
	int size = pDigest->getPacketSize();
	if (size >= 0 && size < PAYLOAD_MAXSIZE)
		++(m_pCurrentArray[size]);
	return eOK;
}

ResultEnum CPayloadLengthResult::PrintResult()
{
	//pthread_mutex_lock(Locks::packetLength_lock);
	ResultEnum rs = eOK;
	if(m_pCurrentArray == m_packetLength)
	{
		m_pCurrentArray = m_tempPacketLength;
		rs = PrintPacketLengthToFile(m_packetLength);
	}
	else
	{
		m_pCurrentArray = m_packetLength;
		rs = PrintPacketLengthToFile(m_tempPacketLength);
	}
	return rs;
}

ResultEnum CPayloadLengthResult::PrintPacketLengthToFile(unsigned int* pArray)
{
	if (NULL == pArray)
	{
		return eEmptyPointer;
	}
	stringstream strstream;
	
	// Year
	strstream << (m_endTime.tm_year + 1900);
	// Month
	if (m_endTime.tm_mon + 1 < 9)
	{
		strstream << 0;
	}
	strstream << m_endTime.tm_mon + 1;
	// Day
	if (m_endTime.tm_mday < 9)
	{
		strstream << 0;
	}
	strstream << m_endTime.tm_mday;
	// Hour
	if (m_endTime.tm_hour < 10)
	{
		strstream << 0;
		if (m_endTime.tm_hour = 0)
		{
			strstream << 0;
		}
		else 
		{
			strstream << m_endTime.tm_hour;
		}
	}
	else 
	{
		strstream << m_endTime.tm_hour;
	}
	// Minute
	if (m_endTime.tm_min < 10)
	{
		strstream << 0;
		if (m_endTime.tm_min = 0)
		{
			strstream << 0;
		}
		else 
		{
			strstream << m_endTime.tm_min;
		}
	}
	else 
	{
		strstream << m_endTime.tm_min;
	}
	string datestr = strstream.str();
	// Only for testing
	ofstream ofile ( "payloadlength", ios::binary | ios::app );
	ofile << datestr << "  ";
	for (int i = 0; i < PAYLOAD_MAXSIZE; i++)
	{
		ofile << pArray[i] << "   ";
		pArray[i] = 0;
	}
	ofile << endl;
	
	

	return eOK;
}
