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
#include "PacketDigest.h"
#include "CommonUtil.h"

using namespace std;

//extern int flow_export;
//extern int fileAdminTimeOff;
//extern int slotsOffline;


	


bool CAnalyzer::tFlag = true;
CUserInputParams* CAnalyzer::s_pUserInputParams = NULL;

CPacketStatistician CAnalyzer::s_packetStatistician;
bool CAnalyzer::s_bIsFirstTime = true;
bool CAnalyzer::s_bIsStoring = false;
tm  CAnalyzer::s_refTime;

int CAnalyzer::analyserpxStartMultiThreaded(CUserInputParams* pParam)
{
    s_pUserInputParams = pParam;
    pthread_t hashTimeOut;
    pthread_t packetStatTimeOut;
    admin_t control;
    control.interval = pParam->GetFlowTimeOutSeconds();
    control.hop = pParam->GetOutputTimeBin();

    ThreadParams tps[pParam->GetThreadNumber()];
    pthread_t workerthreads[pParam->GetThreadNumber()];

    /* interrupt routine to Ctrl-C */
    signal ( SIGINT, task_ctrl_C );
    //time_t t = 0;
    //s_refTime = localtime(&t);


    pthread_mutex_init ( &Locks::cap_lock , NULL ) ;
    CAnalyzerAggregator::initVariables(pParam);


    for ( int i = 0; i < pParam->GetThreadNumber(); ++i )
    {
        for ( int j = 0; j < 6; ++j )
        {
            tps[i].count[j]=0;
        }
        tps[i].counttotal = 0;
        //tps[i].conf = pParam->GetCaptureConfig();
    }



    //pthread_create(&hashTimeOut, NULL, verifyHashTimeOut, &fileAdminTime);
    string strFileName = pParam->GetFilePrefix();
    if ( pParam->isOnlineMode() )
    {
        pthread_create ( &hashTimeOut, NULL, CAnalyzerAggregator::verifyHashTimeOut, &control );
        pthread_create( &packetStatTimeOut, NULL, CPacketStatistician::PacketStatisticTimeOut, &control );
    }
    else
    {
        strFileName.append("_0");
    }
    int analyserpxError;
    for (int j = 0; j < 56; j++)
    {
		string intstr = CommonUtil::itoa(j, 10);
		//cout << "number string ready: " << intstr << endl;
		//cin >> c ;
		string namebase = pParam->GetReadingFileName();
		//cout << "name base ready "<< namebase << endl;
		//cin >> c;
		if (j > 0)
		{
			namebase.append(intstr);	
		}
		if ( j == 3)
		{
			int b = 10;
		}
		//cout << "Name ready " << namebase << endl;
		//cin >> c; 
		analyserpxError = CCaptureUtil::initiate_capture ( pParam->GetCaptureConfig(), pParam->isOnlineMode(), namebase );
        if ( analyserpxError == 0 )
        {
			
            for ( int i = 0; i < pParam->GetThreadNumber(); ++i )
            {
                pthread_create ( &workerthreads[i], NULL, threadsLoop, &tps[i] );
				//cout << "thread " << i << "created" <<endl;
            }
            for ( int i = 0; i < pParam->GetThreadNumber(); ++i )
            {
                pthread_join ( workerthreads[i],NULL );
				//cout << "thread " << i << "terminated" <<endl;
            }
        }
		
		cout <<"File "<< namebase << " processing finished " <<  endl;
    }
    tFlag = false;
	tm t = s_refTime;
	time_t newTime = mktime(&t) + 1;
	t = *(localtime(&newTime));
    //    printHash(fileName);
	RecordStatus(&t);
    CAnalyzerAggregator::printHash();   
	
    if ( pParam->isOnlineMode() )
    {
        pthread_exit ( ( void* ) CAnalyzerAggregator::verifyHashTimeOut );
        pthread_exit((void*) CPacketStatistician::PacketStatisticTimeOut );
    }
	return analyserpxError;
}

