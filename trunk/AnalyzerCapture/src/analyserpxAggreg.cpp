#include <unistd.h>
#include <pthread.h>
#include <time.h>

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <iostream>
#include <sstream>

#include "analyserpxAggreg.h"
#include "locks.h"
#include "analyserpxFlow.h"
#include "analyserpxFile.h"
#include "circularbuffer.h"
#include "classifier.h"
#include "hashtab.h"
#include "userinputparams.h"
#include "analyzer.h"
#include "macro.h"

//char *baseFileName = "cap", *logFileName = "logcap", *fileName = "cap0";

// RTA 18-03-08 - to avoid multiple definitions
hash_tab * CAnalyzerAggregator::test_table;

time_t CAnalyzerAggregator::tvSec;
time_t CAnalyzerAggregator::tvUSec;
string CAnalyzerAggregator::m_strFileName;
CUserInputParams* CAnalyzerAggregator::s_pInputParams;

//int fileAdminTimeOff;
int filenameCount = 0;
int flow_export=1;
//int slotsOffline;
//int numo=0;


// fixed
unsigned long long frames_cap = 0;
unsigned long long bytes_cap = 0;
// fixed


CAnalyzerAggregator::CAnalyzerAggregator ( CUserInputParams* pUserInputParams )
{
	s_pInputParams = pUserInputParams;
}

CAnalyzerAggregator::~CAnalyzerAggregator()
{
	//FREE_OBJECT(test_table);
}


void CAnalyzerAggregator::initVariables()
{
	pthread_mutex_init ( &Locks::hash_lock , NULL ) ;
	pthread_mutex_init ( &Locks::print_lock , NULL ) ;
	test_table = HashTableUtil::init_hash_table ( "ANALYSERPX_CAP_TABLE", CFlowUtil::compare_flow, CFlowUtil::flow_key,
	             CFlowUtil::delete_flow, HASH_SIZE );
}

ResultEnum CAnalyzerAggregator::optimumCleanHash ( hash_tab * hash, time_t sec, time_t usec, const char *fileName )
{
	ResultEnum rs = eOK;
	//cout << "I entered the clean hash program" << endl;
	//Sync Table begin
	//pthread_mutex_lock(&hash_lock);
	while ( pthread_mutex_trylock ( &Locks::hash_lock ) != 0 )
		{}
	;
	printf ( "Clean \n" );

	flow_t *flow_hsh=NULL;
	HashTableUtil::init_hash_walk ( hash );
	double hashTime=0, lastTime=0;
	lastTime = sec* ( 1e6 ) + usec;
	while ( ( flow_hsh = ( flow_t* ) HashTableUtil::next_hash_walk ( hash ) ) )
	{
		hashTime = ( ( flow_hsh->end_sec ) * ( 1e6 ) ) + ( flow_hsh->end_mic );
		if ( flow_export )
		{
			CFlowUtil::printFlowToFile ( flow_hsh, fileName );
			if ( ( lastTime - hashTime ) > ( TIMEOUT* ( 1e6 ) ) )
			{
				HashTableUtil::clear_hash_entry ( hash, flow_hsh );
			}
		}
		else
		{
			if ( ( lastTime - hashTime ) > ( TIMEOUT* ( 1e6 ) ) )
			{
				CFlowUtil::printFlowToFile ( flow_hsh, fileName );
				HashTableUtil::clear_hash_entry ( hash, flow_hsh );
			}
		}

	}

	//Sync Table begin
	pthread_mutex_unlock ( &Locks::hash_lock );
	return rs;

}
void CAnalyzerAggregator::cleanHash ( hash_tab * hash, time_t sec, time_t usec, char *fileName )
{
	flow_t *flow_hsh=NULL;
	HashTableUtil::init_hash_walk ( hash );
	double hashTime=0, lastTime=0;
	lastTime = sec* ( 1e6 ) + usec;
	while ( ( flow_hsh = ( flow_t* ) HashTableUtil::next_hash_walk ( hash ) ) )
	{
		hashTime = ( ( flow_hsh->end_sec ) * ( 1e6 ) ) + ( flow_hsh->end_mic );
		if ( ( lastTime - hashTime ) > ( TIMEOUT* ( 1e6 ) ) )
		{
			CFlowUtil::printFlowToFile ( flow_hsh, fileName );
			HashTableUtil::clear_hash_entry ( hash, flow_hsh );
		}

	}
	return;
}

