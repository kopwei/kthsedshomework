#
# Analyser Tool Makefile
#
# Please see README.TXT for required libraries and instalation procedures
#

CC=g++

#CFLAGS=-w -g -g3 -fmessage-length=0
CFLAGS=-w -g -O0 -fmessage-length=0

SOURCES_CAP =$(shell ls ./src/*.cpp |grep -v readBinaryFlow.cpp)
SOURCES_READ=$(shell ls ./src/*.cpp |grep -v analyserpxCaptureMain.cpp | grep -v analyserpxAggreg.cpp )

OBJS_CAP =$(SOURCES_CAP:.cpp=.o)
OBJS_READ=$(SOURCES_READ:.cpp=.o)

EXEC_CAP =bin/analyserpx_cap
EXEC_READ=bin/analyserpx_read

INCLUDES=

LIBS_CAP =-lpcap -lpthread
LIBS_READ=-lpcap -lpthread

all: Cap Read

Cap: $(SOURCES_CAP) $(EXEC_CAP)

Read: $(SOURCES_READ) $(EXEC_READ)

%.o: %.cpp

	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

$(EXEC_CAP): $(OBJS_CAP)

	$(CC) $(CFLAGS) $(OBJS_CAP) $(LIBS_CAP) -o $@

$(EXEC_READ): $(OBJS_READ)

	$(CC) $(CFLAGS) $(OBJS_READ) $(LIBS_READ) -o $@

clean: 

	cd ./src && rm -f *~ *.o; cd ..

	rm -f $(EXEC_CAP)

	rm -f $(EXEC_READ)

	@echo

	@echo " ** To recompile, please type \"make\" **"

	@echo

