// Generated by the protocol buffer compiler.  DO NOT EDIT!

#ifndef PROTOBUF_PacketStatistic_2eproto__INCLUDED
#define PROTOBUF_PacketStatistic_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#include "resultenum.h"

#if GOOGLE_PROTOBUF_VERSION < 2000000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2000000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>

class CPacketStatistic;
class CPacketDigest;

// ===================================================================

class CPacketStatistic : public ::google::protobuf::Message {
 public:
  CPacketStatistic();
  virtual ~CPacketStatistic();
  
  CPacketStatistic(const CPacketStatistic& from);
  
  inline CPacketStatistic& operator=(const CPacketStatistic& from) {
    CopyFrom(from);
    return *this;
  }

  /**
   *	This method is used to add a new packet digest into the info base
   */
  ResultEnum					AddPacketInfo(const CPacketDigest* pDigest);

  
  inline static const CPacketStatistic& default_instance() {
    return default_instance_;
  }
  
  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _reflection_.unknown_fields();
  }
  
  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return _reflection_.mutable_unknown_fields();
  }
  
  static const ::google::protobuf::Descriptor* descriptor();
  
  // implements Message ----------------------------------------------
  
  CPacketStatistic* New() const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SetCachedSize(int size) const { _cached_size_ = size; }
  public:
  
  const ::google::protobuf::Descriptor* GetDescriptor() const;
  const ::google::protobuf::Message::Reflection* GetReflection() const;
  ::google::protobuf::Message::Reflection* GetReflection();
  
  // nested types ----------------------------------------------------
  
  // accessors -------------------------------------------------------
  
  // required fixed64 m_llPacketNumber = 1;
  inline bool has_m_llpacketnumber() const;
  inline void clear_m_llpacketnumber();
  inline ::google::protobuf::uint64 m_llpacketnumber() const;
  inline void set_m_llpacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llTrafficVolume = 2;
  inline bool has_m_lltrafficvolume() const;
  inline void clear_m_lltrafficvolume();
  inline ::google::protobuf::uint64 m_lltrafficvolume() const;
  inline void set_m_lltrafficvolume(::google::protobuf::uint64 value);
  
  // required fixed64 m_llEmptyPacketNumber = 3;
  inline bool has_m_llemptypacketnumber() const;
  inline void clear_m_llemptypacketnumber();
  inline ::google::protobuf::uint64 m_llemptypacketnumber() const;
  inline void set_m_llemptypacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llTCPPacketNumber = 4;
  inline bool has_m_lltcppacketnumber() const;
  inline void clear_m_lltcppacketnumber();
  inline ::google::protobuf::uint64 m_lltcppacketnumber() const;
  inline void set_m_lltcppacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llTCPTrafficVolume = 5;
  inline bool has_m_lltcptrafficvolume() const;
  inline void clear_m_lltcptrafficvolume();
  inline ::google::protobuf::uint64 m_lltcptrafficvolume() const;
  inline void set_m_lltcptrafficvolume(::google::protobuf::uint64 value);
  
  // required fixed64 m_llUDPPacketNumber = 6;
  inline bool has_m_lludppacketnumber() const;
  inline void clear_m_lludppacketnumber();
  inline ::google::protobuf::uint64 m_lludppacketnumber() const;
  inline void set_m_lludppacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llUDPTrafficVolume = 7;
  inline bool has_m_lludptrafficvolume() const;
  inline void clear_m_lludptrafficvolume();
  inline ::google::protobuf::uint64 m_lludptrafficvolume() const;
  inline void set_m_lludptrafficvolume(::google::protobuf::uint64 value);
  
  // required fixed64 m_llP2PPacketNumber = 8;
  inline bool has_m_llp2ppacketnumber() const;
  inline void clear_m_llp2ppacketnumber();
  inline ::google::protobuf::uint64 m_llp2ppacketnumber() const;
  inline void set_m_llp2ppacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llP2PTrafficVolume = 9;
  inline bool has_m_llp2ptrafficvolume() const;
  inline void clear_m_llp2ptrafficvolume();
  inline ::google::protobuf::uint64 m_llp2ptrafficvolume() const;
  inline void set_m_llp2ptrafficvolume(::google::protobuf::uint64 value);
  
  // required fixed64 m_llHTTPPacketNumber = 10;
  inline bool has_m_llhttppacketnumber() const;
  inline void clear_m_llhttppacketnumber();
  inline ::google::protobuf::uint64 m_llhttppacketnumber() const;
  inline void set_m_llhttppacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llHTTPTrafficVolume = 11;
  inline bool has_m_llhttptrafficvolume() const;
  inline void clear_m_llhttptrafficvolume();
  inline ::google::protobuf::uint64 m_llhttptrafficvolume() const;
  inline void set_m_llhttptrafficvolume(::google::protobuf::uint64 value);
  
  // required fixed64 m_llUnidentifiedPacketNumber = 12;
  inline bool has_m_llunidentifiedpacketnumber() const;
  inline void clear_m_llunidentifiedpacketnumber();
  inline ::google::protobuf::uint64 m_llunidentifiedpacketnumber() const;
  inline void set_m_llunidentifiedpacketnumber(::google::protobuf::uint64 value);
  
  // required fixed64 m_llUnidentifiedTrafficVolume = 13;
  inline bool has_m_llunidentifiedtrafficvolume() const;
  inline void clear_m_llunidentifiedtrafficvolume();
  inline ::google::protobuf::uint64 m_llunidentifiedtrafficvolume() const;
  inline void set_m_llunidentifiedtrafficvolume(::google::protobuf::uint64 value);
  
 private:
  ::google::protobuf::internal::GeneratedMessageReflection _reflection_;
  mutable int _cached_size_;
  
  ::google::protobuf::uint64 m_llpacketnumber_;
  ::google::protobuf::uint64 m_lltrafficvolume_;
  ::google::protobuf::uint64 m_llemptypacketnumber_;
  ::google::protobuf::uint64 m_lltcppacketnumber_;
  ::google::protobuf::uint64 m_lltcptrafficvolume_;
  ::google::protobuf::uint64 m_lludppacketnumber_;
  ::google::protobuf::uint64 m_lludptrafficvolume_;
  ::google::protobuf::uint64 m_llp2ppacketnumber_;
  ::google::protobuf::uint64 m_llp2ptrafficvolume_;
  ::google::protobuf::uint64 m_llhttppacketnumber_;
  ::google::protobuf::uint64 m_llhttptrafficvolume_;
  ::google::protobuf::uint64 m_llunidentifiedpacketnumber_;
  ::google::protobuf::uint64 m_llunidentifiedtrafficvolume_;
  
  static const CPacketStatistic default_instance_;
  static const int _offsets_[13];
  
  ::google::protobuf::uint32 _has_bits_[(13 + 31) / 32];
  
  // WHY DOES & HAVE LOWER PRECEDENCE THAN != !?
  inline bool _has_bit(int index) const {
    return (_has_bits_[index / 32] & (1u << (index % 32))) != 0;
  }
  inline void _set_bit(int index) {
    _has_bits_[index / 32] |= (1u << (index % 32));
  }
  inline void _clear_bit(int index) {
    _has_bits_[index / 32] &= ~(1u << (index % 32));
  }
};
// ===================================================================


