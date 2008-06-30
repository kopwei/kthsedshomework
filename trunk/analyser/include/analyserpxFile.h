/**
*	 This module provides facilities to file manipulation 
*/


#ifndef _ANALYSERPXFILE_H_
#define _ANALYSERPXFILE_H_

#include "analyserpxTypes.h"


class CFileUtil
{
public:
	/*!     \file analyserpxFile.h
	\brief Manipulation file header file.

	This file contains the procedures declaration to deal with input/output file system.
	*/

	/*!     \fn void writeStringToLogFile (const char *fileName, const char *str, char *errbuff)
	\brief Save string to a log file.

	This function receives a character string srt and tries to store on file called filename. If any error takes place, an especific message will be notified by errbuff string.
	\param *fileName		log file name
	\param *str		string to be saved into log file
	\param *errbuff		string buffer used to report error. Pass NULL to ignore it
	*/
	static void writeStringToLogFile (const char *fileName, const char *str, char *errbuff);

	/*!     \fn FILE *openFile (const char *fileName, const char *mode, char *errbuff, char *logFile)
	\brief Open a file.

	Open a file from the local file system according to given parameters.
	\param *fileName	file name.
	\param *mode		open mode, e.g., a+, r, w...
	\param *errbuff		string buffer used to report error. Pass NULL to ignore it.
	\param *logFile  	log file name, to report errors. Use NULL to ignore. Valid only if errbuff is not NULL.
	\return 		File pointer.
	*/
	static FILE *openFile (const char *fileName, const char *mode, char *errbuff, char *logFile);

	/*!     \fn void closeFile(FILE *p)
	\brief Close a file.

	Try to close a opened file from a File pointer parameter.
	\param *p 		FILE pointer to be closed
	*/
	static void closeFile(FILE *p);

	/*!     \fn int existFile(char *fileName)
	\brief Verify the existence of a file.

	\param *fileName	file's name
	\return			A non zero number is return if the file exist.
	*/
	static int existFile(char *fileName);


	static long getSizeofFile(char *fileName);

	static int deleteFile(char *fileName);

};

#endif
