/**
 *   resultrecorder.h
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
 *   along with this program; if not, write to the
 *   Copyright (C) 2008 by Ericsson
 */

#ifndef RESULTRECORDER_H
#define RESULTRECORDER_H

#include "analyserpxTypes.h"

class CPacketStatistician;

class RecordParameter
{
	
};

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/

/**
 * This class is a util class used for recording the result into file or database
 */
class CResultRecorder{
public:
    CResultRecorder();

    ~CResultRecorder();
	
	static ResultEnum RecordResult(const CPacketStatistician* pStatistician, const RecordParameter* pParam);

};

#endif