// ===================================================================


// ===================================================================

// CPacketStatistic

// required fixed64 m_llPacketNumber = 1;
inline bool CPacketStatistic::has_m_llpacketnumber() const {
  return _has_bit(0);
}
inline void CPacketStatistic::clear_m_llpacketnumber() {
  m_llpacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(0);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llpacketnumber() const {
  return m_llpacketnumber_;
}
inline void CPacketStatistic::set_m_llpacketnumber(::google::protobuf::uint64 value) {
  _set_bit(0);
  m_llpacketnumber_ = value;
}

// required fixed64 m_llTrafficVolume = 2;
inline bool CPacketStatistic::has_m_lltrafficvolume() const {
  return _has_bit(1);
}
inline void CPacketStatistic::clear_m_lltrafficvolume() {
  m_lltrafficvolume_ = GOOGLE_ULONGLONG(0);
  _clear_bit(1);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_lltrafficvolume() const {
  return m_lltrafficvolume_;
}
inline void CPacketStatistic::set_m_lltrafficvolume(::google::protobuf::uint64 value) {
  _set_bit(1);
  m_lltrafficvolume_ = value;
}

// required fixed64 m_llEmptyPacketNumber = 3;
inline bool CPacketStatistic::has_m_llemptypacketnumber() const {
  return _has_bit(2);
}
inline void CPacketStatistic::clear_m_llemptypacketnumber() {
  m_llemptypacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(2);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llemptypacketnumber() const {
  return m_llemptypacketnumber_;
}
inline void CPacketStatistic::set_m_llemptypacketnumber(::google::protobuf::uint64 value) {
  _set_bit(2);
  m_llemptypacketnumber_ = value;
}

// required fixed64 m_llTCPPacketNumber = 4;
inline bool CPacketStatistic::has_m_lltcppacketnumber() const {
  return _has_bit(3);
}
inline void CPacketStatistic::clear_m_lltcppacketnumber() {
  m_lltcppacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(3);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_lltcppacketnumber() const {
  return m_lltcppacketnumber_;
}
inline void CPacketStatistic::set_m_lltcppacketnumber(::google::protobuf::uint64 value) {
  _set_bit(3);
  m_lltcppacketnumber_ = value;
}

// required fixed64 m_llTCPTrafficVolume = 5;
inline bool CPacketStatistic::has_m_lltcptrafficvolume() const {
  return _has_bit(4);
}
inline void CPacketStatistic::clear_m_lltcptrafficvolume() {
  m_lltcptrafficvolume_ = GOOGLE_ULONGLONG(0);
  _clear_bit(4);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_lltcptrafficvolume() const {
  return m_lltcptrafficvolume_;
}
inline void CPacketStatistic::set_m_lltcptrafficvolume(::google::protobuf::uint64 value) {
  _set_bit(4);
  m_lltcptrafficvolume_ = value;
}

// required fixed64 m_llUDPPacketNumber = 6;
inline bool CPacketStatistic::has_m_lludppacketnumber() const {
  return _has_bit(5);
}
inline void CPacketStatistic::clear_m_lludppacketnumber() {
  m_lludppacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(5);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_lludppacketnumber() const {
  return m_lludppacketnumber_;
}
inline void CPacketStatistic::set_m_lludppacketnumber(::google::protobuf::uint64 value) {
  _set_bit(5);
  m_lludppacketnumber_ = value;
}

// required fixed64 m_llUDPTrafficVolume = 7;
inline bool CPacketStatistic::has_m_lludptrafficvolume() const {
  return _has_bit(6);
}
inline void CPacketStatistic::clear_m_lludptrafficvolume() {
  m_lludptrafficvolume_ = GOOGLE_ULONGLONG(0);
  _clear_bit(6);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_lludptrafficvolume() const {
  return m_lludptrafficvolume_;
}
inline void CPacketStatistic::set_m_lludptrafficvolume(::google::protobuf::uint64 value) {
  _set_bit(6);
  m_lludptrafficvolume_ = value;
}

// required fixed64 m_llP2PPacketNumber = 8;
inline bool CPacketStatistic::has_m_llp2ppacketnumber() const {
  return _has_bit(7);
}
inline void CPacketStatistic::clear_m_llp2ppacketnumber() {
  m_llp2ppacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(7);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llp2ppacketnumber() const {
  return m_llp2ppacketnumber_;
}
inline void CPacketStatistic::set_m_llp2ppacketnumber(::google::protobuf::uint64 value) {
  _set_bit(7);
  m_llp2ppacketnumber_ = value;
}

// required fixed64 m_llP2PTrafficVolume = 9;
inline bool CPacketStatistic::has_m_llp2ptrafficvolume() const {
  return _has_bit(8);
}
inline void CPacketStatistic::clear_m_llp2ptrafficvolume() {
  m_llp2ptrafficvolume_ = GOOGLE_ULONGLONG(0);
  _clear_bit(8);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llp2ptrafficvolume() const {
  return m_llp2ptrafficvolume_;
}
inline void CPacketStatistic::set_m_llp2ptrafficvolume(::google::protobuf::uint64 value) {
  _set_bit(8);
  m_llp2ptrafficvolume_ = value;
}

// required fixed64 m_llHTTPPacketNumber = 10;
inline bool CPacketStatistic::has_m_llhttppacketnumber() const {
  return _has_bit(9);
}
inline void CPacketStatistic::clear_m_llhttppacketnumber() {
  m_llhttppacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(9);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llhttppacketnumber() const {
  return m_llhttppacketnumber_;
}
inline void CPacketStatistic::set_m_llhttppacketnumber(::google::protobuf::uint64 value) {
  _set_bit(9);
  m_llhttppacketnumber_ = value;
}

// required fixed64 m_llHTTPTrafficVolume = 11;
inline bool CPacketStatistic::has_m_llhttptrafficvolume() const {
  return _has_bit(10);
}
inline void CPacketStatistic::clear_m_llhttptrafficvolume() {
  m_llhttptrafficvolume_ = GOOGLE_ULONGLONG(0);
  _clear_bit(10);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llhttptrafficvolume() const {
  return m_llhttptrafficvolume_;
}
inline void CPacketStatistic::set_m_llhttptrafficvolume(::google::protobuf::uint64 value) {
  _set_bit(10);
  m_llhttptrafficvolume_ = value;
}

// required fixed64 m_llUnidentifiedPacketNumber = 12;
inline bool CPacketStatistic::has_m_llunidentifiedpacketnumber() const {
  return _has_bit(11);
}
inline void CPacketStatistic::clear_m_llunidentifiedpacketnumber() {
  m_llunidentifiedpacketnumber_ = GOOGLE_ULONGLONG(0);
  _clear_bit(11);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llunidentifiedpacketnumber() const {
  return m_llunidentifiedpacketnumber_;
}
inline void CPacketStatistic::set_m_llunidentifiedpacketnumber(::google::protobuf::uint64 value) {
  _set_bit(11);
  m_llunidentifiedpacketnumber_ = value;
}

// required fixed64 m_llUnidentifiedTrafficVolume = 13;
inline bool CPacketStatistic::has_m_llunidentifiedtrafficvolume() const {
  return _has_bit(12);
}
inline void CPacketStatistic::clear_m_llunidentifiedtrafficvolume() {
  m_llunidentifiedtrafficvolume_ = GOOGLE_ULONGLONG(0);
  _clear_bit(12);
}
inline ::google::protobuf::uint64 CPacketStatistic::m_llunidentifiedtrafficvolume() const {
  return m_llunidentifiedtrafficvolume_;
}
inline void CPacketStatistic::set_m_llunidentifiedtrafficvolume(::google::protobuf::uint64 value) {
  _set_bit(12);
  m_llunidentifiedtrafficvolume_ = value;
}

#endif  // PROTOBUF_PacketStatistic_2eproto__INCLUDED
