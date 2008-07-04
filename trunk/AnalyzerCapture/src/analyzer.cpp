/**
 *   analyzer.cpp
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

#include <pthread.h>
#include <pcap.h>
#include <signal.h>		//ctrl+c routine
#include <iostream>

#include "analyzer.h"
#include "locks.h"
#include "ipheaderutil.h"
#include "classifier.h"
#include "analyserpxFlow.h"
#include "cap.h"
#include "userinputparams.h"
#include "macro.h"

using namespace std;

int onlineCapMode;

extern int flow_export;
extern int fileAdminTimeOff;
extern int slotsOffline;





CAnalyzer analyzer;
CPacketStatistician s_packetStatistician;

int CAnalyzer::analyserpxStartMultiThreaded(const CUserInputParams* pParam)
{
	pthread_t hashTimeOut;
	pthread_t packetStatTimeOut;
	
	return 0;
}

int CAnalyzer::analyserpxStartMultiThreaded ( cap_config * conf, int fileAdminTime, int fileExpTime,
        char *offLineFile, int flow_exp, int threadNum )
{
	extern int analyserpxError;
	pthread_t hashTimeOut;
	pthread_t packetStatTimeOut;
	extern char baseFileName[];
	extern char fileName[];
	extern int tFlag;
	admin_t *control= ( admin_t * ) malloc ( sizeof ( admin_t ) );
	control->interval=fileAdminTime;
	control->hop=fileExpTime;
	ThreadParams *tps        = new ThreadParams[threadNum];
	pthread_t *workerthreads = new pthread_t[threadNum];
	flow_export=flow_exp;
	/* interrupt routine to Ctrl-C */
	signal ( SIGINT, task_ctrl_C );

	pthread_mutex_init ( &Locks::cap_lock , NULL ) ;
	CAnalyzerAggregator::initVariables();


	for ( int i = 0; i < threadNum; ++i )
	{
		for ( int j = 0; j < 6; ++j )
		{
			tps[i].count[j]=0;
		}
		tps[i].counttotal = 0;
		tps[i].conf = conf;
	}



	//pthread_create(&hashTimeOut, NULL, verifyHashTimeOut, &fileAdminTime);

	if ( offLineFile == NULL )
	{
		onlineCapMode = 1;
		pthread_create ( &hashTimeOut, NULL, CAnalyzerAggregator::verifyHashTimeOut, control );
		pthread_create( &packetStatTimeOut, NULL, CPacketStatistician::PacketStatisticTimeOut, control );
	}
	else
	{
		snprintf ( fileName,256,"%s_0",baseFileName );
		onlineCapMode = 0;
		fileAdminTimeOff = fileAdminTime;
		slotsOffline=control->hop;
	}

	analyserpxError = CCaptureUtil::initiate_capture ( conf, onlineCapMode, offLineFile );
	if ( analyserpxError == 0 )
	{
		for ( int i = 0; i < threadNum; ++i )
		{
			pthread_create ( &workerthreads[i], NULL, threadsLoop, &tps[i] );
		}
		for ( int i = 0; i < threadNum; ++i )
		{
			pthread_join ( workerthreads[i],NULL );
		}
	}

	tFlag=0;
	//    printHash(fileName);
	CAnalyzerAggregator::printHash();
	if ( offLineFile == NULL )
	{
		pthread_exit ( ( void* ) CAnalyzerAggregator::verifyHashTimeOut );
	}
	//printHash(fileName);
	free ( control );
	delete [] workerthreads;
	delete [] tps;
	return analyserpxError;
}



