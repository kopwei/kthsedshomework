#include "classifier.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>


int CClassifier::coupeEOF ( const unsigned char *payload, const u_short mess_len, unsigned char* strg )
{

	strncpy ( ( char* ) strg,"",1 );
	int i=0;
	int c=0;

	//assert(payload_len < 500);
	for ( i = 0; i < ( mess_len ); i++ )
	{
		if ( ! ( payload[i]=='\0' ) )
		{
			strg[c] = payload[i];
			c++;
		}
	}
	strg[c] = '\0';
	return ( c );
}

unsigned char* CClassifier::getPayload ( const struct ip *iph )
{
	unsigned char *payload = NULL;
	unsigned char *transport = ( unsigned char * ) iph + iph->ip_hl * 4;
	// if protocol is TCP
	if ( ( ( ( int ) iph->ip_p ) == IPPROTO_TCP ) )
	{
		struct tcphdr *tcph = ( struct tcphdr * ) transport;
		payload = transport + tcph->th_off * 4;
	}
	// if protocol is UDP
	else if ( ( ( ( int ) iph->ip_p ) == IPPROTO_UDP ) )
	{
		// udp has a fixed length of 8 bytes
		payload = transport + 8;
	}
	return payload;
}

unsigned short CClassifier::getPayloadLen ( const struct ip *iph, const u_short ipLen )
{
	unsigned char *transport = ( unsigned char * ) iph + iph->ip_hl * 4;
	// if protocol is TCP
	if ( ( ( ( int ) iph->ip_p ) == IPPROTO_TCP ) )
	{
		struct tcphdr *tcph = ( struct tcphdr * ) transport;
		return ( ipLen - ( iph->ip_hl * 4 ) - ( tcph->th_off * 4 ) );
	}
	// if protocol is UDP
	else if ( ( ( ( int ) iph->ip_p ) == IPPROTO_UDP ) )
	{
		// udp has a fixed length of 8 bytes
		return ( ipLen - ( iph->ip_hl * 4 ) - ( 2 * 4 ) );
	}
	return ( ipLen - ( iph->ip_hl * 4 ) );
	//return ipLen - (payload - ((unsigned char *) iph));
}

