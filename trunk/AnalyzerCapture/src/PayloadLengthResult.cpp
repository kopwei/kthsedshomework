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
#include "CommonUtil.h"
#include <iostream>
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
	string datestr = GetTimeStr(false);
	//cout << "date is " << datestr << endl;
	// Only for testing
	ofstream ofile ( "payloadlength.ret", ios::binary | ios::app );
	string indent = "  ";
	ofile << datestr << indent;
	//cout << "date printed ..."<<endl;
	for (int i = 0; i < PAYLOAD_MAXSIZE; i++)
	{
		ofile << pArray[i] << indent;
		pArray[i] = 0;
	}
	//cout << "finish print" << endl;
	ofile << endl;
	
	

	return eOK;
}
