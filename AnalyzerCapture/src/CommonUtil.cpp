/**
 *   CommonUtil.cpp
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
#include "CommonUtil.h"

/**
 * This method is used to convert a integer value to standard string
 */
std::string CommonUtil::itoa(unsigned long long value, uint base/* = 10 */) {
	enum { kMaxDigits = 35 };
	std::string buf;
	buf.reserve( kMaxDigits ); // Pre-allocate enough space.
	// check that the base if valid
	if (base < 2 || base > 16) return buf;	
	unsigned long long quotient = value;
	// Translating number to string with base:
	do {
	
		buf += "0123456789abcdef"[ quotient % base ];
		quotient /= base;
	
	} while ( quotient );
	// Append the negative sign for base 10
	
	//if ( value < 0 && base == 10) buf += '-';
	//std::reverse( buf.begin(), buf.end() );
	return buf;
}