void * CAnalyzer::threadsLoop ( void *par )
{
	ThreadParams       *tp = ( ThreadParams* ) par;
	struct pcap_pkthdr *header;
	const u_char       *packet;
	const struct ip *ip;	/* IP header */
	int size;
	int sizetmp;
	long long count = 0;

	struct pcap_pkthdr *head = ( pcap_pkthdr* ) malloc ( sizeof ( const struct pcap_pkthdr ) );
	const u_char       *pkt = ( unsigned char* ) malloc ( tp->conf->snap_len + 2 );

	int res;
	do
	{
		//pthread_mutex_lock(&cap_lock);
		while ( pthread_mutex_trylock ( &Locks::cap_lock ) != 0 )
			{}
		;
		res = pcap_next_ex ( tp->conf->descr, &header, &packet );
		if ( res >= 0 )
		{
			ip = ( struct ip * ) ( packet + ETHER_HDR_LEN );
			sizetmp = ntohs ( ip->ip_len );
			if ( sizetmp <= ( tp->conf->snap_len - ETHER_HDR_LEN ) )
			{
				size = sizetmp;
			}
			else
			{
				size = tp->conf->snap_len - ETHER_HDR_LEN;
			}
			memcpy ( ( void* ) head  , ( void* ) header, sizeof ( struct pcap_pkthdr ) );
			memcpy ( ( void* ) pkt, ( void* ) packet, size + ETHER_HDR_LEN );

			pthread_mutex_unlock ( &Locks::cap_lock );
			analyzer.processNewPacket ( ( u_char * ) & ( tp->conf->snap_len ), head, pkt, tp );
			//mount_flow( (u_char *) & (tp->conf->snap_len), head, pkt, tp );
			++tp->counttotal;
		}
		else
		{
			pthread_mutex_unlock ( &Locks::cap_lock );
		}


	}
	while ( res >= 0 );
	pthread_mutex_lock ( &Locks::print_lock );
	printf ( "Packets count : %d \n", tp->counttotal );
	long long tmp = tp->counttotal;
	for ( int i=0; i < 6; ++i )
	{
		printf ( "Packets count if%d : %d \n", i, tp->count[i] );//, ((double)tp->count[i]/(double)tp->counttotal)*100 );
		tmp -= tp->count[i];

	}
	printf ( "Packets not classified : %d \n", tmp );
	pthread_mutex_unlock ( &Locks::print_lock );
	free ( head );
	//free(pkt);
}

ResultEnum CAnalyzer::processNewPacket ( unsigned char *arg, const struct pcap_pkthdr *header, const u_char *packet,
        ThreadParams *tp )
{
	// Process the ip header first
	ip* pIPHeader;
	ResultEnum rs= CIPHeaderUtil::GetIPHeader ( packet, pIPHeader );
	EABASSERT ( rs );
	// Get the port info from packet
	u_int16_t src_port = 0;
	u_int16_t dst_port = 0;
	rs = CIPHeaderUtil::GetSrcAndDstPort ( packet, &src_port, &dst_port );

	// Get related info from ip header
	u_short tempIpLength =tempIpLength = * ( ( u_short* ) arg ) - ETHER_HDR_LEN;
	u_short len = ntohs ( pIPHeader->ip_len );
	u_short ipLength = tempIpLength <= len ? tempIpLength : len;
	u_short classifier = CClassifier::getID ( pIPHeader, ipLength );

	CPacketDigest* pPacketDigest = new CPacketDigest ( header, packet, classifier );
	rs = s_packetStatistician.AddNewPacketInfo ( pPacketDigest );
	EABASSERT ( rs );

	//m_totalPacketStatistic.addPacketInfo(&packetDigest);
	FREE_OBJECT ( pPacketDigest );
	// Process the flows
	CAnalyzerAggregator::mount_flow ( ipLength, header, pIPHeader, src_port, dst_port, classifier, tp );
	return rs;
}


void CAnalyzer::task_ctrl_C ( int i )
{
	//printHash("stdout");
	/*    char *filenameCountStr = malloc(sizeof(char) * 36);
	extern char fileName[];
	char *data = (char *) (malloc(sizeof(char)*7));
	extern char baseFileName[];
	time_t init = 0;
	filenameCount++;
	getDate(&init,data,6) ;
	snprintf(filenameCountStr,36,"%s_%u",data,filenameCount);
	snprintf(fileName,256,"%s%s",baseFileName, filenameCountStr);*/
	CAnalyzerAggregator::printHash();
	printf ( " <- Interrupt received.\nProgram will exit now!\n" );

	s_packetStatistician.PrintStatisticResult();

	/*free(filenameCountStr);
	free(data);*/
	exit ( 0 );
}


