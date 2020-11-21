#include <jni.h>
#include <string>
#include "art_method.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_fixprojectdemo1_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_fixprojectdemo1_DexManager_doReplace(JNIEnv *env, jclass clazz,
                                                      jobject wrong_method, jobject right_method) {
//ArtMethod存在于Android 系统源码中，只需要导入我们需要的部分（art_method.h）
    art::mirror::ArtMethod *wrong = (art::mirror::ArtMethod *) env->FromReflectedMethod(
            wrong_method);
    art::mirror::ArtMethod *right = (art::mirror::ArtMethod *) env->FromReflectedMethod(
            right_method);
    //    method   --->class ----被加载--->ClassLoader
    //错误的成员变量替换为正确的成员变量
    wrong->declaring_class_ = right->declaring_class_;
    wrong->dex_cache_resolved_methods_ = right->dex_cache_resolved_methods_;
    wrong->access_flags_ = right->access_flags_;
    wrong->dex_cache_resolved_types_ = right->dex_cache_resolved_types_;
    wrong->dex_code_item_offset_ = right->dex_code_item_offset_;
    //    这里   方法索引的替换
    wrong->method_index_ = right->method_index_;
    wrong->dex_method_index_ = right->dex_method_index_;

}