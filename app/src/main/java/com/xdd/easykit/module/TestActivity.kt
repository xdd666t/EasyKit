package com.xdd.easykit.module

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.xdd.easykit.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        Logger.d("----------------999999999999");
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        Logger.d("++++++++++++++++++++++++++++++++");
        return true
    }
}