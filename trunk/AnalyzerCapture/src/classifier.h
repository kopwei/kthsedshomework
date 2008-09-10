/*!     \file classifier.h
\brief P2P Classifier module.

Identify protocol messages based on IPP22P signatures. (Further information, visit the IPP2P Project - http://www.ipp2p.org/)
*/


#ifndef _CLASSIFIER_H_
#define _CLASSIFIER_H_

#include "analyserpxTypes.h"
#include "expressions.h"

/*Convertion function-like macro to interger types to*/

/*! \def get_u8(X,O)
\brief Convertion function-like macro to 8 bits integer type.
*/

/*! \def get_u16(X,O)
\brief Convertion function-like macro to 16 bits integer type.
*/

/*! \def get_u32(X,O)
\brief Convertion function-like macro to 32 bits integer type.
*/

#define get_u8(X,O)  (*(__u8 *)(X + O))
#define get_u16(X,O)  (*(__u16 *)(X + O))
#define get_u32(X,O)  (*(__u32 *)(X + O))

/*Smallest classification number*/

/*! \def UP_BASE_P2P_CLASS_NUMBER
\brief Smallest P2P (Up Base) class classification number.
*/

/*! \def DOWN_BASE_P2P_CLASS_NUMBER
\brief Smallest P2P (Down Base) class classification number.
*/

/*! \def HTTP_SUPER_CLASS_NUMBER
\brief Smallest HTTP super class classification number.
*/

/*! \def SSL_SUPER_CLASS_NUMBER
\brief Smallest SSL super class classification number.
*/

const unsigned int UP_BASE_P2P_CLASS_NUMBER = 200;  //minor classification number
const unsigned int DOWN_BASE_P2P_CLASS_NUMBER = 101;  //minor classification number
const unsigned int HTTP_SUPER_CLASS_NUMBER = 201;  //minor classification number
const unsigned int SSL_SUPER_CLASS_NUMBER = 1302;  //minor classification number

/* The last column of the flow file (printed file) identify the tranport protocol below the IP, it can be filled
* with one of these numbers: 0 for UDP and 1 for TCP
* or it also can be filled with 2 that identifies ICMP or 3 (Unknown type)
*/

/*! \def PROTO_ID_TCP
\brief TCP Protocol's Identifier.
*/
const unsigned int PROTO_ID_TCP	=	IPPROTO_TCP;

/*! \def PROTO_ID_EDONKEY
\brief EDonkey Protocol's Identifier.
*/
const unsigned int PROTO_ID_EDONKEY =	106;	// also "Emule" and "Kademlia"

/*! \def PROTO_ID_BITTORRENT
\brief Bittorrent Protocol's Identifier.
*/
const unsigned int PROTO_ID_BITTORRENT =	104;

/*! \def PROTO_ID_KAZAA
\brief Kazaa Protocol's Identifier.
*/
const unsigned int PROTO_ID_KAZAA	=	107;

/*! \def PROTO_ID_GNU
\brief GNU Protocol's Identifier.
*/
const unsigned int PROTO_ID_GNU	=	110;

/*! \def PROTO_ID_GOOBOGY
\brief Goobogy Protocol's Identifier.
*/
const unsigned int PROTO_ID_GOOBOGY    =    111; //new, korean per-to-peer, see netfilter's site

/*! \def PROTO_ID_SORIBADA
\brief Soribada Protocol's Identifier.
*/
const unsigned int PROTO_ID_SORIBADA    =   119; //new, korean per-to-peer, see netfilter's site

/*! \def PROTO_ID_SOULSEEK
\brief Soulseek Protocol's Identifier.
*/
const unsigned int PROTO_ID_SOULSEEK	= 120;

/*! \def PROTO_ID_ARES
\brief Ares Protocol's Identifier.
*/
const unsigned int PROTO_ID_ARES	=	103;

/*! \def PROTO_ID_WINMX104
\brief WinMX Protocol's Identifier.
*/
const unsigned int PROTO_ID_WINMX		= 126;

/*! \def PROTO_ID_MUTE
\brief MUTE Protocol's Identifier.
*/
const unsigned int PROTO_ID_MUTE		= 115;

/*! \def PROTO_ID_NAP
\brief NAP Protocol's Identifier.
*/
const unsigned int PROTO_ID_NAP		= 116;

