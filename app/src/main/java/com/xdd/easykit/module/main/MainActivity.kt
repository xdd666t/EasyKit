package com.xdd.easykit.module.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.xdd.easy_kit.adapter.SingleRvAdapter
import com.xdd.easy_kit.module.main.MainVM
import com.xdd.easykit.R
import com.xdd.easykit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var mIntent: Intent? = null
    private var mSingleRvAdapter: SingleRvAdapter<String>? = null
    private lateinit var mMainVM: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        //和当前Activity生命周期绑定
        mMainVM = ViewModelProvider(this)[MainVM::class.java]
        mIntent = Intent()
        setAdapter()

        mMainVM.msg.observe(this) {
            mBinding.tvTest.text = it
        }
    }


    private fun setAdapter() {
        //列表实现
        mBinding.rvModel.layoutManager = GridLayoutManager(this, 2)
        mSingleRvAdapter =
            SingleRvAdapter(mMainVM.list.value, R.layout.item_normal) { holder, msg ->
                val textView: TextView? = holder?.getView(R.id.tv_content)
                textView?.text = msg
            }
        mSingleRvAdapter?.setOnItemClickListener(object : SingleRvAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> {
//                        //开始埋点，“app”是最后生成的性能分析文件
//                        Debug.startMethodTracing("App")
//                        mIntent!!.setClass(context, DialogActivity::class.java)
//                        startActivity(mIntent)
//
//                        //埋点结束，期间start 到 stop 之间的代码，就是你要测试的代码范围
//                        Debug.stopMethodTracing()
                    }

                    1 -> {
//                        mIntent!!.setClass(context, FileActivity::class.java)
//                        startActivity(mIntent)
                    }

                    2 -> {
//                        mIntent!!.setClass(context, DBActivity::class.java)
//                        startActivity(mIntent)
                    }

                    3 -> {
//                        mIntent!!.setClass(context, NetActivity::class.java)
//                        startActivity(mIntent)
                    }

                    4 -> {
//                        mIntent!!.setClass(context, UserViewActivity::class.java)
//                        startActivity(mIntent)
                    }

                    5 -> {
//                        mIntent!!.setClass(context, TestActivity::class.java)
//                        startActivity(mIntent)
                    }
                }
            }
        })
        mBinding.rvModel.adapter = mSingleRvAdapter
        mSingleRvAdapter?.updateData(mMainVM.list.value)
    }
}