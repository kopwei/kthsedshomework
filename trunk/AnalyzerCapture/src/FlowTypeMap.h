/**
 *   FlowTypeMap.h
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
#ifndef FLOWTYPEMAP_H
#define FLOWTYPEMAP_H

#include <map>
#include <string>

class flow_t;
/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CFlowTypeMap
{
public:
    CFlowTypeMap();
    ~CFlowTypeMap();
	
	const std::string toString() const;
	void  clear();
	
	void  AddNewFlow(const flow_t* pFlow);
	
private:
	
	typedef unsigned long long uint64;
	typedef std::map<unsigned short, uint64> TypeMap;
	
	TypeMap m_typeMap;
};

#endif
