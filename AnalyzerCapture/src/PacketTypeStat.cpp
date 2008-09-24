/**
 *   PacketTypeStat.cpp
 *   Author: Wei Zhenfang
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to Ericsson AB
 *   Copyright (C) 2008 by Ericsson AB
 */

#include "PacketTypeStat.h"
#include "flow.pb.h"
#include "analyserpxFlow.h"
#include <iostream>
#include <fstream>

CPacketTypeStat::CPacketTypeStat(void)
{
}

CPacketTypeStat::~CPacketTypeStat(void)
{
}

ResultEnum CPacketTypeStat::processNewFlow( const flow_t* flow )
{
	u_short proto = flow->class_proto();
	FlowDigestMap::iterator itor = s_digestMap.find(proto);
	if (s_digestMap.end() != itor)
	{
		itor->second.packetNumber += flow->n_frames();
		itor->second.volume += flow->n_bytes();
	}
	else
	{
		FlowDigest* pDigest = new FlowDigest();
		pDigest->packetNumber = flow->n_frames();
		pDigest->volume = flow->n_bytes();
		s_digestMap.insert(pair<u_short, FlowDigest>(proto, *pDigest));
	}
}

ResultEnum CPacketTypeStat::PrintResult()
{
	unsigned long long totalPacket = 0;
	unsigned long long totalVolume = 0;
	FlowDigestMap::const_iterator const_itor;
	for (const_itor = s_digestMap.begin(); const_itor != s_digestMap.end(); ++const_itor)
	{
		totalPacket += const_itor->second.packetNumber;
		totalVolume += const_itor->second.volume;
	}
	cout << "Totally there are " << totalPacket << " frames" << endl;
	cout << "Totally there are " << totalVolume << " bytes" << endl;
	for (const_itor = s_digestMap.begin(); const_itor != s_digestMap.end(); ++const_itor)
	{
		double packetPercent = (double)(const_itor->second.packetNumber) / (double)totalPacket * 100;
		double volumePercent = (double)(const_itor->second.volume) / (double)totalVolume * 100;
		cout << const_itor->first << "  " << CFlowUtil::get_protocolName(const_itor->first) << " : packet " << const_itor->second.packetNumber << "  " <<packetPercent << "% "
			<< " : volume " << const_itor->second.volume << "  " << volumePercent << "%" << endl;
	}
}

ResultEnum CPacketTypeStat::Clear()
{
	s_digestMap.clear();
}

