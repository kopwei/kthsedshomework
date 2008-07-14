/**
 *   resultrecorder.cpp
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

#define MYSQLPP_MYSQL_HEADERS_BURIED

#include "resultrecorder.h"
#include "PacketStatistician.h"
#include "locks.h"
#include "macro.h"
#include "mysql++/mysql++.h"

RecordParameter::RecordParameter(const RecordTypeEnum recordType, const time_t& startTime, const time_t& endTime)
: m_recordType(recordType), m_startTime(startTime), m_endTime(endTime)
{
}

CResultRecorder::CResultRecorder()
{
}


CResultRecorder::~CResultRecorder()
{
}


ResultEnum CResultRecorder::RecordTimeOutResult(const CPacketStatistician* pStatistician, const RecordParameter* pParam)
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	switch (pParam->RecordType())
	{
	case eRecordToDatabase:
		rs = RecordToDatabase(pStatistician, pParam);
		EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);
		break;
	default:
		break;
	}
	
	return rs;
}

ResultEnum CResultRecorder::RecordToDatabase( const CPacketStatistician* pStatistician ,const RecordParameter* pParam)
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	map<unsigned int, CSubscriberStatistic> recordingMap = pStatistician->GetStatisticMap();
	//MYSQL* pMysql = mysql_init(NULL);
	mysqlpp::Connection con;
	
	return rs;
}

ResultEnum CResultRecorder::RecordToXML(const CPacketStatistician* pStatistician, const RecordParameter* pParam)
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	return rs;
}
