package com.ai.project.ailab

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.ai.project.libui.RecyclerViewItem

class MenuItemView: RecyclerViewItem<MenuData> {
    private var tvTitle: TextView? = null
    private var tvSubTitle: TextView? = null
    private var imgMenu: ImageView? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setData(data: MenuData?) {
        try {
            super.setData(data)
            initComponent()
            initListener(data)
            setDataToUI(data)
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    private fun setDataToUI(data: MenuData?) {
        tvTitle?.text = data?.title
        tvSubTitle?.text = data?.subTitle
        //imgMenu?.setImageBitmap(data?.bitmap)
    }

    private fun initComponent() {
        tvTitle = findViewById(R.id.tv_title)
        tvSubTitle = findViewById(R.id.tv_subtitle)
        imgMenu = findViewById(R.id.img_menu)
    }

    private fun initListener(data: MenuData?) {
        setOnClickListener {
            listener.onRecyclerViewItemClicked(verticalScrollbarPosition, it, data)
        }
    }
}