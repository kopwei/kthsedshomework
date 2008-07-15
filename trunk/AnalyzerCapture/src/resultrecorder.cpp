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



#include "resultrecorder.h"
#include "PacketStatistician.h"
#include "locks.h"
#include "macro.h"
#include "analyserpxFlow.h"

RecordParameter::RecordParameter(const RecordTypeEnum recordType, const time_t& startTime, const time_t& endTime)
: m_recordType(recordType), m_startTime(startTime), m_endTime(endTime)
{
}

CResultRecorder::CResultRecorder()
{
	try 
	{
		m_connection.connect(0, "localhost", "ericsson", "ericsson");
	}
	catch (exception& er) 
	{
		cerr << "Connection failed: " << er.what() << endl;
	}
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
	if (NULL == pStatistician || NULL == pParam)
		return eEmptyPointer;
	// Check if the database "Analyzer" is there
	string strDbName = "analyzer";
	rs = CheckDatabase(strDbName);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);
	
	// Check if the table with date is there;
	string strTableName;
	rs = GetCurrentDate(strTableName);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	string strUploadTableName = strTableName.append("_upload");
	rs = CheckTable(strUploadTableName);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	string strDownloadTableName = strTableName.append("_download");
	rs = CheckTable(strDownloadTableName);
	EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

	// TODO: Need implementation here
	pthread_mutex_lock(&Locks::packetMap_lock);
	map<unsigned int, CSubscriberStatistic> recordingMap = pStatistician->GetStatisticMap();
	pthread_mutex_unlock(&Locks::packetMap_lock);

	map<unsigned int, CSubscriberStatistic>::const_iterator itor = recordingMap.begin();
	for (; itor != recordingMap.end(); ++itor)
	{
		rs = RecordStatisticIntoTable(strTableName, itor->first, pParam->StartTime(), pParam->EndTime(), 
			itor->second.GetUploadStatistic());
		EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);

		rs = RecordStatisticIntoTable(strTableName, itor->first, pParam->StartTime(), pParam->EndTime(), 
			itor->second.GetDownloadStatistic());
		EABASSERT(rs == eOK); ON_ERROR_RETURN(rs != eOK, rs);
	}

	//MYSQL* pMysql = mysql_init(NULL);
	
	return rs;
}

ResultEnum CResultRecorder::RecordToXML(const CPacketStatistician* pStatistician, const RecordParameter* pParam)
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	return rs;
}

ResultEnum CResultRecorder::CheckDatabase( const string& strDbName )
{
	ResultEnum rs = eOK;
	// TODO: Need implementation here
	mysqlpp::NoExceptions ne(m_connection);
	if (!m_connection.select_db(strDbName))
	{
		if (m_connection.create_db(strDbName))
		{
			if(!m_connection.select_db(strDbName))
				return eCommonError;
		}
		else
		{
			return eCommonError;
		}
	}
	return rs;
}

ResultEnum CResultRecorder::CheckTable( const string& strTableName )
{
	ResultEnum rs = eOK;
	// TODO: Need implementation here

	// Check if the table is already inside
	mysqlpp::Query query = m_connection.query();
	query << "show tables";
	mysqlpp::StoreQueryResult qr = query.store();
	bool bFound = false;
	for (int i = 0; i < qr[0].size(); i++)
	{
		if (strTableName == (string)qr[0][i])
		{
			bFound = true;
			break;
		}
	}
	// If there is no such table, create it
	if (!bFound)
	{
		query.reset();
		query << "CREATE TABLE %0q ( "
			<< " id BIGINT UNSIGNED not NULL PRIMARY KEY, "
			<< " subscriber INT UNSIGNED not NULL," 
			<< " start_time INT not NULL, " 
			<< " end_time INT not NULL, "
			<< " total_packet BIGINT UNSIGNED not NULL, "
			<< " total_volume BIGINT UNSIGNED not NULL, "
			<< " empty_packet BIGINT UNSIGNED not NULL, "
			<< " tcp_packet BIGINT UNSIGNED not NULL, "
			<< " tcp_volume BIGINT UNSIGNED not NULL, "
			<< " udp_packet BIGINT UNSIGNED not NULL, "
			<< " udp_volume BIGINT UNSIGNED not NULL, "
			<< " http_packet BIGINT UNSIGNED not NULL, "
			<< " http_volume BIGINT UNSIGNED not NULL, "
			<< " p2p_packet BIGINT UNSIGNED not NULL, "
			<< " p2p_volume BIGINT UNSIGNED not NULL, "
			<< " unidentified_packet BIGINT UNSIGNED not NULL, "
			<< " unidentified_volume BIGINT UNSIGNED not NULL, "
			<< " )" ;

		query.parse();
		query.execute(strTableName.c_str());

	}
	
	return rs;
}

ResultEnum CResultRecorder::GetCurrentDate( string& strDate ) const
{
	ResultEnum rs = eOK;
	// TODO: Need implementation here
	time_t init = 0;
	time ( &init );
	char date[7]; 
	if (!CFlowUtil::getDate(&init, date, sizeof(date)))
		return eCommonError;
	strDate = string(date, sizeof(date));
	return rs;
}

ResultEnum CResultRecorder::RecordStatisticIntoTable(const string& strTableName, const unsigned int iSubuscriber, 
													 const time_t start_time, const time_t end_time, 
													 const CPacketStatistic& stat)
{
	ResultEnum rs = eOK;
	if (0 == strTableName.length())
		return eCommonError;
	// TODO: Need implementation here
	unsigned long long id = iSubuscriber;

	mysqlpp::Query query = m_connection.query();
	query << " INSERT INTO %0q VALUES ( " 
		<< " %1q, %2q, %3q, %4q, %5q, %6q, %7q, %8q, %9q, %10q, "
		<< " %11q, %12q, %13q, %14q, %15q, %16q, %17q) " ;
	query.parse();

	query.execute(strTableName, id, iSubuscriber, (unsigned int)start_time, (unsigned int)end_time, stat.packetnumber(),
		stat.trafficvolume(), stat.emptypacketnumber(), stat.tcppacketnumber(), stat.tcptrafficvolume(),
		stat.udppacketnumber(), stat.udptrafficvolume(), stat.httppacketnumber(), stat.httptrafficvolume(),
		stat.p2ppacketnumber(), stat.p2ptrafficvolume(), stat.unidentifiedpacketnumber(), stat.unidentifiedtrafficvolume());

	return rs;
}
