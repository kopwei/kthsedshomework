

#define _BSD_SOURCE 1

#include "cap.h"
#include <pcap.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <string.h>
#include <iostream>

using namespace std;

void CCaptureUtil::init_cap_config(cap_config* pCapConfig)
{
    //cap_config *c = (cap_config *) malloc(sizeof(cap_config));
//     snprintf(c->dev,10,"%s","");//c->dev = NULL;
//     snprintf(c->errbuf,PCAP_ERRBUF_SIZE,"%s","");//c->errbuf = NULL;
//     snprintf(c->filter_app,3,"%s","ip");
//     //c->filter_app = "ip";
//     c->netp=0;
//     c->maskp=0;
//     c->numOfPackets = 0;
//     c->snap_len = 1518;
//     c->descr=NULL;
//     return c;
	if (NULL == pCapConfig) return;
	pCapConfig->dev= "";
	pCapConfig->errbuf = "";
	pCapConfig->filter_app = "ip";
	pCapConfig->netp = 0;
	pCapConfig->maskp = 0;
	pCapConfig->numOfPackets = 0;
	pCapConfig->snap_len = 1518;
	pCapConfig->descr = NULL;
}



int CCaptureUtil::start_capture(pcap_handler func, u_char * arg, cap_config * c,
		  int onlineCapMode, char *offLineFile)
{
	char* err = const_cast<char *>(c->errbuf.c_str());
	pcap_lookupnet(c->dev.c_str(), &(c->netp), &(c->maskp), err);
    if (onlineCapMode == 1) {
        /* set our capture device */
        if (c->dev.length() == 0) {
			char* pRs = pcap_lookupdev(err);
			c->dev = string(pRs, strlen(pRs));
   	    	//snprintf(c->dev,10,"%s",pcap_lookupdev(c->errbuf));
        }
		/* open capture device in online mode */
		c->descr = pcap_open_live(c->dev.c_str(), c->snap_len, 1, 0, err);
		if (c->descr == NULL) {
	    	fprintf(stderr, " (pcap_open_live failed)");
	    	return PCAP_OPEN_LIVE_ERROR;
		}
    } else {
		/* open capture device in offline mode */
		c->descr = pcap_open_offline(offLineFile, err);
		if (c->descr == NULL) {
	    	fprintf(stderr, " (pcap_open_offline failed)");
	    	return PCAP_OPEN_LIVE_ERROR;
		}
    }

    /* apply the rules */
    if (pcap_compile(c->descr, &(c->fp), c->filter_app.c_str(), 0, c->netp) == -1) {
		fprintf(stderr, " (pcap_compile failed)");
		return PCAP_COMPILE_ERROR;
    }

    if (pcap_setfilter(c->descr, &(c->fp)) == -1) {
		fprintf(stderr, " (pcap_setfilter failed)");
		return -1;
    }
    pcap_loop(c->descr, c->numOfPackets, func, arg);

    return 0;

}

/* Add 22/05 - Rafael */
int CCaptureUtil::initiate_capture(cap_config * c, const bool onlineCapMode, const string& offLineFile)
{
	if (NULL == c)
	{
		cout << "empty configure pointer" << endl;
		return -1;
	}
	char* err = const_cast<char *>(c->errbuf.c_str());
    pcap_lookupnet(c->dev.c_str(), &(c->netp), &(c->maskp), err);
    if (onlineCapMode) {
        /* set our capture device */
        if (c->dev.length() == 0) {
			char* pRs = pcap_lookupdev(err);
			c->dev = string(pRs, strlen(pRs));
        }
		/* open capture device in online mode */
		c->descr = pcap_open_live(c->dev.c_str(), c->snap_len, 1, 0, err);
		if (c->descr == NULL) {
	    	fprintf(stderr, " (pcap_open_live failed)");
	    	return PCAP_OPEN_LIVE_ERROR;
		}
    } else {
		/* open capture device in offline mode */
		c->descr = pcap_open_offline(offLineFile.c_str(), err);
		if (c->descr == NULL) {
	    	fprintf(stderr, " (pcap_open_offline failed)");
	    	return PCAP_OPEN_LIVE_ERROR;
		}
    }
    /* apply the rules */
    if (pcap_compile(c->descr, &(c->fp), c->filter_app.c_str(), 0, c->netp) == -1) {
		fprintf(stderr, " (pcap_compile failed)");
		return PCAP_COMPILE_ERROR;
    }

    if (pcap_setfilter(c->descr, &(c->fp)) == -1) {
		fprintf(stderr, " (pcap_setfilter failed)");
		return -1;
    }

    return 0;

}

int CCaptureUtil::stop_offline_capture(cap_config * c)
{
	if (c == NULL)
	{
		cout << "empty configure pointer" << endl;
		return -1;
	}
	if (NULL == c->descr)
	{
		cout << "empty handler pointer" << endl;
		return -1;
	}
	pcap_close(c->descr);
}

void CCaptureUtil::delete_cap_config(void *data)
{
	// TODO: Unfinished
}