int CAnalyzerAggregator::verifyTimeOut ( flow_t * flow1, flow_t * flow2 )
{
	double temp1 = flow1->end_sec* ( 1e6 ) + ( flow1->end_mic ) ;
	double temp2 = flow2->end_sec* ( 1e6 ) + ( flow2->end_mic );
	double result = temp2 - temp1;
	return ( result > ( TIMEOUT* ( 1e6 ) ) );
}

void CAnalyzerAggregator::verifyTimeOutHash ( flow_t *flow )
{

	static time_t start = 0;
	double dif;
	char *str = ( char* ) malloc ( sizeof ( char ) *1024 );
	//extern char baseFileName[]; //-b modification on 1 August 2007
	//extern char fileName[];
	static int offCount = 0;
	//static int offCount = 0;
	//u_short i = 0;
	time_t sec=0, usec=0;


	char *filenameCountStr = ( char* ) malloc ( sizeof ( char ) * 36 ); //-b modification on 1 August 2007


	if ( start == 0 )
	{
		start = flow->end_sec;
	}
	else
	{
		dif = difftime ( flow->end_sec,start );
		if ( s_pInputParams->GetOutputTimeBin() != 0 )
		{
			if ( dif >= ( ( ( double ) s_pInputParams->GetFlowTimeOutSeconds() ) * s_pInputParams->GetOutputTimeBin() ) )
			{
				//char *buffer=malloc(sizeof(char)*17);;
				//offCount++;
				//i=getIntLen(offCount);
				//snprintf(buffer,i+1, "%u", offCount);
				//snprintf(str, strlen(baseFileName)+i+1, "%s%u", baseFileName, offCount);
				//printHash(str);
				sec = tvSec;
				usec = tvUSec;
				//cleanHash(test_table, sec, usec, fileName);
				m_strFileName = s_pInputParams->GetFilePrefix();
				m_strFileName.append("_");
				stringstream strCount;
				strCount << offCount;
				m_strFileName.append(strCount.str());
				
				//snprintf ( fileName,256,"%s_%u",baseFileName, offCount ); //-b modification on 1 August 2007
				optimumCleanHash ( test_table, sec, usec, m_strFileName.c_str() );//introduced on 1 August 2007, it aims to optimize
				//the analyzer-px output according to -b parameter
				offCount++;					  //as well as this line
				//numo=offCount;					  //and this another one
				start = 0;
				//free(buffer);

			}
		}


	}
	free ( str );
	free ( filenameCountStr );


}

