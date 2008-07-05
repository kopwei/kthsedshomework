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
#include "analyserpxFlow.h"
#include "analyserpxTypes.h"
#include "cap.h"
#include "version.h"
#include "macro.h"

#include "unistd.h"
#include <iostream>


CUserInputParams::CUserInputParams() :
		m_pCaptureConfig(NULL), m_strFilePrefix ( "cap" ),  m_iFlowTimeOutSeconds ( 300 ), m_iOutputTimeBin ( 0 ),
			m_strReadingFileName ( "" ), m_bOptimumFlowOutputEnabled ( false ), m_strLogFileName ( "logcap" ), 
			m_bOutputThroughputEnabled ( false ), m_strOutThroughputFileName ( "throuput" ), m_iThreadNumber ( 1 )
{

	m_pCaptureConfig = new cap_config();
	CCaptureUtil::init_cap_config(m_pCaptureConfig);
}

CUserInputParams::~CUserInputParams()
{
	FREE_OBJECT(m_pCaptureConfig);
}

ResultEnum CUserInputParams::ParseInputParams ( const int argc, char** argv )
{
	ResultEnum rs = eOK;
	char c = -1;
	while ( ( c = getopt ( argc, argv, "hi:w:c:s:e:t:z:l:r:q:b:m::o" ) ) != -1 )
	{
		switch ( c )
		{
			case 'i':
			{
				// Set capture interface
				m_pCaptureConfig->strDev = optarg;
				// m_strCaptureInterface = optarg;
				break;
			}
			case 'w':
			{
				time_t tempo;
				time ( &tempo );
				char* data = ( char * ) ( malloc ( sizeof ( char ) *7 ) );
				CFlowUtil::getDate ( &tempo,data,6 );

				string strBaseFileName = optarg;
				strBaseFileName.append ( data );
				strBaseFileName.append ( "_0" );
				m_strFilePrefix =  strBaseFileName;

				free ( data );
				break;
			}
			case 'c':
			{

				m_pCaptureConfig->numOfPackets = atoi ( optarg );
				break;
			}
			case 's':
			{
				m_pCaptureConfig->snap_len = atoi ( optarg )
				//m_iMaxByteInFrame = atoi ( optarg ) ;
				break;
			}
			case 'e':
			{
				m_pCaptureConfig->filter_app = optarg;
				//m_strTrafficType = optarg;
				break;
			}
			case 't':
			{
				m_iFlowTimeOutSeconds= atoi ( optarg ) ;
				break;
			}
			case 'z':
			{
				m_iOutputTimeBin =  atoi ( optarg );
				break;
			}
			case 'l':
			{
				m_strLogFileName = optarg;
				break;
			}
			case 'r':
			{
				m_strReadingFileName = optarg;
				break;
			}
			case 'b':
			{
				m_bOptimumFlowOutputEnabled = atoi ( optarg );
				break;
			}
			case 'h':
			{
				printHelp ( argv[0] );
				return eCommonError;
			}
			case 'm':
			{
				m_bOutputThroughputEnabled = true;
				break;
			}
			case 'o':
			{
				m_strOutThroughputFileName = optarg;
				break;
			}
			case 'q':
			{
				m_iThreadNumber = atoi ( optarg );
				break;
			}
			case '?':
				if ( isprint ( optopt ) )
				{
					fprintf ( stderr, "Unknown option `-%c'.\n", optopt );
					fprintf ( stderr, "Use \"%s -h\" to see valid options\n", argv[0] );
				}
				else
				{
					fprintf ( stderr,	"Unknown option character `\\x%x'.\n", optopt );
					fprintf ( stderr, "Use \"%s -h\" to see valid options\n", argv[0] );
				}
				return eCommonError;
			default:
				abort();
		}
	}
	for ( int index = optind; index < argc; index++ )
	{
		printf ( "Non-option argument %s\n", argv[index] );
	}
	return rs;
}

void CUserInputParams::printHelp ( char *progname )
{

	cout << progname << " version " << ANALYSERPXCAP_VERSION << endl;
	cout << progname << " monitors, collects and store traffic that is" << endl
	          << "visible in a interface. The packets are classified and aggregated" << endl
	          << "into flows and stored on a binary file. The binay file can be read by " << endl 
	          << "the analyserpx_read program and the flows printed on screen in text format. " << endl
	          << endl
	          << "Usage:" << progname << "[-h] [-c count] [-i interface] [ -s snaplen ] [-w file] " << endl
	          << "[-e expression] [-t administrative time] [-l logFileName] " << endl
	          << "[-r input file] [-z number of time bins to change the output] " << endl
	          << "[-q threads number] " << endl
	          << "where " << endl
	          << "-h	this message " << endl
	          << "-c	Maximum number of frames to be capured (Default=0 (infinity)) " << endl
	          << "-i	Specify the interface to be listened (Default=active interface) " << endl
	          << "-s	Maximum number of bytes to be captured in each frame (Default: 1518) " << endl
	          << "-w	File prefix where collected data will be stored (Default: cap) " << endl
	          << "-e	Select the traffic that will be captured. See tcpdump expression " << endl
	          << "for more details (Default: ip) " << endl
	          << "-t	Specify the time out in seconds (time bin)  for expired flows to be " << endl
	          << "flushed out of the memory (Default: 300) " << endl
	          << "-z	Specify how many time bins will be considered to generate a new  " << endl
	          << "output file name. As default, 0 means all day long (Default: 0) " << endl
	          << "-r	Input file to offline capture " << endl
	          << "-b      Enabling an optimun flows exportation to Profiling (Disabled by default: 0) " << endl
	          << "-l	Specify the log file name (Default: logcap) " << endl
	          << "-m	Output throughput " << endl
	          << "-o	Specify the output file name for throughput measurement (Default: throughput) " << endl
	          << "-q	Specify the worker threads number (Default: 1) " << endl << endl
	          << "Examples: " << endl
	          << progname << " -i eth0 -s 1518 -w cap -e ip -t 300 -l log " << endl
	          << "Captures flows of ip traffic from eth0 with at " << endl
	          << "maximum 1518 bytes length. The flows will be stored " << endl
	          << "on the files cap0, cap1... " << endl << endl
	          << progname << " -w cap " << endl
	          << "Captures all traffic from the active interface. " << endl;
}

const cap_config* CUserInputParams::GetCaptureConfig() const
{
	return m_pCaptureConfig;
}

// const string CUserInputParams::GetCaptureInterface() const
// {
// 	return m_strCaptureInterface;
// }

const unsigned int CUserInputParams::GetThreadNumber() const
{
	return m_iThreadNumber;
}

// const long long CUserInputParams::GetMaxFrameNumber() const
// {
// 	return m_llMaxFrameNumber;
// }




const string CUserInputParams::GetFilePrefix() const
{
	return m_strFilePrefix;
}


// const unsigned int CUserInputParams::GetMaxByteInFrame() const
// {
// 	return m_iMaxByteInFrame;
// }


// const string CUserInputParams::GetTrafficType() const
// {
// 	return m_strTrafficType;
// }


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


const bool CUserInputParams::IsOutputThroughputEnabled() const
{
	return m_bOutputThroughputEnabled;
}


const string CUserInputParams::GetOutThroughputFileName() const
{
	return m_strOutThroughputFileName;
}

