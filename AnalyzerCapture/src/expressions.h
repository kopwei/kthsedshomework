#ifndef _EXPRESSIONS_H_
#define _EXPRESSIONS_H_
#include <regex.h>
#include <stdio.h>


#ifdef DEFINE_GLOBALS
#define GLOBAL_PX
#else // !DEFINE_GLOBALS
#define GLOBAL_PX extern
#endif


//#include <asm-x86_64/types.h>
/*!     \file expressions.h
        \brief Regular Expressions header file.

        This header file declares the regular expressions used by Analyser-PX and its manipulation functions..
*/

/*The types were defined to agree with the IPP2P functions*/
/*!     \var typedef unsigned char u8
	\brief An unsigned byte variable.

	This variable was defined with the aim of compatibility
*/
typedef unsigned char u8;

/*!     \var typedef unsigned char __u8
	\brief Just another unsigned byte variable.

	This variable was defined with the aim of compatibility with IP2P's procedures.
*/
typedef unsigned char __u8;

/*!     \var typedef unsigned short u16
        \brief An unsigned variable with two bytes long.

        This variable was defined with the aim of compatibility with IP2P's procedures.
*/
typedef unsigned short u16;

/*!     \var typedef unsigned short __u16
        \brief An unsigned variable with two bytes long.

        This variable was defined with the aim of compatibility with IP2P's procedures.
*/
typedef unsigned short __u16;

/*!     \var typedef unsigned long int u32
        \brief An unsigned variable with four bytes long.

        This variable was defined with the aim of compatibility with IP2P's procedures.
*/
typedef unsigned int u32;

/*!     \var typedef unsigned long int __u32
        \brief An unsigned variable with four bytes long.

        This variable was defined with the aim of compatibility with IP2P's procedures.
*/

#ifndef __ANALYSERPX__
typedef unsigned int __u32;
#endif

/**
* Regular expressions
**/
/*!     \var regex_t* http
	\brief Regular expression variable to HTTP protocol.

	This variable stores the regular expression of http protocol messages.
*/
GLOBAL_PX regex_t* http;

/*!     \var regex_t* http2
        \brief Second release of regular expression variable to HTTP protocol.

        This variable stores the regular expression of http protocol messages (second release).
*/
GLOBAL_PX regex_t* http2;

/*!     \var regex_t* dns
        \brief Regular expression variable to DNS protocol.

        This variable stores the regular expression of dns protocol messages.
*/
GLOBAL_PX regex_t* dns;

/*!     \var regex_t* aim
        \brief Regular expression variable to aim protocol.

        This variable stores the regular expression of aim protocol messages.
*/
GLOBAL_PX regex_t* aim;

/*!     \var regex_t* irc
        \brief Regular expression variable to irc protocol.

        This variable stores the regular expression of irc protocol messages.
*/
GLOBAL_PX regex_t* irc;

/*!     \var regex_t* msn
        \brief Regular expression variable to recognize msn protocol.

        This variable stores the regular expression of msn protocol messages.
*/
GLOBAL_PX regex_t* msn;

/*!     \var regex_t* yahooMess
        \brief Regular expression variable to recognize yahoo Message protocol.

        This variable stores the regular expression of yahoo Message.
*/
GLOBAL_PX regex_t* yahooMess;

/*!     \var regex_t* rtsp
        \brief Regular expression variable based on rtsp messages patterns.

        This variable stores the regular expression of rtsp messages.
*/
GLOBAL_PX regex_t* rtsp;

/*!     \var regex_t* httpQuicktime
        \brief Regular expression variable based on Quicktime over http messages patterns.

        This variable stores the regular expression of Quicktime over http messages.
*/
GLOBAL_PX regex_t* httpQuicktime;

/*!     \var regex_t* httpVideo
        \brief Regular expression variable based on messages's patterns from Video over http.

        This variable stores the regular expression of Video over http messages.
*/
GLOBAL_PX regex_t* httpVideo;

/*!     \var regex_t* httpAudio
        \brief Regular expression variable based on messages's patterns from Audio over http.

        This variable stores the regular expression of Audio over http messages.
*/
GLOBAL_PX regex_t* httpAudio;

/*!     \var regex_t* pop3
        \brief Regular expression variable based on messages's patterns from POP3 protocol.

        This variable stores the regular expression POP messages.
*/
GLOBAL_PX regex_t* pop3;

/*!     \var regex_t* smtp
        \brief Regular expression variable based on messages's patterns from smtp protocol..

        This variable stores the regular expression of smtp messages.
*/
GLOBAL_PX regex_t* smtp;

/*!     \var regex_t* ftp
        \brief FTP regular expression variable.

        This variable stores the regular expression of ftp messages.
*/
GLOBAL_PX regex_t* ftp;

/*!     \var regex_t* hlcs
        \brief HLCS regular expression variable.

        This variable stores the regular expression of hlcs messages.
*/
GLOBAL_PX regex_t* hlcs;

