bin_PROGRAMS = analyzercapture
analyzercapture_SOURCES = AnalyzedResult.cpp AppAnalyzedResult.cpp \
	AppStatistic.cpp CommonUtil.cpp FlowAnalyzedResult.cpp FlowStatisticMap.cpp \
	FlowTypeMap.cpp IPRangeLocator.cpp PacketDigest.cpp PacketStatistic.cpp \
	PacketStatisticMap.cpp PacketStatistician.cpp PacketTypeStat.cpp PayloadLengthResult.cpp \
	SubscriberAnalyzedResult.cpp SubscriberStatistic.cpp TrafficAnalyzedresult.cpp UserUtil.cpp \
	analyserpxAggreg.cpp analyserpxFile.cpp analyserpxFlow.cpp analyzer.cpp analyzercapture.cpp \
	cap.cpp circularbuffer.cpp classifier.cpp expressions.cpp hashtab.cpp \
	ipheaderutil.cpp locks.cpp userinputparams.cpp

# set the include path found by configure
INCLUDES= $(all_includes)

# the library search path.
analyzercapture_LDFLAGS = $(all_libraries) 
noinst_HEADERS = AnalyzedResult.h AppAnalyzedResult.h AppStatistic.h \
	CommonUtil.h Flow.h FlowAnalyzedResult.h FlowStatisticMap.h IPRangeLocator.h \
	MetaTraffic.h PacketDigest.h PacketStatistic.h PacketStatisticMap.h \
	PacketStatistician.h PayloadLengthResult.h SubscriberAnalyzedResult.h SubscriberStatistic.h \
	TrafficAnalyzedresult.h UserUtil.h analyserpxAggreg.h analyserpxFile.h analyserpxFlow.h \
	analyserpxTypes.h analyzer.h cap.h circularbuffer.h classifier.h expressions.h hashtab.h \
	ipheaderutil.h locks.h macro.h userinputparams.h version.h
_SOURCES = FlowTypeMap.h
_SOURCES = FlowTypeMap.cpp FlowTypeMap.h
