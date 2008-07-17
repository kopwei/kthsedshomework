#ifndef _ANALYSERPX_FLOW_
#define _ANALYSERPX_FLOW_

#include "analyserpxTypes.h"
#include "resultenum.h"

/*!     \file analyserpxFlow.h
\brief Flow manipulation functions.

This header contains the functions that deal with flows maintenance on hashtable.
*/

/*!     \def FORMAT_FLOW_SECONDS
\brief Code of format flow in seconds.
*/
const unsigned int	FORMAT_FLOW_SECONDS  =		1;

/*!     \def FORMAT_FLOW_DATE_DURATION
\brief Format date duration code.
*/
const unsigned int	FORMAT_FLOW_DATE_DURATION	= 2;

/*!     \def FORMAT_FLOW_DEFAULT
\brief Default format flow code.
*/
const unsigned int	FORMAT_FLOW_DEFAULT	=	FORMAT_FLOW_SECONDS;

/*!     \def FORMAT_PROTO_ID
\brief Protocol Identifier.
*/
const unsigned int	FORMAT_PROTO_ID		= 1;

/*!     \def FORMAT_PROTO_NAME
\brief Protocol name format.
*/
const unsigned int	FORMAT_PROTO_NAME =	2;

/*!     \def FORMAT_PROTO_DEFAULT
\brief Default Protocol name.
*/
const unsigned int	FORMAT_PROTO_DEFAULT =	FORMAT_PROTO_ID;

class flow_t;
class flow_collection;

class CFlowUtil
{
public:

	/*!     \fn int compare_flow(const void * data1, const void * data2)
	\brief Compare flows function.

	Compare two flows and return if they are the same base on the flow key.
	\param data1	First flow_t.
	\param data2 	Second flow_t.
	\return Return 0 if the flows are equals, a non-zero number otherwise.
	*/
	static int compare_flow(const void * data1, const void * data2);

	/*!     \fn unsigned long flow_key(const void * data)
	\brief Flow key generator function.

	Determine the flow's key.
	\param data	Flow to extract the key.
	\return Unsigned long interger with the key number.
	*/
	static unsigned long flow_key(const void * data);

	/*!     \fn void delete_flow(void * data)
	\brief Delete flow function.

	Release the memory allocated for a flow. 
	\param data	Flow to be released.
	*/
	static void delete_flow(void * data);

	/*!     \fn flow_t *createFlow_t( unsigned char proto, unsigned char class_proto, char *src_if, char *dst_if, u_short src_port, u_short dst_port, unsigned int n_bytes, unsigned int n_frames, time_t ini_sec, time_t end_sec, time_t ini_mic, time_t end_mic, struct in_addr ip_src, struct in_addr ip_dst)
	\brief Create a new flow

	\param proto		Protocol.
	\param *src_if		Source interface.
	\param *dst_if		Destination interface
	\param src_port		Source port.
	\param dst_port		Destination port.
	\param n_bytes		Number of bytes.
	\param n_frames		Number of frames.
	\param ini_sec		Start time in seconds.
	\param end_sec		End time in seconds.
	\param ini_mic		Start time in microseconds.
	\param end_mic		End time in microseconds.
	\param ip_src		Source ip.
	\param ip_dst		Destination ip.
	\param class_proto	Protocolo number based on signature identification method.
	\return Created flow.
	*/
	static flow_t *createFlow_t( unsigned char proto, unsigned char class_proto, string& src_if, string& dst_if, 
		u_short src_port, u_short dst_port, unsigned int n_bytes, 
		unsigned int n_frames, time_t ini_sec, time_t end_sec, time_t ini_mic, 
		time_t end_mic, struct in_addr ip_src, struct in_addr ip_dst);	 

	/*!     \fn void adjustProtocol(char* str_proto, char* final)
	\brief Format protocol string.

	Adjust the protocol number to print it on the right format, put 0-3 zeros at the front of the protocol number.

	\param str_proto Original protocol string.
	\param final Formated Protocol string.
	*/
	static void adjustProtocol(char* str_proto, char* final);

	/*!     \fn void flowToString(char format, char proto_format, flow_t *flow, char* str)
	\brief Convert a flow to string.

	\param format	Format to output flow string.
	\param proto_format Protocol format, i.e. either numerical or short-string format.
	\param flow	Source flow.
	\param str	Equivalent flow string.
	*/
	static void flowToString(char format, char proto_format, flow_t *flow, char* str);

	/*!     \fn int printFlowToFile(flow_t *flow, const char #define*file)
	\brief Store a flow to a binary file.

	\param flow	Flow to be stored.
	\param file	destination file.
	\return Error code.
	*/
	static int printFlowToFile(flow_t *flow, const char *file);

	static ResultEnum printFlowCollectionToFile(flow_collection* pFlowCol, const string& strFileName);

	static ResultEnum addFlowToFile(flow_t* pFlow, const string& strFileName);

	static ResultEnum readFlowCollectionFromFile(flow_collection* pFlowCol, const string& strFileName);

	/*!     \fn void printFlowStruct(char format, char* str)
	\brief Write the string flow header.

	\param format 	Format code.
	\param str	Pointer to receive the flow text header structure.
	*/
	static void printFlowStruct(char format, char* str);

	

	/*!     \fn flow_t *readFlowFromFile(flow_t *flow, const char *file, int ind)
	\brief Read a flow from a binary file.

	Read a flow from a file. The ind parameter concerns the flow to be read, if the first (0), the second (1) or any other. The function return NULL when there is any flow or if the ind is bigger then the flow's number 
	\param flow	Destination structure for a new flow.
	\param file	Source file with stored flows. 
	\param ind	Determine the flow to be read in the file (memory deslocation in the file).
	\return 	Pointer to the read flow. 
	*/
	static flow_t *readFlowFromFile(flow_t *flow, const char *file, int ind);

	/*!     \fn char * get_protocolName( unsigned short proto_id )
	\brief Get protocol name from identifier.
	#define
	Returns the protocol name, given the protocol ID
	\param proto_id The protocol ID, according to the classification process.
	\return Pointer to protocol name string. 
	*/
	static const char * get_protocolName( unsigned short proto_id );

	/*!     \fn int getDate(time_t *tloc, char *str, int str_len)
	\brief Store a local time data on a string.

	\param tloc	Local time pointer.
	\param str 	Destination string.
	\param str_len	Destination string length.
	\return		Return 1 if all the procedures were perfectly done, and 0 otherwise.
	*/
	//static int getDate(time_t *tloc, char *str, int str_len);

	static int getDate(const time_t* tloc, string& str);
	/*!     \fn u_short getIntLen(unsigned long num)
	\brief Verify the number of digits in a unsigned long integer.

	\param num 	Input number.
	\return		Number of digits.
	*/

	static u_short getIntLen(unsigned long num);
	/*!     \fn u_short getDouLen(double num)
	\brief Get the number of digits of a double number.

	\param num 	Input double number.
	\return		Number of digits.
	*/
	u_short getDouLen(double num);


};
#endif
