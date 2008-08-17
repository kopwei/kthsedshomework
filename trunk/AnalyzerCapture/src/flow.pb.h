// Generated by the protocol buffer compiler.  DO NOT EDIT!

#ifndef PROTOBUF_flow_2eproto__INCLUDED
#define PROTOBUF_flow_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 2000001
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2000001 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>

// Internal implementation detail -- do not call this.
void protobuf_BuildDesc_flow_2eproto();

class flow_t;

// ===================================================================

class flow_t : public ::google::protobuf::Message {
 public:
  flow_t();
  virtual ~flow_t();
  
  flow_t(const flow_t& from);
  
  inline flow_t& operator=(const flow_t& from) {
    CopyFrom(from);
    return *this;
  }
  
  inline static const flow_t& default_instance() {
    return default_instance_;
  }
  
  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }
  
  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }
  
  static const ::google::protobuf::Descriptor* descriptor();
  
  // implements Message ----------------------------------------------
  
  flow_t* New() const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SetCachedSize(int size) const { _cached_size_ = size; }
  public:
  
  const ::google::protobuf::Descriptor* GetDescriptor() const;
  const ::google::protobuf::Reflection* GetReflection() const;
  
  // nested types ----------------------------------------------------
  
  // accessors -------------------------------------------------------
  
  // required uint32 proto = 1;
  inline bool has_proto() const;
  inline void clear_proto();
  inline ::google::protobuf::uint32 proto() const;
  inline void set_proto(::google::protobuf::uint32 value);
  
  // required uint32 class_proto = 2;
  inline bool has_class_proto() const;
  inline void clear_class_proto();
  inline ::google::protobuf::uint32 class_proto() const;
  inline void set_class_proto(::google::protobuf::uint32 value);
  
  // required string src_if = 3;
  inline bool has_src_if() const;
  inline void clear_src_if();
  inline const ::std::string& src_if() const;
  inline void set_src_if(const ::std::string& value);
  inline void set_src_if(const char* value);
  inline ::std::string* mutable_src_if();
  
  // required string dst_if = 4;
  inline bool has_dst_if() const;
  inline void clear_dst_if();
  inline const ::std::string& dst_if() const;
  inline void set_dst_if(const ::std::string& value);
  inline void set_dst_if(const char* value);
  inline ::std::string* mutable_dst_if();
  
  // required uint32 src_port = 5;
  inline bool has_src_port() const;
  inline void clear_src_port();
  inline ::google::protobuf::uint32 src_port() const;
  inline void set_src_port(::google::protobuf::uint32 value);
  
  // required uint32 dst_port = 6;
  inline bool has_dst_port() const;
  inline void clear_dst_port();
  inline ::google::protobuf::uint32 dst_port() const;
  inline void set_dst_port(::google::protobuf::uint32 value);
  
  // required uint32 n_bytes = 7;
  inline bool has_n_bytes() const;
  inline void clear_n_bytes();
  inline ::google::protobuf::uint32 n_bytes() const;
  inline void set_n_bytes(::google::protobuf::uint32 value);
  
  // required uint32 n_frames = 8;
  inline bool has_n_frames() const;
  inline void clear_n_frames();
  inline ::google::protobuf::uint32 n_frames() const;
  inline void set_n_frames(::google::protobuf::uint32 value);
  
  // required uint32 ini_sec = 9;
  inline bool has_ini_sec() const;
  inline void clear_ini_sec();
  inline ::google::protobuf::uint32 ini_sec() const;
  inline void set_ini_sec(::google::protobuf::uint32 value);
  
  // required uint32 ini_mic = 10;
  inline bool has_ini_mic() const;
  inline void clear_ini_mic();
  inline ::google::protobuf::uint32 ini_mic() const;
  inline void set_ini_mic(::google::protobuf::uint32 value);
  
  // required uint32 end_sec = 11;
  inline bool has_end_sec() const;
  inline void clear_end_sec();
  inline ::google::protobuf::uint32 end_sec() const;
  inline void set_end_sec(::google::protobuf::uint32 value);
  
  // required uint32 end_mic = 12;
  inline bool has_end_mic() const;
  inline void clear_end_mic();
  inline ::google::protobuf::uint32 end_mic() const;
  inline void set_end_mic(::google::protobuf::uint32 value);
  
  // required uint32 src_ip = 13;
  inline bool has_src_ip() const;
  inline void clear_src_ip();
  inline ::google::protobuf::uint32 src_ip() const;
  inline void set_src_ip(::google::protobuf::uint32 value);
  
  // required uint32 dst_ip = 14;
  inline bool has_dst_ip() const;
  inline void clear_dst_ip();
  inline ::google::protobuf::uint32 dst_ip() const;
  inline void set_dst_ip(::google::protobuf::uint32 value);
  
 private:
  ::google::protobuf::UnknownFieldSet _unknown_fields_;
  mutable int _cached_size_;
  
  ::google::protobuf::uint32 proto_;
  ::google::protobuf::uint32 class_proto_;
  ::std::string* src_if_;
  static const ::std::string _default_src_if_;
  ::std::string* dst_if_;
  static const ::std::string _default_dst_if_;
  ::google::protobuf::uint32 src_port_;
  ::google::protobuf::uint32 dst_port_;
  ::google::protobuf::uint32 n_bytes_;
  ::google::protobuf::uint32 n_frames_;
  ::google::protobuf::uint32 ini_sec_;
  ::google::protobuf::uint32 ini_mic_;
  ::google::protobuf::uint32 end_sec_;
  ::google::protobuf::uint32 end_mic_;
  ::google::protobuf::uint32 src_ip_;
  ::google::protobuf::uint32 dst_ip_;
  friend void protobuf_BuildDesc_flow_2eproto();
  static const flow_t default_instance_;
  static const int _offsets_[14];
  
  ::google::protobuf::uint32 _has_bits_[(14 + 31) / 32];
  
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

