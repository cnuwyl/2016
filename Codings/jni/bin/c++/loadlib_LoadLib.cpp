#include <jni.h>
#include <stdio.h>
#include <string.h>
#include<vector>
#include<fstream>
#include<iostream>

#include "loadlib_LoadLib.h"

using namespace std;
vector <string> WordVec;

JNIEXPORT jboolean JNICALL Java_loadlib_LoadLib_Init
	(JNIEnv *env, jclass obj, jstring line)
{
	 const char *pFileName = NULL;
	 pFileName = env->GetStringUTFChars(line,false);
	 if(pFileName == NULL)
		 return false;
	 ifstream in ( pFileName);
	 if( !in)
	 {
		 cerr << "can not open the file of " << pFileName << endl;
		 return false;
	 }
	 string sWord;
	 while( getline(in, sWord))
	 {
		 WordVec.push_back(sWord);

	 }
	 return true;
}


JNIEXPORT jstring JNICALL Java_loadlib_LoadLib_SegmentALine
(JNIEnv *env, jclass obj, jstring line)
{
      char buf[128];
      buf[0] = 0;
      const char *str = NULL;
      str = env->GetStringUTFChars(line, false);
      if (str == NULL)
    	  return NULL;
      strcpy (buf, str);
     if(!WordVec.empty()){
            	 strcat (buf, WordVec[0].c_str());
     }
    	//strcat (buf, "c++ codes!");
      env->ReleaseStringUTFChars(line, str);
      return env->NewStringUTF(buf);
}

