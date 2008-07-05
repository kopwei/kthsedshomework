/**
* Provides facilities access libpcap library
*/

#ifndef _ANALYSERPX_CAP_
#define _ANALYSERPX_CAP_

/*!     \file cap.h
\brief Capture procedures and defines.

This header includes defines and functions which deal with configuration and capturing packets procedures.
*/

/*!     \def PCAP_OPEN_LIVE_ERROR
\brief Open live error code.

The integer number stored on this macro is used to identify open live capture errors.
*/ 
const unsigned int PCAP_OPEN_LIVE_ERROR = 10;

/*!     \def PCAP_COMPILE_ERROR
\brief Compile error code.

The integer number stored on this macro is used to identify a compile failure.
*/
const unsigned int PCAP_COMPILE_ERROR = 11;

/*!     \def PCAP_SETFILTER_ERROR
\brief Setfilter error code.

The integer number stored on this macro is used to identify a particular setfilter error.
*/
const unsigned int PCAP_SETFILTER_ERROR = 12;
#include "analyserpxTypes.h" 
				 

class CCaptureUtil
{
public:
	/*! 	\fn cap_config *new_cap_config()
	\brief Capture configuration structure creation procedure.

	Create a new instance of {@link cap_config} structure.
	Default values:
	dev = NULL; (will capture from all interfaces)
	filter_app = "ip"; (ip packets filter expression)
	numOfPackets = 0; (no stop)
	snap_len = 1518;
	*/
	static void init_cap_config(cap_config* pCapConfig);

	/*!     \fn int start_capture(pcap_handler func, u_char* arg, cap_config *c, int onlineCapMode, char *offLineFile)
	\brief Start the capture packets up.

	Start capture proces. All libpcap operations will be performed inside this function.
	\param func		an prototype  function that will be called for every arrived packet.
	\param arg		arguments passes to func.
	\param c		cap_config struct that contain pcap informations, like device.
	\param onlineCapMode 	if the capture works online or throught file.
	\param offLineFile	if onlineCapMode is '0' then this param inform the file to read from.
	*/
	static int start_capture(pcap_handler func, u_char* arg, cap_config *c, int onlineCapMode, char *offLineFile);

	/*!     \fn void delete_cap_config(void *data)
	\brief Capture configuration structure finalization.

	Receive a cap_config structure and release its allocated memory.
	\param *data Pointer to a cap_config structure.
	*/
	static void delete_cap_config(void *data);


	static int initiate_capture(cap_config * c, const bool onlineCapMode, const string offLineFile);

};
#endif
