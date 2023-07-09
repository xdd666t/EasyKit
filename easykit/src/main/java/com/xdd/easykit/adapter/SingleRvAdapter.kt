package com.xdd.easy_kit.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.xdd.easy_kit.adapter.SingleRvAdapter.SingleViewHolder

class SingleRvAdapter<T> : RecyclerView.Adapter<SingleRvAdapter<T>.SingleViewHolder?> {
    private var mList: MutableList<T>?
    var context: Context? = null
    private var layoutId = 0

    constructor() {
        mList = ArrayList()
    }

    constructor(layoutId: Int) {
        mList = ArrayList()
        this.layoutId = layoutId
    }

    constructor(
        mutableList: MutableList<T>?,
        layoutId: Int,
        onBindDataToView: ((holder: SingleRvAdapter<T>.SingleViewHolder?, t: T) -> Unit)?
    ) {
        var list = mutableList
        if (list == null) {
            list = ArrayList()
        }
        mList = list
        this.layoutId = layoutId
        this.onBindDataToView = onBindDataToView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return SingleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        onBindDataToView?.let { it(holder, mList!![position]) }
    }

    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    fun updateData(list: MutableList<T>?) {
        var list = list
        if (list == null) {
            list = ArrayList()
        }
        mList = list
        notifyDataSetChanged()
    }

    fun add(bean: T) {
        mList!!.add(bean)
        notifyDataSetChanged()
    }

    fun addAll(beans: List<T>?) {
        mList!!.addAll(beans!!)
        notifyDataSetChanged()
    }

    fun clear() {
        mList!!.clear()
        notifyDataSetChanged()
    }

    inner class SingleViewHolder(itemView: View?) : ViewHolder(itemView!!) {
        private val mViews: SparseArray<View?> = SparseArray()
        var mItemView: View

        init {
            this.mItemView = itemView!!
            this.mItemView.setOnClickListener {
                if (mOnItemClickListener != null && mList!!.size != 0) {
                    mOnItemClickListener!!.onItemClick(absoluteAdapterPosition)
                }
            }
        }

        fun <T : View?> getView(viewId: Int): T? {
            var view = mViews[viewId]
            if (view == null) {
                view = itemView.findViewById(viewId)
                mViews.put(viewId, view)
            }
            return view as T?
        }
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param t
     */
    private var onBindDataToView: ((holder: SingleViewHolder?, t: T) -> Unit)? =
        null


    /**
     * 点击布局监听
     */
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }
}