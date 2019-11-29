package com.ai.project.ailab

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.project.libui.AiActivity
import com.ai.project.libui.AiRecyclerView
import com.ai.project.libui.AiRecyclerViewAdapter
import com.ai.project.libui.RecyclerViewItem

class RecyclerViewSampleActivity : AiActivity(), RecyclerViewItem.ItemListener<MenuData> {
    private var aiRecyclerView: AiRecyclerView? = null
    private var adapter: AiRecyclerViewAdapter<MenuData>? = null
    private var menuDataList: MutableList<MenuData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_recycler_view_sample)
            initComponent()
            setMenuData()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onRecyclerViewItemClicked(position: Int, view: View?, data: MenuData?) {
        showAlertToast("${data?.title} Clicked")
    }

    private fun initComponent() {
        initActionBar()
        initRecyclerView()
    }

    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar?.title = "Main Menu"
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        aiRecyclerView = findViewById(R.id.rv_menu)
        aiRecyclerView?.setDefaultDecoration()
        aiRecyclerView?.setHasFixedSize(true)
        aiRecyclerView?.layoutManager = layoutManager
        adapter = AiRecyclerViewAdapter(this, R.layout.item_menu, menuDataList)
        adapter?.setRecyclerListener(this)
        aiRecyclerView?.adapter = adapter
    }

    private fun setMenuData() {
        val menu1 = MenuData()
        menu1.title = "Menu 1"
        menu1.subTitle = "Ini adalah menu 1"

        val menu2 = MenuData()
        menu2.title = "Menu 2"
        menu2.subTitle = "Ini adalah menu 2"

        val menu3 = MenuData()
        menu3.title = "Menu 3"
        menu3.subTitle = "Ini adalah menu 3"

        val menu4 = MenuData()
        menu4.title = "Menu 4"
        menu4.subTitle = "Ini adalah menu 4"

        val menu5 = MenuData()
        menu5.title = "Menu 5"
        menu5.subTitle = "Ini adalah menu 5"

        val menu6 = MenuData()
        menu6.title = "Menu 6"
        menu6.subTitle = "Ini adalah menu 6"

        val menu7 = MenuData()
        menu7.title = "Menu 7"
        menu7.subTitle = "Ini adalah menu 7"

        val menu8 = MenuData()
        menu8.title = "Menu 8"
        menu8.subTitle = "Ini adalah menu 8"

        val menu9 = MenuData()
        menu9.title = "Menu 9"
        menu9.subTitle = "Ini adalah menu 9"

        val menu10 = MenuData()
        menu10.title = "Menu 10"
        menu10.subTitle = "Ini adalah menu 10"

        val menu11 = MenuData()
        menu11.title = "Menu 11"
        menu11.subTitle = "Ini adalah menu 11"

        val menu12 = MenuData()
        menu12.title = "Menu 12"
        menu12.subTitle = "Ini adalah menu 12"

        menuDataList.add(menu1)
        menuDataList.add(menu2)
        menuDataList.add(menu3)
        menuDataList.add(menu4)
        menuDataList.add(menu5)
        menuDataList.add(menu6)
        menuDataList.add(menu7)
        menuDataList.add(menu8)
        menuDataList.add(menu9)
        menuDataList.add(menu10)
        menuDataList.add(menu11)
        menuDataList.add(menu12)
        adapter?.notifyDataSetChanged()
    }
}
