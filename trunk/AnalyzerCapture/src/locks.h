/**
 *   locks.h
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
 *   along with this program; if not, write to the Ericsson
 *   Copyright (C) 2008 by Ericsson
 */

#ifndef LOCKS_H
#define LOCKS_H

#include <pthread.h>

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
class Locks{
public:
	static pthread_mutex_t cap_lock;
	static pthread_mutex_t print_lock;
	static pthread_mutex_t hash_lock;
	static pthread_mutex_t packetMap_lock;
	static pthread_mutex_t fileName_lock;
	static pthread_mutex_t storing_lock;
	static pthread_mutex_t time_lock;
	
	static pthread_mutex_t total_subscriber_lock;
	
	static pthread_mutex_t userset_lock;
	
	static pthread_mutex_t traffic_result_lock;

	static pthread_mutex_t subscriber_result_lock;

	static pthread_mutex_t payload_result_lock;
	
	static pthread_mutex_t app_result_lock;
	
	static pthread_mutex_t flow_analyzer_lock;
};

#endif
