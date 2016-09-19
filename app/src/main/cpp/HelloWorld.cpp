//
// Created by Administrator on 2016/9/19 0019.
//

#include <jni.h>
#include <string>
#include "HelloWorld.h"

char* HelloWorld::Test() {
	return "String from C++ Body1";
}

extern "C"
jstring
Java_com_jiahelogistic_activity_MainAppCompatActivity_HelloWorld(
		JNIEnv *env, jobject /* this */) {
	HelloWorld* hw = new HelloWorld();
	std::string hello = hw->Test();
	return env->NewStringUTF(hello.c_str());
}