/*! \def PROTO_ID_XDCC
\brief XDCC Protocol's Identifier.
*/
const unsigned int PROTO_ID_XDCC		= 128;

/*! \def PROTO_ID_DIRECTCONNECT
\brief DirectConnect Protocol's Identifier.
*/
const unsigned int PROTO_ID_DIRECTCONNECT	= 105;

/*! \def PROTO_ID_APPLEJUICE
\brief AppleJuice Protocol's Identifier.
*/
const unsigned int PROTO_ID_APPLEJUICE	= 102;

/*! \def PROTO_ID_WASTE
\brief Waste Protocol's Identifier.
*/
const unsigned int PROTO_ID_WASTE		= 129;

/*! \def PROTO_ID_UDP
\brief UDP Protocol's Identifier.
*/
const unsigned int PROTO_ID_UDP		= IPPROTO_UDP;


/*#define PROTO_ID_EDKUDP		130
#define PROTO_ID_BITTORRENTUDP	131
#define PROTO_ID_KAZAAUDP	132
#define PROTO_ID_GNUUDP		133*/



/*! \def PROTO_ID_ICMP
\brief ICMP Protocol's Identifier.
*/
const unsigned int PROTO_ID_ICMP		= IPPROTO_ICMP;

/*! \def PROTO_ID_OTHER
\brief Other Protocol's Identifier.
*/
const unsigned int PROTO_ID_OTHER		= 19;
//new104

/*! \def PROTO_ID_UNKNOWN
\brief Unknown Protocol's Identifier.
*/
const unsigned int PROTO_ID_UNKNOWN	= 0;

/*! \def PROTO_ID_NONPAYLOAD
\brief Nonpayload Protocol's Identifier.
*/
const unsigned int PROTO_ID_NONPAYLOAD	= 3000;

/*! \def PROTO_ID_HTTP
\brief HTTP Protocol's Identifier.
*/
const unsigned int PROTO_ID_HTTP		= 201;

/*! \def PROTO_ID_OTHERHTTP
\brief Other HTTP Protocol's Identifier.
*/
const unsigned int PROTO_ID_OTHERHTTP      = 299;

/*! \def PROTO_ID_AIM104
\brief AIM Protocol's Identifier.
*/
const unsigned int PROTO_ID_AIM		= 301;

/*! \def PROTO_ID_IRC
\brief IRC Protocol's Identifier.
*/
const unsigned int PROTO_ID_IRC		= 303;

/*! \def PROTO_ID_MSN
\brief MSN Protocol's Identifier.
*/
const unsigned int PROTO_ID_MSN		= 306;

/*! \def PROTO_ID_YAHOOMESS
\brief Yahoo Messenger Protocol's Identifier.
*/
const unsigned int PROTO_ID_YAHOOMESS	= 308;

/*! \def PROTO_ID_OTHERCHAT
\brief Other Chat Protocol's Identifier.
*/
const unsigned int PROTO_ID_OTHERCHAT	= 399;

/*! \def PROTO_ID_HLCS
\brief HLCS Protocol's Identifier.
*/
const unsigned int PROTO_ID_HLCS		= 403;

/*! \def PROTO_ID_HL2DEATH
\brief HL2DEATH Protocol's Identifier.
*/
const unsigned int PROTO_ID_HL2DEATH	= 407;

/*! \def PROTO_ID_HL2CS
\brief HL2CS Protocol's Identifier.
*/
const unsigned int PROTO_ID_HL2CS		= 415;

/*! \def PROTO_ID_HLDEATH
\brief HLDEATH Protocol's Identifier.
*/
const unsigned int PROTO_ID_HLDEATH	= 416;

/*! \def PROTO_ID_QUAKE3
\brief QUAKE3 Protocol's Identifier.
*/
const unsigned int PROTO_ID_QUAKE3		= 417;

/*! \def PROTO_ID_DNS
\brief DNS Protocol's Identifier.
*/
const unsigned int PROTO_ID_DNS		= 501;

/*! \def PROTO_ID_NETBIOS
\brief NetBios Protocol's Identifier.
*/
const unsigned int PROTO_ID_NETBIOS	= 506;

/*! \def PROTO_ID_NBNS
\brief NBNS Protocol's Identifier.
*/
const unsigned int PROTO_ID_NBNS		= 509;

