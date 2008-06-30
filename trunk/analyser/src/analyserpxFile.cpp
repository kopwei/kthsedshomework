#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../include/analyserpxFile.h"


void CFileUtil::writeStringToLogFile(const char *fileName, const char *str,
			  char *errbuff)
{
    FILE *logs = fopen(fileName, "a+");
    if (!logs) {
	if (errbuff != NULL) {
	    snprintf(errbuff,strlen("\nError openning log file: \n")+strlen(fileName)+1, "\nError openning log file: %s\n", fileName);
	}
	return;
    }
    fprintf(logs, "%s\n", str);
    closeFile(logs);
}

FILE* CFileUtil::openFile(const char *fileName, const char *mode, char *errbuff,
	       char *logFile)
{
    FILE *p = fopen(fileName, mode);
    if (!p) {
	if (errbuff != NULL) {
	    strncpy(errbuff, "",1);
	    snprintf(errbuff, strlen("\nError openning file: \n")+strlen(fileName)+1,"\nError openning file: %s\n", fileName);

	    if (logFile != NULL) {
		writeStringToLogFile(logFile, errbuff, errbuff);
	    }
	    return NULL;
	}
    }
    return p;
}

void CFileUtil::closeFile(FILE * p)
{
    if (p == NULL)
	return;
    fclose(p);
}

long CFileUtil::getSizeofFile(char *fileName)
{
    FILE *file = openFile(fileName, "r", NULL, NULL);
    long size = 0;
    if (file == NULL)
	return size;
    fseek(file, 0, SEEK_END);
    size = ftell(file);
    fclose(file);
    return size;
}

int CFileUtil::existFile(char *fileName)
{
    FILE *f = fopen(fileName, "r");
    if (f == NULL)
	return 0;
    else
	closeFile(f);
    return 1;
}

int CFileUtil::deleteFile(char *fileName)
{
    if (existFile(fileName)) {
	return remove(fileName);
    }
    return 0;
}
