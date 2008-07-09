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

RecordParameter::RecordParameter(RecordTypeEnum recordType)
{
	m_recordType = recordType;
}

RecordParameter::GetRecordType() const
{
	return m_recordType;
}

CResultRecorder::CResultRecorder()
{
}


CResultRecorder::~CResultRecorder()
{
}


ResultEnum CResultRecorder::RecordResult(const CPacketStatistician* pStatistician, const RecordParameter* pParam)
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	return rs;
}

ResultEnum CResultRecorder::RecordToDatabase( const CPacketStatistician* pStatistician )
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	return rs;
}

RecordTypeEnum CResultRecorder::RecordToXML(const CPacketStatistician* pStatistician)
{
	ResultEnum rs = eNotImplemented;
	// TODO: Need implementation here
	return rs;
}
