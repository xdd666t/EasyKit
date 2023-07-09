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

abstract class BaseRecyclerViewAdapter<T> :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<T>.BaseViewHolder> {
    protected var mList: MutableList<T>?
    var context: Context? = null
        protected set

    constructor() {
        mList = ArrayList()
    }

    constructor(list: MutableList<T>?) {
        mList = list
    }

    constructor(context: Context?, list: MutableList<T>?) {
        mList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        var view: View?
        view = setViewBinding(parent)
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(getItemLayoutId(viewType), parent, false)
        }
        setContentView(view)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindDataToView(holder, mList!![position], position)
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

    inner class BaseViewHolder(itemView: View?) : ViewHolder(itemView!!) {
        private val mViews: SparseArray<View?> = SparseArray()
        lateinit var mItemView: View

        init {
            this.mItemView = itemView!!
            this.mItemView.setOnClickListener {
                if (mOnItemClickListener != null && mList!!.size != 0) {
                    mOnItemClickListener!!.onItemClick(adapterPosition)
                }
                onItemClick(adapterPosition)
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
     * 绑定布局id
     *
     * @param viewType
     * @return
     */
    protected abstract fun getItemLayoutId(viewType: Int): Int

    /**
     * 绑定数据
     *
     * @param holder
     * @param t
     */
    protected abstract fun onBindDataToView(holder: BaseViewHolder?, t: T, position: Int)

    /**
     * ItemView里的某个子控件的单击事件(如果需要，重写此方法就行)
     * 只有在[.onBindDataToView]注册了[BaseViewHolder.setClickListener]才有效果
     *
     * @param position
     */
    protected fun onSingleViewClick(view: View?, position: Int) {}

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
    protected fun setTextChangedListener(holder: BaseViewHolder?, itemView: View?) {}
    protected fun setRatingBarListener(holder: BaseViewHolder?, itemView: View?) {}

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