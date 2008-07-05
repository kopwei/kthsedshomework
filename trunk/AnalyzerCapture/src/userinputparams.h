/**
 *   userinputparams.h
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

#ifndef USERINPUTPARAMS_H
#define USERINPUTPARAMS_H

#include "resultenum.h"

#include <string>

struct cap_config;

using namespace std;

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CUserInputParams
{
	public:
		CUserInputParams();

		~CUserInputParams();

		ResultEnum ParseInputParams ( const int argc, char** argv );

		const string GetFilePrefix() const;

		const unsigned int GetFlowTimeOutSeconds() const;

		const unsigned int GetOutputTimeBin() const;

		const string GetReadingFileName() const;

		const bool IsOptimumFlowOutputEnabled() const;

		const string GetLogFileName() const;

		const string GetOutThroughputFileName() const;

		const bool IsOutputThroughputEnabled() const;

		const unsigned int GetThreadNumber() const;

		cap_config* GetCaptureConfig() const;

		const bool isOnlineMode() const;


	private:

		void printHelp ( char *progname );


		cap_config* m_pCaptureConfig;
		bool m_bOnlineMode;
		string m_strFilePrefix; /** -w	File prefix where collected data will be stored (Default: cap) */
		unsigned int m_iFlowTimeOutSeconds; /** -t	Specify the time out in seconds (time bin)  for expired flows to be flushed out of the memory (Default: 300) */
		unsigned int m_iOutputTimeBin; /** -z	Specify how many time bins will be considered to generate a new	output file name. As default, 0 means all day long (Default: 0) */
		string m_strReadingFileName; /** -r	Input file to offline capture */
		bool m_bOptimumFlowOutputEnabled; /**-b      Enabling an optimun flows exportation to Profiling (Disabled by default: false) */
		string m_strLogFileName; /** -l	Specify the log file name (Default: logcap) */
		bool m_bOutputThroughputEnabled; /** -m	Output throughput*/
		string m_strOutThroughputFileName /** -o	Specify the output file name for throughput measurement (Default: throughput) */;
		unsigned int m_iThreadNumber; /** -q	Specify the worker threads number (Default: 1) */

		/**
				 * Avoid bit-wise copy
		 */
		CUserInputParams ( const CUserInputParams& );
		const CUserInputParams& operator = ( const CUserInputParams& );




};

#endif