/*! \def PROTO_ID_NBDS
\brief NBDS Protocol's Identifier.
*/
const unsigned int PROTO_ID_NBDS		= 510;

/*! \def PROTO_ID_BOOTSTRAP
\brief Bootstrap Protocol's Identifier.
*/
const unsigned int PROTO_ID_BOOTSTRAP	= 511;

/*! \def PROTO_ID_OTHERDNS
\brief Other DNbeforeS Protocol's Identifier.
*/
const unsigned int PROTO_ID_OTHERDNS       = 599;

/*! \def PROTO_ID_RTSP
\brief RTSP Protocol's Identifier.
*/
const unsigned int PROTO_ID_RTSP		= 601;

/*! \def PROTO_ID_HTTPQUICKTIME
\brief HTTP Quick Time Protocol's Identifier.
*/
const unsigned int PROTO_ID_HTTPQUICKTIME	= 602;

/*! \def PROTO_ID_HTTPVIDEO
\brief HTTP Video Protocol's Identifier.
*/
const unsigned int PROTO_ID_HTTPVIDEO	= 606;

/*! \def PROTO_ID_HTTPAUDIO
\brief HTTP Audio Protocol's Identifier.
*/
const unsigned int PROTO_ID_HTTPAUDIO	= 608;

/*! \def PRO#defineTO_ID_GOOGLEVIDEO
\brief Google Video Protocol's Identifier.
*/
const unsigned int PROTO_ID_GOOGLEVIDEO	= 611;

/*! \def PROTO_ID_YOUTUBE
\brief Youtube Protocol's Identifier.
*/
const unsigned int PROTO_ID_YOUTUBE	= 612;

/*! \def PROTO_ID_ZIPPYVIDEO
\brief ZippyVideo Protocol's Identifier.
*/
const unsigned int PROTO_ID_ZIPPYVIDEO	= 613;

/*! \def PROTO_ID_VEOH
\brief VEOH Protocol's Identifier.
*/
const unsigned int PROTO_ID_VEOH		= 614;

/*! \def PROTO_ID_VIDILIFE
\brief VIDILIFE Protocol's Identifier.
*/
const unsigned int PROTO_ID_VIDILIFE	= 615;

/*! \def PROTO_ID_OTHERVIDEO
\brief Other Video Protocol's Identifier.
*/
const unsigned int PROTO_ID_OTHERVIDEO	= 699;

/*! \def PROTO_ID_POP3
\brief POP3 Protocol's Identifier.
*/
const unsigned int PROTO_ID_POP3		= 703;

/*! \def PROTO_ID_SMTP
\brief SMTP Protocol's Identifier.
*/
const unsigned int PROTO_ID_SMTP		= 704;

/*! \def PROTO_ID_OTHERMAIL
\brief Other Mail Protocol's Identifier.
*/
const unsigned int PROTO_ID_OTHERMAIL	= 799;

/*! \def PROTO_ID_FTP
\brief FTP Protocol's Identifier.
*/
const unsigned int PROTO_ID_FTP		= 801;

/*! \def PROTO_ID_MYSQL
\brief MySQL Protocol's Identifier.
*/
const unsigned int PROTO_ID_MYSQL          = 802;

/*! \def PROTO_ID_SKYPETOSKYPE
\brief Skype-to-Skype Protocol's Identifier.
*/
const unsigned int PROTO_ID_SKYPETOSKYPE	= 1002;

/*! \def PROTO_ID_SKYPEOUT= 
\brief SkypeOut Protocol's Identifier.
*/
const unsigned int PROTO_ID_SKYPEOUT	= 1001;

/*! \def PROTO_ID_SSH
\brief SSH Protocol's Identifier.
*/
const unsigned int PROTO_ID_SSH		= 1301;

/*! \def PROTO_ID_SSL
\brief SSL Protocol's Identifier.
*/
const unsigned int PROTO_ID_SSL		= 1302;

/*! \def PROTO_ID_VALIDCERTSSL
\brief SSL Certificate Validation Protocol's Identifier.
*/
const unsigned int PROTO_ID_VALIDCERTSSL	=1303;


//end
/*! 	\def PROTO_NAME_TCP
\brief TCP L4 Protocol's string description.
*/
#define PROTO_NAME_TCP			"TCP"

