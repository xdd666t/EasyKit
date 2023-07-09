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
        list: MutableList<T>?,
        layoutId: Int,
        onBindDataToView: ((holder: SingleRvAdapter<T>.SingleViewHolder?, t: T) -> Unit)?
    ) {
        var list = list
        if (list == null) {
            list = ArrayList()
        }
        mList = list
        this.layoutId = layoutId
        this.onBindDataToView = onBindDataToView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        context = parent.context
        var view: View?
        view = setViewBinding(parent)
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(layoutId, parent, false)
        }
        setContentView(view)
        return SingleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        onBindDataToView?.let { it(holder, mList!![position]) }
    }

    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    //获取item的view，可以做一些适配操作
    fun setContentView(view: View?) {}
    fun setViewBinding(parent: ViewGroup?): View? {
        return null
    }

    fun setDataList(list: MutableList<T>?) {
        mList = list
    }

    val dataList: List<T>?
        get() = mList

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
        lateinit var mItemView: View

        init {
            this.mItemView = itemView!!
            this.mItemView.setOnClickListener {
                if (mOnItemClickListener != null && mList!!.size != 0) {
                    mOnItemClickListener!!.onItemClick(adapterPosition)
                    onItemClick(adapterPosition)
                }
            }
            this.mItemView.setOnLongClickListener {
                if (mOnItemLongClickListener != null && mList!!.size != 0) {
                    mOnItemLongClickListener!!.onLongClick(adapterPosition)
                }
                false
            }
            setTextChangedListener(this, itemView)
            setRatingBarListener(this, itemView)
        }

        fun <T : View?> getView(viewId: Int): T? {
            var view = mViews[viewId]
            if (view == null) {
                view = itemView.findViewById(viewId)
                mViews.put(viewId, view)
            }
            return view as T?
        }

        fun setText(viewId: Int, text: String?) {
            val tv = getView<TextView>(viewId)!!
            tv.text = text
        }

        fun setImage(viewId: Int, params: Any?) {
            val iv = getView<ImageView>(viewId)!!
            if (params is String) {
                //自己写加载图片的逻辑
            } else if (params is Int) {
                iv.setImageResource((params as Int?)!!)
            } else if (params is Bitmap) {
                iv.setImageBitmap(params as Bitmap?)
            } else if (params is Drawable) {
                iv.setImageDrawable(params as Drawable?)
            } else {
                try {
                    throw Exception("params is wrong!")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun setClickListener(viewId: Int) {
            val v = getView<View>(viewId)!!
            v.setOnClickListener { v -> onSingleViewClick(v, adapterPosition) }
        }

        val currentPosition: Int
            get() = adapterPosition
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
     * ItemView里的某个子控件的单击事件(如果需要，重写此方法就行)
     * 只有在[.onBindDataToView]注册了[SingleViewHolder.setClickListener]才有效果
     *
     * @param position
     */
    protected

    fun onSingleViewClick(view: View?, position: Int) {
    }

    /**
     * ItemView的单击事件(如果需要，重写此方法就行)
     *
     * @param position
     */
    protected fun onItemClick(position: Int) {}

    /**
     * 为edittext添加文本监听(如果需要，重写此方法就行)
     *
     * @param holder
     * @param itemView
     */
    protected fun setTextChangedListener(holder: SingleViewHolder?, itemView: View?) {}
    protected fun setRatingBarListener(holder: SingleViewHolder?, itemView: View?) {}

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

    /**
     * 长按布局监听
     */
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    interface OnItemLongClickListener {
        fun onLongClick(position: Int)
    }

    fun setOnItemClickListener(onItemLongClickListener: OnItemLongClickListener?) {
        mOnItemLongClickListener = onItemLongClickListener
    }
}