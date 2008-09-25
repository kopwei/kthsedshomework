/**
 *   analyzer.h
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

#ifndef ANALYZER_H
#define ANALYZER_H

#include "PacketStatistician.h"
#include "analyserpxAggreg.h"
//#include <y>

struct pcap_pkthdr;
class CUserInputParams;

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class CAnalyzer
{
	public:
		/*!     \fn int analyserpxStart(cap_config *conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp)
		\brief Analyser-PX Start up function.

		Start all capturing, aggregating, and classifier process.
		\param *conf		Pointer to cap_config structure pointer with all libcap needed parameter.
		\param fileAdminTime  	The cicle time to save flows to file.
		\param fileExpTime  	Interval counter to output file modification.
		\param *offLineFile	Name of file to read the packets.
		\param flow_exp		Export flow optmization flag.
		\return Error code.
		 */
		//int analyserpxStart(cap_config *conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp);



		int analyserpxStartMultiThreaded ( cap_config * conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp, int threadNum );

		int analyserpxStartMultiThreaded (CUserInputParams* pParam);
		
		static bool tFlag;

	private:


		static void *threadsLoop ( void *par );

		static ResultEnum processNewPacket ( unsigned char *args, const struct pcap_pkthdr *header, const u_char *packet, ThreadParams *tp );

		/*!     \fn void task_ctrl_C(int i)
		\brief CTRL+C Interpreter function.

		Cath the interrupt SIGINT caused by pressing the "CRT-C" buttons  and save to file all the remained flow that still into hash.
		\param i	Interrupt signal.
		 */
		static void task_ctrl_C ( int i );

		static bool NeedStoreResult(const pcap_pkthdr* header, const tm* t);

		static bool NeedStoreDailyResult(const pcap_pkthdr* header, const tm* t);

		static ResultEnum RecordStatus(const tm* t);

		static ResultEnum RecordDailyStatus(const tm* t);
		
		static ResultEnum RecordFinalResult();
		
		static CUserInputParams* s_pUserInputParams;
		
		static CPacketStatistician		s_packetStatistician;

		static bool s_bIsStoring;

		static bool s_bIsFirstTime;

		static tm  s_refTime;
};

#endif
