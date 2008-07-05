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

/*!     \def IF_CHAR_SIZE
        \brief Define the maximum interface name length.
*/
#define IF_CHAR_SIZE 5

/*!     \def TIMEOUT
	\brief Default flow timeout.
*/
#define TIMEOUT 64



/*!     \struct cap_config
        \brief Set of libpcap parameters.
*/
typedef struct
{
	string strDev;					/*!< capture device */
	string errbuf;			/*!< error buffer */
	pcap_t *descr;					/*!< sniff handler */
	struct bpf_program fp;				/*!< compiled program */
	bpf_u_int32 maskp;				/*!< subnet mask */
	bpf_u_int32 netp;				/*!< ip */
	string filter_app;				/*!< well known filter expression */
	int numOfPackets;				/*!< number of packets to capture */
	unsigned short snap_len;			/*!< packet's length to capture */
} cap_config;

/*!     \struct flow_t
	\brief Flow type.
*/
typedef struct
{
	u_short proto; 			/*!< protocol number*/
	u_short class_proto; 		/*!< Classification protocol number*/
	char 	src_if[IF_CHAR_SIZE],	/*!< source native interface name*/
	dst_if[IF_CHAR_SIZE];	/*!< destination native interface name*/
	u_short dst_port,		/*!< source port*/
	src_port; 		/*!< destination port*/
	unsigned int 	n_bytes,	/*!< number of bytes*/
	n_frames;	/*!< number of frames*/
	time_t 	ini_sec,		/*!< seconds init time*/
	ini_mic,		/*!< microseconds init time*/
	end_sec,		/*!< seconds end time*/
	end_mic;		/*!< microseconds end time*/
	struct in_addr 	src_ip,		/*!< source ip*/
				dst_ip;		/*!< destination ip*/
} flow_t;

/*!     \struct admin_t
	\brief Adiministration Time type.
*/
typedef struct
{
	int interval;                                   /*!< flow timeout.Time Bin definition*/
	int hop;                                        /*!< output name file administration. time bins counter*/
} admin_t;

typedef struct
{
	long long count[6];
	long long counttotal;
	cap_config          *conf;
} ThreadParams;




#endif