/*! 	\def PROTO_NAME_EDONKEY
\brief eDonkey P2P Protocol's string description.
*/
#define PROTO_NAME_EDONKEY		"eDonkey"

/*!     \def PROTO_NAME_EMULE
\brief eMule P2P Protocol's string description.
*/
#define PROTO_NAME_EMULE		"eMule"

/*!     \def PROTO_NAME_KADEMLIA
\brief Kademia P2P Protocol's string description.
*/
#define PROTO_NAME_KADEMLIA		"KademliaExt"

/*!     \def PROTO_NAME_BITTORRENT
\brief BitTorrent P2P Protocol's string description.
*/
#define PROTO_NAME_BITTORRENT		"BitTorrent"

/*!     \def PROTO_NAME_KAZAA
\brief KaZaA P2P Protocol's string description.
*/
#define PROTO_NAME_KAZAA		"Kazaa"

/*!     \def PROTO_NAME_GNU
\brief GNU P2P Protocol's string description.
*/
#define PROTO_NAME_GNU			"GNU"

/*!     \def PROTO_NAME_GOBOOGY
\brief Goboogy P2P Protocol's string description.
*/
#define PROTO_NAME_GOBOOGY              "Goboogy"

/*!     \def PROTO_NAME_SORIBADA
\brief Soribada P2P Protocol's string description.
*/
#define PROTO_NAME_SORIBADA             "Soribada"

/*!     \def PROTO_NAME_SOULSEEK
\brief Soulseek P2P Protocol's string description.
*/
#define PROTO_NAME_SOULSEEK		"Soulseek"

/*!     \def PROTO_NAME_ARES
\brief Ares P2P Protocol's string description.
*/
#define PROTO_NAME_ARES			"Ares"

/*!     \def PROTO_NAME_WINMX
\brief WinMX P2P Protocol's string description.
*/
#define PROTO_NAME_WINMX		"WinMX"

/*!     \def PROTO_NAME_MUTE
\brief Mute P2P Protocol's string description.
*/
#define PROTO_NAME_MUTE			"Mute"

/*!     \def PROTO_NAME_NAP
\brief Napster P2P Protocol's string description.
*/
#define PROTO_NAME_NAP			"Napster"

/*!     \def PROTO_NAME_XDCC
\brief Xdcc P2P Protocol's string description.
*/
#define PROTO_NAME_XDCC			"Xdcc"

/*!     \def PROTO_NAME_DIRECTCONNECT
\brief DirectConnect P2P Protocol's string description.
*/
#define PROTO_NAME_DIRECTCONNECT	"DirectConnect"

/*!     \def PROTO_NAME_APPLEJUICE
\brief AppleJuice P2P Protocol's string description.
*/
#define PROTO_NAME_APPLEJUICE		"AppleJuice"

/*!     \def PROTO_NAME_WASTE
\brief Waste P2P Protocol's string description.
*/
#define PROTO_NAME_WASTE		"Waste"

/*!     \def PROTO_NAME_UDP
\brief UDP L4 Protocol's string description.
*/
#define PROTO_NAME_UDP			"UDP"
/*#define PROTO_NAME_EDKUDP		"edkUDP"
#define PROTO_NAME_BITTORRENTUDP	"BiTtorrentUDP"
#define PROTO_NAME_KAZAAUDP		"kazaaUDP"
#define PROTO_NAME_GNUUDP		"gnuUDP"*/

/*!     \def PROTO_NAME_ICMP
\brief ICMP string description.
*/
#define PROTO_NAME_ICMP			"ICMP"

/*!     \def PROTO_NAME_OTHER
\brief Other L7 Protocol's string description.
*/
#define PROTO_NAME_OTHER		"Other"
//new

/*!     \def PROTO_NAME_UNKNOWN
\brief Unknown Protocol's string description.
*/
#define PROTO_NAME_UNKNOWN		"Unknwon"

/*!     \def PROTO_NAME_NONPAYLOAD
\brief NonPayload Protocol's string description.
*/
#define PROTO_NAME_NONPAYLOAD		"NonPayload"

/*!     \def PROTO_NAME_HTTP
\brief HTTP Protocol's string description.
*/
#define PROTO_NAME_HTTP			"HTTP"

