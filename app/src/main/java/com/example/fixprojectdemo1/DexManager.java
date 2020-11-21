package com.example.fixprojectdemo1;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class DexManager {

    private Context mContext;

    public DexManager(Context context) {
        this.mContext = context;
    }

    public void load(File file) {
        try {
            DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                    new File(mContext.getCacheDir(), "opt").getAbsolutePath(),
                    Context.MODE_PRIVATE);
            if (null != dexFile) {
                //当前Dex里所以的Class类名集合
                Enumeration<String> enumerations = dexFile.entries();
                if (null != enumerations) {
                    while (enumerations.hasMoreElements()) {
                        String clazzName = enumerations.nextElement();
                        if (TextUtils.equals(clazzName, "Calculator")) {
                            Class realClazz = dexFile.loadClass(clazzName,
                                    mContext.getClassLoader());
                            if (null != realClazz) {
                                fixClazz(realClazz);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixClazz(Class realClazz) {
        Method[] methods = realClazz.getDeclaredMethods();
        for (Method rightMethod : methods) {
            Replace replace = rightMethod.getAnnotation(Replace.class);
            if (null == replace) {
                continue;
            }
            String clazzName = replace.clazz();
            String methodName = replace.method();

            try {
                Class wrongClazz = Class.forName(clazzName);
                Method wrongMethod =
                        wrongClazz.getMethod(methodName, rightMethod.getParameterTypes());
                //Native 中进行替换ArtMethod相关变量
                doReplace(wrongMethod,rightMethod);

            } catch (ClassNotFoundException | NoSuchMethodException e) {//105
                e.printStackTrace();
            }
        }

    }

    public static native void doReplace(Method wrongMethod, Method rightMethod);

}
