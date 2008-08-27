/**
 *   AnalyzedResult.h
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

#pragma once

#include "resultenum.h"
#include <time.h>
#include <string>

class CPacketDigest;
//struct tm;

using namespace std;

class CAnalyzedResult
{
public:
	CAnalyzedResult(void);
	virtual ~CAnalyzedResult(void);

	virtual ResultEnum AddNewPacketInfo(const CPacketDigest* pDigest) = 0;

	virtual ResultEnum PrintResult() = 0;

	virtual ResultEnum PrintDailyResult() {};
	
	void 		setStartTime(const tm& t) {m_startTime = t;}
	void 		setEndTime(const tm& t) {m_endTime = t;}

protected:

	const 	std::string GetTimeStr(const bool bIsStart);

	tm			m_startTime;
	tm			m_endTime;			
	
private:
	CAnalyzedResult(const CAnalyzedResult&)	;
	const CAnalyzedResult& operator = (const CAnalyzedResult&);

};