void CAnalyzerAggregator::addFlowSync ( flow_t * flow, const struct ip *ip, unsigned short ipLen, u_short classifier, ThreadParams *tp )
{
	//extern int onlineCapMode;

	flow_t *flow_hsh;
	flow_t *tmp_flow;
	flow_t *reverse_flow_hsh;

	if ( !s_pInputParams->isOnlineMode() )
	{
		verifyTimeOutHash ( flow );
	}

	//Sync Table begin
	//pthread_mutex_lock(&hash_lock);
	while ( pthread_mutex_trylock ( &Locks::hash_lock ) != 0 )
		{}
	;

	flow_hsh = ( flow_t* ) HashTableUtil::find_hash_entry ( test_table, flow );

	pthread_mutex_unlock ( &Locks::hash_lock );
	//Sync Table end

	if ( flow_hsh == NULL )
	{
		++tp->count[0];
	}
	else if ( verifyTimeOut ( flow_hsh, flow ) )
	{
		++tp->count[1];
	}
	else
	{
		if ( ( flow_hsh->class_proto == PROTO_ID_NONPAYLOAD ) )
		{
			//We can't classify a flow with NONPAYLOAD type only because his first packet
			++tp->count[2];
		}
		else if ( ( flow_hsh->class_proto < DOWN_BASE_P2P_CLASS_NUMBER ) )
		{
			++tp->count[3];
		}
		else if ( CClassifier::isSuperClass ( flow_hsh->class_proto ) )
		{
			++tp->count[4];
		} /*else if ( (flow_hsh->class_proto>UP_BASE_P2P_CLASS_NUMBER)&&(flow_hsh->class_proto<PROTO_ID_NONPAYLOAD) ){
		++tp->count[5];
		classifier = getID(ip, ipLen);
	}*/
	}

	tmp_flow = CFlowUtil::createFlow_t ( ip->ip_p,ip->ip_p, NULL, NULL, flow->dst_port, flow->src_port,
	                                     ( unsigned int ) ntohs ( ip->ip_len ), 1, flow->ini_sec,
	                                     flow->end_sec, flow->ini_mic, flow->end_mic, ip->ip_dst,ip->ip_src );
	//Sync Table begin
	//pthread_mutex_lock(&hash_lock);
	while ( pthread_mutex_trylock ( &Locks::hash_lock ) != 0 )
		{}
	;
	flow_hsh         = ( flow_t* ) HashTableUtil::find_hash_entry ( test_table, flow );
	reverse_flow_hsh = ( flow_t* ) HashTableUtil::find_hash_entry ( test_table, tmp_flow );

	if ( flow_hsh == NULL )
	{
		HashTableUtil::add_hash_entry ( test_table, flow );
		if ( ( reverse_flow_hsh == NULL ) )
		{
			flow->class_proto = classifier;
		}
		else
		{
			u_short tmp_id=0;
			tmp_id = CClassifier::verID ( reverse_flow_hsh->class_proto,classifier );
			reverse_flow_hsh->class_proto = tmp_id;
			flow->class_proto = tmp_id;
		}

	}
	else if ( verifyTimeOut ( flow_hsh, flow ) )
	{
		//extern char fileName[];
		CFlowUtil::printFlowToFile ( flow_hsh, m_strFileName.c_str() );
		HashTableUtil::clear_hash_entry ( test_table, flow_hsh );
		HashTableUtil::add_hash_entry ( test_table, flow );
		if ( ( reverse_flow_hsh == NULL ) )
		{
			flow->class_proto = classifier;
		}
		else
		{
			u_short tmp_id=0;
			tmp_id = CClassifier::verID ( reverse_flow_hsh->class_proto,classifier );
			reverse_flow_hsh->class_proto = tmp_id;
			flow->class_proto = tmp_id;
		}
	}
	else
	{
		flow_hsh->n_bytes += flow->n_bytes;
		flow_hsh->n_frames += flow->n_frames;

		/* RTA - 26/05/08
		Verification added for synchronization purposes
		*/
		double end   = ( double ) flow_hsh->end_sec* ( 1e6 ) + ( double ) ( flow_hsh->end_mic ) ;
		double act   = ( double ) flow->ini_sec* ( 1e6 ) + ( double ) ( flow->ini_mic ) ;
		if ( act > end )
		{
			flow_hsh->end_sec = flow->ini_sec;
			flow_hsh->end_mic = flow->ini_mic;
		}
		else
		{
			double ini   = ( double ) flow_hsh->ini_sec* ( 1e6 ) + ( double ) ( flow_hsh->ini_mic ) ;
			if ( act < ini )
			{
				flow_hsh->ini_sec = flow->ini_sec;
				flow_hsh->ini_mic = flow->ini_mic;
			}
		}

		CFlowUtil::delete_flow ( flow );
		if ( ( flow_hsh->class_proto == PROTO_ID_NONPAYLOAD ) )
		{
			//We can't classify a flow with NONPAYLOAD type only because his first packet
			if ( ( reverse_flow_hsh == NULL ) )
			{
				flow_hsh->class_proto = classifier;
			}
			else
			{
				u_short tmp_id=0;
				tmp_id = CClassifier::verID ( reverse_flow_hsh->class_proto,classifier );
				reverse_flow_hsh->class_proto = tmp_id;
				flow_hsh->class_proto = tmp_id;
			}
		}
		else if ( ( flow_hsh->class_proto < DOWN_BASE_P2P_CLASS_NUMBER ) )
		{
			if ( ( classifier>=DOWN_BASE_P2P_CLASS_NUMBER ) && ( classifier<PROTO_ID_NONPAYLOAD ) )
			{
				if ( ( reverse_flow_hsh == NULL ) )
				{
					flow_hsh->class_proto = classifier;
				}
				else
				{
					u_short tmp_id=0;
					tmp_id = CClassifier::verID ( reverse_flow_hsh->class_proto,classifier );
					reverse_flow_hsh->class_proto = tmp_id;
					flow_hsh->class_proto = tmp_id;
				}
			}
		}
		else if ( CClassifier::isSuperClass ( flow_hsh->class_proto ) )
		{
			if ( ( classifier>=DOWN_BASE_P2P_CLASS_NUMBER ) && ( classifier<PROTO_ID_NONPAYLOAD ) )
			{
				if ( ( reverse_flow_hsh == NULL ) )
				{
					if ( ! ( classifier == 116 && flow_hsh->class_proto == 201 ) ) //adicionado temporariamente lembrar de tirar
						flow_hsh->class_proto = classifier;
				}
				else
				{
					u_short tmp_id=0;
					tmp_id = CClassifier::verID ( reverse_flow_hsh->class_proto,classifier );
					if ( ! ( tmp_id == 116 && flow_hsh->class_proto == 201 ) )
					{
						//adicionado temporariamente lembrar de tirar
						reverse_flow_hsh->class_proto = tmp_id;
						flow_hsh->class_proto = tmp_id;
					}
				}
			}
		} /*else if ( (flow_hsh->class_proto>UP_BASE_P2P_CLASS_NUMBER)&&(flow_hsh->class_proto<PROTO_ID_NONPAYLOAD) ){
		if( ((classifier<=UP_BASE_P2P_CLASS_NUMBER) && (classifier>=DOWN_BASE_P2P_CLASS_NUMBER)) ) {
		if((reverse_flow_hsh == NULL)) {
		flow_hsh->class_proto = classifier;
	} else {
		reverse_flow_hsh->class_proto = classifier;
		flow_hsh->class_proto = classifier;
	}
	}
	}*/
	}

	pthread_mutex_unlock ( &Locks::hash_lock );
	//Sync Table End
	CFlowUtil::delete_flow ( tmp_flow );
}

