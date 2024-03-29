/**
 *   locks.cpp
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
#include "locks.h"

pthread_mutex_t Locks::cap_lock;

pthread_mutex_t Locks::print_lock;

pthread_mutex_t Locks::hash_lock;

pthread_mutex_t Locks::packetMap_lock;

pthread_mutex_t Locks::fileName_lock;

pthread_mutex_t Locks::storing_lock;

pthread_mutex_t Locks::time_lock;

pthread_mutex_t Locks::total_subscriber_lock;

pthread_mutex_t Locks::userset_lock;

pthread_mutex_t Locks::traffic_result_lock;

pthread_mutex_t Locks::subscriber_result_lock;

pthread_mutex_t Locks::payload_result_lock;

pthread_mutex_t Locks::app_result_lock;

pthread_mutex_t Locks::flow_analyzer_lock;

