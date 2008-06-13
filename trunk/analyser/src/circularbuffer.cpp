#include "../include/circularbuffer.h"



CircularBuffer::CircularBuffer( int maxIpSize, int dataBlockNum )
{
	//const struct pcap_pkthdr teste;	
	lenDataBlock    = maxIpSize + sizeof(const struct pcap_pkthdr);
	this->maxIpSize = maxIpSize;  
	head = (unsigned char*)malloc( lenDataBlock * dataBlockNum );
	tail = head;
	low = head;
	maxcapacity = dataBlockNum;
	numelem     = 0;
	upper = low + ( lenDataBlock * dataBlockNum ) - 1;
}

CircularBuffer::CircularBuffer()
{
	
}

CircularBuffer::~CircularBuffer()
{
	free( head );
}
	
bool CircularBuffer::pushElement( const struct pcap_pkthdr *header, const unsigned char *packet )
{	
	const struct ip *ip;	/* IP header */	
	int pktcpysize;
	
	/* define/compute ip header offset */
    ip = (struct ip *) (packet + ETHER_HDR_LEN);

    /* This verification is not here 
	  -> maybe for performance is possible
	size_ip = ip->ip_hl * 4;
    if ( size_ip < 20 ) {
		return false;
    }	
	*/
	
	pktcpysize = ntohs(ip->ip_len);
	if ( pktcpysize > this->maxIpSize ){
		pktcpysize = this->maxIpSize;			
	}
	
	//enter critical section
	if ( head >= upper ){
		head = low;
	}
	if ( numelem == maxcapacity ){
		//leave critical section
		return false;			
	}
	++numelem;
	head += lenDataBlock;	
	//leave critical section	
	return true;
	
}

bool CircularBuffer::popElement( struct pcap_pkthdr *header, struct ip *ip_pkt ){
	const struct ip *ip_tmp;	/* IP header */		
	int pktcpysize;
	
	//enter critical section
	if ( tail >= upper ){
		tail = low;
	}
	if ( numelem == 0 ){
		//leave critical section
		return false;	
	}
	
	//get the copy size
	ip_tmp = (ip*)(tail+sizeof(struct pcap_pkthdr));
	pktcpysize = ntohs(ip_tmp->ip_len);
	if ( pktcpysize > this->maxIpSize ){
		pktcpysize = this->maxIpSize;			
	}	
						  
	memcpy ( (void*) header, (void*)tail, sizeof(struct pcap_pkthdr) );
	memcpy ( (void*) ip_pkt, (void*)ip_tmp, pktcpysize);		
	
	--numelem;
	tail += lenDataBlock;
	//leave critical section
	return true;
}
		
