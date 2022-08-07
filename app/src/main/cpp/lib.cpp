//
// Created by Darot on 06/08/2022.
//

#include <jni.h>
#include <string>
extern "C" JNIEXPORT jstring
Java_com_darothub_theweatherapp_Keys_apiKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "a3dcb83d3dca466c9a4155723220501";
    return env->NewStringUTF(api_key.c_str());
}