package com.duddu.antitampering;

import android.os.Debug;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.lang.reflect.Field;


class DebugDetection {

    public static void check(Context context) throws Exception {
        if (hasDebuggerAttached()) {
            throw new Exception("Debugger attached");
        } else if (getDebugField(context)) {
            throw new Exception("App running in Debug mode");
        } else if (isAppDebuggable(context)) {
            throw new Exception("App running in Debug mode");
        }
    }

    private static Boolean getDebugField(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> buildConfigClass = Class.forName(context.getPackageName().concat(".BuildConfig"));
        Field debugField = buildConfigClass.getField("DEBUG");
        return debugField.getBoolean(null);
    }

    private static Boolean hasDebuggerAttached() {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

    private static boolean isAppDebuggable(Context context) {
        if (context == null) {
            return false;
        }
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