void * CAnalyzer::threadsLoop ( void *par )
{
	
    ThreadParams       *tp = ( ThreadParams* ) par;
    struct pcap_pkthdr *header;
    const u_char       *packet;
    const struct ip *ip;	// IP header 
	int size;
    int sizetmp;
    long long count = 0;

    cap_config* pCapConfig = s_pUserInputParams->GetCaptureConfig();
    struct pcap_pkthdr *head = ( pcap_pkthdr* ) malloc ( sizeof ( const struct pcap_pkthdr ) );
    /* const*/ u_char       *pkt = ( unsigned char* ) malloc ( pCapConfig->snap_len + 2 );



    int res;
	
    do
    {
		
        //pthread_mutex_lock(&cap_lock);
        while ( pthread_mutex_trylock ( &Locks::cap_lock ) != 0 )
            {}
        ;
        res = pcap_next_ex ( pCapConfig->descr, &header, &packet );
		
        if ( res >= 0 )
        {
			
            if (s_bIsFirstTime)
            {
                if (0 == pthread_mutex_trylock(&Locks::time_lock))
                {
                    s_refTime = *(localtime(&(header->ts.tv_sec)));
                    s_bIsFirstTime = false;
                    pthread_mutex_unlock(&Locks::time_lock);
                }
            }
            // If the result storing is needed, we set the flag of storing to true
			tm refTime = s_refTime;
			if (NeedStoreResult(header, &refTime) && !s_bIsStoring)
            {
               if (0 == pthread_mutex_trylock(&Locks::storing_lock))
                {
					
                    s_bIsStoring = true;
                    s_refTime = *(localtime(&(header->ts.tv_sec)));
                    tm t = s_refTime;
					//cout << "entered to record"<< endl;
                    RecordStatus(&t);
					// If the day is end, we have to store the daily result					
                    s_bIsStoring = false;
				   
                    pthread_mutex_unlock(&Locks::storing_lock);				   
                }
            }
			

            // Start to analyze
            ip = ( struct ip * ) ( packet + ETHER_HDR_LEN );
            sizetmp = ntohs ( ip->ip_len );
            if ( sizetmp <= ( pCapConfig->snap_len - ETHER_HDR_LEN ) )
            {
                size = sizetmp;
            }
            else
            {
                size = pCapConfig->snap_len - ETHER_HDR_LEN;
            }
            memcpy ( ( void* ) head  , ( void* ) header, sizeof ( struct pcap_pkthdr ) );
			void* pk = ( void* ) pkt;
			void* pack = ( void* ) packet;
            memcpy ( pk, pack, size + ETHER_HDR_LEN );

            pthread_mutex_unlock ( &Locks::cap_lock );
            processNewPacket ( ( u_char * ) & ( pCapConfig->snap_len ), head, pkt, tp );
            //mount_flow( (u_char *) & (tp->conf->snap_len), head, pkt, tp );
            ++tp->counttotal;
        }
        else
        {
            pthread_mutex_unlock ( &Locks::cap_lock );
        }

		
		
    }
    while ( res >= 0 );
	
//	pthread_mutex_lock ( &Locks::print_lock );
//	printf ( "Packets count : %d \n", tp->counttotal );
//	long long tmp = tp->counttotal;
//	for ( int i=0; i < 6; ++i )
//	{
//		printf ( "Packets count if%d : %d \n", i, tp->count[i] );//, ((double)tp->count[i]/(double)tp->counttotal)*100 );
//		tmp -= tp->count[i];

//	}
//	printf ( "Packets not classified : %d \n", tmp );
//	pthread_mutex_unlock ( &Locks::print_lock );

    //s_packetStatistician.PrintStatisticResult(localtime(&(header->ts.tv_sec)));
    free ( head );
	head = NULL;
    free(pkt);
	pkt= NULL;
	

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
    u_short tempIpLength = * ( ( u_short* ) arg ) - ETHER_HDR_LEN;
    u_short len = ntohs ( pIPHeader->ip_len );
    u_short ipLength = tempIpLength <= len ? tempIpLength : len;
	//u_short classifier = 0;
    u_short classifier = CClassifier::getID ( pIPHeader, ipLength );
    //u_short classifier = CClassifier::getID(pIPHeader, ipLength, src_port, dst_port);

    // Process the flows
	flow_t* pflow = NULL;
    pflow = CAnalyzerAggregator::mount_flow ( ipLength, header, pIPHeader, src_port, dst_port, classifier, tp );

    CPacketDigest packetDigest( header, packet, pflow );
    rs = s_packetStatistician.AddNewPacketInfo ( &packetDigest );
    EABASSERT ( rs );
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

    //s_packetStatistician.PrintStatisticResult();

    /*free(filenameCountStr);
    free(data);*/
    exit ( 0 );
}

bool CAnalyzer::NeedStoreResult( const pcap_pkthdr* header, const tm* t )
{
    //TODO: need implementation here
    tm* time = localtime(&(header->ts.tv_sec));
    int packetMin = time->tm_min;
    int refMin = t->tm_min;
    return (time->tm_sec != t->tm_sec
		|| time->tm_min != t->tm_min 
		|| time->tm_hour != t->tm_hour 
		|| time->tm_yday != t->tm_yday);
}

ResultEnum CAnalyzer::RecordStatus(const tm* t)
{
    // TODO:
    //s_refTime
    s_packetStatistician.PrintStatisticResult(t);
	CAnalyzerAggregator::PrintStatisticResult(t);

    return eOK;
}
