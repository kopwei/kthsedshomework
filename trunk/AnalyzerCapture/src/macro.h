/**
 *   macro.h
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

#pragma once

#include <assert.h>

#define FREE_OBJECT(pPointer) \
	delete pPointer; \
	pPointer = NULL;

#ifdef _DEBUG
#define EABASSERT(value) \
	assert(value);
#endif

#ifndef _DEBUG
#define EABASSERT(value)
#endif

#define ON_ERROR_RETURN(boolVal, retVal) \
	if (boolVal) return retVal;

