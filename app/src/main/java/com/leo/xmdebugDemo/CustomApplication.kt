package com.leo.xmdebugDemo

import android.app.Application
import com.leo.xmdebug.utils.DebugCrashHandler

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DebugCrashHandler.setCrashHandler(this)
    }
}