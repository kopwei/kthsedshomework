/**
 *   ipheaderutil.h
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

#ifndef IPHEADERUTIL_H
#define IPHEADERUTIL_H

//#include "neti.h"
#include "analyserpxTypes.h"
#include "resultenum.h"


/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CIPHeaderUtil{
public:
	
	static ResultEnum GetIPHeader(const u_char* packet, ip*& pIPHeader);
	
	static ResultEnum GetSrcAndDstPort(const u_char* packet, u_int16_t* src_port, u_int16_t* dst_port);
	
	static const unsigned int ConvertIPToInt(const in_addr* pIPAddr);
	
	static const in_addr* ConvertIntToIP(const unsigned int pIntIP);
	

};

#endif
