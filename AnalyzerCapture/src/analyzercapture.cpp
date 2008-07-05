

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <iostream>
#include <cstdlib>

#include <unistd.h>
#include <stdlib.h>
#include <ctype.h>
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

	//int index;
	//opterr = 0;
	//cap_config *conf = CCaptureUtil::new_cap_config();
	//char *offLineFile = NULL;
	//char *data=NULL;
	//int fileAdminTime = 300;
	//int flow_exp=1;
	//int fileExpTime = 0;
	//int threadnumber = 1;
	//time_t tempo = 0;
	
	CUserInputParams userInputParam;
	
	userInputParam.ParseInputParams(argc, argv);

	cout << "Starting program..." << endl;

	cout << "Number of worker threads: " << userInputParam.GetThreadNumber() << endl ;
	
	
	
	CAnalyzer analyzer;
	//analyserpxError = analyserpxStart(conf, fileAdminTime, fileExpTime, offLineFile,flow_exp);
//	analyserpxError = analyzer.analyserpxStartMultiThreaded ( conf, fileAdminTime, fileExpTime, offLineFile,
//	                  flow_exp, threadnumber );
	analyzer.analyserpxStartMultiThreaded(&userInputParam);
	if ( analyserpxError )
	{
		cout << endl << "The program will exit with erro code " << analyserpxError << endl;
	}
	else
	{
		cout << "Capture complete." <<endl;
	}
	//fprintf ( stdout, "%s\n","deve deletar" );
	freeExpressions();
	//fprintf ( stdout, "%s - OK\n","deve deletar" );
	//free ( conf );
	return 0;
}
