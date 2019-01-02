package com.leo.xmdebugDemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.leo.xmdebug.DebugHomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugHomeActivity.enterActivity(this)
        finish()
    }
}
