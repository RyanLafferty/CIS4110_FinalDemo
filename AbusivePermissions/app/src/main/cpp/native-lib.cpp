#include <jni.h>
#include <string>
#include <array>
#include <cstdio>
#include <iostream>
#include <memory>

/* Information: Android seems to restrict the available system calls that can be used
 * in a JNI library
 *
*/

extern "C"
jstring
Java_lafferty_com_abusivepermissions_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//create a file without permissions
extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_cCall(
        JNIEnv* env,
jobject obj) {
    char cmd [] = "monkey /storage/emulated/0/a.txt";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}


//delete a file without permissions
extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_dCall(
        JNIEnv* env,
        jobject obj) {
    char cmd [] = "rm /storage/emulated/0/a.txt";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}



extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_lsCall(
        JNIEnv * env,
        jobject obj,
        jstring jinput) {
    const char * input = env->GetStringUTFChars(jinput, (jboolean *) JNI_FALSE);
    printf("input = %s\n", input);
    char cmd [] = "ls";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(input, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    if(input != NULL)
    {
        env->ReleaseStringUTFChars(jinput, input);
    }

    return env->NewStringUTF(result.c_str());
}

//curl call without permissions
extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_curlCall(
        JNIEnv* env,
        jobject obj) {
    char cmd [] = "curl https://www.google.ca/";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}

extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_upCall(
        JNIEnv* env,
        jobject obj) {
    char cmd [] = "uptime";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}

//start -a android.intent.action.VIEW -d http://www.stackoverflow.com
extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_webCall(
        JNIEnv* env,
        jobject obj) {
    char cmd [] = "am start -a android.intent.action.VIEW -d http://www.stackoverflow.com";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}

extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_suCall(
        JNIEnv* env,
        jobject obj) {
    char cmd [] = "chmod 4755 /system/bin/su";

    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}

extern "C"
jstring
Java_lafferty_com_abusivepermissions_ShellActivity_suTestCall(
        JNIEnv* env,
        jobject obj) {

    char cmd [] = "su echo a";
    std::array<char, 128> buffer;
    std::string result = "";

    //open pipe
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);

    //check if pipe was opened
    if (!pipe)
    {
        throw std::runtime_error("popen() failed!");
    }

    //get result
    while (!feof(pipe.get()))
    {
        if (fgets(buffer.data(), 128, pipe.get()) != NULL)
        {
            result += buffer.data();
        }
    }

    return env->NewStringUTF(result.c_str());
}