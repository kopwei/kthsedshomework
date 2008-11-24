/**
 *   AppStatistic.h
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
#ifndef APPSTATISTIC_H
#define APPSTATISTIC_H

#include <MetaTraffic.h>
#include <resultenum.h>

const int APP_PAYLOAD_MAXSIZE = 1515;

class CPacketDigest;

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CAppStatistic
{
public:
    CAppStatistic();

    ~CAppStatistic();
	
	ResultEnum AddNewPacket(const CPacketDigest* pDigest);
	
	const std::string toString() const;
	
private:
	MetaTraffic				m_totalTraffic;
	MetaTraffic				m_localTraffic;
	
	unsigned long long 		m_payloadLength[APP_PAYLOAD_MAXSIZE];

};

#endif
