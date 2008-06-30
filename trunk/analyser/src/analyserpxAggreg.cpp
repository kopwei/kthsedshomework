#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include <signal.h>		//ctrl+c routine
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include "../include/analyserpxAggreg.h"
#include "../include/cap.h"
#include "../include/analyserpxFlow.h"
#include "../include/analyserpxFile.h"
#include "../include/classifier.h"
#include "../include/circularbuffer.h"


#include "../include/PacketStatistician.h"
//char *baseFileName = "cap", *logFileName = "logcap", *fileName = "cap0";

// RTA 18-03-08 - to avoid multiple definitions
hash_tab *test_table;

time_t tvSec, tvUSec;
int onlineCapMode;
int fileAdminTimeOff;
int filenameCount = 0;
int flow_export=1;
int slotsOffline;
int numo=0;

pthread_mutex_t cap_lock;
pthread_mutex_t hash_lock;
pthread_mutex_t print_lock;

// fixed
unsigned long long frames_cap = 0;
unsigned long long bytes_cap = 0;
// fixed

CPacketStatistician statistician;

void CAnalyzerAggregator::optimumCleanHash(hash_tab * hash, time_t sec, time_t usec, char *fileName)
{
	//Sync Table begin
	//pthread_mutex_lock(&hash_lock);
	while ( pthread_mutex_trylock(&hash_lock) != 0 ){};
	printf("Clean \n");	

	flow_t *flow_hsh=NULL;
	HashTableUtil::init_hash_walk(hash);
	double hashTime=0, lastTime=0;
	lastTime = sec*(1e6) + usec;
	while ( (flow_hsh = (flow_t*) HashTableUtil::next_hash_walk(hash)) ) 
	{
		hashTime = ( (flow_hsh->end_sec)*(1e6) ) + (flow_hsh->end_mic);
		if(flow_export) 
		{
			CFlowUtil::printFlowToFile(flow_hsh, fileName);
			if ( (lastTime - hashTime) > (TIMEOUT*(1e6)) ) 
			{
				HashTableUtil::clear_hash_entry(hash, flow_hsh);
			}
		} 
		else 
		{
			if ( (lastTime - hashTime) > (TIMEOUT*(1e6)) ) 
			{
				CFlowUtil::printFlowToFile(flow_hsh, fileName);
				HashTableUtil::clear_hash_entry(hash, flow_hsh);
			}
		}

	}
	return;
	//Sync Table begin
	pthread_mutex_unlock(&hash_lock);

}
void CAnalyzerAggregator::cleanHash(hash_tab * hash, time_t sec, time_t usec, char *fileName)
{
	flow_t *flow_hsh=NULL;
	HashTableUtil::init_hash_walk(hash);
	double hashTime=0, lastTime=0;
	lastTime = sec*(1e6) + usec;
	while ( (flow_hsh =  (flow_t*) HashTableUtil::next_hash_walk(hash))) 
	{
		hashTime = ( (flow_hsh->end_sec)*(1e6) ) + (flow_hsh->end_mic);
		if ( (lastTime - hashTime) > (TIMEOUT*(1e6)) ) 
		{
			CFlowUtil::printFlowToFile(flow_hsh, fileName);
			HashTableUtil::clear_hash_entry(hash, flow_hsh);
		}

	}
	return;
}

int CAnalyzerAggregator::verifyTimeOut(flow_t * flow1, flow_t * flow2)
{
	double temp1 = flow1->end_sec*(1e6) + (flow1->end_mic) ;
	double temp2 = flow2->end_sec*(1e6) + (flow2->end_mic);
	double result = temp2 - temp1;
	return ( result > (TIMEOUT*(1e6)) );
}