/*!     \var regex_t* skypeToSkype
        \brief First Skype regular expression variable.

        This variable stores the regular expression of skype-to-skype messages.
*/
GLOBAL_PX regex_t* skypeToSkype;

/*!     \var regex_t* skypeOut
        \brief Second Skype regular expression variable.

        This variable stores the regular expression of skype messages.
*/
GLOBAL_PX regex_t* skypeOut;

/*!     \var regex_t* ssh
        \brief SSH regular expression variable.

        This variable stores the regular expression of ssh messages.
*/
GLOBAL_PX regex_t* ssh;

/*!     \var regex_t* ssl
        \brief SSL regular expression variable.

        This variable stores the regular expression of ssl messages.
*/
GLOBAL_PX regex_t* ssl;

/*!     \var regex_t* validcertssl
        \brief validcertssl regular expression variable.

        This variable stores the regular expression of validcertssl messages.
*/
GLOBAL_PX regex_t* validcertssl;

/*!     \var regex_t* netbios
        \brief netbios regular expression variable.

        This variable stores the regular expression of netbios messages.
*/
GLOBAL_PX regex_t* netbios;

/*!     \var regex_t* nbns
        \brief nbns regular expression variable.

        This variable stores the regular expression of nbns messages.
*/
GLOBAL_PX regex_t* nbns;

/*!     \var regex_t* nbds
        \brief nbds regular expression variable.

        This variable stores the regular expression of nbds messages.
*/
GLOBAL_PX regex_t* nbds;

/*!     \var regex_t* bootstrap
        \brief bootstrap regular expression variable.

        This variable stores the regular expression of bootstrap messages.
*/
GLOBAL_PX regex_t* bootstrap;

/*!     \var regex_t* googlevideo
        \brief googlevideo regular expression variable.

        This variable stores the regular expression of googlevideo messages.
*/
GLOBAL_PX regex_t* googlevideo;

/*!     \var regex_t* youtube
        \brief youtube regular expression variable.

        This variable stores the regular expression of youtube messages.
*/
GLOBAL_PX regex_t* youtube;

/*!     \var regex_t* zippyvideo
        \brief zippyvideo regular expression variable.

        This variable stores the regular expression of zippyvideo messages.
*/
GLOBAL_PX regex_t* zippyvideo;

/*!     \var regex_t* veoh
        \brief veoh regular expression variable.

        This variable stores the regular expression of veoh messages.
*/
GLOBAL_PX regex_t* veoh;

/*!     \var regex_t* vidilife
        \brief vidilife regular expression variable.

        This variable stores the regular expression of vidilife messages.
*/
GLOBAL_PX regex_t* vidilife;

/*!     \var regex_t* napster
        \brief napster regular expression variable.

        This variable stores the regular expression of napster messages.
*/
GLOBAL_PX regex_t* napster; //added recently

/*!     \var regex_t* otherChat
        \brief otherChat regular expression variable.

        This variable stores the regular expression of otherChat messages.
*/
GLOBAL_PX regex_t* otherChat;

/*!     \var regex_t* otherVideoTCP
        \brief otherVideoTCP regular expression variable.

        This variable stores the regular expression of otherVideoTCP messages.
*/
GLOBAL_PX regex_t* otherVideoTCP;

/*!     \var regex_t* otherVideoUDP
        \brief otherVideoUDP regular expression variable.

        This variable stores the regular expression of otherVideoUDP messages.
*/
GLOBAL_PX regex_t* otherVideoUDP;

/*!     \var regex_t* otherMail 
        \brief otherMail regular expression variable.

        This variable stores the regular expression of otherMail messages.
*/
GLOBAL_PX regex_t* otherMail;

/*!     \var regex_t* mySQL
        \brief mySQL regular expression variable.

        This variable stores the regular expression of mySQL messages.
*/
GLOBAL_PX regex_t* mySQL;

/*!     \var regex_t* otherDNS
        \brief otherDNS regular expression variable.

        This variable stores the regular expression of otherDNS messages.
*/
GLOBAL_PX regex_t* otherDNS;

/*!     \var regex_t* otherHTTP
        \brief otherHTTP regular expression variable.

        This variable stores the regular expression of otherHTTP messages.
*/
GLOBAL_PX regex_t* otherHTTP;
/*new*/

/*!     \var regex_t* goBoogy
        \brief goBoogy regular expression variable.

        This variable stores the regular expression of goBoogy messages.
*/
GLOBAL_PX regex_t* goBoogy; //Korean peer-to-peer -> netfilter's site

/*!     \var regex_t* soribada
        \brief soribada regular expression variable.

        This variable stores the regular expression of soribada messages.
*/
GLOBAL_PX regex_t* soribada; //Korean peer-to-peer -> netfilter's site

/*!     \fn void allocateMemory()
	\brief Function to allocate memory for regex_t pointers.
*/
void allocateMemory();

/*!     \fn void loadExpressions()
	\brief Function to load the regular expresions.
*/
void loadExpressions();

/*!     \fn void freeExpressions()
        \brief Function to unload allocated memory by regular expresions.
*/
void freeExpressions();
#endif
