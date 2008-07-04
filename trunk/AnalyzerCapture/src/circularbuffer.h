#ifndef _CIRCULARBUFFER_H_
#define _CIRCULARBUFFER_H_
/**
 *	 This module provides a fast circular buffer implementation 
 */

#include <string.h>
#include <stdlib.h>
#include "analyserpxTypes.h" 

class CircularBuffer 
{
public:
	CircularBuffer( int maxIpSize, int dataBlockNum );
	CircularBuffer();
	
	bool pushElement( const struct pcap_pkthdr *header, const unsigned char *packet );
	bool popElement( struct pcap_pkthdr *header, struct ip *ip_pkt );
	~CircularBuffer();
		
protected:
		
private:
	unsigned char *head;
	unsigned char *tail;
	unsigned char *low;
	unsigned char *upper;
	int lenDataBlock;
	int maxIpSize;
	int maxcapacity;
	int numelem;

};

#endif
