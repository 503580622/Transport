//
// Created by Administrator on 2016/9/19 0019.
//

#include <jni.h>
#include <string>
#include <GLES/gl.h>
#include <GLES/glext.h>
#include <android/cpufeatures/cpu-features.h>
#include "HelloWorld.h"

char *HelloWorld::Test() {
	return "String from C++ Body1";
}

extern "C" {
jstring Java_com_jiahelogistic_activity_MainAppCompatActivity_HelloWorld(
		JNIEnv *env, jobject thiz) {
	HelloWorld *hw = new HelloWorld();
	std::string hello = hw->Test();


	const GLubyte* version = glGetString(GL_VENDOR);
	__android_log_print(ANDROID_LOG_ERROR, "GLES", "the glGetString is %s", version);
	return env->NewStringUTF(hello.c_str());
}
}