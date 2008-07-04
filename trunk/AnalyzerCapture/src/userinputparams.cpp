/**
 *   userinputparams.cpp
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
#include "userinputparams.h"

CUserInputParams::CUserInputParams() :
		m_llMaxFrameNumber(0), m_strCaptureInterface("activeinterface"), m_iMaxByteInFrame(1518), m_strFilePrefix("cap"), m_strTrafficType("ip"), m_iFlowTimeOutSeconds(300), m_iOutputTimeBin(0), m_strReadingFileName(""), m_bOptimumFlowOutputEnabled(false), m_strLogFileName("logcap"), m_bOutputThroughputEnabled(false), m_strOutThroughputFileName("throuput"), m_iThreadNumber(1)											
{
}


CUserInputParams::~CUserInputParams()
{
}

const string CUserInputParams::GetCaptureInterface() const
{
	return m_strCaptureInterface;
}

const unsigned int CUserInputParams::GetThreadNumber() const
{
	return m_iThreadNumber;
}

const long long CUserInputParams::GetMaxFrameNumber() const
{
	return m_llMaxFrameNumber;
}




const string CUserInputParams::GetFilePrefix() const
{
	return m_strFilePrefix;
}


const unsigned int CUserInputParams::GetMaxByteInFrame() const
{
	return m_iMaxByteInFrame;
}


const string CUserInputParams::GetTrafficType() const
{
	return m_strTrafficType;
}


const unsigned int CUserInputParams::GetFlowTimeOutSeconds() const
{
	return m_iFlowTimeOutSeconds;
}


const unsigned int CUserInputParams::GetOutputTimeBin() const
{
	return m_iOutputTimeBin;
}


const string CUserInputParams::GetReadingFileName() const
{
	return m_strReadingFileName;
}


const bool CUserInputParams::IsOptimumFlowOutputEnabled() const
{
	return m_bOptimumFlowOutputEnabled;
}


const string CUserInputParams::GetLogFileName() const
{
	return m_strLogFileName;
}


void CUserInputParams::SetMaxFrameNumber ( const long long& theValue )
{
	m_llMaxFrameNumber = theValue;
}


void CUserInputParams::SetCaptureInterface ( string theValue )
{
	m_strCaptureInterface = theValue;
}


void CUserInputParams::SetMaxByteInFrame ( unsigned int theValue )
{
	m_iMaxByteInFrame = theValue;
}


void CUserInputParams::SetFilePrefix ( string theValue )
{
	m_strFilePrefix = theValue;
}


void CUserInputParams::SetTrafficType ( string theValue )
{
	m_strTrafficType = theValue;
}


void CUserInputParams::SetFlowTimeOutSeconds ( unsigned int theValue )
{
	m_iFlowTimeOutSeconds = theValue;
}


void CUserInputParams::SetOutputTimeBin ( unsigned int theValue )
{
	m_iOutputTimeBin = theValue;
}


void CUserInputParams::SetReadingFileName ( string theValue )
{
	m_strReadingFileName = theValue;
}


void CUserInputParams::SetOptimumFlowOutputEnabled ( bool theValue )
{
	m_bOptimumFlowOutputEnabled = theValue;
}


void CUserInputParams::SetLogFileName ( string theValue )
{
	m_strLogFileName = theValue;
}


const bool CUserInputParams::IsOutputThroughputEnabled() const
{
	return m_bOutputThroughputEnabled;
}


void CUserInputParams::SetOutputThroughputEnabled ( bool theValue )
{
	m_bOutputThroughputEnabled = theValue;
}


const string CUserInputParams::GetOutThroughputFileName() const
{
	return m_strOutThroughputFileName;
}


void CUserInputParams::SetOutThroughputFileName ( string theValue )
{
	m_strOutThroughputFileName = theValue;
}



void CUserInputParams::SetThreadNumber ( unsigned int theValue )
{
	m_iThreadNumber = theValue;
}
