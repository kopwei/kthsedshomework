#ifndef _ANALYSERPX_TYPES_
#define _ANALYSERPX_TYPES_

#include <sys/types.h>
#include <netinet/in_systm.h>
#include <sys/socket.h>
#include <pcap.h>
#include <arpa/inet.h>
#include <net/if.h>
#include <net/if_arp.h>
#include <netinet/in.h>
#include <netinet/if_ether.h>
#include <netinet/ip.h>
#ifndef __FAVOR_BSD
#define __FAVOR_BSD
#endif
#include <netinet/tcp.h>
#include <netinet/udp.h>

#include <string>

using namespace std;
/*!     \file analyserpxTypes.h
        \brief Common types header file.

        This file contains the most common types and structures used in Analyser-PX.
*/

/*!     \struct ip
	\brief Internet protocol struct.
*/
//struct ip;

/*!     \struct tcphdr
	\brief TCP header struct.
*/
//struct tcphdr;

/*!     \struct udphdr
	\brief UDP header struct.
*/
//struct udphdr;

/*!     \struct ether_header
	\brief Ethernet header.
*/
//struct ether_header;

//struct cap_config;

//struct flow_t;


/*!     \def TIMEOUT
	\brief Default flow timeout.
*/
#define TIMEOUT 64



/*!     \struct cap_config
        \brief Set of libpcap parameters.
*/
struct cap_config
{
	string dev;					/*!< capture device */
	string errbuf;			/*!< error buffer */
	pcap_t *descr;					/*!< sniff handler */
	struct bpf_program fp;				/*!< compiled program */
	bpf_u_int32 maskp;				/*!< subnet mask */
	bpf_u_int32 netp;				/*!< ip */
	string filter_app;				/*!< well known filter expression */
	int numOfPackets;				/*!< number of packets to capture */
	unsigned short snap_len;			/*!< packet's length to capture */
} ;



/*!     \struct admin_t
	\brief Adiministration Time type.
*/
struct admin_t
{
	int interval;                                   /*!< flow timeout.Time Bin definition*/
	int hop;                                        /*!< output name file administration. time bins counter*/
};

struct ThreadParams
{
	long long count[6];
	long long counttotal;
	//cap_config          *conf;
} ;




#endif

