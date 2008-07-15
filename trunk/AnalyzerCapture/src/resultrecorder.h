/**
 *   resultrecorder.h
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


#ifndef RESULTRECORDER_H
#define RESULTRECORDER_H

#define MYSQLPP_MYSQL_HEADERS_BURIED

#include "resultenum.h"
#include "SubscriberStatistic.h"

#include "time.h"
#include "mysql++/mysql++.h"
#include <string>
#include <google/protobuf/stubs/common.h>

using namespace std;

class CPacketStatistician;
class CPacketStatistic;

typedef  map<unsigned int, CSubscriberStatistic> StatisticMap;

//////////////////////////////////////////////////////////////////////////

/**
 *	
 */
enum RecordTypeEnum
{
	eUnknown,
	eRecordToTextFile,
	eRecordToDatabase,
	eRecordToXML
};

//////////////////////////////////////////////////////////////////////////
/**
 *	This class is a parameter class
 */
class RecordParameter
{
public:
	RecordParameter(const RecordTypeEnum recordType, const time_t& startTime, const time_t& endTime);

	RecordTypeEnum RecordType() const { return m_recordType; }
	time_t StartTime() const { return m_startTime; }
	time_t EndTime() const { return m_endTime; }

private:
	RecordTypeEnum m_recordType;
	time_t m_startTime;	
	time_t m_endTime;
	
};

//////////////////////////////////////////////////////////////////////////
/**
 * This class is a util class used for recording the result into file or database
 */
class CResultRecorder{
public:
    CResultRecorder();

    ~CResultRecorder();
	
	ResultEnum RecordTimeOutResult(const StatisticMap& statMap, const RecordParameter* pParam);

	ResultEnum RecordDailyResult(const CPacketStatistic* pPacketStat);

private:

	ResultEnum RecordToDatabase(const StatisticMap& statMap, const RecordParameter* pParam);

	ResultEnum RecordToXML(const StatisticMap& statMap, const RecordParameter* pParam);

	ResultEnum RecordStatisticIntoTable(const string& strTableName, const unsigned int iSubuscriber, 
		const time_t start_time, const time_t end_time, 
		const CPacketStatistic& stat);

	ResultEnum CheckDatabase(const string& strDbName);

	ResultEnum CheckTable(const string& strTableName);

	ResultEnum GetCurrentDate(string& strDate) const;

	mysqlpp::Connection			m_connection;
};

#endif

