#ifndef _ANALYSERPXAGGREG_H_
#define _ANALYSERPXAGGREG_H_

#define HASH_SIZE 1000
#define TEST_AMOUNT 100
#include "hashtab.h"
#include "analyserpxTypes.h"

/*!     \file analyserpxAggreg.h
\brief Aggregation module header files.

Define the procedures and variables used in the Analyser-PX's Aggregation Module.
*/

/*!     \var hash_tab *test_table
\brief Pointer to Hash structure that will be used to save flows.
*/
extern hash_tab *test_table;

class CAnalyzerAggregator
{
public:


	/*!     \fn void cleanHash(hash_tab *hash, time_t sec, time_t usec, char *fileName)
	\brief Clean Flows' Hashtable.

	Clear the hash table based on parameter's condition. The last time for each flow is verified, so the expered flows are removed from teh hast table ane saved into filename parameter.

	\param *hash		Pointer to a hash structure that will be cleaned.
	\param sec		Indicate the timestamp seconds of the last packet arrived.
	\param usec		Indicate the timestamp miliseconds of the last packet arrived.
	\param *fileName	Name/location to save the expired flows.
	*/ 
	static void cleanHash(hash_tab *hash, time_t sec, time_t usec, char *fileName);


	/*!     \fn int verifyTimeOut(flow_t *flow1, flow_t *flow2)
	\brief Check the timeout of a given flow.

	Verify if a frame belongs to an active flow based on TIMEOUT. The flow2 parameter is a flow_t structure. In this case, it isn't necessary to create a new structure like frame, only to include a frame in a flow_t structure.  

	\param flow1	Last flow.
	\param flow2	New frame that will be or not be a new flow.
	\return 	Return a non-zero value if the timeout has been exceeded.
	*/
	static int verifyTimeOut(flow_t *flow1, flow_t *flow2);


	/*!     \fn void addFlow(flow_t *flow, const struct ip *ip, unsigned short ipLen)
	\brief Add a packet to a flow.

	Add a flow to the hash table, it also keep a ip address to classify the flow based on ip payload.

	\param flow	Flow to be added.
	\param *ip	Pointer to the ip address .
	\param ipLen	Ip payload size.
	*/ 
	//void addFlow(flow_t *flow, const struct ip *ip, unsigned short ipLen);

	/*!     \fn void mount_flow(unsigned char *args, const struct pcap_pkthdr *header, const u_char *packet)
	\brief Mount Flow function.

	Function called every time when a packet arrives at libpcap.
	\param *args	argument passed by pcap_loop function
	\param *header	pointer to a pcap_pkthdr structure pointer
	\param *header 	header of a dumped packet in the file
	\param *packet	an arrived packet
	*/
	static void
		mount_flow(unsigned char *args, const struct pcap_pkthdr *header, const u_char *packet, ThreadParams *tp);

	/*!     \fn void printHash()
	\brief Print Hashtable to file.

	Save to file all the remained flow still into hashtable.
	*/
	static void printHash();

	/*!     \fn void task_ctrl_C(int i)
	\brief CTRL+C Interpreter function.

	Cath the interrupt SIGINT caused by pressing the "CRT-C" buttons  and save to file all the remained flow that still into hash.
	\param i	Interrupt signal.
	*/ 
	static void task_ctrl_C(int i);

	/*!     \fn int analyserpxStart(cap_config *conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp)
	\brief Analyser-PX Start up function.

	Start all capturing, aggregating, and classifier process.
	\param *conf		Pointer to cap_config structure pointer with all libcap needed parameter.
	\param fileAdminTime  	The cicle time to save flows to file.
	\param fileExpTime  	Interval counter to output file modification.
	\param *offLineFile	Name of file to read the packets.
	\param flow_exp		Export flow optmization flag.
	\return Error code. 
	*/
	//int analyserpxStart(cap_config *conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp);

	/*!     \fnvoid optimumCleanHash(hash_tab * hash, time_t sec, time_t usec, char *fileName)
	\brief Optimum Clean hashtable function

	Clean hashtable with optimizations to meansure entropy among flows. Otherwise, it runs the same procedures as well as cleanHash function.
	\param *hash		Pointer to a hash structure that will be cleaned.
	\param sec		Indicate the timestamp seconds of the last packet arrived.
	\param usec		Indicate the timestamp miliseconds of the last packet arrived.
	\param *fileName	Name/location to save the expired flows.
	*/
	static void optimumCleanHash(hash_tab * hash, time_t sec, time_t usec, char *fileName);

	static int analyserpxStartMultiThreaded(cap_config * conf, int fileAdminTime, int fileExpTime, char *offLineFile, int flow_exp, int threadNum);

	static void *threadsLoop(void *par);

	static void addFlowSync(flow_t * flow, const struct ip *ip, unsigned short ipLen, ThreadParams *tp);

private :
	static void verifyTimeOutHash(flow_t *flow);
	static void* verifyHashTimeOut(void *par);
};

#endif