void CAnalyzerAggregator::verifyTimeOutHash(flow_t *flow)
{

	static time_t start = 0;
	double dif;
	char *str = (char*) malloc(sizeof(char)*1024);
	extern char baseFileName[]; //-b modification on 1 August 2007
	extern char fileName[];
	static int offCount = 0;
	//static int offCount = 0;
	//u_short i = 0;
	time_t sec=0, usec=0;


	char *filenameCountStr = (char*) malloc(sizeof(char) * 36); //-b modification on 1 August 2007


	if(start == 0)
	{
		start = flow->end_sec;
	}
	else
	{
		dif = difftime (flow->end_sec,start);
		if(slotsOffline) {
			if(dif >= (((double)fileAdminTimeOff)*slotsOffline))
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
				snprintf(fileName,256,"%s_%u",baseFileName, offCount); //-b modification on 1 August 2007
				optimumCleanHash(test_table, sec, usec, fileName);//introduced on 1 August 2007, it aims to optimize
				//the analyzer-px output according to -b parameter
				offCount++;					  //as well as this line
				numo=offCount;					  //and this another one
				start = 0;
				//free(buffer);

			}
		}


	}
	free(str);
	free(filenameCountStr);


}

void CAnalyzerAggregator::addFlowSync(flow_t * flow, const struct ip *ip, unsigned short ipLen, ThreadParams *tp)
{
	flow_t *flow_hsh;
	flow_t *tmp_flow;
	flow_t *reverse_flow_hsh;

	u_short classifier;
	if(!onlineCapMode)
	{
		verifyTimeOutHash(flow);
	}

	//Sync Table begin
	//pthread_mutex_lock(&hash_lock);
	while ( pthread_mutex_trylock(&hash_lock) != 0 ){};

	flow_hsh = (flow_t*) HashTableUtil::find_hash_entry(test_table, flow);

	pthread_mutex_unlock(&hash_lock);
	//Sync Table end

	if (flow_hsh == NULL) {
		++tp->count[0];
		classifier = CClassifier::getID(ip, ipLen);
	} else if (verifyTimeOut(flow_hsh, flow)) {
		++tp->count[1];
		classifier =CClassifier:: getID(ip, ipLen);
	} else {
		if( (flow_hsh->class_proto == PROTO_ID_NONPAYLOAD) ) {//We can't classify a flow with NONPAYLOAD type only because his first packet
			++tp->count[2];
			classifier = CClassifier::getID(ip, ipLen);
		}
		else if( (flow_hsh->class_proto < DOWN_BASE_P2P_CLASS_NUMBER) ) {
			++tp->count[3];
			classifier = CClassifier::getID(ip, ipLen);
		} else if ( CClassifier::isSuperClass(flow_hsh->class_proto) ) {
			++tp->count[4];
			classifier = CClassifier::getID(ip, ipLen);
		} /*else if ( (flow_hsh->class_proto>UP_BASE_P2P_CLASS_NUMBER)&&(flow_hsh->class_proto<PROTO_ID_NONPAYLOAD) ){
		  ++tp->count[5];
		  classifier = getID(ip, ipLen);
		  }*/
	}

	tmp_flow = CFlowUtil::createFlow_t(ip->ip_p,ip->ip_p, NULL, NULL, flow->dst_port, flow->src_port,
		(unsigned int) ntohs(ip->ip_len), 1, flow->ini_sec,
		flow->end_sec, flow->ini_mic, flow->end_mic, ip->ip_dst,ip->ip_src);
	//Sync Table begin
	//pthread_mutex_lock(&hash_lock);
	while ( pthread_mutex_trylock(&hash_lock) != 0 ){};
	flow_hsh         = (flow_t*) HashTableUtil::find_hash_entry(test_table, flow);
	reverse_flow_hsh = (flow_t*) HashTableUtil::find_hash_entry(test_table, tmp_flow);

	if (flow_hsh == NULL) {
		HashTableUtil::add_hash_entry(test_table, flow);
		if((reverse_flow_hsh == NULL)) {
			flow->class_proto = classifier;
		} else {
			u_short tmp_id=0;
			tmp_id = CClassifier::verID(reverse_flow_hsh->class_proto,classifier);
			reverse_flow_hsh->class_proto = tmp_id;
			flow->class_proto = tmp_id;
		}

	} else if (verifyTimeOut(flow_hsh, flow)) {
		extern char fileName[];
		CFlowUtil::printFlowToFile(flow_hsh, fileName);
		HashTableUtil::clear_hash_entry(test_table, flow_hsh);
		HashTableUtil::add_hash_entry(test_table, flow);
		if((reverse_flow_hsh == NULL)) {
			flow->class_proto = classifier;
		} else {
			u_short tmp_id=0;
			tmp_id = CClassifier::verID(reverse_flow_hsh->class_proto,classifier);
			reverse_flow_hsh->class_proto = tmp_id;
			flow->class_proto = tmp_id;
		}
	} else {
		flow_hsh->n_bytes += flow->n_bytes;
		flow_hsh->n_frames += flow->n_frames;

		/* RTA - 26/05/08
		Verification added for synchronization purposes
		*/
		double end   = (double)flow_hsh->end_sec*(1e6) + (double)(flow_hsh->end_mic) ;    	
		double act   = (double)flow->ini_sec*(1e6) + (double)(flow->ini_mic) ;
		if ( act > end ){
			flow_hsh->end_sec = flow->ini_sec;
			flow_hsh->end_mic = flow->ini_mic;	
		} else 	{
			double ini   = (double)flow_hsh->ini_sec*(1e6) + (double)(flow_hsh->ini_mic) ;
			if ( act < ini ){
				flow_hsh->ini_sec = flow->ini_sec;
				flow_hsh->ini_mic = flow->ini_mic;	
			}
		}

		CFlowUtil::delete_flow(flow);
		if( (flow_hsh->class_proto == PROTO_ID_NONPAYLOAD) ) {//We can't classify a flow with NONPAYLOAD type only because his first packet
			if((reverse_flow_hsh == NULL)) {
				flow_hsh->class_proto = classifier;
			} else {
				u_short tmp_id=0;
				tmp_id = CClassifier::verID(reverse_flow_hsh->class_proto,classifier);
				reverse_flow_hsh->class_proto = tmp_id;
				flow_hsh->class_proto = tmp_id;
			}
		}
		else if( (flow_hsh->class_proto < DOWN_BASE_P2P_CLASS_NUMBER) ) {
			if( (classifier>=DOWN_BASE_P2P_CLASS_NUMBER) && (classifier<PROTO_ID_NONPAYLOAD) ) {
				if((reverse_flow_hsh == NULL)) {
					flow_hsh->class_proto = classifier;
				} else {
					u_short tmp_id=0;
					tmp_id = CClassifier::verID(reverse_flow_hsh->class_proto,classifier);
					reverse_flow_hsh->class_proto = tmp_id;
					flow_hsh->class_proto = tmp_id;
				}
			}
		} else if ( CClassifier::isSuperClass(flow_hsh->class_proto) ) {
			if( (classifier>=DOWN_BASE_P2P_CLASS_NUMBER)&&(classifier<PROTO_ID_NONPAYLOAD)  ) {
				if((reverse_flow_hsh == NULL)) {
					if(!(classifier == 116 && flow_hsh->class_proto == 201)) //adicionado temporariamente lembrar de tirar
						flow_hsh->class_proto = classifier;
				} else {
					u_short tmp_id=0;
					tmp_id = CClassifier::verID(reverse_flow_hsh->class_proto,classifier);
					if(!(tmp_id == 116 && flow_hsh->class_proto == 201)){ //adicionado temporariamente lembrar de tirar
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

	pthread_mutex_unlock(&hash_lock);
	//Sync Table End
	CFlowUtil::delete_flow(tmp_flow);
}

/*
* dissect packet
*/
void
CAnalyzerAggregator::mount_flow(u_char * args, const struct pcap_pkthdr *header,
								const u_char * packet, ThreadParams *tp)
{

	extern int outputThroughput;

	flow_t *flow;		//flow structure        

	/* define pointers to packet headers */
	const struct ether_header *ethernet;	/* ethernet header */
	const struct ip *ip;	/* IP header */


	/* ethernet headers are always exactly (time_t)(header->ts.tv_sec)14 bytes */
	int size_ethernet = ETHER_HDR_LEN;
	int size_ip;
	u_short ipLen;


	/* define ethernet header */
	ethernet = (struct ether_header *) (packet);

	/* define/compute ip header offset */
	ip = (struct ip *) (packet + size_ethernet);

	size_ip = ip->ip_hl * 4;	
	if (size_ip < 20) {		
		return;
	}

	u_int16_t src_port, dst_port;

	/* determine protocol */
	switch (ip->ip_p) {
	case IPPROTO_TCP:
		src_port =
			((struct tcphdr *) (packet + size_ethernet +
			size_ip))->th_sport;
		dst_port =
			((struct tcphdr *) (packet + size_ethernet +
			size_ip))->th_dport;
		break;
	case IPPROTO_UDP:
		src_port =
			((struct udphdr *) (packet + size_ethernet +
			size_ip))->uh_sport;
		dst_port =
			((struct udphdr *) (packet + size_ethernet +
			size_ip))->uh_dport;
		break;
	case IPPROTO_ICMP:
		src_port = htons(0);
		dst_port = htons(0);
		break;
	default:
		src_port = htons(0);
		dst_port = htons(0);
		break;
	}

	// fixed
	if (outputThroughput) {
		frames_cap++;
		bytes_cap += (unsigned int) ntohs(ip->ip_len);
	}
	// fixed

	flow = CFlowUtil::createFlow_t(ip->ip_p, ip->ip_p, NULL, NULL, src_port, dst_port,
		(unsigned int) ntohs(ip->ip_len), 1,
		(time_t) (header->ts.tv_sec),
		(time_t) (header->ts.tv_sec),
		(time_t) (header->ts.tv_usec),
		(time_t) (header->ts.tv_usec), ip->ip_src,
		ip->ip_dst);

	tvSec = (time_t) (header->ts.tv_sec);
	tvUSec = (time_t) (header->ts.tv_usec);
	ipLen = *(unsigned short *) args;
	ipLen -= ETHER_HDR_LEN;
	if((unsigned int)(ntohs(ip->ip_len)) <= ipLen ) {
		addFlowSync(flow, ip, (unsigned int) ntohs(ip->ip_len), tp);
	} else {
		addFlowSync(flow, ip, ipLen, tp);
	}


	return;
}

//void printHash(char *fileName)
void CAnalyzerAggregator::printHash()
{
	flow_t *flow_hsh;
	HashTableUtil::init_hash_walk(test_table);
	struct tm *clock = NULL;
	//extern char baseFileName[];
	//extern char fileName[];
	//snprintf(fileName,256,"%s_%u",baseFileName, numo);
	char *filenameCountStr = (char*) malloc(sizeof(char) * 36);
	extern char fileName[];
	char *data = (char *) (malloc(sizeof(char)*7));
	extern char baseFileName[];
	time_t init = 0;
	time(&init);

	clock = (struct tm *)localtime(&(init));
	CFlowUtil::getDate(&init,data,6) ;
	snprintf(filenameCountStr,36,"%s_latestFile",data);
	snprintf(fileName,256,"%s%s",baseFileName, filenameCountStr);
	while ((flow_hsh = (flow_t*) HashTableUtil::next_hash_walk(test_table))) {
		//	fprintf(stdout,"Estamos aqui 1\n");
		CFlowUtil::printFlowToFile(flow_hsh, fileName);
	}
	free(filenameCountStr);
	free(data);
	HashTableUtil::clear_hash_table(test_table);
}

void CAnalyzerAggregator::task_ctrl_C(int i)
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
	printHash();
	printf(" <- Interrupt received.\nProgram will exit now!\n");
	/*free(filenameCountStr);
	free(data);*/
	exit(0);
}

void * CAnalyzerAggregator::verifyHashTimeOut(void *par)
{
	int filenameCount = 0; int flag=1;
	int fileAdminTime = ((admin_t *) par)->interval;
	int fileExpTime = ((admin_t *) par)->hop;
	time_t init = 0, final = 0, sec, usec;
	struct tm *clock = NULL;
	extern int analyserpxError;
	extern char logFileName[];
	extern char baseFileName[];
	extern char fileName[];
	extern char outputFileName[];
	extern int outputThroughput;
	extern int tFlag;
	int interCounter=0;

	// fixed
	unsigned long long frames_caled = 0;
	unsigned long long bytes_caled = 0;
	unsigned long long frames_cal;
	unsigned long long bytes_cal;
	float fraction;
	if (outputThroughput) {
		fraction = 1.0 / fileAdminTime;
	}
	// fixed

	char *filenameCountStr = (char*) malloc(sizeof(char) * 36);
	char *data = (char *) (malloc(sizeof(char)*7));

	while ((analyserpxError == 0)&&tFlag) {
		sleep(fileAdminTime - (final - init));

		// fixed
		if (outputThroughput) {
			frames_cal = frames_cap - frames_caled;
			//      bytes_cal = bytes_cap - bytes_caled;
			FILE *out = fopen(outputFileName, "a");
			if (out == NULL)
				printf("error!\n");
			fprintf(out, "# %.2f fps\n", frames_cal*fraction);
			fclose(out);
			//     	printf("# %.0d %.0d %.2fpackets/s %.0d %.2fbytes/s\n", (int)frames_cap, (int)frames_cal, frames_cal*fraction, (int)bytes_cal, bytes_cal*fraction);
			frames_caled += frames_cal;
			//		bytes_caled += bytes_cal;
		}
		// fixed

		sec = tvSec;
		usec = tvUSec;
		time(&init);

		clock = (struct tm *)localtime(&(init));
		if((interCounter>=fileExpTime)&&(fileExpTime>0)){
			filenameCount++;
			CFlowUtil::getDate(&init,data,6) ;
			snprintf(filenameCountStr,36,"%s_%u",data,filenameCount);
			snprintf(fileName,256,"%s%s",baseFileName, filenameCountStr);
			interCounter=0;
		}
		if( ((clock->tm_hour) == 0)&&((clock->tm_min)<((int)((fileAdminTime*2)/60))) ) {
			if(flag) {
				filenameCount=0;
				CFlowUtil::getDate(&init,data,6) ;
				snprintf(filenameCountStr,36,"%s_%u",data,filenameCount);
				snprintf(fileName,256,"%s%s",baseFileName, filenameCountStr);
			}
			flag=0;
		} else {
			flag=1;
		}
		//cleanHash(test_table, sec, usec, fileName);		

		optimumCleanHash(test_table, sec, usec, fileName);//introduced on 1 August 2007, it aims to optimize 
		//the analyzer-px output according to -b parameter 
		interCounter++;
		time(&final);
	}
	if (analyserpxError) {
		char buffer[50];
		snprintf(buffer, 50,"ERROR CLEANING HASH. ERROR CODE: %u", analyserpxError);
		CFileUtil::writeStringToLogFile(logFileName, buffer, NULL);
	}

	free(filenameCountStr);
	free(data);

	//pthread_exit (0);
	return (void *) NULL;
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

int CAnalyzerAggregator::analyserpxStartMultiThreaded(cap_config * conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp, int threadNum)
{
	extern int analyserpxError;
	pthread_t hashTimeOut;

	extern char baseFileName[];
	extern char fileName[];
	extern int tFlag;
	admin_t *control=(admin_t *)malloc(sizeof(admin_t));
	control->interval=fileAdminTime;
	control->hop=fileExpTime;
	ThreadParams *tps        = new ThreadParams[threadNum];
	pthread_t *workerthreads = new pthread_t[threadNum];

	flow_export=flow_exp; 
	/* interrupt routine to Ctrl-C */
	signal(SIGINT, task_ctrl_C);	

	pthread_mutex_init(&cap_lock , NULL ) ;
	pthread_mutex_init(&hash_lock , NULL ) ;
	pthread_mutex_init(&print_lock , NULL ) ;

	for (int i = 0; i < threadNum; ++i ){
		for (int j = 0; j < 6; ++j ){
			tps[i].count[j]=0;
		}
		tps[i].counttotal = 0;
		tps[i].conf = conf;
	}

	test_table = HashTableUtil::init_hash_table("ANALYSERPX_CAP_TABLE", CFlowUtil::compare_flow, CFlowUtil::flow_key,
		CFlowUtil::delete_flow, HASH_SIZE);

	//pthread_create(&hashTimeOut, NULL, verifyHashTimeOut, &fileAdminTime);

	if (offLineFile == NULL) {
		onlineCapMode = 1;
		pthread_create(&hashTimeOut, NULL, verifyHashTimeOut, control);
	}
	else {
		snprintf(fileName,256,"%s_0",baseFileName);
		onlineCapMode = 0;	
		fileAdminTimeOff = fileAdminTime;
		slotsOffline=control->hop;
	}

	analyserpxError = CCaptureUtil::initiate_capture( conf, onlineCapMode, offLineFile);
	if ( analyserpxError == 0 ){
		for (int i = 0; i < threadNum; ++i ){
			pthread_create(&workerthreads[i], NULL, threadsLoop, &tps[i]);	
		}
		for (int i = 0; i < threadNum; ++i ){
			pthread_join(workerthreads[i],NULL);
		}
	}

	tFlag=0;
	//    printHash(fileName);
	printHash();
	if (offLineFile == NULL) {
		pthread_exit((void*)verifyHashTimeOut);
	}
	//printHash(fileName);
	free(control);
	delete [] workerthreads;
	delete [] tps;
	return analyserpxError;
}

void * CAnalyzerAggregator::threadsLoop(void *par){
	ThreadParams       *tp = (ThreadParams*)par;
	struct pcap_pkthdr *header;
	const u_char       *packet;
	const struct ip *ip;	/* IP header */
	int size;
	int sizetmp;
	long long count = 0;

	struct pcap_pkthdr *head = (pcap_pkthdr*)malloc(sizeof(const struct pcap_pkthdr));
	const u_char       *pkt = (unsigned char*)malloc(tp->conf->snap_len + 2);

	int res;
	do{
		//pthread_mutex_lock(&cap_lock);
		while ( pthread_mutex_trylock(&cap_lock) != 0 ){};
		res = pcap_next_ex( tp->conf->descr, &header, &packet );	
		if ( res >= 0 ){
			ip = (struct ip *) (packet + ETHER_HDR_LEN);
			sizetmp = ntohs(ip->ip_len);
			if( sizetmp <= ( tp->conf->snap_len - ETHER_HDR_LEN) ) {
				size = sizetmp;
			} else {
				size = tp->conf->snap_len - ETHER_HDR_LEN;
			}			 		
			memcpy ( (void*) head  , (void*)header, sizeof(struct pcap_pkthdr) );
			memcpy ( (void*) pkt, (void*)packet, size + ETHER_HDR_LEN );

			pthread_mutex_unlock(&cap_lock);	
			statistician.processNewPacket((u_char *) & (tp->conf->snap_len), head, pkt, tp);
			//mount_flow( (u_char *) & (tp->conf->snap_len), head, pkt, tp );
			++tp->counttotal;	
		} else {
			pthread_mutex_unlock(&cap_lock);		
		}


	}while (res >= 0);
	pthread_mutex_lock(&print_lock);
	printf("Packets count : %d \n", tp->counttotal);
	long long tmp = tp->counttotal;
	for (int i=0; i < 6; ++i){
		printf("Packets count if%d : %d \n", i, tp->count[i] );//, ((double)tp->count[i]/(double)tp->counttotal)*100 );
		tmp -= tp->count[i];

	}
	printf("Packets not classified : %d \n", tmp);
	pthread_mutex_unlock(&print_lock);
	free(head);
	//free(pkt);
}