/*!     \def PROTO_NAME_OTHERHTTP
\brief OtherHTTP Protocol's string description.
*/
#define PROTO_NAME_OTHERHTTP            "OtherHTTP"

/*!     \def PROTO_NAME_AIM
\brief AIM Protocol's string description.
*/
#define PROTO_NAME_AIM			"AIM"

/*!     \def PROTO_NAME_IRC
\brief IRC Protocol's string description.
*/
#define PROTO_NAME_IRC			"IRC"

/*!     \def PROTO_NAME_MSN
\brief MSN Messenger Protocol's string description.
*/
#define PROTO_NAME_MSN			"MSNMessenger"

/*!     \def PROTO_NAME_YAHOOMESS
\brief Yahoo Messenger Protocol's string description.
*/
#define PROTO_NAME_YAHOOMESS		"YahooMessenger"

/*!     \def PROTO_NAME_OTHERCHAT
\brief Other Chat Protocol's string description.
*/
#define PROTO_NAME_OTHERCHAT            "OtherChat"

/*!     \def PROTO_NAME_HLCS
\brief HalfLife CounterStrike Protocol's string description.
*/
#define PROTO_NAME_HLCS			"HalfLifeCounterStrike"

/*!     \def PROTO_NAME_HL2DEATH
\brief  HalfLife2Deathmatch Protocol's string description.
*/
#define PROTO_NAME_HL2DEATH		"HalfLife2Deathmatch"

/*!     \def PROTO_NAME_HL2CS
\brief HalfLife2CounterStrike Protocol's string description.
*/
#define PROTO_NAME_HL2CS		"HalfLife2CounterStrike"

/*!     \def PROTO_NAME_HLDEATH
\brief HalfLifeDeathmatch Protocol's string description.
*/
#define PROTO_NAME_HLDEATH		"HalfLifeDeathmatch"

/*!     \def PROTO_NAME_QUAKE3
\brief Quake Protocol's v3 string description.
*/
#define PROTO_NAME_QUAKE3		"Quake3"

/*!     \def PROTO_NAME_DNS
\brief DNS Protocol's string description.
*/
#define PROTO_NAME_DNS			"DNS"

/*!     \def PROTO_NAME_OTHERDNS
\brief Other DNS Protocol's string description.
*/
#define PROTO_NAME_OTHERDNS             "OtherDNS"

/*!     \def PROTO_NAME_RTSP
\brief RTSP Protocol's string description.
*/
#define PROTO_NAME_RTSP			"RTSP"

/*!     \def PROTO_NAME_HTTPQUICKTIME
\brief HTTP Quicktime Protocol's string description.
*/
#define PROTO_NAME_HTTPQUICKTIME	"HTTPQuicktime"

/*!     \def PROTO_NAME_HTTPVIDEO
\brief HTTP Video Protocol's string description.
*/
#define PROTO_NAME_HTTPVIDEO		"HTTPVideo"

/*!     \def PROTO_NAME_YOUTUBE
\brief YouTube Messages string description.
*/
#define PROTO_NAME_YOUTUBE		"YouTube"

/*!     \def PROTO_NAME_GOOGLEVIDEO
\brief Google Video Messages string description.
*/
#define PROTO_NAME_GOOGLEVIDEO		"GoogleVideo"

/*!     \def PROTO_NAME_ZIPPYVIDEO
\brief Zippy Video Messages string description.
*/
#define PROTO_NAME_ZIPPYVIDEO		"ZippyVideo"

/*!     \def PROTO_NAME_VEOH
\brief Veoh Messages string description.
*/
#define PROTO_NAME_VEOH			"Veoh"

/*!     \def PROTO_NAME_VIDILIFE
\brief Vidilife Messages string description.
*/
#define PROTO_NAME_VIDILIFE		"Vidilife"

/*!     \def PROTO_NAME_OTHERVIDEO
\brief Other Video Messages string description.
*/
#define PROTO_NAME_OTHERVIDEO           "OtherVideo"

/*!     \def PROTO_NAME_HTTPAUDIO
\brief HTTP Audio Messages string description.
*/
#define PROTO_NAME_HTTPAUDIO		"HTTPAudio"

/*!     \def PROTO_NAME_POP3
\brief POP3 Protocol's string description.
*/
#define PROTO_NAME_POP3			"POP3"

