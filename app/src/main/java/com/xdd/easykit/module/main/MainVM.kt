package com.xdd.easy_kit.module.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 作者: 余涛
 * 功能描述: 请描述该文件的功能
 * 创建时间: 2020/5/25 22:52
 */
class MainVM : ViewModel() {
    //动态绑定View变化，类似小程序的setData
    var msg = MutableLiveData<String>()

    //监听一些数据变化，view层做相应处理
    val list = MutableLiveData<MutableList<String>>()

    init {
        msg.value = "葬爱"
        list.value = mutableListOf(
            "进入Dialog",
            "进入File",
            "进入DB",
            "进入Net",
            "进入View",
            "进入Test"
        )
    }
}