/*
* dissect packet
*/
void CAnalyzerAggregator::mount_flow ( unsigned short ipLen, const struct pcap_pkthdr *header,
                                       const ip * pIpHeader, u_int16_t src_port, u_int16_t dst_port,
                                       u_short classifier, ThreadParams *tp )
{

	//extern int outputThroughput;

	// fixed
	if ( s_pInputParams->IsOutputThroughputEnabled() )
	{
		frames_cap++;
		bytes_cap += ( unsigned int ) ntohs ( pIpHeader->ip_len );
	}
	// fixed

	flow_t* flow = CFlowUtil::createFlow_t ( pIpHeader->ip_p, pIpHeader->ip_p, NULL, NULL, src_port, dst_port,
	               ( unsigned int ) ntohs ( pIpHeader->ip_len ), 1,
	               ( time_t ) ( header->ts.tv_sec ),
	               ( time_t ) ( header->ts.tv_sec ),
	               ( time_t ) ( header->ts.tv_usec ),
	               ( time_t ) ( header->ts.tv_usec ), pIpHeader->ip_src,
	               pIpHeader->ip_dst );

	tvSec = ( time_t ) ( header->ts.tv_sec );
	tvUSec = ( time_t ) ( header->ts.tv_usec );

	addFlowSync ( flow, pIpHeader, ipLen, classifier, tp );

	return;
}