/*!     \def PROTO_NAME_SMTP
\brief SMTP Protocol's string description.
*/
#define PROTO_NAME_SMTP			"SMTP"

/*!     \def PROTO_NAME_FTP
\brief FTP Protocol's string description.
*/
#define PROTO_NAME_FTP			"FTP"

/*!     \def PROTO_NAME_MYSQL
\brief MySQL Protocol's string description.
*/
#define PROTO_NAME_MYSQL                "MySQL"

/*!     \def PROTO_NAME_OTHERMAIL
\brief Other Mail Protocol's string description.
*/
#define PROTO_NAME_OTHERMAIL		"OtherMail"

/*!     \def PROTO_NAME_SKYPETOSKYPE
\brief Skype-to-Skype Messages' string description.
*/
#define PROTO_NAME_SKYPETOSKYPE		"SkypeToSkype"

/*!     \def PROTO_NAME_SKYPEOUT
\brief Skype out Messages' string description.
*/
#define PROTO_NAME_SKYPEOUT		"SkypeOut"

/*!     \def PROTO_NAME_SSL
\brief SSL Protocol's string description.
*/
#define PROTO_NAME_SSL			"SSL"

/*!     \def PROTO_NAME_VALIDCERTSSL
\brief Valid Certificate SSL's string description.
*/
#define PROTO_NAME_VALIDCERTSSL		"VALIDCERTSSL"

/*!     \def PROTO_NAME_SSH
\brief SSH Protocol's string description.
*/
#define PROTO_NAME_SSH			"SSH"

/*!     \def PROTO_NAME_NETBIOS
\brief Netbios Protocol's string description.
*/
#define PROTO_NAME_NETBIOS		"NetBios"

/*!     \def PROTO_NAME_NBNS
\brief NBNS Protocol's string description.
*/
#define PROTO_NAME_NBNS			"NBNS"

/*!     \def PROTO_NAME_NBDS
\brief NBDS Protocol's string description.
*/
#define PROTO_NAME_NBDS			"NBDS"

/*!     \def PROTO_NAME_BOOTSTRAP
\brief Bootstrap Protocol's string description.
*/
#define PROTO_NAME_BOOTSTRAP		"Bootstrap"
//end new

/*The types were defined to agree with the IPP2P functions*/
/*typedef unsigned char u8;
typedef unsigned char __u8;
typedef unsigned short u16;
typedef unsigned short __u16;
typedef unsigned long int u32;
typedef unsigned long int __u32;
typedef unsigned short u_short;*/

class CClassifier
{

public:

	/*!     \fn u_short getID(const struct ip *iph, u_short ipLen)
	\brief Return the type recognized by the ipp2p signature-matching classifier.
	\param *iph		IP header structure (payload included).
	\param *ipLen		Collected IP packet length.
	\return Identifier code.
	*/
	static u_short getID(const struct ip *iph, u_short ipLen);


	/*!     \fn u_short verID(u_short old_id, u_short new_id)
	\brief Check identifier code hierarchy.

	\param old_id         Last code.
	\param new_id         Eventual new code.
	\return Return hierarchically higher code.
	*/
	static u_short verID(u_short old_id, u_short new_id);

	/*!     \fn u_short isSuperClass(u_short id)
	\brief Verify Super Class codes.

	\param id         Identifier code.
	\return		  Return 1 if id is a super class, 0 otherwise.
	*/
	static u_short isSuperClass(u_short id);

	//////////////////////////////////////////////////////////////////////////
	
	static bool IsP2P(const u_short id);

	static bool IsHTTP(const u_short id);

	static bool IsNonPayload(const u_short id);


private:

