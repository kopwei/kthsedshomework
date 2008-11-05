/**
 *   Flow.h
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
#ifndef FLOW_H
#define FLOW_H

//#include <stdio.h>
#include <string.h>
#include <string>
#include <netinet/in.h>

/**
	@author LM Ericsson,,, <ericsson@ericsson-computer>
*/
const u_short IF_CHAR_SIZE = 5;

class flow_t
{
public:
    flow_t():
            proto_(0), 			/*!< protocol number*/
            class_proto_(0), 		/*!< Classification protocol number*/
            dst_port_(0),		/*!< source port*/
            src_port_(0), 		/*!< destination port*/
            n_bytes_(0),	/*!< number of bytes*/
            n_frames_(0),	/*!< number of frames*/
            ini_sec_(0),		/*!< seconds init time*/
            ini_mic_(0),		/*!< microseconds init time*/
            end_sec_(0),		/*!< seconds end time*/
            end_mic_(0)		/*!< microseconds end time*/
    {}
			
    ~flow_t() {};

    const u_short proto() const
    {
        return proto_;    /*!< protocol number*/
    }
    const u_short class_proto() const
    {
        return class_proto_;    /*!< Classification protocol number*/
    }
    const char* src_if() const
    {
        return	src_if_;    /*!< source native interface name*/
    }
    const char* dst_if() const
    {
        return dst_if_;    /*!< destination native interface name*/
    }
    const u_short dst_port() const
    {
        return dst_port_;    /*!< source port*/
    }
    const u_short src_port() const
    {
        return src_port_;    /*!< destination port*/
    }
    const unsigned int n_bytes() const
    {
        return n_bytes_;    /*!< number of bytes*/
    }
    const unsigned int n_frames() const
    {
        return n_frames_;    /*!< number of frames*/
    }
    const time_t 	ini_sec() const
    {
        return ini_sec_;    /*!< seconds init time*/
    }
    const time_t ini_mic() const
    {
        return ini_mic_;    /*!< microseconds init time*/
    }
    const time_t end_sec()const
    {
        return end_sec_;    /*!< seconds end time*/
    }
    const time_t end_mic() const
    {
        return end_mic_;    /*!< microseconds end time*/
    }
    const in_addr	src_ip() const
    {
        return src_ip_;    /*!< source ip*/
    }
    const in_addr dst_ip() const
    {
        return dst_ip_;    /*!< destination ip*/
    }

    const unsigned long long GetKey() const
    {
        typedef unsigned long long uint64;
        return (uint64)(src_ip_.s_addr) * 59 + (uint64)(dst_ip_.s_addr) * 37 +
               (uint64)proto_ * 7 + (uint64)src_port_*23 + (uint64)dst_port_* 29;
    }



    void set_proto(const u_short val)
    {
        proto_ = val;
    }

    void set_class_proto(const u_short val)
    {
        class_proto_ = val;
    }

    void set_src_if(const char* val)
    {
        if (NULL != val)
        {
            if (strlen(val) > 0 && strlen(val) <=IF_CHAR_SIZE)
            {
                strncpy(src_if_, val, strlen(val));
            }
        }
    }

    void set_src_if(const std::string& val)
    {
        set_src_if(val.c_str());
    }

    void set_dst_if(const char* val)
    {
        if (NULL != val)
        {
            if (strlen(val) > 0 && strlen(val) <=IF_CHAR_SIZE)
            {
                strncpy(dst_if_, val, strlen(val));
            }
        }
    }

    void set_dst_if(const std::string& val)
    {
        set_dst_if(val.c_str());
    }

    void set_src_port(const u_short val)
    {
        src_port_ = val;
    }

    void set_dst_port(const u_short val)
    {
        dst_port_ = val;
    }

    void set_ini_sec(const time_t val)
    {
        ini_sec_ = val;
    }

    void set_ini_mic(const time_t val)
    {
        ini_mic_ = val;
    }

    void set_end_sec(const time_t val)
    {
        end_sec_ = val;
    }

    void set_end_mic(const time_t val)
    {
        end_mic_ = val;
    }

    void set_n_frames(const unsigned int val)
    {
        n_frames_ = val;
    }

    void set_n_bytes(const unsigned int val)
    {
        n_bytes_ = val;
    }

    void set_src_ip(const in_addr& val)
    {
        src_ip_ = val;
    }

    void set_dst_ip(const in_addr& val)
    {
        dst_ip_ = val;
    }





private:
    /**
     * Avoid bitwise-copy
     */
    flow_t(const flow_t&);
    const flow_t operator =(const flow_t&);


    u_short proto_; 			/*!< protocol number*/
    u_short class_proto_; 		/*!< Classification protocol number*/
    char 	src_if_[IF_CHAR_SIZE],	/*!< source native interface name*/
    dst_if_[IF_CHAR_SIZE];	/*!< destination native interface name*/
    u_short dst_port_,		/*!< source port*/
    src_port_; 		/*!< destination port*/
    unsigned int 	n_bytes_,	/*!< number of bytes*/
    n_frames_;	/*!< number of frames*/
    time_t 	ini_sec_,		/*!< seconds init time*/
    ini_mic_,		/*!< microseconds init time*/
    end_sec_,		/*!< seconds end time*/
    end_mic_;		/*!< microseconds end time*/
    in_addr 	src_ip_,		/*!< source ip*/
    dst_ip_;		/*!< destination ip*/



};

#endif