//void printHash(char *fileName)
void CAnalyzerAggregator::printHash()
{
	flow_t *flow_hsh;
	HashTableUtil::init_hash_walk ( test_table );
	struct tm *clock = NULL;
	//extern char baseFileName[];
	//extern char fileName[];
	//snprintf(fileName,256,"%s_%u",baseFileName, numo);
	//char *filenameCountStr = ( char* ) malloc ( sizeof ( char ) * 36 );
	//extern char fileName[];
	char *data = ( char * ) ( malloc ( sizeof ( char ) *7 ) );
	//extern char baseFileName[];
	time_t init = 0;
	time ( &init );

	clock = ( struct tm * ) localtime ( & ( init ) );
	CFlowUtil::getDate ( &init,data,6 ) ;
	m_strFileName = s_pInputParams->GetFilePrefix();
	string date = string(data, strlen(data));
	m_strFileName.append(date);
	m_strFileName.append("_latestFile");
	//snprintf ( filenameCountStr,36,"%s_latestFile",data );
	//snprintf ( fileName,256,"%s%s",baseFileName, filenameCountStr );
	while ( ( flow_hsh = ( flow_t* ) HashTableUtil::next_hash_walk ( test_table ) ) )
	{
		//	fprintf(stdout,"Estamos aqui 1\n");
		CFlowUtil::printFlowToFile ( flow_hsh, m_strFileName.c_str() );
	}
	//free ( filenameCountStr );
	free ( data );
	HashTableUtil::clear_hash_table ( test_table );
}



