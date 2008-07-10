bin_PROGRAMS = analyzercapture
analyzercapture_SOURCES = PacketDigest.cpp PacketStatistic.cpp \
	PacketStatistician.cpp SubscriberStatistic.cpp analyserpxAggreg.cpp analyserpxFile.cpp \
	analyserpxFlow.cpp analyzer.cpp analyzercapture.cpp cap.cpp circularbuffer.cpp \
	classifier.cpp expressions.cpp flow.pb.cpp hashtab.cpp ipheaderutil.cpp locks.cpp \
	resultrecorder.cpp userinputparams.cpp

# set the include path found by configure
INCLUDES= $(all_includes)

# the library search path.
analyzercapture_LDFLAGS = $(all_libraries) 
noinst_HEADERS = PacketDigest.h PacketStatistic.h PacketStatistician.h \
	SubscriberStatistic.h analyserpxAggreg.h analyserpxFile.h analyserpxFlow.h analyserpxTypes.h \
	analyzer.h cap.h circularbuffer.h classifier.h expressions.h flow.pb.h hashtab.h \
	ipheaderutil.h locks.h macro.h resultrecorder.h userinputparams.h version.h