// flow_t

// required uint32 proto = 1;
inline bool flow_t::has_proto() const {
  return _has_bit(0);
}
inline void flow_t::clear_proto() {
  proto_ = 0u;
  _clear_bit(0);
}
inline ::google::protobuf::uint32 flow_t::proto() const {
  return proto_;
}
inline void flow_t::set_proto(::google::protobuf::uint32 value) {
  _set_bit(0);
  proto_ = value;
}

// required uint32 class_proto = 2;
inline bool flow_t::has_class_proto() const {
  return _has_bit(1);
}
inline void flow_t::clear_class_proto() {
  class_proto_ = 0u;
  _clear_bit(1);
}
inline ::google::protobuf::uint32 flow_t::class_proto() const {
  return class_proto_;
}
inline void flow_t::set_class_proto(::google::protobuf::uint32 value) {
  _set_bit(1);
  class_proto_ = value;
}

// required string src_if = 3;
inline bool flow_t::has_src_if() const {
  return _has_bit(2);
}
inline void flow_t::clear_src_if() {
  if (src_if_ != &_default_src_if_) {
    src_if_->clear();
  }
  _clear_bit(2);
}
inline const ::std::string& flow_t::src_if() const {
  return *src_if_;
}
inline void flow_t::set_src_if(const ::std::string& value) {
  _set_bit(2);
  if (src_if_ == &_default_src_if_) {
    src_if_ = new ::std::string;
  }
  src_if_->assign(value);
}
inline void flow_t::set_src_if(const char* value) {
  _set_bit(2);
  if (src_if_ == &_default_src_if_) {
    src_if_ = new ::std::string;
  }
  src_if_->assign(value);
}
inline ::std::string* flow_t::mutable_src_if() {
  _set_bit(2);
  if (src_if_ == &_default_src_if_) {
    src_if_ = new ::std::string;
  }
  return src_if_;
}

// required string dst_if = 4;
inline bool flow_t::has_dst_if() const {
  return _has_bit(3);
}
inline void flow_t::clear_dst_if() {
  if (dst_if_ != &_default_dst_if_) {
    dst_if_->clear();
  }
  _clear_bit(3);
}
inline const ::std::string& flow_t::dst_if() const {
  return *dst_if_;
}
inline void flow_t::set_dst_if(const ::std::string& value) {
  _set_bit(3);
  if (dst_if_ == &_default_dst_if_) {
    dst_if_ = new ::std::string;
  }
  dst_if_->assign(value);
}
inline void flow_t::set_dst_if(const char* value) {
  _set_bit(3);
  if (dst_if_ == &_default_dst_if_) {
    dst_if_ = new ::std::string;
  }
  dst_if_->assign(value);
}
inline ::std::string* flow_t::mutable_dst_if() {
  _set_bit(3);
  if (dst_if_ == &_default_dst_if_) {
    dst_if_ = new ::std::string;
  }
  return dst_if_;
}

// required uint32 src_port = 5;
inline bool flow_t::has_src_port() const {
  return _has_bit(4);
}
inline void flow_t::clear_src_port() {
  src_port_ = 0u;
  _clear_bit(4);
}
inline ::google::protobuf::uint32 flow_t::src_port() const {
  return src_port_;
}
inline void flow_t::set_src_port(::google::protobuf::uint32 value) {
  _set_bit(4);
  src_port_ = value;
}

