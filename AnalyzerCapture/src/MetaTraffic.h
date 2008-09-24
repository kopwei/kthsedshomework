/**
 *   MetaTraffic.h
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
#ifndef METATRAFFIC_H
#define METATRAFFIC_H

#include <string>
#include <sstream>


/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
struct MetaTraffic
{
	typedef unsigned long long _uint_64;
public:	
	MetaTraffic(void) : packetnumber(0), trafficvolume(0){}
		
	void clear() {packetnumber = 0; trafficvolume = 0;}
	
	void AddNewPacket(const uint packetSize)
	{
		++packetnumber;
		trafficvolume += packetSize;
	}
	
	void AddNewTraffic(const _uint_64 packets, const _uint_64 packetSize)
	{
		packetnumber += packets;
		trafficvolume += packetSize;
	}
	
	const std::string toString() const 
	{
		std::stringstream strStream;
		std::string indent = "   ";
		strStream << packetnumber << indent << trafficvolume << indent;
		return strStream.str();
	}
	
	_uint_64 GetPacketNumber() const {return packetnumber;}
	_uint_64 GetTrafficVolume() const {return trafficvolume;}
	
private:
		
	_uint_64 packetnumber;
	_uint_64 trafficvolume;

};

#endif
