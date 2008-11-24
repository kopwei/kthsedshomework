/**
 *   AppAnalyzedResult.h
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
#ifndef APPANALYZEDRESULT_H
#define APPANALYZEDRESULT_H

#include <AnalyzedResult.h>
#include <AppStatistic.h>
#include <map>

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CAppAnalyzedResult : public CAnalyzedResult
{
public:
    CAppAnalyzedResult();

    ~CAppAnalyzedResult();

    ResultEnum AddNewPacketInfo( const CPacketDigest* pDigest );

    ResultEnum PrintResult();
	
	ResultEnum PrintFinalResult();

private:
    /**
     * Avoid bitwise copy
     */
    CAppAnalyzedResult(const CAppAnalyzedResult&);
    const CAppAnalyzedResult&	operator = (const CAppAnalyzedResult&);

    ResultEnum PrintInfoToFile(std::map<unsigned short, CAppStatistic>* pAppMap);

    std::map<unsigned short, CAppStatistic> m_appMap;
    std::map<unsigned short, CAppStatistic> m_tempAppMap;

    std::map<unsigned short, CAppStatistic>* m_pAppMap;
	
	std::map<unsigned short, CAppStatistic> m_totalAppMap;

};

#endif