// required uint32 dst_port = 6;
inline bool flow_t::has_dst_port() const {
  return _has_bit(5);
}
inline void flow_t::clear_dst_port() {
  dst_port_ = 0u;
  _clear_bit(5);
}
inline ::google::protobuf::uint32 flow_t::dst_port() const {
  return dst_port_;
}
inline void flow_t::set_dst_port(::google::protobuf::uint32 value) {
  _set_bit(5);
  dst_port_ = value;
}

// required uint32 n_bytes = 7;
inline bool flow_t::has_n_bytes() const {
  return _has_bit(6);
}
inline void flow_t::clear_n_bytes() {
  n_bytes_ = 0u;
  _clear_bit(6);
}
inline ::google::protobuf::uint32 flow_t::n_bytes() const {
  return n_bytes_;
}
inline void flow_t::set_n_bytes(::google::protobuf::uint32 value) {
  _set_bit(6);
  n_bytes_ = value;
}

// required uint32 n_frames = 8;
inline bool flow_t::has_n_frames() const {
  return _has_bit(7);
}
inline void flow_t::clear_n_frames() {
  n_frames_ = 0u;
  _clear_bit(7);
}
inline ::google::protobuf::uint32 flow_t::n_frames() const {
  return n_frames_;
}
inline void flow_t::set_n_frames(::google::protobuf::uint32 value) {
  _set_bit(7);
  n_frames_ = value;
}

// required uint32 ini_sec = 9;
inline bool flow_t::has_ini_sec() const {
  return _has_bit(8);
}
inline void flow_t::clear_ini_sec() {
  ini_sec_ = 0u;
  _clear_bit(8);
}
inline ::google::protobuf::uint32 flow_t::ini_sec() const {
  return ini_sec_;
}
inline void flow_t::set_ini_sec(::google::protobuf::uint32 value) {
  _set_bit(8);
  ini_sec_ = value;
}

// required uint32 ini_mic = 10;
inline bool flow_t::has_ini_mic() const {
  return _has_bit(9);
}
inline void flow_t::clear_ini_mic() {
  ini_mic_ = 0u;
  _clear_bit(9);
}
inline ::google::protobuf::uint32 flow_t::ini_mic() const {
  return ini_mic_;
}
inline void flow_t::set_ini_mic(::google::protobuf::uint32 value) {
  _set_bit(9);
  ini_mic_ = value;
}

// required uint32 end_sec = 11;
inline bool flow_t::has_end_sec() const {
  return _has_bit(10);
}
inline void flow_t::clear_end_sec() {
  end_sec_ = 0u;
  _clear_bit(10);
}
inline ::google::protobuf::uint32 flow_t::end_sec() const {
  return end_sec_;
}
inline void flow_t::set_end_sec(::google::protobuf::uint32 value) {
  _set_bit(10);
  end_sec_ = value;
}

// required uint32 end_mic = 12;
inline bool flow_t::has_end_mic() const {
  return _has_bit(11);
}
inline void flow_t::clear_end_mic() {
  end_mic_ = 0u;
  _clear_bit(11);
}
inline ::google::protobuf::uint32 flow_t::end_mic() const {
  return end_mic_;
}
inline void flow_t::set_end_mic(::google::protobuf::uint32 value) {
  _set_bit(11);
  end_mic_ = value;
}

// required uint32 src_ip = 13;
inline bool flow_t::has_src_ip() const {
  return _has_bit(12);
}
inline void flow_t::clear_src_ip() {
  src_ip_ = 0u;
  _clear_bit(12);
}
inline ::google::protobuf::uint32 flow_t::src_ip() const {
  return src_ip_;
}
inline void flow_t::set_src_ip(::google::protobuf::uint32 value) {
  _set_bit(12);
  src_ip_ = value;
}

// required uint32 dst_ip = 14;
inline bool flow_t::has_dst_ip() const {
  return _has_bit(13);
}
inline void flow_t::clear_dst_ip() {
  dst_ip_ = 0u;
  _clear_bit(13);
}
inline ::google::protobuf::uint32 flow_t::dst_ip() const {
  return dst_ip_;
}
inline void flow_t::set_dst_ip(::google::protobuf::uint32 value) {
  _set_bit(13);
  dst_ip_ = value;
}

#endif  // PROTOBUF_flow_2eproto__INCLUDED