u_short CClassifier::getID ( const struct ip *iph, u16 ipLen )
{
	unsigned char *strg;
	unsigned char *transp = ( unsigned char * ) iph + iph->ip_hl * 4;
	if ( ( ( ( int ) iph->ip_p ) == IPPROTO_TCP ) )
	{
		unsigned char *payload = getPayload ( iph );
		u16 mess_len = getPayloadLen ( iph, ipLen );
		struct tcphdr* temp = ( struct tcphdr* ) transp;

		if ( mess_len == 0 )
			return PROTO_ID_NONPAYLOAD;	//Nonpayload TCP traffic
		/*if (isHLCS(payload, mess_len))
		return 403;*///this function was replaced above because its regexec function
//printf("%x %u\n",payload[2],mess_len);
		switch ( payload[0] )
		{
			case 0x00:
				if ( mess_len == 17 )
					if ( get_u32 ( payload, 0 ) == htonl ( 0x0d ) && payload[4] == 0x06 && get_u32 ( payload, 13 ) == htonl ( 0x4000 ) )
						return PROTO_ID_BITTORRENT;
				/*payload pattern extracted from Blinc signatures paper*/
				if ( mess_len >= 7 )
					if ( payload[1] == 0x00 && payload[5] == 0x00 && payload[6] == 0x00 )
					{
						if ( payload[2] == 0x40 && payload[3] == 0x09 && payload[4] == 0x07 )
							return PROTO_ID_BITTORRENT;
						if ( payload[2] == 0x00 && payload[3] == 0x0d && payload[4] == 0x06 )
							return PROTO_ID_BITTORRENT;
						if ( payload[2] == 0x00 && payload[3] == 0x05 && payload[4] == 0x04 )
							return PROTO_ID_BITTORRENT;
					}
				break;
			case 0x13:
				if ( mess_len > 20 )
					if ( memcmp ( payload + 1, "BitTorrent protocol", 19 ) == 0 )
						return PROTO_ID_BITTORRENT;
				break;
			case 0x14:
				if ( mess_len >= 9 )
					/* match 14 00 00 00 01 yy 00 00 00 STRING(YY) 01 00 00 00 00 46|50 00 00 00 00 */
					/* without size at the beginning !!! */
					if ( payload[1] == 0x00 && payload[2] == 0x00 && payload[3] == 0x00 && get_u8 ( payload, 4 ) == 0x01 )
					{
						__u32 y = get_u32 ( payload, 5 );
						/* we need 19 chars + string */
						if ( ( y + 19 ) <= ( mess_len ) )
						{
							const unsigned char *w = payload + 9 + y;
							if ( get_u32 ( w, 0 ) == 0x01
							        && ( get_u16 ( w, 4 ) == 0x4600 || get_u16 ( w, 4 ) == 0x5000 )
							        && get_u32 ( w, 6 ) == 0x00 )
								return PROTO_ID_SOULSEEK;
						}
					}
				break;
			case 0x24: /* $ */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
					case 0x43: /* C */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 9 )
							if ( memcmp ( payload + 2, "onnectT", 7 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x44: /* D */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 4 )
							if ( memcmp ( payload + 2, "ir", 2 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x47: /* G */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 4 )
							if ( memcmp ( payload + 2, "et", 2 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x48: /* H */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 6 )
							if ( memcmp ( payload + 2, "ello", 4 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 8 )
							if ( memcmp ( payload + 2, "ubName", 6 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x4b: /* K */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 5 )
							if ( memcmp ( payload + 2, "ey ", 3 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x4c: /* L */
						if ( mess_len >= 7 )
							if ( ( payload[mess_len - 1] == 0x7c ) && ( memcmp ( payload + 2, "ock ", 4 ) == 0 ) )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x4d: /* M */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 7 )
							if ( memcmp ( payload + 2, "yINFO", 5 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						if ( mess_len >= 9 )
							if ( ( payload[mess_len - 1] == 0x7c ) && ( memcmp ( payload + 2, "yNick ", 6 ) == 0 ) )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x51: /* Q */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 6 )
							if ( memcmp ( payload + 2, "uit ", 4 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x52: /* R */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 8 )
							if ( memcmp ( payload + 2, "evConn", 6 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x53: /* S */
						if ( mess_len >= 6 )
							if ( memcmp ( payload + 2, "end|", 4 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 8 )
							if ( memcmp ( payload + 2, "earch ", 6 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 9 )
							if ( memcmp ( payload + 2, "upports", 7 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
					case 0x56: /* V */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 9 )
							if ( memcmp ( payload + 2, "ersion ", 7 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
				}
				break;
			case 0x47: /* G */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
					case 0x45: /* E */
						if ( mess_len >= 5 )
						{
							if ( memcmp ( payload + 2, "T /", 3 ) == 0 )
							{
								switch ( payload[5] )
								{
									case 0x24: /* $ */
										if ( mess_len >= 15 )
											if ( memcmp ( payload + 6, "$$$$$$$$/", 9 ) == 0 )
												return 124;
										break;
									case 0x2e: /* . */
										if ( mess_len >= 11 )
											if ( memcmp ( payload + 6, "hash=", 5 ) == 0 && payload[mess_len-2] == 0x0d && payload[mess_len-1] == 0x0a )
												return 107;
										break;
										/* message announce */
									case 0x61: /* a */
										if ( mess_len >= 24 )
											if ( memcmp ( payload + 6, "nnounce?info_hash=", 18 ) == 0 )
												return PROTO_ID_BITTORRENT;
										break;
									case 0x67: /* g */
										if ( mess_len >= 9 )
											if ( memcmp ( payload + 6, "et/", 3 ) == 0 )
												if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
													return PROTO_ID_GNU;
										break;
										/* message scrape */
									case 0x73: /* s */
										if ( mess_len >= 22 )
											if ( memcmp ( payload + 6, "crape?info_hash=", 16 ) == 0 )
												return PROTO_ID_BITTORRENT;
										break;
									case 0x74: /* t */
										/*payload pattern extracted from Blinc signatures paper*/
										if ( mess_len > 20 )
											if ( memcmp ( payload + 6, "orrents/", 8 ) == 0 )
												return PROTO_ID_BITTORRENT;
										break;
									case 0x75: /* u */
										if ( mess_len >= 13 )
											if ( memcmp ( payload + 6, "ri-res/", 7 ) == 0 )
												if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
													return PROTO_ID_GNU;
										break;
								}
								if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
								{
									u16 c = 8;
									if ( mess_len < 35 )
										break;
									const u16 end = mess_len - 22;
									while ( c < end )
									{
										if ( payload[c] == 0x0a && payload[c + 1] == 0x0d
										        &&
										        ( ( memcmp ( &payload[c + 2], "X-Kazaa-Username: ", 18 ) == 0 )
										          ||
										          ( memcmp ( &payload[c + 2], "User-Agent: PeerEnabler/", 24 ) == 0 ) ) )
											return 107;
										c++;
									}
								}
							}
							else
							{
								/*payload pattern extracted from Blinc signatures paper*/
								if ( mess_len >= 9 )
									if ( ( memcmp ( payload + 2, "T hash:", 7 ) == 0 ) || ( memcmp ( payload + 2, "T sha1:", 7 ) == 0 ) )
										return PROTO_ID_ARES;
								if ( mess_len >= 9 )
									if ( memcmp ( payload + 2, "T.sha1:", 6 ) == 0 )
										return 129;
							}
						}
						break;
					case 0x49: /* I */
						if ( mess_len >= 5 )
							if ( memcmp ( payload + 2, "VE ", 3 ) == 0 )
								if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
									return 107;
						break;
					case 0x4e: /* N */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 9 )
							if ( memcmp ( payload + 2, "UTELLA", 6 ) == 0 )
								return PROTO_ID_GNU;
						break;
					case 0x4f: /* O */
						if ( mess_len >= 4 )
							if ( memcmp ( payload + 2, "!!", 2 ) == 0 )
								return 125;
						break;
				}
				break;
			case 0x48: /* H */
				/*payload pattern extracted from Blinc signatures paper*/
				if ( mess_len >= 16 )
					if ( payload[1] == 0x54 )
						if ( memcmp ( payload + 2, "TP/1.1 503 ", 11 ) == 0 )
						{
							if ( ( memcmp ( payload + 13, "Que", 3 ) == 0 ) || ( memcmp ( payload + 13, "Ful", 3 ) == 0 ) || ( memcmp ( payload + 13, "Not", 3 ) == 0 ) )
								return PROTO_ID_GNU;
							if ( memcmp ( payload + 13, "Bus", 3 ) == 0 )
								return PROTO_ID_ARES;
						}
				break;
			case 0x4d: /* M */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
					case 0x44: /* D */
						if ( mess_len >= 4 )
							if ( memcmp ( payload + 2, "5", 1 ) == 0 )
								return 125;
						break;
				}
				break;
			case 0x50: /* P */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
					case 0x52: /* R */
						if ( mess_len > 20 && mess_len < 200
						        && payload[mess_len - 1] == 0x0a && payload[mess_len - 2] == 0x0d
						        && memcmp ( payload + 2, "IVMSG ", 6 ) == 0 )
						{
							u16 x = 10;
							const u16 end = mess_len - 13;

							/* is seems to be a irc private massage, chedck for xdcc command */
							while ( x < end )
							{
								if ( payload[x] == ':' )
								{
									if ( memcmp ( &payload[x + 1], "xdcc send #", 11 ) == 0 )
										return 128;
								}
								x++;
							}
						}
						break;
					case 0x55: /* U */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 5 )
							if ( memcmp ( payload + 2, "SH ", 3 ) == 0 )
								return PROTO_ID_ARES;
						break;
					case 0x75: /* u */
						if ( mess_len == 209 || mess_len == 345 || mess_len == 473
						        || mess_len == 609 || mess_len == 1121 )
							if ( memcmp ( payload + 2, "blicKey: ", 9 ) == 0 )
								return 115;
						break;
				}
				break;
			case 0x53: /* S */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
					case 0x49: /* I */
						if ( mess_len >= 4 )
							if ( ( memcmp ( payload + 2, "Z", 1 ) == 0 ) && ( payload[3] == 0x20 ) )
								return 125;
						break;
					case 0x54: /* T */
						if ( mess_len >= 4 )
							if ( ( memcmp ( payload + 2, "R", 1 ) == 0 ) && ( payload[3] == 0x20 ) )
								return 125;
						break;
					case 0x65: /* e */
						/*payload pattern extracted from Blinc signatures paper*/
						if ( mess_len >= 11 )
							if ( ( memcmp ( payload + 2, "rver: Mor", 9 ) == 0 ) || ( memcmp ( payload + 2, "rver: Lim", 9 ) == 0 ) )
								return PROTO_ID_GNU;
						break;
				}
				break;
			case 0x55: /* U */
				/*payload pattern extracted from Blinc signatures paper*/
				if ( mess_len >= 16 )
					if ( payload[1] == 0x73 )
						if ( memcmp ( payload + 2, "er-Agent: Lime", 14 ) == 0 )
							return PROTO_ID_GNU;
				break;
			case 0x56: /* V */
				/*payload pattern extracted from Blinc signatures paper*/
				if ( mess_len >= 16 )
					if ( payload[1] == 0x65 )
						if ( memcmp ( payload + 2, "ndor-Message: ", 14 ) == 0 )
							return PROTO_ID_GNU;
				break;
			case 0x58: /* X */
				/*payload pattern extracted from Blinc signatures paper*/
				if ( mess_len >= 9 )
					if ( payload[1] == 0x2d )
						if ( ( memcmp ( payload + 2, "Query", 5 ) == 0 ) || ( memcmp ( payload + 2, "Guess", 5 ) == 0 ) || ( memcmp ( payload + 2, "Ultrap", 6 ) == 0 ) ||
						        ( memcmp ( payload + 2, "Ext-", 4 ) == 0 ) || ( memcmp ( payload + 2, "Try-", 4 ) == 0 ) || ( memcmp ( payload + 2, "Degree", 6 ) == 0 ) ||
						        ( memcmp ( payload + 2, "Lo", 2 ) == 0 ) || ( memcmp ( payload + 2, "Max-", 4 ) == 0 ) || ( memcmp ( payload + 2, "Version", 7 ) == 0 ) ||
						        ( memcmp ( payload + 2, "Dynami", 6 ) == 0 ) )
							return PROTO_ID_GNU;
				break;
			case 0x61: /* a */
				if ( mess_len >= 8 )
					if ( ( payload[6] == 0x0d ) && ( payload[7] == 0x0a ) && ( memcmp ( payload + 1, "jprot", 5 ) == 0 ) )
						return 102;
				break;
			case 0x62: /* b */
				if ( mess_len >= 5 )
					if ( memcmp ( payload + 1, "oogy", 4 ) == 0 )
						return 111;
				break;
			case 0x67: /* g */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
					case 0x6f: /* o */
						if ( mess_len >= 7 )
							if ( memcmp ( payload + 2, "boogy", 5 ) == 0 )
								return 111;
						break;
				}
				break;
			case 0xe3:
				if ( mess_len < 5 )
					break;
				if ( payload[5] == 0x47 )
					return 106;
				const u16 cmd = get_u16 ( payload, 1 );
				if ( cmd == ( mess_len - 5 ) )
				{
					switch ( payload[5] )
					{
						case 0x01:
							return 106;	/*Client: hello or Server:hello */
						case 0x4c:
							return 106;	/*Client: Hello-Answer */
					}
				}
				break;
		}

		if ( mess_len >= 2 )
		{
			switch ( payload[1] )
			{
				case 0x00:
					if ( mess_len >= 6 )
						if ( ( mess_len - payload[0] ) == 3 )
						{
							switch ( payload[2] )
							{
								case 0x5a:
									/* ares connect */
									if ( mess_len == 6 && payload[5] == 0x05 )
										return PROTO_ID_ARES;
									break;
								case 0x09:
									/* ares search, min 3 chars --> 14 bytes
									 * lets define a search can be up to 30 chars --> max 34 bytes
									 */
									if ( mess_len >= 14 && mess_len <= 34 )
										return PROTO_ID_ARES;
									break;
							}
						}
					break;
			}
		}

		if ( mess_len >= 5 )
		{
			switch ( payload[4] )
			{
				case 0x00:
					if ( mess_len >= 9 )
						if ( get_u8 ( payload, 7 ) == 0x00 )
							if ( get_u32 ( payload, 0 ) == ( mess_len - 4 ) )
								return PROTO_ID_SOULSEEK;
					break;
				case 0x01:
					if ( mess_len >= 9 )
						if ( get_u16 ( payload, 6 ) == 0x0000 )
							if ( get_u32 ( payload, 0 ) == ( mess_len - 4 ) )
								return PROTO_ID_SOULSEEK;
					break;
			}
		}

		if ( mess_len >= 15 )
		{
			switch ( payload[14] )
			{
					/*payload pattern extracted from Blinc signatures paper*/
				case 0x43: /* C */
					if ( mess_len >= 28 )
						if ( memcmp ( payload + 15, "ontent-Range:", 13 ) == 0 )
							return 107;
					break;
			}
		}

		if ( mess_len >= 18 )
		{
			switch ( payload[17] )
			{
					/*payload pattern extracted from Blinc signatures paper*/
				case 0x52: /* R */
					if ( mess_len >= 29 )
						if ( memcmp ( payload + 18, "etry-After:", 11 ) == 0 )
							return 107;
					break;
			}
		}

		if ( mess_len >= 33 )
		{
			switch ( payload[33] )
			{
					/*payload pattern extracted from Blinc signatures paper*/
				case 0x42: /* B */
					if ( mess_len >= 44 )
						if ( memcmp ( payload + 34, "usy Queued", 10 ) == 0 )
							return PROTO_ID_GNU;
					break;
			}
		}

		if ( get_u32 ( payload, 0 ) == ( mess_len - 4 ) )
		{
			/* match 00 yy yy 00, yy can be everything
			if (get_u8(payload, 4) == 0x00 && get_u8(payload, 7) == 0x00) {
				return PROTO_ID_SOULSEEK;
			}
			 next match: 01 yy 00 00 | yy can be everything
			if (get_u8(payload, 4) == 0x01 && get_u16(payload, 6) == 0x0000) {
				return PROTO_ID_SOULSEEK;
			} */

			const __u32 m = get_u32 ( payload, 4 );

			/* other soulseek commandos are: 1-5,7,9,13-18,22,23,26,28,35-37,40-46,50,51,60,62-69,91,92,1001 */
			/* try to do this in an intelligent way */
			/* get all small commandos */
			switch ( m )
			{
				case 7:
				case 9:
				case 22:
				case 23:
				case 26:
				case 28:
				case 50:
				case 51:
				case 60:
				case 91:
				case 92:
				case 1001:
					return PROTO_ID_SOULSEEK;
			}

			if ( m > 0 && m < 6 )
			{
				return PROTO_ID_SOULSEEK;
			}

			if ( m > 12 && m < 19 )
			{
				return PROTO_ID_SOULSEEK;
			}

			if ( m > 34 && m < 38 )
			{
				return PROTO_ID_SOULSEEK;
			}

			if ( m > 39 && m < 47 )
			{
				return PROTO_ID_SOULSEEK;
			}

			if ( m > 61 && m < 70 )
			{
				return PROTO_ID_SOULSEEK;
			}
		}

		strg = ( unsigned char* ) malloc ( ( mess_len+1 ) *sizeof ( char ) );
		mess_len = coupeEOF ( payload, mess_len, strg );

		if ( mess_len == 0 )
			return PROTO_ID_NONPAYLOAD;	//Nonpayload TCP traffic

		switch ( strg[0] )
		{
			case 0x01:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x03:
						if ( regexec ( validcertssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1303;
						}
						if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1302;
						}
					default:
						if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						if ( regexec ( soribada, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 119;
						}
						break;
				}
				break;
			case 0x02:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x02:
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( ( ntohs ( temp->th_sport ) == 6667 || ntohs ( temp->th_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 303;
						}
					default:
						if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
				}
				break;
			case 0x03:
			case 0x04:
			case 0x05:
			case 0x06:
			case 0x07:
			case 0x08:
			case 0x09:
			case 0x0a:
			case 0x0b:
			case 0x0c:
			case 0x0d:
			case 0x20:
			case 0x21:
			case 0x22:
			case 0x23:
			case 0x24:
			case 0x25:
			case 0x26:
			case 0x27:
			case 0x28:
			case 0x29:
				if ( ( ntohs ( temp->th_sport ) == 6667 || ntohs ( temp->th_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
				{
					free ( strg );
					return 303;
				}
				break;
			case 0x10:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x02:
					case 0x0a:
					case 0x0e:
						if ( regexec ( nbds, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 510;
						}
						break;
					case 0x14:
					case 0x15:
					case 0x16:
						if ( regexec ( soribada, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 119;
						}
						break;
				}
				break;
			case 0x11:
			case 0x12:
			case 0x13:
			case 0x14:
			case 0x15:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x02:
					case 0x0a:
					case 0x0e:
						if ( regexec ( nbds, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 510;
						}
						break;
				}
				break;
			case 0x16:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x03:
						if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1302;
						}
						break;
					case 0x02:
					case 0x0a:
					case 0x0e:
						if ( regexec ( nbds, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 510;
						}
						break;
				}
				break;
			case 0x2a: /* * */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x01:
					case 0x02:
						if ( ( ntohs ( temp->th_sport ) < 27000 || ntohs ( temp->th_dport ) < 27000 ) && ( regexec ( aim, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 301;
						}
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x05:
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x20: /*   */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x2b: /* + */
			case 0x2d: /* - */
				if ( regexec ( pop3, ( char* ) strg, 0, NULL, 0 ) == 0 )
				{
					free ( strg );
					return 703;
				}
				if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
				{
					free ( strg );
					return 799;
				}
				break;
			case 0x31: /* 1 */
				if ( regexec ( napster, ( char* ) strg, 0, NULL, 0 ) == 0 )
				{
					free ( strg );
					return 116;
				}
				break;
			case 0x32: /* 2 */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x32: /* 2 */
						if ( regexec ( smtp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 704;
						}
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
					case 0x33: /* 3 */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
					case 0x35: /* 5 */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x33: /* 3 */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x33: /* 3 */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
					case 0x35: /* 5 */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x34: /* 4 */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x32: /* 2 */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
				}
				break;
			case 0x3a: /* : */
				if ( ( ntohs ( temp->th_sport ) == 6667 || ntohs ( temp->th_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
				{
					free ( strg );
					return 303;
				}
				break;
			case 0x43: /* C */
			case 0x63: /* c */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x48: /* H */
					case 0x68: /* h */
					case 0x56: /* V */
					case 0x76: /* v */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x4f: /* O */
					case 0x6f: /* o */
						if ( regexec ( otherVideoTCP, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 699;
						}
						break;
				}
				break;
			case 0x44: /* D */
			case 0x64: /* d */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x41: /* A */
					case 0x61: /* a */
					case 0x4f: /* O */
					case 0x6f: /* o */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x45: /* E */
			case 0x65: /* e */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x48: /* H */
					case 0x68: /* h */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x47: /* G */
				if ( mess_len == 3 )
					if ( memcmp ( strg + 1, "ET", 2 ) == 0 )
						return 126;
			case 0x67: /* g */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x45: /* E */
					case 0x65: /* e */
						if ( mess_len >= 5 )
						{
							if ( ( memcmp ( strg + 2, "T /", 3 ) == 0 ) || ( memcmp ( strg + 2, "t /", 3 ) == 0 ) )
							{
								switch ( strg[5] )
								{
									case 0x46: /* F */
									case 0x66: /* f */
										if ( regexec ( vidilife, ( char* ) strg, 0, NULL, 0 ) == 0 )
										{
											free ( strg );
											return 615;
										}
										break;
									case 0x47: /* G */
									case 0x67: /* g */
										switch ( strg[6] )
										{
											case 0x45: /* E */
											case 0x65: /* e */
												if ( regexec ( goBoogy, ( char* ) strg, 0, NULL, 0 ) == 0 )
												{
													free ( strg );
													return 111;
												}
												break;
											case 0x4f: /* O */
											case 0x6f: /* o */
												if ( regexec ( googlevideo, ( char* ) strg, 0, NULL, 0 ) == 0 )
												{
													free ( strg );
													return 611;
												}
												break;
										}
										break;
									case 0x49: /* I */
									case 0x69: /* i */
										if ( regexec ( zippyvideo, ( char* ) strg, 0, NULL, 0 ) == 0 )
										{
											free ( strg );
											return 613;
										}
										break;
									case 0x4d: /* M */
									case 0x6d: /* m */
										if ( regexec ( veoh, ( char* ) strg, 0, NULL, 0 ) == 0 )
										{
											free ( strg );
											return 614;
										}
										break;
									case 0x50: /* P */
									case 0x70: /* p */
										if ( regexec ( youtube, ( char* ) strg, 0, NULL, 0 ) == 0 )
										{
											free ( strg );
											return 612;
										}
										break;
									case 0x51: /* Q */
									case 0x71: /* q */
										if ( regexec ( goBoogy, ( char* ) strg, 0, NULL, 0 ) == 0 )
										{
											free ( strg );
											return 111;
										}
										break;
								}
							}
							else
							{
								if ( regexec ( soribada, ( char* ) strg, 0, NULL, 0 ) == 0 )
								{
									free ( strg );
									return 119;
								}
							}
						}
						if ( mess_len >= 10 )
							if ( memcmp ( strg, "GET", 3 ) == 0 )
							{
								u16 c = 4;
								const u16 end = mess_len - 2;
								u8 count = 0;
								while ( c < end )
								{
									if ( strg[c] == 0x20 && strg[c + 1] == 0x22 )
									{
										c++;
										count++;
										if ( count >= 2 )
											return 126;
									}
									c++;
								}
							}
						break;
				}
				break;
			case 0x49: /* I */
			case 0x69: /* i */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x4a: /* J */
			case 0x6a: /* j */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4f: /* O */
					case 0x6f: /* o */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x4d: /* M */
			case 0x6d: /* m */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x53: /* S */
					case 0x73: /* s */
						if ( regexec ( msn, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 306;
						}
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x41: /* A */
					case 0x61: /* a */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x4e: /* N */
			case 0x6e: /* n */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x49: /* I */
					case 0x69: /* i */
						if ( ( ntohs ( temp->th_sport ) == 6667 || ntohs ( temp->th_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 303;
						}
						break;
					case 0x4c: /* L */
					case 0x6c: /* l */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x4f: /* O */
			case 0x6f: /* o */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4d: /* M */
					case 0x6d: /* m */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x50: /* P */
			case 0x70: /* p */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x41: /* A */
					case 0x61: /* a */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						if ( regexec ( otherVideoTCP, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 699;
						}
						break;
					case 0x4c: /* L */
					case 0x6c: /* l */
						if ( regexec ( otherVideoTCP, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 699;
						}
						break;
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x51: /* Q */
			case 0x71: /* q */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x52: /* R */
			case 0x72: /* r */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x43: /* C */
					case 0x63: /* c */
					case 0x45: /* E */
					case 0x65: /* e */
					case 0x53: /* S */
					case 0x73: /* s */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
				}
				break;
			case 0x53: /* S */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x45: /* E */
						if ( memcmp ( strg + 2, "ND", 2 ) == 0 )
						{
							if ( mess_len == 4 )
								return 126;
							if ( mess_len >= 10 )
								if ( memcmp ( strg + 1, "END", 3 ) == 0 )
								{
									u16 c = 4;
									const u16 end = mess_len - 2;
									u8 count = 0;
									while ( c < end )
									{
										if ( strg[c] == 0x20 && strg[c + 1] == 0x22 )
										{
											c++;
											count++;
											if ( count >= 2 )
												return 126;
										}
										c++;
									}
								}
						}
						break;
				}
			case 0x73: /* s */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x53: /* S */
					case 0x73: /* s */
						if ( regexec ( ssh, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1301;
						}
						break;
					case 0x54: /* T */
					case 0x74: /* t */
						if ( regexec ( otherMail, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 799;
						}
						break;
					case 0x59: /* Y */
					case 0x79: /* y */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
				}
				break;
			case 0x55: /* U */
			case 0x75: /* u */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x53: /* S */
					case 0x73: /* s */
						if ( mess_len < 3 )
							break;
						switch ( strg[2] )
						{
							case 0x45: /* E */
							case 0x65: /* e */
								if ( ( ntohs ( temp->th_sport ) == 6667 || ntohs ( temp->th_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
								{
									free ( strg );
									return 303;
								}
								if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
								{
									free ( strg );
									return 801;
								}
								break;
							case 0x52: /* R */
							case 0x72: /* r */
								if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
								{
									free ( strg );
									return 399;
								}
								break;
						}
						break;
				}
				break;
			case 0x59: /* Y */
			case 0x79: /* y */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4d: /* M */
					case 0x6d: /* m */
						if ( regexec ( yahooMess, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 308;
						}
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x48: /* H */
					case 0x68: /* h */
					case 0x50: /* P */
					case 0x70: /* p */
						if ( regexec ( yahooMess, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 308;
						}
						break;
				}
				break;
			case 0xff:
				if ( mess_len > 4 )
					if ( ( strg[1] == 0xff ) && ( strg[2] == 0xff ) && ( strg[3] == 0xff ) )
						if ( regexec ( hlcs, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 403;
						}
				break;
		}

		if ( mess_len >= 2 )
		{
			switch ( strg[1] )
			{
				case 0x01:
					if ( mess_len < 3 )
						break;
					switch ( strg[2] )
					{
						case 0x03:
							if ( regexec ( validcertssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1303;
							}
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
						default:
							if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
							{
								free ( strg );
								return 501;
							}
							break;
					}
					break;
				case 0x02:
					if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					if ( regexec ( napster, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 116;
					}
					break;
				case 0x06:
					if ( regexec ( napster, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 116;
					}
					break;
				case 0x16:
					if ( mess_len < 3 )
						break;
					switch ( strg[2] )
					{
						case 0x03:
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
							break;
					}
					break;
			}
		}

		if ( mess_len >= 3 )
		{
			switch ( strg[2] )
			{
				case 0x01:
					if ( mess_len < 4 )
						break;
					switch ( strg[3] )
					{
						case 0x03:
							if ( regexec ( validcertssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1303;
							}
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
						default:
							if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
							{
								free ( strg );
								return 501;
							}
							break;
					}
					break;
				case 0x02:
					if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					break;
				case 0x16:
					if ( mess_len < 4 )
						break;
					switch ( strg[3] )
					{
						case 0x03:
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
							break;
					}
					break;
			}
		}

		if ( mess_len >= 4 )
		{
			switch ( strg[3] )
			{
				case 0x01:
				case 0x02:
					if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					break;
				case 0x5f: /* _ */
					if ( regexec ( otherVideoTCP, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 699;
					}
					break;
			}
		}

		if ( mess_len >= 5 )
		{
			switch ( strg[4] )
			{
				case 0x01:
				case 0x02:
					if ( ( ntohs ( temp->th_sport ) == 53 || ntohs ( temp->th_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					break;
				case 0x03:
					if ( regexec ( mySQL, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 802;
					}
					break;
			}
		}

		if ( mess_len >= 16 )
		{
			switch ( strg[12] )
			{
				case 0x4d: /* M */
				case 0x6d: /* m */
					if ( regexec ( otherVideoTCP, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 699;
					}
					break;
			}
		}

		if ( regexec ( hlcs, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 403;
		}

		if ( regexec ( msn, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 306;
		}

		if ( regexec ( httpQuicktime, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 602;
		}

		if ( regexec ( httpVideo, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 606;
		}

		if ( regexec ( httpAudio, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 608;
		}

		if ( ( regexec ( http, ( char* ) strg, 0, NULL, 0 ) == 0 ) || ( regexec ( http2, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 201;
		}

		if ( ( ntohs ( temp->th_sport ) == 6667 || ntohs ( temp->th_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 303;
		}

		if ( ( ntohs ( temp->th_sport ) < 27000 || ntohs ( temp->th_dport ) < 27000 ) && ( regexec ( aim, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 301;
		}

		if ( regexec ( goBoogy, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 111;
		}

		if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 399;
		}

		if ( regexec ( rtsp, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 601;
		}

		if ( regexec ( otherVideoTCP, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 699;
		}

		if ( regexec ( netbios, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 506;
		}

		if ( regexec ( nbns, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 509;
		}

		/*		if (isEdk(payload, mess_len))
					return 106;		//eDonkey, eMule, Kademlia 52->106
				if (isEdk_all(payload, mess_len))
					return 106;		//eDonkey, eMule, Kademlia Extended 52->106
				if (isBittorrent(payload, mess_len))
					return 104;		//BitTorrent 54->104
				if (isKazaa(payload, mess_len))
					return 107;		//Kazaa 51->107
				if (isKazaa_all(payload, mess_len))
					return 107;		//Kazaa 51->107
				if (isGnu(payload, mess_len))
					return PROTO_ID_GNU;		//GNU 3 53->110
				if (isGnu_all(payload, mess_len))
					return PROTO_ID_GNU;		//GNU 3 53->110
				if (isSoul(payload, mess_len))
					return 120;		//Soulseek 5 55->120
				if (isAres(payload, mess_len))
					return PROTO_ID_ARES;		//Ares 9  59->103
				if (isMute(payload, mess_len))
					return 115;		//Mute 13    63->115
				if(isNap(payload, mess_len))
				return 116;		//OpenNap 11    61->116
				if(isEarthS5(payload, mess_len))
					return 124;		//EarthStation5 8	58->124
				if(isMP2P(payload, mess_len))
					return 125;		//MP2P	10	60->125
				if (isXdcc(payload, mess_len))FTP
					return 128;		//Xdcc 14   64->128
				if (isDc(payload, mess_len))
					return 105;		//DirectConnect  67->105
				if (isDc_all(payload, mess_len))
					return 105;		//DirectConnect   67->105
				if (isApple(payload, mess_len))
					return 102;		//AppleJuice 66->102
				if (isWaste(payload, mess_len))
					return 129;		//Waste 65->129

				strg = (unsigned char*)malloc((mess_len+1)*sizeof(char));
				mess_len = coupeEOF(payload, mess_len, strg);

				if (isHLCS(payload, mess_len)) {
					free(strg); return 403; }
				if (isGoBoogy(payload, mess_len)) {
					free(strg); return 111;} //new, korean peer-to-peer application, see netfilter's site
				if (isSoribada(payload, mess_len)) {
					free(strg); return 119;} //new, korean peer-to-peer application, see netfilter's site
				if (isMSN(strg, mess_len)) {
					free(strg); return 306; }
				if (isHTTPQuicktime(strg, mess_len)) {
					free(strg); return 602; }
				if (isHTTPVideo(strg, mesDNSs_len)) {
					free(strg); return 606;}
				if (isYouTube(strg, mess_len)) {
					free(strg); return 612;}
				if (isGoogleVideo(strg, mess_len)) {
					free(strg); return 611;}
				if (isZippyVideo(strg, mess_len)) {
					free(strg); return 613;}
				if (isVeoh(strg, mess_len)) {
					free(strg); return 614;}
				if (isVidilife(strg, mess_len)) {
					free(strg); return 615;}
				if (isHTTPAudio(strg, mess_len)) {
					free(strg); return 608;}
				if (isHTTP(strg, mess_len)) {
					free(strg); return 201;}
				if (isOtherHTTP(strg, mess_len)) {
					free(strg); return 299;}
				if (isNap(strg, mess_len)) {
					free(strg); return 116;} //OpenNap 11    61->116
				if (isWinMX(strg, mess_len)) {
					free(strg); return 126;}   //WinMX 62->126
				if (isSMTP(strg, mess_len)) {
					free(strg); return 704;}
				if (isPOP3(strg, mess_len)) {
					free(strg); return 703;}
				if (isOtherMail(strg, mess_len)) {
					free(strg); return 799;}
				if (isIRC(strg, mess_len) && (ntohs(temp->th_sport) == 6667 || ntohs(temp->th_dport) == 6667)) {
					free(strg); return 303;}
				if (isFTP(strg, mess_len)) {
					free(strg); return 801;}
				if (isDNS(strg, mess_len) && (ntohs(temp->th_sport) == 53 || ntohs(temp->th_dport) == 53)) {
					free(strg); return 501;}
				if (isYahooMess(strg, mess_len)) {
					free(strg); return 308;}
				if (isAIM(strg, mess_len) && (ntohs(temp->th_sport) < 27000 || ntohs(temp->th_dport) < 27000)) {
					free(strg); return 301;}
				if (isOtherChat(strg, mess_len)) {
					free(strg); return 399;}
				if (isRTSP(strg, mess_len)) {
					free(strg); return 601;}
				if (isOtherVideoTCP(strg, mess_len)) {
					free(strg); return 699;}
				if (isHLDEATH(strg, mess_len)) {
					free(strg); return 416;}
				if (isHL2DEATH(strg, mess_len)) {
					free(strg); return 407;}
				if (isHL2CS(strg, mess_len)){
					free(strg); return 415;}
				if (isVALIDCERTSSL(strg, mess_len)) {
					free(strg); return 1303;}
				if (isSSL(strg, mess_len)){
					free(strg); return 1302;}
				if (isSSH(strg, mess_len)) {
					free(strg); return 1301;}
				if (isNetBios(strg, mess_len)) {
					free(strg); return 506;}
				if (isNbns(strg, mess_len)){
					free(strg); return 509;}
				if (isNbds(strg, mess_len)) {
					free(strg); return 510;}
				if (isMySQL(strg, mess_len)) {
					free(strg); return 802;}*/
		free ( strg );
		return IPPROTO_TCP; //Unknown application, under TCP traffic
	}
	else if ( ( ( ( int ) iph->ip_p ) == IPPROTO_UDP ) )
	{
		unsigned char *payload = getPayload ( iph );
		u16 mess_len = getPayloadLen ( iph, ipLen );
		struct udphdr* temp2 = ( struct udphdr* ) transp;

		if ( mess_len == 0 )
			return PROTO_ID_NONPAYLOAD;	//Nonpayload UDP traffic

		switch ( payload[0] )
		{
			case 0x24: /* $ */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
						/*payload pattern extracted from Blinc signatures paper*/
					case 0x50: /* P */
						if ( mess_len >= 9 )
							if ( memcmp ( payload + 2, "in", 2 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
						/*payload pattern extracted from Blinc signatures paper*/
					case 0x53: /* S */
						if ( mess_len >= 9 )
							if ( memcmp ( payload + 2, "R ", 2 ) == 0 )
								return PROTO_ID_DIRECTCONNECT;
						break;
				}
				break;
			case 0x27: /* ' */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
						/*payload pattern extracted from Blinc signatures paper*/
					case 0x00:
						if ( mess_len >= 6 )
							if ( ( payload[2] == 0x00 ) && ( payload[3] == 0x00 ) && ( payload[4] == 0x29 || payload[4] == 0xa9 ) && ( payload[5] == 0x80 ) )
								return 107;
						break;
				}
				break;
			case 0x28: /* ( */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
						/*payload pattern extracted from Blinc signatures paper*/
					case 0x00:
						if ( mess_len >= 6 )
							if ( ( payload[2] == 0x00 ) && ( payload[3] == 0x00 ) && ( payload[4] == 0x29 || payload[4] == 0xa9 ) && ( payload[5] == 0x00 ) )
								return 107;
						break;
				}
				break;
			case 0x29: /* ( */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
						/*payload pattern extracted from Blinc signatures paper*/
					case 0x00:
						if ( mess_len >= 6 )
							if ( ( payload[2] == 0x00 ) && ( payload[3] == 0x00 ) && ( payload[4] == 0x29 || payload[4] == 0xa9 ) )
								return 107;
						break;
				}
				break;
			case 0x47: /* G */
				if ( mess_len < 2 )
					break;
				switch ( payload[1] )
				{
						/*payload pattern extracted from Blinc signatures paper*/
					case 0x4e:
						if ( mess_len >= 11 )
							if ( payload[2] == 0x44 )
								return PROTO_ID_GNU;
						break;
				}
				break;
		}

		if ( mess_len >= 3 )
		{
			switch ( payload[2] )
			{
				case 0xcf:
					if ( mess_len >= 4 )
						if ( payload[3] == 0x40 )
							return 124;
					break;
			}
		}

		if ( mess_len >= 9 )
		{
			switch ( payload[8] )
			{
				case 0x00:
					if ( mess_len < 10 )
						break;
					switch ( payload[9] )
					{
						case 0x00:
							if ( ( ( mess_len == 24 ) && ( get_u16 ( payload, 10 ) == ntohs ( 0x0417 ) ) && ( get_u32 ( payload, 12 ) == htonl ( 0x27101980 ) ) )
							        || ( ( mess_len == 29 ) && ( get_u16 ( payload, 10 ) == ntohs ( 0x0401 ) ) )
							        || ( ( mess_len == 52 ) && ( get_u16 ( payload, 10 ) == ntohs ( 0x0827 ) ) &&	( get_u32 ( payload, 12 ) == htonl ( 0x37502950 ) ) )
							        || ( ( mess_len == 211 ) && ( get_u16 ( payload, 10 ) == ntohs ( 0x0405 ) ) ) )
								return PROTO_ID_BITTORRENT;
							break;
					}
					break;
				case 0x47: /* G */
					if ( mess_len >= 11 )
						if ( memcmp ( payload + 9, "ND", 2 ) == 0 )
							return PROTO_ID_GNU;
					if ( mess_len >= 18 )
						if ( memcmp ( payload + 9, "NUTELLA ", 8 ) == 0 )
							return PROTO_ID_GNU;
					break;
				case 0x64: /* d */
					if ( mess_len < 10 )
						break;
					switch ( payload[9] )
					{
						case 0x31: /* 1 */
							if ( mess_len > 30 )
								if ( ( memcmp ( payload + 10, ":ad2:id20:", 10 ) == 0 ) || ( memcmp ( payload + 10, ":rd2:id20:", 10 ) == 0 ) )
									return PROTO_ID_BITTORRENT;
							break;
					}
					break;
				case 0xe3:
					if ( mess_len < 10 )
						break;
					/* edonkey */
					switch ( payload[9] )
					{
							/* client -> server status request */
						case 0x96:
							if ( mess_len == 14 )
								return 106;
							break;
							/* server -> client status request */
						case 0x97:
							if ( mess_len == 42 )
								return 106;
							break;
							/* server description request */
						case 0xa2:
							if ( ( mess_len == 14 ) && ( get_u16 ( payload, 10 ) == htons ( 0xfff0 ) ) )
								return 106;
							break;
							/* server description response */
						case 0x9a:
							if ( mess_len == 26 )
								return 106;
							break;
						case 0x92:
							if ( mess_len == 18 )
								return 106;
							break;
					}
					break;
				case 0xe4:
					if ( mess_len < 10 )
						break;
					switch ( payload[9] )
					{
							/* e4 20 .. | size == 43 */
						case 0x20:
							if ( ( mess_len == 43 ) && ( payload[10] != 0x00 ) && ( payload[42] != 0x00 ) )
								return 106;
							break;
							/* e4 00 .. 00 | size == 35 ? */
						case 0x00:
							/* e4 10 .. 00 | size == 35 ? */
						case 0x10:
							/* e4 18 .. 00 | size == 35 ? */
						case 0x18:
							if ( ( mess_len == 35 ) && ( payload[34] == 0x00 ) )
								return 106;
							break;
							/* e4 52 .. | size = 44 */
						case 0x52:
							if ( mess_len == 44 )
								return 106;
							break;
							/* e4 58 .. | size == 6 */
						case 0x58:
							if ( mess_len == 14 )
								return 106;
							break;
							/* e4 59 .. | size == 2 */
						case 0x59:
							if ( mess_len == 10 )
								return 106;
							break;
							/* e4 28 .. | mess_len == 52,77,102,127... */
						case 0x28:
							if ( ( ( mess_len - 52 ) % 25 ) == 0 )
								return 106;
							break;
							/* e4 50 xx xx | size == 4 */
						case 0x50:
							if ( mess_len == 12 )
								return 106;
							break;
							/* e4 40 xx xx | size == 48 */
						case 0x40:
							if ( mess_len == 56 )
								return 106;
							break;
						case 0x11:
							if ( mess_len == 38 ) /*acrescentado por thiago*/
								return 106;
							break;
					}
					break;
			}
		}

		if ( mess_len >= 17 )
		{
			switch ( payload[16] )
			{
				case 0x00:
					if ( mess_len < 18 )
						break;
					switch ( payload[17] )
					{
						case 0x00:
							if ( ( ( mess_len == 44 ) && ( get_u16 ( payload, 18 ) == ntohs ( 0x0400 ) ) )
							        //	&& (get_u32(payload, 36) == htonl(0x00000104))
							        || ( ( mess_len == 65 ) && ( get_u16 ( payload, 18 ) == ntohs ( 0x0404 ) ) )
							        //	&& (get_u32(payload, 36) == htonl(0x00000104))
							        || ( ( mess_len == 67 ) && ( get_u16 ( payload, 18 ) == ntohs ( 0x0406 ) ) )
							        //	&& (get_u32(payload, 36) == htonl(0x00000104))
							        || ( ( mess_len >= 40 ) && ( get_u16 ( payload, 18 ) == ntohs ( 0x0402 ) ) &&	( get_u32 ( payload, 36 ) == htonl ( 0x00000104 ) )
							             && ( mess_len != 44 ) && ( mess_len != 52 ) && ( mess_len != 65 ) && ( mess_len != 67 ) && ( mess_len != 211 ) )
							   )
								return 104;
							break;
							/*payload pattern extracted from Blinc signatures paper*/
						case 0x01:
							if ( mess_len == 23 )
								if ( ( get_u32 ( payload, 18 ) == ntohl ( 0x00000000 ) ) && ( get_u8 ( payload, 22 ) == 0x00 ) )
									return PROTO_ID_GNU;
							break;
					}
					break;
					/*payload pattern extracted from Blinc signatures paper*/
				case 0x01:
					if ( mess_len >= 23 )
						if ( ( get_u32 ( payload, 17 ) == ntohl ( 0x01001f00 ) ) && ( get_u16 ( payload, 21 ) == ntohs ( 0x0000 ) ) )
							return PROTO_ID_GNU;
					break;
			}
		}

		if ( mess_len >= 24 )
		{
			switch ( payload[23] )
			{
					/*payload pattern extracted from Blinc signatures paper*/
				case 0x4c: /* L */
					if ( mess_len >= 27 )
						if ( memcmp ( payload + 24, "IME", 3 ) == 0 )
							return PROTO_ID_GNU;
					break;
			}
		}

		switch ( payload[mess_len - 1] )
		{
			case 0x00:
				if ( memcmp ( payload + ( mess_len - 6 ), "KaZaA", 5 ) == 0 )
					return 107;
				break;
		}

		strg = ( unsigned char* ) malloc ( ( mess_len+1 ) *sizeof ( char ) );
		mess_len = coupeEOF ( payload, mess_len, strg );

		if ( mess_len == 0 )
			return PROTO_ID_NONPAYLOAD;	//Nonpayload UDP traffic

		switch ( strg[0] )
		{
			case 0x01:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x01:
					case 0x06:
					case 0x07:
					case 0x15:
					case 0x16:
					case 0x17:
					case 0x18:
					case 0x19:
					case 0x20:
						if ( ( ntohs ( temp2->uh_sport ) < 27000 && ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( bootstrap, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 511;
						}
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
					case 0x02:
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( otherDNS, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 599;
						}
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
					case 0x03:
						if ( regexec ( validcertssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1303;
						}
						if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1302;
						}
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
					default:
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
				}
				break;
			case 0x02:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x01:
					case 0x06:
					case 0x07:
					case 0x15:
					case 0x16:
					case 0x17:
					case 0x18:
					case 0x19:
					case 0x20:
						if ( ( ntohs ( temp2->uh_sport ) < 27000 && ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( bootstrap, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 511;
						}
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
					case 0x02:
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( ( ntohs ( temp2->uh_sport ) == 6667 || ntohs ( temp2->uh_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 303;
						}
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
					default:
						if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 501;
						}
						break;
				}
				break;
			case 0x03:
			case 0x04:
			case 0x05:
			case 0x06:
			case 0x07:
			case 0x08:
			case 0x09:
			case 0x0a:
			case 0x0b:
			case 0x0c:
			case 0x0d:
			case 0x20:
			case 0x21:
			case 0x22:
			case 0x23:
			case 0x24:
			case 0x25:
			case 0x26:
			case 0x27:
			case 0x28:
			case 0x29:
				if ( ( ntohs ( temp2->uh_sport ) == 6667 || ntohs ( temp2->uh_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
				{
					free ( strg );
					return 303;
				}
				break;
			case 0x10:
			case 0x11:
			case 0x12:
			case 0x13:
			case 0x14:
			case 0x15:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x02:
					case 0x0a:
					case 0x0e:
						if ( ( ntohs ( temp2->uh_sport ) < 27000 && ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( nbds, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 510;
						}
						break;
				}
				break;
			case 0x16:
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x03:
						if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1302;
						}
						break;
					case 0x02:
					case 0x0a:
					case 0x0e:
						if ( ( ntohs ( temp2->uh_sport ) < 27000 && ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( nbds, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 510;
						}
						break;
				}
				break;
			case 0x2a: /* * */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x01:
					case 0x02:
						if ( ( ntohs ( temp2->uh_sport ) < 27000 || ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( aim, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 301;
						}
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x05:
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x2b: /* + */
			case 0x2d: /* - */
				if ( regexec ( pop3, ( char* ) strg, 0, NULL, 0 ) == 0 )
				{
					free ( strg );
					return 703;
				}
				break;
			case 0x32: /* 2 */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x32: /* 2 */
						if ( regexec ( smtp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 704;
						}
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
					case 0x33: /* 3 */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
				}
				break;
			case 0x33: /* 3 */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x33: /* 3 */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
				}
				break;
			case 0x34: /* 4 */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x32: /* 2 */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
				}
				break;
			case 0x3a: /* : */
				if ( ( ntohs ( temp2->uh_sport ) == 6667 || ntohs ( temp2->uh_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
				{
					free ( strg );
					return 303;
				}
				break;
			case 0x43: /* C */
			case 0x63: /* c */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x48: /* H */
					case 0x68: /* h */
					case 0x56: /* V */
					case 0x76: /* v */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x4a: /* J */
			case 0x6a: /* j */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4f: /* O */
					case 0x6f: /* o */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x4d: /* M */
			case 0x6d: /* m */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x53: /* S */
					case 0x73: /* s */
						if ( regexec ( msn, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 306;
						}
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x4e: /* N */
			case 0x6e: /* n */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x49: /* I */
					case 0x69: /* i */
						if ( ( ntohs ( temp2->uh_sport ) == 6667 || ntohs ( temp2->uh_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
						{
							free ( strg );
							return 303;
						}
						break;
					case 0x4c: /* L */
					case 0x6c: /* l */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x50: /* P */
			case 0x70: /* p */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x41: /* A */
					case 0x61: /* a */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x51: /* Q */
			case 0x71: /* q */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4e: /* N */
					case 0x6e: /* n */
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
				}
				break;
			case 0x53: /* S */
			case 0x73: /* s */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x53: /* S */
					case 0x73: /* s */
						if ( regexec ( ssh, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 1301;
						}
						break;
					case 0x59: /* Y */
					case 0x79: /* y */
						if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 801;
						}
						break;
				}
				break;
			case 0x55: /* U */
			case 0x75: /* u */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x53: /* S */
					case 0x73: /* s */
						if ( mess_len < 3 )
							break;
						switch ( strg[2] )
						{
							case 0x45: /* E */
							case 0x65: /* e */
								if ( ( ntohs ( temp2->uh_sport ) == 6667 || ntohs ( temp2->uh_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
								{
									free ( strg );
									return 303;
								}
								if ( regexec ( ftp, ( char* ) strg, 0, NULL, 0 ) == 0 )
								{
									free ( strg );
									return 801;
								}
								break;
							case 0x52: /* R */
							case 0x72: /* r */
								if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
								{
									free ( strg );
									return 399;
								}
								break;
						}
						break;
				}
				break;
			case 0x59: /* Y */
			case 0x79: /* y */
				if ( mess_len < 2 )
					break;
				switch ( strg[1] )
				{
					case 0x4d: /* M */
					case 0x6d: /* m */
						if ( regexec ( yahooMess, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 308;
						}
						if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 399;
						}
						break;
					case 0x48: /* H */
					case 0x68: /* h */
					case 0x50: /* P */
					case 0x70: /* p */
						if ( regexec ( yahooMess, ( char* ) strg, 0, NULL, 0 ) == 0 )
						{
							free ( strg );
							return 308;
						}
						break;
				}
				break;
		}

		if ( mess_len >= 2 )
		{
			switch ( strg[1] )
			{
				case 0x01:
					if ( mess_len < 3 )
						break;
					switch ( strg[2] )
					{
						case 0x03:
							if ( regexec ( validcertssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1303;
							}
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
						default:
							if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
							{
								free ( strg );
								return 501;
							}
							break;
					}
					break;
				case 0x02:
					if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					break;
				case 0x16:
					if ( mess_len < 3 )
						break;
					switch ( strg[2] )
					{
						case 0x03:
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
							break;
					}
					break;
			}
		}

		if ( mess_len >= 3 )
		{
			switch ( strg[2] )
			{
				case 0x01:
					if ( mess_len < 4 )
						break;
					switch ( strg[3] )
					{
						case 0x03:
							if ( regexec ( validcertssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1303;
							}
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
						default:
							if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
							{
								free ( strg );
								return 501;
							}
							break;
					}
					break;
				case 0x02:
					if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					if ( regexec ( skypeToSkype, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 1002;
					}
					break;
				case 0x16:
					if ( mess_len < 4 )
						break;
					switch ( strg[3] )
					{
						case 0x03:
							if ( regexec ( ssl, ( char* ) strg, 0, NULL, 0 ) == 0 )
							{
								free ( strg );
								return 1302;
							}
							break;
					}
					break;
			}
		}

		if ( mess_len >= 4 )
		{
			switch ( strg[3] )
			{
				case 0x01:
				case 0x02:
					if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					break;
			}
		}

		if ( mess_len >= 5 )
		{
			switch ( strg[4] )
			{
				case 0x00:
					if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( otherDNS, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 599;
					}
					break;
				case 0x01:
				case 0x02:
					if ( ( ntohs ( temp2->uh_sport ) == 53 || ntohs ( temp2->uh_dport ) == 53 ) && ( regexec ( dns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
					{
						free ( strg );
						return 501;
					}
					break;
			}
		}

		if ( mess_len >= 9 )
		{
			switch ( strg[8] )
			{
				case 0x41: /* A */
				case 0x61: /* a */
					if ( regexec ( otherVideoUDP, ( char* ) strg, 0, NULL, 0 ) == 0 )
					{
						free ( strg );
						return 699;
					}
					break;
			}
		}

		if ( regexec ( msn, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 306;
		}

		if ( regexec ( httpQuicktime, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 602;
		}

		if ( regexec ( httpVideo, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 606;
		}

		if ( regexec ( httpAudio, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 608;
		}

		if ( ( regexec ( http, ( char* ) strg, 0, NULL, 0 ) == 0 ) || ( regexec ( http2, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 201;
		}

		if ( ( ntohs ( temp2->uh_sport ) == 6667 || ntohs ( temp2->uh_dport ) == 6667 ) && ( regexec ( irc, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 303;
		}

		if ( ( ntohs ( temp2->uh_sport ) < 27000 || ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( aim, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 301;
		}

		if ( regexec ( otherChat, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 399;
		}

		if ( regexec ( rtsp, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 601;
		}

		if ( regexec ( netbios, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 506;
		}

		if ( ( ntohs ( temp2->uh_sport ) < 27000 && ntohs ( temp2->uh_dport ) < 27000 ) && ( regexec ( nbns, ( char* ) strg, 0, NULL, 0 ) == 0 ) )
		{
			free ( strg );
			return 509;
		}

		if ( regexec ( skypeOut, ( char* ) strg, 0, NULL, 0 ) == 0 )
		{
			free ( strg );
			return 1001;
		}

		/*		if (isEdku(payload, mess_len))
					return 106;		//edk 22    82->106
				if (isBittorrentu(payload, mess_len))
					return 104;		//BiTtorrent UDP 24    84->104
				if (isKazaau(payload, mess_len))
					return 107;		//kazaa 21     81->107
				if (isGnuu(payload, mess_len))
					return PROTO_ID_GNU;		//gnu 23     83->110
				if (isDcu(payload, mess_len))
					return 105;		//Dc 27              87->105
				if(isEarthu(payload, mess_len))
					return 124;		//EarthStation5 28	88->124

				strg = (unsigned char*)malloc((mess_len+1)*sizeof(char));
				mess_len=coupeEOF(payload, mess_len, strg);
				if (isHLCS(payload, mess_len)) {
					free(strg); return 403; }
				if (isDNS(strg, mess_len) && (ntohs(temp2->uh_sport) == 53 || ntohs(temp2->uh_dport) == 53)) {
					free(strg); return 501;}
				if (isGoBoogyu(payload, mess_len)) {
					free(strg); return 111;} //new, korean peer-to-peer, see netfilter's site
				if (isSoribada(payload, mess_len)) {
					free(strg); return 119;} //new, korean peer-to-peer, see netfilter's site
				if (isOtherDNS(strg, mess_len) && (ntohs(temp2->uh_sport) == 53 || ntohs(temp2->uh_dport) == 53)) {
					free(strg); return 599;}
				if (isMSN(strg, mess_len)) {
					free(strg); return 306;}
				if (isHTTPQuicktime(strg, mess_len)) {
					free(strg); return 602;}
				if (isHTTPVideo(strg, mess_len)) {
					free(strg); return 606;}
				if (isHTTPAudio(strg, mess_len)) {
					free(strg); return 608;}
				if (isHTTP(strg, mess_len)) {
					free(strg); return 201;}
				if (isSMTP(strg, mess_len)) {
					free(strg); return 704;}
				if (isPOP3(strg, mess_len)) {
					free(strg); return 703;}
				if (isIRC(strg, mess_len) && (ntohs(temp2->uh_sport) == 6667 || ntohs(temp2->uh_dport) == 6667)) {
					free(strg); return 303;}
				if (isFTP(strg, mess_len)) {
					free(strg); return 801;}
				if (isYahooMess(strg, mess_len)) {
					free(strg); return 308;}
				if (isAIM(strg, mess_len) && (ntohs(temp2->uh_sport) < 27000 && ntohs(temp2->uh_dport) < 27000)) {
					free(strg); return 301;}
				if (isOtherChat(strg, mess_len)) {
					free(strg); return 399;}
				if (isRTSP(strg, mess_len)) {
					free(strg); return 601;}
				if (isOtherVideoUDP(strg, mess_len)) {
					free(strg); return 699;}
				if (isHLDEATH(strg, mess_len)) {
					free(strg); return 416;}
				if (isHL2DEATH(strg, mess_len)) {
					free(strg); return 407;}
				if (isHL2CS(strg, mess_len)) {
					free(strg); return 415;}
				if (isVALIDCERTSSL(strg, mess_len)) {
					free(strg); return 1303;}
				if (isSSL(strg, mess_len)) {
					free(strg); return 1302;}
				if (isSSH(strg, mess_len)) {
					free(strg); return 1301;}
				if (isNetBios(strg, mess_len)) {
					free(strg); return 506;}
				if (isNbns(strg, mess_len) && (ntohs(temp2->uh_sport) < 27000 && ntohs(temp2->uh_dport) < 27000)) {
					free(strg); return 509;}
				if (isNbds(strg, mess_len) && (ntohs(temp2->uh_sport) < 27000 && ntohs(temp2->uh_dport) < 27000)) {
					free(strg); return 510;}
				if (isBootstrap(strg, mess_len) && (ntohs(temp2->uh_sport) < 27000 && ntohs(temp2->uh_dport) < 27000)) {
					free(strg); return 511;}
				if(isSkypeToSkype(strg, mess_len)) {
					free(strg); return 1002; }
				if(isSkypeOut(strg, mess_len)){
					free(strg); return 1001; }*/
		free ( strg );
		return IPPROTO_UDP;	//Unknown application, under UDP traffic
	}
	else if ( ( ( int ) iph->ip_p ) == IPPROTO_ICMP )
	{
		return IPPROTO_ICMP;	//icmp traffic
	}
	//others
	return 19;

}

u_short CClassifier::getID(const struct ip *iph, const u_short ipLen, const u_short src_port, const u_short dst_port )
{
	unsigned char* payLoad = getPayload(iph);
	u_short mess_len = getPayloadLen(iph, ipLen);
	if (isBittorrent(payLoad, mess_len))
	{
		return PROTO_ID_BITTORRENT;
	}
	
	if (src_port > 1024 && dst_port > 1024)
	{
		return PROTO_ID_UNKNOWN;
	}
	
	switch (src_port)
	{
	case IPPORT_FTP:
		return PROTO_ID_FTP;
	case IPPORT_FTP_DATA:
		return PROTO_ID_FTP;
	case IPPORT_HTTPS:
		return PROTO_ID_HTTP;
	case 80:  /* HTTP */
		return PROTO_ID_HTTP;
	case IPPORT_SMTP:
		return PROTO_ID_SMTP;
	case 465: 
		return PROTO_ID_SMTP;
	case IPPORT_POP3:
		return PROTO_ID_POP3;
	case 993:
		return PROTO_ID_POP3;
	}
	switch (dst_port)
	{
	case IPPORT_FTP:
		return PROTO_ID_FTP;
	case IPPORT_FTP_DATA:
		return PROTO_ID_FTP;
	case IPPORT_HTTPS:
		return PROTO_ID_HTTP;
	case 80:  /* HTTP */
		return PROTO_ID_HTTP;
	case IPPORT_SMTP:
		return PROTO_ID_SMTP;
	case 465: 
		return PROTO_ID_SMTP;
	case IPPORT_POP3:
		return PROTO_ID_POP3;
	case 993:
		return PROTO_ID_POP3;
	}
	return PROTO_ID_UNKNOWN;
	
}

//Earth
u_short CClassifier::isEarthu ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;
	if ( mess_len < 4 )
		return 0;
	t += 2;
	if ( ( t[0] == 0xcf ) && ( t[1] == 64 ) )
		return 1;
	return 0;
}

/**
 * MP2P
 */
u_short CClassifier::isMP2Pu ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;
	if ( mess_len < 5 )
		return 0;
	t += 2;
	if ( ( ( t[0] == 0x00 ) && ( t[1] == 0x00 ) && ( t[2] == 0x00 ) ) && ( ( t[3] == 0x00 ) || ( t[3] == 0x01 ) || ( t[3] == 0x02 ) || ( t[3] == 0x03 ) || ( t[3] == 0x04 ) ) )
		return 1;
	return 0;
}

/**
 *  OpenNap/Napster for TCP
 */
u_short CClassifier::isNap ( unsigned char *payload, const u16 mess_len )
{
	/*unsigned char *t = (unsigned char *) payload;

	if (mess_len < 5)
	return 0;
	t += 2;
	if (t[0] == 0x31)
	return 1;
	if ((t[0] == 0x00) && (t[1] == 0x00) && (t[2] == 0xca)
	&& (t[3] == 0x00))
	return 1;
	if ((t[0] == 0xc8) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0xc9) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0xcb) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0xcc) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0x00) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0xd6) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0x02) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0x03) && (t[1] == 0x00))
	return 1;
	if ((t[0] == 0x06) && (t[1] == 0x00))
	return 1;
	if (mess_len < 6)
	return 0;
	if ((memcmp(t, "SEND", 4) == 0) || (memcmp(t, "GET", 3) == 0))*/
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( napster, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}


/**
 * Earth Station 5 for TCP//Identificacao do frames UDP
 */
u_short CClassifier::isEarthS5 ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;

	if ( mess_len < 15 )
		return 0;
	if ( memcmp ( t, "GET /$$$$$$$$$/", 15 ) == 0 )
		return 1;		//Default TCP message
	else
		return 0;
}

/* MP2P for TCP*/
u_short CClassifier::isMP2P ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;

	if ( mess_len < 4 )
		return 0;
	if ( ( memcmp ( t, "GO!!", 4 ) == 0 ) || ( memcmp ( t, "MD5", 3 ) == 0 )
	        || ( ( memcmp ( t, "SIZ", 3 ) == 0 ) && ( t[3] == 0x20 ) )
	        || ( ( memcmp ( t, "STR", 3 ) == 0 ) && ( t[3] == 0x20 ) ) )
		return 1;		//Mess padrao para TCP
	else
		return 0;
}

/*Search for UDP KaZaA commands*/
u_short CClassifier::isKazaau ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;
	if ( mess_len < 6 )
		return 0;
	/*payload pattern extracted from Blinc signatures paper*/
	if ( t[0] == 0x27 && t[1] == 0x00 && t[2] == 0x00 && t[3] == 0x00 && ( t[4] == 0x29 || t[4] == 0xa9 ) && t[5] == 0x80 )
		return 1;
	if ( t[0] == 0x28 && t[1] == 0x00 && t[2] == 0x00 && t[3] == 0x00 && ( t[4] == 0x29 || t[4] == 0xa9 ) && t[5] == 0x00 )
		return 1;
	if ( t[0] == 0x29 && t[1] == 0x00 && t[2] == 0x00 && t[3] == 0x00 && ( t[4] == 0x29 || t[4] == 0xa9 ) )
		return 1;
	/*fim*/
	if ( t[mess_len - 1] == 0x00 )
	{
		t += ( mess_len - 6 );
		if ( memcmp ( t, "KaZaA", 5 ) == 0 )
			return 1;
	}

	return 0;
}				/*udp_search_kazaa */

/*check for gnutella get commands and other typical data*/
u_short CClassifier::isGnu_all ( const unsigned char *payload, const u16 mess_len )
{

	if ( mess_len < 2 )
		return 0;
	if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
	{

		if ( mess_len < 17 )
			return 0;
		if ( memcmp ( payload, "GNUTELLA CONNECT/", 17 ) == 0 )
			return 1;
		if ( memcmp ( payload, "GNUTELLA/", 9 ) == 0 )
			return 1;


		if ( ( memcmp ( payload, "GET /get/", 9 ) == 0 )
		        || ( memcmp ( payload, "GET /uri-res/", 13 ) == 0 ) )
		{
			u16 c = 8;
			if ( mess_len < 30 )
				return 0;
			const u16 end = mess_len - 22;
			while ( c < end )
			{
				if ( payload[c] == 0x0a && payload[c + 1] == 0x0d
				        && ( ( memcmp ( &payload[c + 2], "X-Gnutella-", 11 ) == 0 )
				             || ( memcmp ( &payload[c + 2], "X-Queue:", 8 ) == 0 ) ) )
					return 1;
				c++;
			}
		}
	}
	return 0;
}

/*intensive but slower check for all direct connect packets*/
u_short CClassifier::isDc_all ( const unsigned char *payload, const u16 mess_len )
{
	//    unsigned char *t = haystack;

	if ( mess_len < 1 )
		return 0;
	if ( payload[0] == 0x24 && payload[mess_len - 1] == 0x7c )
	{
		const unsigned char *t = &payload[1];
		/* Client-Hub-Protocol */
		if ( mess_len < 6 )
			return 0;
		if ( memcmp ( t, "Lock ", 5 ) == 0 )
			return 1;
		/* Client-Client-Protocol, some are already recognized by client-hub (like lock) */
		if ( mess_len < 8 )
			return 0;
		if ( memcmp ( t, "MyNick ", 7 ) == 0 )
			return 1;
	}
	return 0;
}

/*Search for UDP BitTorrent commands*/
u_short CClassifier::isBittorrentu ( const unsigned char *payload, const u16 mess_len )
{
	switch ( mess_len )
	{
		case 24:
			/* ^ 00 00 04 17 27 10 19 80 */
			if ( ( ntohl ( get_u32 ( payload, 8 ) ) == 0x00000417 )
			        && ( ntohl ( get_u32 ( payload, 12 ) ) == 0x27101980 ) )
				return 1;
			break;
		case 44:
			//                      if (get_u32(payload, 16) == __constant_htonl(0x00000400) && get_u32(haystack, 36) == __constant_htonl(0x00000104))
			if ( get_u32 ( payload, 16 ) == htonl ( 0x00000400 )
			        && get_u32 ( payload, 36 ) == htonl ( 0x00000104 ) )
				return 1;
			//                      if (get_u32(haystack, 16) == __constant_htonl(0x00000400))
			if ( get_u32 ( payload, 16 ) == htonl ( 0x00000400 ) )
				return 1;
			break;
		case 65:
			//                      if (get_u32(haystack, 16) == __constant_htonl(0x00000404) && get_u32(haystack, 36) == __constant_htonl(0x00000104))
			if ( get_u32 ( payload, 16 ) == htonl ( 0x00000404 )
			        && get_u32 ( payload, 36 ) == htonl ( 0x00000104 ) )
				return 1;
			//                      if (get_u32(haystack, 16) == __constant_htonl(0x00000404))
			if ( get_u32 ( payload, 16 ) == htonl ( 0x00000404 ) )
				return 1;
			break;
		case 67:
			//                      if (get_u32(haystack, 16) == __constant_htonl(0x00000406) && get_u32(haystack, 36) == __constant_htonl(0x00000104))
			if ( get_u32 ( payload, 16 ) == htonl ( 0x00000406 )
			        && get_u32 ( payload, 36 ) == htonl ( 0x00000104 ) )
				return 1;
			//                      if (get_u32(haystack, 16) == __constant_htonl(0x00000406))
			if ( get_u32 ( payload, 16 ) == htonl ( 0x00000406 ) )
				return 1;
			break;
		case 211:
			//                      if (get_u32(haystack, 8) == __constant_htonl(0x00000405))
			if ( get_u32 ( payload, 8 ) == htonl ( 0x00000405 ) )
				return 1;
			break;
		case 29:
			//                      if ((get_u32(haystack, 8) == __constant_htonl(0x00000401)))
			if ( ( get_u32 ( payload, 8 ) == htonl ( 0x00000401 ) ) )
				return 1;
			break;
		case 52:
			//                      if (get_u32(haystack,8)  == __constant_htonl(0x00000827) &&
			//                      get_u32(haystack,12) == __constant_htonl(0x37502950))
			if ( get_u32 ( payload, 8 ) == htonl ( 0x00000827 ) &&
			        get_u32 ( payload, 12 ) == htonl ( 0x37502950 ) )
				return 1;
			break;
		default:
			/* this packet does not have a constant size */
			//                      if (packet_len >= 40 && get_u32(haystack, 16) == __constant_htonl(0x00000402) && get_u32(haystack, 36) == __constant_htonl(0x00000104))
			if ( mess_len >= 40 && get_u32 ( payload, 16 ) == htonl ( 0x00000402 )
			        && get_u32 ( payload, 36 ) == htonl ( 0x00000104 ) )
				return 1;
			break;
	}

	/* some extra-bitcomet rules:
	 * "d1:" [a|r] "d2:id20:"
	 */
	if ( mess_len > 30 && get_u8 ( payload, 8 ) == 'd'
	        && get_u8 ( payload, 9 ) == '1' && get_u8 ( payload, 10 ) == ':' )
	{
		if ( get_u8 ( payload, 11 ) == 'a' || get_u8 ( payload, 11 ) == 'r' )
		{
			if ( memcmp ( payload + 12, "d2:id20:", 8 ) == 0 )
				return 1;
		}
	}
#if 0
	/* bitlord rules */
	/* packetlen must be bigger than 40 */
	/* first 4 bytes are zero */
	if ( mess_len > 40 && get_u32 ( payload, 8 ) == 0x00000000 )
	{
		/* first rule: 00 00 00 00 01 00 00 xx xx xx xx 00 00 00 00 */
		if ( get_u32 ( payload, 12 ) == 0x00000000 &&
		        get_u32 ( payload, 16 ) == 0x00010000 &&
		        get_u32 ( payload, 24 ) == 0x00000000 )
			return 1;

		/* 00 01 00 00 0d 00 00 xx xx xx xx 00 00 00 00 */
		if ( get_u32 ( payload, 12 ) == 0x00000001 &&
		        get_u32 ( payload, 16 ) == 0x000d0000 &&
		        get_u32 ( payload, 24 ) == 0x00000000 )
			return 1;


	}
#endif

	return 0;
}				/*udp_search_bit */

/* search for waste */
u_short CClassifier::isWaste ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len >= 9 && memcmp ( payload, "GET.sha1:", 9 ) == 0 )
		return 1;

	return 0;
}

u_short CClassifier::isXdcc ( const unsigned char *payload, const u16 mess_len )
{
	/* search in small packets only */
	if ( mess_len > 20 && mess_len < 200 && payload[mess_len - 1] == 0x0a
	        && payload[mess_len - 2] == 0x0d
	        && memcmp ( payload, "PRIVMSG ", 8 ) == 0 )
	{

		u16 x = 10;
		const u16 end = mess_len - 13;

		/* is seems to be a irc private massage, chedck for xdcc command */
		while ( x < end )
		{
			if ( payload[x] == ':' )
			{
				if ( memcmp ( &payload[x + 1], "xdcc send #", 11 ) == 0 )
					return 1;
			}
			x++;
		}
	}
	return 0;
}

u_short CClassifier::isKazaa_all ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len < 2 )
		return 0;
	if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
	{

		if ( mess_len < 5 )
			return 0;
		if ( memcmp ( payload, "GIVE ", 5 ) == 0 )
			return 1;

		if ( memcmp ( payload, "GET /", 5 ) == 0 )
		{
			u16 c = 8;
			if ( mess_len < 35 )
				return 0;
			const u16 end = mess_len - 22;
			while ( c < end )
			{
				if ( payload[c] == 0x0a && payload[c + 1] == 0x0d
				        &&
				        ( ( memcmp ( &payload[c + 2], "X-Kazaa-Username: ", 18 ) ==
				            0 )
				          ||
				          ( memcmp
				            ( &payload[c + 2], "User-Agent: PeerEnabler/",
				              24 ) == 0 ) ) )
					return 1;
				c++;
			}
		}
	}
	return 0;
}

u_short CClassifier::isMute ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len == 209 || mess_len == 345 || mess_len == 473
	        || mess_len == 609 || mess_len == 1121 )
	{
		//printk(KERN_DEBUG "size hit: %u",size);
		if ( memcmp ( payload, "PublicKey: ", 11 ) == 0 )
		{
			return 1;

			/*			if (memcmp(t+size-14,"\x0aEndPublicKey\x0a",14) == 0)
			{
				printk(KERN_DEBUG "end pubic key hit: %u",size);

			}*/
		}
	}
	return 0;
}

u_short CClassifier::isWinMX ( const unsigned char *payload, const u16 mess_len )
{
	if ( ( ( mess_len ) == 4 ) && ( memcmp ( payload, "SEND", 4 ) == 0 ) )
		return 1;
	if ( ( ( mess_len ) == 3 ) && ( memcmp ( payload, "GET", 3 ) == 0 ) )
		return 1;
	if ( mess_len < 10 )
		return 0;

	if ( ( memcmp ( payload, "SEND", 4 ) == 0 )
	        || ( memcmp ( payload, "GET", 3 ) == 0 ) )
	{
		u16 c = 4;
		const u16 end = mess_len - 2;
		u8 count = 0;
		while ( c < end )
		{
			if ( payload[c] == 0x20 && payload[c + 1] == 0x22 )
			{
				c++;
				count++;
				if ( count >= 2 )
					return 1;
			}
			c++;
		}
	}

	if ( mess_len == 149 && payload[0] == '8' )
	{
		if ( get_u32 ( payload, 17 ) == 0 && get_u32 ( payload, 21 ) == 0
		        && get_u32 ( payload, 25 ) == 0 &&
		        //get_u16(payload,39) == 0 && get_u16(payload,135) == __constant_htons(0x7edf) && get_u16(payload,147) == __constant_htons(0xf792))
		        get_u16 ( payload, 39 ) == 0
		        && get_u16 ( payload, 135 ) == htons ( 0x7edf )
		        && get_u16 ( payload, 147 ) == htons ( 0xf792 ) )
		{
			return 1;
		}
	}
	return 0;
}				/*search_winmx */


u_short CClassifier::isKazaa ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len < 11 )
		return 0;
	if ( ( payload[mess_len-2] == 0x0d ) && ( payload[mess_len-1] == 0x0a ) && memcmp ( payload, "GET /.hash=", 11 ) == 0 )
		return 1;
	/*    if (mess_len < 11)
	return 0;
	if ((payload[mess_len - 2] == 0x0d) && (payload[mess_len - 1] == 0x0a)
	&& memcmp(payload, "GET /.hash=", 11) == 0)
	return 1;
	 */
	/*payload pattern extracted from Blinc signatures paper*/
	unsigned char *t = ( unsigned char * ) payload;
	if ( mess_len < 28 )
		return 0;
	t += 14;
	if ( memcmp ( t, "Content-Range:", 14 ) == 0 )
		return 1;
	if ( mess_len < 29 )
		return 0;
	t += 3;
	if ( memcmp ( t, "Retry-After:", 12 ) == 0 )
		return 1;
	/*end of payload pattern*/
	return 0;
}

u_short CClassifier::isBittorrent ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len > 20 )
	{
		/* test for match 0x13+"BitTorrent protocol" */
		if ( payload[0] == 0x13 )
		{
			if ( memcmp ( payload + 1, "BitTorrent protocol", 19 ) == 0 )
				return 1;
		}

		/* get tracker commandos, all starts with GET /
		 * then it can follow: scrape| announce
		 * and then ?hash_info=
		 */
		if ( memcmp ( payload, "GET /", 5 ) == 0 )
		{
			/*payload pattern extracted from Blinc signatures paper*/
			if ( memcmp ( payload + 5, "torrents/", 9 ) == 0 )
				return 1;
			/*end of payload pattern*/
			/* message scrape */
			if ( mess_len < 22 )
				return 0;
			if ( memcmp ( payload + 5, "scrape?info_hash=", 17 ) == 0 )
				return 1;
			if ( mess_len < 24 )
				return 0;
			/* message announce */
			if ( memcmp ( payload + 5, "announce?info_hash=", 19 ) == 0 )
				return 1;
		}
	}
	else
	{
		/* bitcomet encryptes the first packet, so we have to detect another
		 * one later in the flow */
		/* first try failed, too many missdetections */
		//if ( size == 5 && get_u32(t,0) == __constant_htonl(1) && t[4] < 3) return (IPP2P_BIT * 100 + 3);

		/* second try: block request packets */
		//              if ( mess_len == 17 && get_u32(payload,0) == __constant_htonl(0x0d) && payload[4] == 0x06 && get_u32(payload,13) == __constant_htonl(0x4000) ) return 1;
		if ( mess_len == 17 && get_u32 ( payload, 0 ) == htonl ( 0x0d )
		        && payload[4] == 0x06 && get_u32 ( payload, 13 ) == htonl ( 0x4000 ) )
			return 1;
	}
	/*payload pattern extracted from Blinc signatures paper*/
	if ( mess_len < 7 )
		return 0;
	if ( payload[0] == 0x00 && payload[1] == 0x00 && payload[5] == 0x00 && payload[6] == 0x00 )
	{
		if ( payload[2] == 0x40 && payload[3] == 0x09 && payload[4] == 0x07 )
			return 1;
		if ( payload[2] == 0x00 && payload[3] == 0x0d && payload[4] == 0x06 )
			return 1;
		if ( payload[2] == 0x00 && payload[3] == 0x05 && payload[4] == 0x04 )
			return 1;
	}
	/*end of payload pattern*/

	return 0;
}

/*intensive but slower check for all direct connect packets*/
u_short CClassifier::isDc ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len < 1 )
		return 0;
	if ( payload[0] != 0x24 )
		return 0;
	else
	{
		/*payload pattern extracted from Blinc signatures paper*/
		if ( mess_len < 4 )
			return 0;
		if ( ( memcmp ( &payload[1], "Get", 3 ) == 0 ) || ( memcmp ( &payload[1], "Dir", 3 ) == 0 ) )
			return 1;
		if ( mess_len < 5 )
			return 0;
		if ( memcmp ( &payload[1], "Key ", 4 ) == 0 )
			return 1;
		/*end of payload pattern*/
		if ( mess_len < 6 )
			return 0;
		if ( memcmp ( &payload[1], "Send|", 5 ) == 0 )
			return 1;
		/*payload pattern extracted from Blinc signatures paper*/
		if ( ( memcmp ( &payload[1], "Hello", 5 ) == 0 ) || ( memcmp ( &payload[1], "Quit ", 5 ) == 0 ) )
			return 1;
		if ( mess_len < 7 )
			return 0;
		if ( memcmp ( &payload[1], "MyINFO", 6 ) == 0 )
			return 1;
		if ( mess_len < 8 )
			return 0;
		if ( ( memcmp ( &payload[1], "RevConn", 7 ) == 0 ) || ( memcmp ( &payload[1], "HubName", 7 ) == 0 ) ||
		        ( memcmp ( &payload[1], "Search ", 7 ) == 0 ) )
			return 1;
		if ( mess_len < 9 )
			return 0;
		if ( ( memcmp ( &payload[1], "ConnectT", 8 ) == 0 ) || ( memcmp ( &payload[1], "Supports", 8 ) == 0 ) ||
		        ( memcmp ( &payload[1], "Version ", 8 ) == 0 ) )
			return 1;
		/*end of payload pattern*/
		else
			return 0;
	}
}

/*Search for appleJuice commands*/
u_short CClassifier::isApple ( const unsigned char *payload, const u16 mess_len )
{
	if ( ( mess_len > 7 ) && ( payload[6] == 0x0d ) && ( payload[7] == 0x0a )
	        && ( memcmp ( payload, "ajprot", 6 ) == 0 ) )
		return 1;

	return 0;
}

/*fast check for edonkey file segment transfer command*/
u_short CClassifier::isEdk ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len < 1 )
		return 0;
	if ( payload[0] != 0xe3 )
		return 0;
	else
	{
		if ( mess_len < 5 )
			return 0;
		if ( payload[5] == 0x47 )
			return 1;
		return 0;
	}
}

/*intensive but slower search for some edonkey packets including size-check*/
u_short CClassifier::isEdk_all ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len < 1 )
		return 0;
	if ( payload[0] != 0xe3 )
		return 0;
	else
	{
		//t += head_len;
		if ( mess_len < 5 )
			return 0;
		const u16 cmd = get_u16 ( payload, 1 );
		if ( cmd == ( mess_len - 5 ) )
		{
			switch ( payload[5] )
			{
				case 0x01:
					return 1;	/*Client: hello or Server:hello */
				case 0x4c:
					return 1;	/*Client: Hello-Answer */
			}
		}
		return 0;
	}
}

u_short CClassifier::isGnu ( const unsigned char *payload, const u16 mess_len )
{
	if ( mess_len < 9 )
		return 0;
	if ( ( payload[mess_len - 2] == 0x0d ) && ( payload[mess_len - 1] == 0x0a ) )
	{
		if ( memcmp ( payload, "GET /get/", 9 ) == 0 )
			return 1;
		if ( mess_len < 13 )
			return 0;
		if ( memcmp ( payload, "GET /uri-res/", 13 ) == 0 )
			return 1;
	}
	/*payload pattern extracted from Blinc signatures paper*/
	if ( memcmp ( payload, "GNUTELLA", 8 ) == 0 )
		return 1;
	if ( memcmp ( payload, "X-", 2 ) == 0 )
	{
		if ( ( memcmp ( payload + 2, "Query", 5 ) == 0 ) || ( memcmp ( payload + 2, "Guess", 5 ) == 0 ) || ( memcmp ( payload + 2, "Ultrap", 6 ) == 0 ) ||
		        ( memcmp ( payload + 2, "Ext-", 4 ) == 0 ) || ( memcmp ( payload + 2, "Try-", 4 ) == 0 ) || ( memcmp ( payload + 2, "Degree", 6 ) == 0 ) ||
		        ( memcmp ( payload + 2, "Lo", 2 ) == 0 ) || ( memcmp ( payload + 2, "Max-", 4 ) == 0 ) || ( memcmp ( payload + 2, "Version", 7 ) == 0 ) ||
		        ( memcmp ( payload + 2, "Dynami", 6 ) == 0 ) )
			return 1;
	}
	if ( mess_len < 11 )
		return 0;
	if ( ( memcmp ( payload, "Server: Mor", 11 ) == 0 ) || ( memcmp ( payload, "Server: Lim", 11 ) == 0 ) )
		return 1;
	if ( mess_len < 16 )
		return 0;
	if ( ( memcmp ( payload, "User-Agent: Lime", 16 ) == 0 ) || ( memcmp ( payload, "Vendor-Message: ", 16 ) == 0 ) )
		return 1;
	if ( memcmp ( payload, "HTTP/1.1 503 ", 13 ) == 0 )
	{
		if ( ( memcmp ( payload + 13, "Que", 3 ) == 0 ) || ( memcmp ( payload + 13, "Ful", 3 ) == 0 ) || ( memcmp ( payload + 13, "Not", 3 ) == 0 ) )
			return 1;
	}
	if ( mess_len < 44 )
		return 0;
	unsigned char *t = ( unsigned char * ) payload;
	t += 33;
	if ( memcmp ( t, "Busy Queued", 11 ) == 0 )
		return 1;
	/*end of payload pattern*/
	return 0;
}

/*Search for SoulSeek commands*/
u_short CClassifier::isSoul ( const unsigned char *payload, const u16 mess_len )
{
	/* match: xx xx xx xx | xx = sizeof(payload) - 4 */
	if ( mess_len < 9 ) return 0;
	if ( get_u32 ( payload, 0 ) == ( mess_len - 4 ) )
	{
		const __u32 m = get_u32 ( payload, 4 );
		/* match 00 yy yy 00, yy can be everything */
		if ( get_u8 ( payload, 4 ) == 0x00 && get_u8 ( payload, 7 ) == 0x00 )
		{
			return 1;
		}
		/* next match: 01 yy 00 00 | yy can be everything */
		if ( get_u8 ( payload, 4 ) == 0x01 && get_u16 ( payload, 6 ) == 0x0000 )
		{
			return 1;
		}

		/* other soulseek commandos are: 1-5,7,9,13-18,22,23,26,28,35-37,40-46,50,51,60,62-69,91,92,1001 */
		/* try to do this in an intelligent way */
		/* get all small commandos */
		switch ( m )
		{
			case 7:
			case 9:
			case 22:
			case 23:
			case 26:
			case 28:
			case 50:
			case 51:
			case 60:
			case 91:
			case 92:
			case 1001:
				return 1;
		}

		if ( m > 0 && m < 6 )
		{
			return 1;
		}
		if ( m > 12 && m < 19 )
		{
			return 1;
		}

		if ( m > 34 && m < 38 )
		{
			return 1;
		}

		if ( m > 39 && m < 47 )
		{
			return 1;
		}

		if ( m > 61 && m < 70 )
		{
			return 1;
		}

	}

	/* match 14 00 00 00 01 yy 00 00 00 STRING(YY) 01 00 00 00 00 46|50 00 00 00 00 */
	/* without size at the beginning !!! */
	if ( get_u32 ( payload, 0 ) == 0x14 && get_u8 ( payload, 4 ) == 0x01 )
	{
		__u32 y = get_u32 ( payload, 5 );
		/* we need 19 chars + string */
		if ( ( y + 19 ) <= ( mess_len ) )
		{
			const unsigned char *w = payload + 9 + y;
			if ( get_u32 ( w, 0 ) == 0x01
			        && ( get_u16 ( w, 4 ) == 0x4600 || get_u16 ( w, 4 ) == 0x5000 )
			        && get_u32 ( w, 6 ) == 0x00 )
				return 1;
		}
	}
	return 0;
}


/*Ares for TCP*/
u_short CClassifier::isAres ( const unsigned char *payload, const u16 mess_len )
{
	/* all ares packets start with  */
	/*payload pattern extracted from Blinc signatures paper*/
	if ( mess_len < 5 )
		return 0;
	if ( memcmp ( payload, "PUSH ", 5 ) == 0 )
		return 1;
	/*end of payload pattern*/
	if ( mess_len < 6 )
		return 0;
	if ( payload[1] == 0 && ( mess_len - payload[0] ) == 3 )
	{
		switch ( payload[2] )
		{
			case 0x5a:
				/* ares connect */
				if ( mess_len == 6 && payload[5] == 0x05 )
					return 1;
				break;
			case 0x09:
				/* ares search, min 3 chars --> 14 bytes
				 * lets define a search can be up to 30 chars --> max 34 bytes
				 */
				if ( mess_len >= 14 && mess_len <= 34 )
					return 1;
				break;
		}
	}
	/*payload pattern extracted from Blinc signatures paper*/
	if ( mess_len < 9 )
		return 0;
	if ( memcmp ( payload, "GET hash:", 9 ) == 0 )
		return 1;
	if ( memcmp ( payload, "GET sha1:", 9 ) == 0 )
		return 1;
	if ( mess_len < 16 )
		return 0;
	if ( memcmp ( payload, "HTTP/1.1 503 Bus", 16 ) == 0 )
		return 1;
	/*end of payload pattern*/

	return 0;
}




/**
 * eDonkey for UDP
 */
u_short CClassifier::isEdku ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;

	if ( mess_len < 10 )
		return 0;
	t += 8;
	switch ( t[0] )
	{
		case 0xe3:
		{
			/*edonkey */
			switch ( t[1] )
			{
					/* client -> server status request */
				case 0x96:
					if ( mess_len == 14 )
						return 1;
					break;
					/* server -> client status request */
				case 0x97:
					if ( mess_len == 42 )
						return 1;
					break;
					/* server description request */
					/* e3 2a ff f0 .. | size == 6 */
					//                                      case 0xa2: if ( (mess_len == 14) && ( get_u16(t,2) == __constant_htons(0xfff0) ) ) return 1;
				case 0xa2:
					if ( ( mess_len == 14 ) && ( get_u16 ( t, 2 ) == htons ( 0xfff0 ) ) )
						return 1;
					break;
					/* server description response */
					/* e3 a3 ff f0 ..  | size > 40 && size < 200 */
					//case 0xa3: return ((IPP2P_EDK * 100) + 53);
					//      break;
				case 0x9a:
					if ( mess_len == 26 )
						return 1;
					break;

				case 0x92:
					if ( mess_len == 18 )
						return 1;
					break;
			}
			break;
		}
		case 0xe4:
		{
			switch ( t[1] )
			{
					/* e4 20 .. | size == 43 */
				case 0x20:
					if ( ( mess_len == 43 ) && ( t[2] != 0x00 ) && ( t[34] != 0x00 ) )
						return 1;
					break;
					/* e4 00 .. 00 | size == 35 ? */
				case 0x00:
					if ( ( mess_len == 35 ) && ( t[26] == 0x00 ) )
						return 1;
					break;
					/* e4 10 .. 00 | size == 35 ? */
				case 0x10:
					if ( ( mess_len == 35 ) && ( t[26] == 0x00 ) )
						return 1;
					break;
					/* e4 18 .. 00 | size == 35 ? */
				case 0x18:
					if ( ( mess_len == 35 ) && ( t[26] == 0x00 ) )
						return 1;
					break;
					/* e4 52 .. | size = 44 */
				case 0x52:
					if ( mess_len == 44 )
						return 1;
					break;
					/* e4 58 .. | size == 6 */
				case 0x58:
					if ( mess_len == 14 )
						return 1;
					break;
					/* e4 59 .. | size == 2 */
				case 0x59:
					if ( mess_len == 10 )
						return 1;
					break;
					/* e4 28 .. | mess_len == 52,77,102,127... */
				case 0x28:
					if ( ( ( mess_len - 52 ) % 25 ) == 0 )
						return 1;
					break;
					/* e4 50 xx xx | size == 4 */
				case 0x50:
					if ( mess_len == 12 )
						return 1;
					break;
					/* e4 40 xx xx | size == 48 */
				case 0x40:
					if ( mess_len == 56 )
						return 1;
					break;
				case 0x11:
					if ( mess_len == 38 ) /*acrescentado por thiago*/
						return 1;
					break;
			}
			break;
		}
	}				/* end of switch (t[0]) */
	return 0;
}				/*udp_search_edk */


//Gnutella
u_short CClassifier::isGnuu ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;

	if ( mess_len < 11 )
		return 0;
	/*payload pattern extracted from Blinc signatures paper*/
	if ( t[0] == 0x47 && t[1] == 0x4e && t[2] == 0x44 )
		return 1;
	/*end of payload pattern*/
	t += 8;
	if ( memcmp ( t, "GND", 3 ) == 0 )
		return 1;
	if ( mess_len < 18 )
		return 0;
	if ( memcmp ( t, "GNUTELLA ", 9 ) == 0 )
		return 1;
	/*payload pattern extracted from Blinc signatures paper*/
	if ( mess_len < 23 )
		return 0;
	t += 8;
	if ( t[0] == 0x00 && t[1] == 0x01 && t[2] == 0x00 && t[3] == 0x00 && t[4] == 0x00 && t[5] == 0x00 && t[6] == 0x00 && mess_len == 23 )
		return 1;
	if ( t[0] == 0x01 && t[1] == 0x01 && t[2] == 0x00 && t[3] == 0x1f && t[4] == 0x00 && t[5] == 0x00 && t[6] == 0x00 )
		return 1;
	if ( mess_len < 27 )
		return 0;
	t += 7;
	if ( memcmp ( t, "LIME ", 4 ) == 0 )
		return 1;
	/*end of payload pattern*/
	return 0;
}

//DirectConnect UDP
u_short CClassifier::isDcu ( const unsigned char *payload, const u16 mess_len )
{
	unsigned char *t = ( unsigned char * ) payload;
	if ( mess_len < 9 )
		return 0;
	if ( ( * ( t + 8 ) == 0x24 ) && ( * ( t + mess_len - 1 ) == 0x7c ) )
	{
		t += 8;
		if ( mess_len < 11 )
			return 0;
		if ( memcmp ( t, "SR ", 3 ) == 0 )
			return 1;
		if ( mess_len < 13 )
			return 0;
		if ( memcmp ( t, "Ping ", 5 ) == 0 )
			return 1;
	}
	/*payload pattern extracted from Blinc signatures paper*/
	if ( memcmp ( t, "$SR ", 4 ) == 0 )
		return 1;
	if ( memcmp ( t, "$Pin", 4 ) == 0 )
		return 1;
	/*end of payload pattern*/
	return 0;
}

u_short CClassifier::isGoBoogy ( unsigned char *payload, const u16 mess_len )
{
	if ( ( regexec ( goBoogy, ( char* ) payload, 0, NULL, 0 ) ==0 ) )
	{
		//free(strg);
		return 1;
	}
	printf ( "ttt\n" );
	if ( mess_len < 5 )
		return 0;
	unsigned char *t = ( unsigned char * ) payload;
	if ( memcmp ( t, "boogy", 5 ) == 0 )
		return 1;
	if ( mess_len < 7 )
		return 0;
	if ( memcmp ( t, "goboogy", 7 ) == 0 )
		return 1;
	return 0;
}

u_short CClassifier::isGoBoogyu ( unsigned char *payload, const u16 mess_len )
{

	if ( ( regexec ( goBoogy, ( char* ) payload, 0, NULL, 0 ) ==0 ) )
	{
		//free(strg);
		return 1;
	}
	return 0;
}

u_short CClassifier::isHTTP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( ( regexec ( http, ( char* ) payload, 0, NULL, 0 ) ==0 ) )
	{
		//free(strg);
		return 1;
	}
	if ( regexec ( http2, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isAIM ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( aim, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isIRC ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( irc, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isMSN ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( msn, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isYahooMess ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( yahooMess, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isHLCS ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( hlcs, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isHL2DEATH ( const unsigned char *payload, const u16 mess_len )
{

	return 0;
}

u_short CClassifier::isHL2CS ( const unsigned char *payload, const u16 mess_len )
{

	return 0;
}

u_short CClassifier::isHLDEATH ( const unsigned char *payload, const u16 mess_len )
{

	/*char* t = payload;
	if(mess_len<3) return 0;
	t = t + 1;
	if(memcmp(t, "\x00\x00", 2)) {
		return 1;

	} t = t + 1;
	if(mess_len<4) return 0;
	if(memcmp(t, "\x00\x00", 2)) {
		return 1;

	}
	//     if(memcmp(payload, "\xff\xff\xff\xff", 4) == 0) {
	//     	return 1;
	// 	}*/
	return 0;
}

u_short CClassifier::isQUAKE3 ( const unsigned char *payload, const u16 mess_len )
{

	return 0;
}

u_short CClassifier::isDNS ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( dns, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isRTSP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( rtsp, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isHTTPQuicktime ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( httpQuicktime, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isHTTPVideo ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( httpVideo, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isHTTPAudio ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( httpAudio, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isPOP3 ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( pop3, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isSMTP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( smtp, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isFTP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';

	if ( regexec ( ftp, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isSkypeOut ( unsigned char *payload, const u16 mess_len )
{

	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( skypeOut, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isSkypeToSkype ( unsigned char *payload, const u16 mess_len )
{

	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( skypeToSkype, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isSSL ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( ssl, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isVALIDCERTSSL ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( validcertssl, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isSSH ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( ssh, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isNetBios ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( netbios, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isNbns ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( nbns, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isNbds ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( nbds, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isBootstrap ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( bootstrap, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isYouTube ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( youtube, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isGoogleVideo ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( googlevideo, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isZippyVideo ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( zippyvideo, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isVeoh ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( veoh, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isVidilife ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( vidilife, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isOtherChat ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( otherChat, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isOtherVideoTCP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( otherVideoTCP, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isOtherVideoUDP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( otherVideoUDP, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isOtherMail ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( otherMail, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isOtherHTTP ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( otherHTTP, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isMySQL ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( mySQL, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isOtherDNS ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( otherDNS, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isSoribada ( unsigned char *payload, const u16 mess_len )
{
	//char *strg = (char*)malloc((mess_len+1)*sizeof(char));
	//coupeEOF(payload, mess_len, strg);
	//payload[mess_len-1]='\0';
	if ( regexec ( soribada, ( char* ) payload, 0, NULL, 0 ) ==0 )
	{
		//free(strg);
		return 1;
	}
	//free(strg);
	return 0;
}

u_short CClassifier::isAP2PID ( u_short id )
{
	if ( ( id>100 ) && ( id<201 ) )
		return 1;
	return 0;
}

u_short CClassifier::verID ( u_short old_id, u_short new_id )
{
	if ( old_id == PROTO_ID_NONPAYLOAD )  //We can't classify a flow with NONPAYLOAD type only because his first packet
	{
		return new_id;
	}
	else if ( ( old_id < DOWN_BASE_P2P_CLASS_NUMBER ) )
	{
		if ( ( new_id>= DOWN_BASE_P2P_CLASS_NUMBER ) && ( new_id<PROTO_ID_NONPAYLOAD ) )
		{
			return new_id;
		}
	}
	else if ( isSuperClass ( old_id ) )
	{
		if ( ( new_id>=DOWN_BASE_P2P_CLASS_NUMBER ) && ( ( new_id<PROTO_ID_NONPAYLOAD ) ) )
			return new_id;
	}
	else if ( ( old_id>UP_BASE_P2P_CLASS_NUMBER ) && ( old_id<PROTO_ID_NONPAYLOAD ) )
	{
		if ( ( new_id<=UP_BASE_P2P_CLASS_NUMBER ) && ( new_id>=DOWN_BASE_P2P_CLASS_NUMBER ) )
			return new_id;
	}
	return old_id;
}

u_short CClassifier::isSuperClass ( u_short id )
{
	if ( ( id==SSL_SUPER_CLASS_NUMBER ) || ( id==HTTP_SUPER_CLASS_NUMBER ) || ( id == 299 ) )
		return 1;
	return 0;
}

bool CClassifier::IsP2P ( const u_short id )
{
	return ( id > DOWN_BASE_P2P_CLASS_NUMBER && id < UP_BASE_P2P_CLASS_NUMBER );
}

bool CClassifier::IsHTTP ( const u_short id )
{
	return ( id == PROTO_ID_HTTP );
}

bool CClassifier::IsNonPayload ( const u_short id )
{
	return ( PROTO_ID_NONPAYLOAD == id );
}

