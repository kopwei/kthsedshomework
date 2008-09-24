/**
 *   FlowAnalyzedResult.h
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
#ifndef FLOWANALYZEDRESULT_H
#define FLOWANALYZEDRESULT_H

#include "AnalyzedResult.h"
#include "MetaTraffic.h"
#include <map>


class flow_t;
/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CFlowAnalyzedResult : public CAnalyzedResult
{
public:
    CFlowAnalyzedResult();

    ~CFlowAnalyzedResult();
	
	ResultEnum AddNewFlowInfo(flow_t* pFlow);
	
	ResultEnum PrintResult();
	
	ResultEnum PrintTotalResult();

	ResultEnum Clear();

private:
	typedef std::map<u_short, uint> FlowTypeDistributionMap;
	
	typedef std::map<u_short, MetaTraffic> FlowDigestMap;

	FlowDigestMap m_digestMap;
	
	FlowTypeDistributionMap m_distributionMap;
	
	

};

#endif
