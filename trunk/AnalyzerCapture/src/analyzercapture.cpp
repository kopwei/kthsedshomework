

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <iostream>
#include <cstdlib>

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <time.h>

#include "cap.h"
#include "version.h"
#include "analyzer.h"
#include "expressions.h"
#include "analyserpxFlow.h"
#include "macro.h"
#include "userinputparams.h"


char fileName[256] = "cap0";
char baseFileName[100] = "cap";
char logFileName[100] = "logcap";
char outputFileName[100] = "throughput";
int outputThroughput = 0;
int tFlag = 1;
int flow_exp = 0;//This variable deals with flow exportation (Profiling)

int analyserpxError = 0;
int STR_MAX_LEN = 1474;


int main(int argc, char **argv){

	loadExpressions();

	int index;
	int c;
	opterr = 0;
	cap_config *conf = CCaptureUtil::new_cap_config();
	char *offLineFile = NULL;
	char *data=NULL;
	int fileAdminTime = 300;
	int flow_exp=1;
	int fileExpTime = 0;
	int threadnumber = 1;
	time_t tempo = 0;
	
	CUserInputParams userInputParam;
	

	while ((c = getopt(argc, argv, "hi:w:c:s:e:t:z:l:r:q:b:m::o")) != -1){
		switch (c) {
			case 'i':
				snprintf(conf->dev,10,"%s",optarg);
				break;
			case 'w':
				snprintf(baseFileName,100,"%s",optarg);
				time(&tempo);
				data = (char *) (malloc(sizeof(char)*7));
				CFlowUtil::getDate(&tempo,data,6) ;
				snprintf(fileName,256,"%s%s_0",baseFileName,data);
				free(data);
				break;
			case 'c':
				conf->numOfPackets = atoi(optarg);
				break;
			case 's':
				conf->snap_len = atoi(optarg);
				break;
			case 'e':
				snprintf(conf->filter_app, 100, "%s",optarg);
				break;
			case 't':
				fileAdminTime = atoi(optarg);
				break;
			case 'z':
				fileExpTime = atoi(optarg);
				break;
			case 'l':
				snprintf(logFileName,100, "%s",optarg);
				break;
			case 'r':
				offLineFile = optarg;
				break;
			case 'b':
				flow_exp = atoi(optarg);
				break;
			case 'h':
				return 0;
			case 'm':
				outputThroughput = 1;
				break;
			case 'o':
				snprintf(outputFileName,100,"%s",optarg);
				break;
			case 'q':
				threadnumber = atoi(optarg);
				break;
			case '?':
				if (isprint(optopt)){
					fprintf(stderr, "Unknown option `-%c'.\n", optopt);
					fprintf(stderr, "Use \"%s -h\" to see valid options\n", argv[0] );
				} else {
					fprintf(stderr,	"Unknown option character `\\x%x'.\n", optopt);
					fprintf(stderr, "Use \"%s -h\" to see valid options\n", argv[0] );
				}
				return 1;
			default:
				abort();
		}
	}
	for ( index = optind; index < argc; index++ )
	{
		printf ( "Non-option argument %s\n", argv[index] );
	}
	printf ( "Starting program...\n" );
	fflush ( stdout );

	printf ( "Number of worker threads: %d \n",threadnumber );
	
	userInputParam.ParseInputParams(argc, argv);
	
	CAnalyzer analyzer;
	//analyserpxError = analyserpxStart(conf, fileAdminTime, fileExpTime, offLineFile,flow_exp);
	analyserpxError = analyzer.analyserpxStartMultiThreaded ( conf, fileAdminTime, fileExpTime, offLineFile,
	                  flow_exp, threadnumber );
	if ( analyserpxError )
	{
		printf ( "\nThe program will exit with erro code %d\n", analyserpxError );
	}
	else
	{
		printf ( "Capture complete.\n" );
	}
	fprintf ( stdout, "%s\n","deve deletar" );
	freeExpressions();
	fprintf ( stdout, "%s - OK\n","deve deletar" );
	free ( conf );
	return 0;
}
