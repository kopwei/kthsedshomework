#ifndef _ANALYSERPXAGGREG_H_
#define _ANALYSERPXAGGREG_H_

#define HASH_SIZE 1000
#define TEST_AMOUNT 100


#include "analyserpxTypes.h"
#include "resultenum.h"
#include "FlowAnalyzedResult.h"
#include "PacketTypeStat.h"
#include <map>

/*!     \file analyserpxAggreg.h
\brief Aggregation module header files.

Define the procedures and variables used in the Analyser-PX's Aggregation Module.
 */

/*!     \var hash_tab *test_table
\brief Pointer to Hash structure that will be used to save flows.
 */
//extern hash_tab *test_table;

class CUserInputParams;
class flow_t;
struct hash_tab;

class CAnalyzerAggregator
{
	typedef map<unsigned long long, flow_t*> FlowMap;
	public:
		//CAnalyzerAggregator(CUserInputParams* pInputParams);
		
		//~CAnalyzerAggregator(void);
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
		static flow_t* mount_flow ( unsigned short ipLen, const struct pcap_pkthdr *header,
		                         const ip * pIpHeader, const u_int16_t src_port, const u_int16_t dst_port, ThreadParams *tpp );
		
		
		//static hash_tab* test_table;
		
		static void* verifyHashTimeOut ( void *par );
		
				/*!     \fn void printHash()
		\brief Print Hashtable to file.

		Save to file all the remained flow still into hashtable.
				 */
		static void printHash();
		
		static void initVariables(CUserInputParams* pUserInputParams);
		
		static ResultEnum PrintStatisticResult(const tm* t);
		
		// static hash_tab* test_table;
		
		static int GetTableSize() {return s_flowMap.size();}

	private :

		static void verifyTimeOutHash ( flow_t *flow );
		

		

		static flow_t* addFlowSync ( flow_t * flow, const struct ip *ip, unsigned short ipLen, ThreadParams *tp );



		/*!     \fn void cleanHash(hash_tab *hash, time_t sec, time_t usec, char *fileName)
			\brief Clean Flows' Hashtable.

			Clear the hash table based on parameter's condition. The last time for each flow is verified, so the expered flows are removed from teh hast table ane saved into filename parameter.

			\param *hash		Pointer to a hash structure that will be cleaned.
			\param sec		Indicate the timestamp seconds of the last packet arrived.
			\param usec		Indicate the timestamp miliseconds of the last packet arrived.
			\param *fileName	Name/location to save the expired flows.
		 */
		//static void cleanHash ( hash_tab *hash, time_t sec, time_t usec, char *fileName );


		/*!     \fn int verifyTimeOut(flow_t *flow1, flow_t *flow2)
			\brief Check the timeout of a given flow.

			Verify if a frame belongs to an active flow based on TIMEOUT. The flow2 parameter is a flow_t structure. In this case, it isn't necessary to create a new structure like frame, only to include a frame in a flow_t structure.

			\param flow1	Last flow.
			\param flow2	New frame that will be or not be a new flow.
			\return 	Return a non-zero value if the timeout has been exceeded.
		 */
		static int verifyTimeOut ( flow_t *flow1, flow_t *flow2 );

		/*!     \fnvoid optimumCleanHash(hash_tab * hash, time_t sec, time_t usec, char *fileName)
			\brief Optimum Clean hashtable function

			Clean hashtable with optimizations to meansure entropy among flows. Otherwise, it runs the same procedures as well as cleanHash function.
			\param *hash		Pointer to a hash structure that will be cleaned.
			\param sec		Indicate the timestamp seconds of the last packet arrived.
			\param usec		Indicate the timestamp miliseconds of the last packet arrived.
			\param *fileName	Name/location to save the expired flows.
		 */
		static ResultEnum optimumCleanHash ( FlowMap * flowMap, time_t sec, time_t usec, const tm* refTime);
		
		static string GetFileName(const tm* time, const int count);

		

		//static ResultEnum printStatistic();
		
		
		static CUserInputParams* s_pInputParams;
		static string s_strFileName;
		
		static time_t tvSec;
		static time_t tvUSec;
		static int s_iFileNameCount;

		static CFlowAnalyzedResult s_flowAnalyzer;
		//static CPacketTypeStat s_PacketTypeStat;
		
		static map<unsigned long long, flow_t*> s_flowMap;
		
		

};

#endif