	/*!     \fn u_short isBittorrent (const unsigned char *payload, const u16 mess_len)
	\brief Look for the BitTorrent messege's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isBittorrent (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isAres (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Ares application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isAres (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSoul (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Soul application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSoul (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isEdku (const unsigned char *payload, const u16 mess_len)
	\brief Look for the eDonkey application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isEdku (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isGnuu (const unsigned char *payload, const u16 mess_len)
	\brief Look for the GNU application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isGnuu (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isDcu (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Direct Connect application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isDcu (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isApple(const unsigned char *payload, const u16 mess_len)
	\brief Look for the Apple Juice application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isApple(const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isKazaa (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Kazaa application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isKazaa (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isDc (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Direct Connect application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isDc (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isGnu (const unsigned char *payload, const u16 mess_len)
	\brief Look for the GNU application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isGnu (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isEdk (const unsigned char *payload, const u16 mess_len)
	\brief Look for the eDonkey application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isEdk (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isEdk_all (const unsigned char *payload, const u16 mess_len)
	\brief Look for the eDonkey application's signature in payload message from TCP segments (low way).

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isEdk_all (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isWinMX (const unsigned char *payload, const u16 mess_len)
	\brief Look for the WinMX application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isWinMX (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isMute (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Mute application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isMute (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isXdcc (const unsigned char *payload, const u16 mess_len)
	\brief Look for the XDCC application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isXdcc (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isWaste(const unsigned char *payload, const u16 mess_len)
	\brief Look for the Waste application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isWaste(const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isBittorrentu (const unsigned char *payload, const u16 mess_len)
	\brief Look for the BitTorrent application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isBittorrentu (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isKazaa_all (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Kazaa application's signature in payload message from TCP segments (low way).

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isKazaa_all (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isDc_all (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Direct Connect application's signature in payload message from TCP segments (low way).

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isDc_all (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isGnu_all (const unsigned char *payload, const u16 mess_len)
	\brief Look for the GNU application's signature in payload message from TCP segments (low way).

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isGnu_all (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isKazaau (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Kazaa application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isKazaau (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isMP2P (const unsigned char *payload, const u16 mess_len)
	\brief Look for the MP2P application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len		Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isMP2P (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isNap (unsigned char *payload, const u16 mess_len)
	\brief Look for the OpenNap application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isNap (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isEarthS5 (const unsigned char *payload, const u16 mess_len)
	\brief Look for the EarthS5 application's signature in payload message from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isEarthS5 (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isMP2Pu (const unsigned char *payload, const u16 mess_len)
	\brief Look for the MP2P application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isMP2Pu (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isEarthu (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Eartha pplication's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isEarthu (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isGoBoogyu (unsigned char *payload, const u16 mess_len)
	\brief Look for the GoBoogy application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isGoBoogyu (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isGoBoogy (unsigned char *payload, const u16 mess_len)
	\brief Look for the GoBoogy application's signature in payload message from TCP segments.

	\param *payload 	Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isGoBoogy (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSoribada (unsigned char *payload, const u16 mess_len)
	\brief Look for the Soribada application's signature in payload message from TCP/UDP segments.

	\param *payload             Message string.
	\param *mess_len            Message string length. 
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSoribada (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHTTP (unsigned char *payload, const u16 mess_len)
	\brief Look for the HTTP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len		Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHTTP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isAIM (unsigned char *payload, const u16 mess_len)
	\brief Look for the AIM protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len		Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isAIM (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isIRC (unsigned char *payload, const u16 mess_len)
	\brief Look for the IRC protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len		Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isIRC (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isMSN (unsigned char *payload, const u16 mess_len)
	\brief Look for the MSN Messenger protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isMSN (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isYahooMess (unsigned char *payload, const u16 mess_len)
	\brief Look for the Yahoo Messenger protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isYahooMess (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHLCS (unsigned char *payload, const u16 mess_len)
	\brief Look for the Half Life Counter-Strike game's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHLCS (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHL2DEATH (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Half Life 2 Deathmatch game's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHL2DEATH (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHL2CS (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Half Life 2 Counter-Strike game's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHL2CS (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHLDEATH (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Half Life Deathmatch game's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHLDEATH (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isQUAKE3 (const unsigned char *payload, const u16 mess_len)
	\brief Look for the Quake 3 game's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isQUAKE3 (const unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isDNS (unsigned char *payload, const u16 mess_len)
	\brief Look for the DNS protocol's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isDNS (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isRTSP (unsigned char *payload, const u16 mess_len)
	\brief Look for the RTSP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isRTSP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHTTPQuicktime (unsigned char *payload, const u16 mess_len)
	\brief Look for the Quicktime under HTTP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHTTPQuicktime (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHTTPVideo (unsigned char *payload, const u16 mess_len)
	\brief Look for Video under HTTP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHTTPVideo (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isHTTPAudio (unsigned char *payload, const u16 mess_len)
	\brief Look for Audio under HTTP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isHTTPAudio (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isPOP3 (unsigned char *payload, const u16 mess_len)
	\brief Look for the POP3  protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isPOP3 (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSMTP (unsigned char *payload, const u16 mess_len)
	\brief Look for the SMTP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSMTP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isFTP (unsigned char *payload, const u16 mess_len)
	\brief Look for the FTP protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isFTP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSkypeOut (unsigned char *payload, const u16 mess_len)
	\brief Look for Skype (VOIP) application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSkypeOut (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSkypeToSkype (unsigned char *payload, const u16 mess_len)
	\brief Look for Skype (CHAT) application's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSkypeToSkype (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSSL (unsigned char *payload, const u16 mess_len)
	\brief Look for the SSL protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSSL (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isVALIDCERTSSL (unsigned char *payload, const u16 mess_len)
	\brief Look for the VALIDCERTSSL (SSL subset) protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isVALIDCERTSSL (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isSSH (unsigned char *payload, const u16 mess_len)
	\brief Look for the SSH protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isSSH (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isNetBios (unsigned char *payload, const u16 mess_len)
	\brief Look for the NetBios protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isNetBios (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isNbns (unsigned char *payload, const u16 mess_len)
	\brief Look for the Nbns protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isNbns (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isNbds (unsigned char *payload, const u16 mess_len)
	\brief Look for the Nbds protocol's signature in payload message from TCP/UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isNbds (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isBootstrap (unsigned char *payload, const u16 mess_len)
	\brief Look for the Bootstrap protocol's signature in payload message from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isBootstrap (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isYouTube (unsigned char *payload, const u16 mess_len)
	\brief Look for YouTube's videos from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isYouTube (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isGoogleVideo (unsigned char *payload, const u16 mess_len)
	\brief Look for GoogleVideo's videos from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isGoogleVideo (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isZippyVideo (unsigned char *payload, const u16 mess_len)
	\brief Look for ZippyVideo's videos from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isZippyVideo (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isVeoh (unsigned char *payload, const u16 mess_len)
	\brief Look for Veoh's videos from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isVeoh (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isVidilife (unsigned char *payload, const u16 mess_len)
	\brief Look for Vidilife's videos from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isVidilife (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isIfilm (unsigned char *payload, const u16 mess_len)
	\brief Look for ifilm's videos from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isIfilm (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isOtherChat (unsigned char *payload, const u16 mess_len)
	\brief Look for other Chat applications from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isOtherChat (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isOtherVideoTCP (unsigned char *payload, const u16 mess_len)
	\brief Look for other Video Streaming types from TCP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isOtherVideoTCP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isOtherVideoUDP (unsigned char *payload, const u16 mess_len)
	\brief Look for other Video Streaming types from UDP segments.

	\param *payload		Message string.
	\param *mess_len	Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isOtherVideoUDP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isOtherMail (unsigned char *payload, const u16 mess_len)
	\brief Look for other mail messages.

	\param *payload         Message string.
	\param *mess_len        Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isOtherMail (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isOtherHTTP (unsigned char *payload, const u16 mess_len)
	\brief Look for other HTTP messages.

	\param *payload         Message string.
	\param *mess_len        Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isOtherHTTP (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isOtherDNS (unsigned char *payload, const u16 mess_len)
	\brief Look for other DNS messages.

	\param *payload         Message string.
	\param *mess_len        Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isOtherDNS (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isMySQL (unsigned char *payload, const u16 mess_len)
	\brief Look for MYSQL's messages.

	\param *payload         Message string.
	\param *mess_len        Message string length.
	\return Return 1 if this function had been able to identify the signature into message string. Otherwise, return 0.
	*/
	static u_short isMySQL (unsigned char *payload, const u16 mess_len);

	/*!     \fn u_short isAP2PID(u_short id)
	\brief Check if a given identifier number is a valid P2P code.

	\param id         Supposed identifier code.
	\return Return 1 if id stores a valid p2p identifier code. Otherwise, return 0.
	*/
	static u_short isAP2PID(u_short id);

	


	static int coupeEOF (const unsigned char *payload, const u_short mess_len, unsigned char* strg);

	static unsigned char* getPayload(const struct ip *iph);

	static unsigned short getPayloadLen(const struct ip *iph, const u_short ipLen);

};
extern int STR_MAX_LEN;

#endif
