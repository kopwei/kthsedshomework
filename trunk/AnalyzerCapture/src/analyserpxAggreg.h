#ifndef _ANALYSERPXAGGREG_H_
#define _ANALYSERPXAGGREG_H_

#define HASH_SIZE 1000
#define TEST_AMOUNT 100


#include "analyserpxTypes.h"
#include "resultenum.h"

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
		static void mount_flow ( unsigned short ipLen, const struct pcap_pkthdr *header,
		                         const ip * pIpHeader, const u_int16_t src_port, const u_int16_t dst_port, const u_short classifier, ThreadParams *tpp );
		
		
		//static hash_tab* test_table;
		
		static void* verifyHashTimeOut ( void *par );
		
				/*!     \fn void printHash()
		\brief Print Hashtable to file.

		Save to file all the remained flow still into hashtable.
				 */
		static void printHash();
		
		static void initVariables(CUserInputParams* pUserInputParams);



	private :

		static void verifyTimeOutHash ( flow_t *flow );
		

		

		static void addFlowSync ( flow_t * flow, const struct ip *ip, unsigned short ipLen, u_short classifier, ThreadParams *tp );



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
		static ResultEnum optimumCleanHash ( hash_tab * hash, time_t sec, time_t usec, const string& fileName );
		
		static ResultEnum GetFileName(const int count, string* fileName);
		
		
		static CUserInputParams* s_pInputParams;
		static string m_strFileName;
		static hash_tab* test_table;
		static time_t tvSec;
		static time_t tvUSec;


};

#endif