void * CAnalyzerAggregator::verifyHashTimeOut ( void *par )
{
	ResultEnum rs = eOK;
	int filenameCount = 0;
	int flag=1;
	int fileAdminTime = ( ( admin_t * ) par )->interval;
	int fileExpTime = ( ( admin_t * ) par )->hop;
	time_t init = 0, final = 0, sec, usec;
	struct tm *clock = NULL;
	//extern int analyserpxError;
	//extern char logFileName[];
	//extern char baseFileName[];
	//extern char fileName[];
	//extern char outputFileName[];
	//extern int outputThroughput;
	//extern int tFlag;
	int interCounter=0;

	// fixed
	unsigned long long frames_caled = 0;
	unsigned long long bytes_caled = 0;
	unsigned long long frames_cal;
	unsigned long long bytes_cal;
	float fraction;
	if ( s_pInputParams->IsOutputThroughputEnabled() )
	{
		fraction = 1.0 / fileAdminTime;
	}
	// fixed

	//char *filenameCountStr = ( char* ) malloc ( sizeof ( char ) * 36 );
	

	while ( /*( analyserpxError == 0 ) &&*/CAnalyzer::tFlag )
	{
		int sleepingTime =  fileAdminTime - ( final - init );
		//cout << "I will sleep for " << sleepingTime << endl;
		sleep ( sleepingTime );

		// fixed
		if ( s_pInputParams->IsOutputThroughputEnabled() )
		{
			frames_cal = frames_cap - frames_caled;
			//      bytes_cal = bytes_cap - bytes_caled;
			FILE *out = fopen ( s_pInputParams->GetOutThroughputFileName().c_str(), "a" );
			if ( out == NULL )
				printf ( "error!\n" );
			fprintf ( out, "# %.2f fps\n", frames_cal*fraction );
			fclose ( out );
			//     	printf("# %.0d %.0d %.2fpackets/s %.0d %.2fbytes/s\n", (int)frames_cap, (int)frames_cal, frames_cal*fraction, (int)bytes_cal, bytes_cal*fraction);
			frames_caled += frames_cal;
			//		bytes_caled += bytes_cal;
		}
		// fixed

		sec = tvSec;
		usec = tvUSec;
		time ( &init );

		clock = ( struct tm * ) localtime ( & ( init ) );
		//string fileName;
		if ( ( interCounter>=fileExpTime ) && ( fileExpTime>0 ) )
		{
			filenameCount++;
			rs = GetFileName(filenameCount, &m_strFileName);
			EABASSERT(rs == eOK);
			interCounter=0;
		}
		if ( ( ( clock->tm_hour ) == 0 ) && ( ( clock->tm_min ) < ( ( int ) ( ( fileAdminTime*2 ) /60 ) ) ) )
		{
			if ( flag )
			{
				filenameCount=0;
				rs = GetFileName(filenameCount, &m_strFileName);
				EABASSERT(rs == eOK);
			//snprintf ( fileName,256,"%s%s",baseFileName, filenameCountStr );
				interCounter=0;
			}
			flag=0;
		}
		else
		{
			flag=1;
		}
		//cleanHash(test_table, sec, usec, fileName);

		rs = optimumCleanHash ( test_table, sec, usec, m_strFileName.c_str() );//introduced on 1 August 2007, it aims to optimize
		EABASSERT(rs == eOK);
		//the analyzer-px output according to -b parameter
		interCounter++;
		time ( &final );
	}
	if ( eOK != rs )
	{
		char buffer[50];
		snprintf ( buffer, 50,"ERROR CLEANING HASH. ERROR CODE: %u", rs );
		CFileUtil::writeStringToLogFile ( s_pInputParams->GetLogFileName().c_str(), buffer, NULL );
	}

	//free ( filenameCountStr );
	//free ( data );

	//pthread_exit (0);
	return ( void * ) NULL;
}

ResultEnum CAnalyzerAggregator::GetFileName(const int count, string* fileName)
{
	time_t init = 0;
	time ( &init );
	if (NULL == fileName) return eEmptyPointer;
	fileName->clear();
	fileName->append(s_pInputParams->GetFilePrefix());
	ResultEnum rs = eOK;
	char *data = ( char * ) ( malloc ( sizeof ( char ) *7 ) );
	CFlowUtil::getDate ( &init,data,6 ) ;
	string filenameCountStr = string(data, strlen(data));
	filenameCountStr.append("_");
	stringstream strCount;
	strCount << count;
	filenameCountStr.append(strCount.str());
			//snprintf ( filenameCountStr,36,"%s_%u",data,filenameCount );
	fileName->append(filenameCountStr);
			//snprintf ( fileName,256,"%s%s",baseFileName, filenameCountStr );
	free(data);
	return rs;
}

/*void *verifyHashTimeOut(void *par)
{
int filenameCount = 0;
int fileAdminTime = *((int *) par);
time_t init = 0, final = 0, sec, usec;
extern int analyserpxError;
extern char logFileName[];
extern char *baseFileName;
extern char *fileName;

char *filenameCountStr = malloc(sizeof(char) * 16);

while (analyserpxError == 0) {
sleep(fileAdminTime - (final - init));
sec = tvSec;
usec = tvUSec;
time(&init);
sprintf(filenameCountStr, "%d", filenameCount++);
strncpy(fileName, baseFileName,strlen(baseFileName));
strncat(fileName, filenameCountStr,strlen(filenameCountStr));
cleanHash(test_table, sec, usec, fileName);
time(&final);
}

if (analyserpxError) {
char buffer[50];
sprintf(buffer, "ERROR CLEANING HASH. ERROR CODE: %d", analyserpxError);
writeStringToLogFile(logFileName, buffer, NULL);
}

return (void *) NULL;
}*/
