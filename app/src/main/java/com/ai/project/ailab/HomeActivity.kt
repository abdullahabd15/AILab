package com.ai.project.ailab

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.project.ailab.activity_lifecycle.FirstActivity
import com.ai.project.ailab.custom_call_screen.DialerActivity
import com.ai.project.ailab.data.MenuData
import com.ai.project.ailab.navigation_activty.AActivity
import com.ai.project.ailab.recyclerView.RecyclerViewSampleActivity
import com.ai.project.libui.AiActivity
import com.ai.project.libui.AiRecyclerView
import com.ai.project.libui.AiRecyclerViewAdapter
import com.ai.project.libui.RecyclerViewItem

class HomeActivity : AiActivity(), RecyclerViewItem.ItemListener<MenuData> {
    private var recyclerView: AiRecyclerView? = null
    private var adapter: AiRecyclerViewAdapter<MenuData>? = null
    private var menuList: MutableList<MenuData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            initComponent()
            setDataToUI()
        } catch (e: Exception) {
            showErrorDialog(e)
        }
    }

    override fun onRecyclerViewItemClicked(position: Int, view: View?, data: MenuData?) {
        when {
            data?.title.equals("Recycler View") -> startActivity(Intent(applicationContext, RecyclerViewSampleActivity::class.java))
            data?.title.equals("Navigation Menu") -> startActivity(Intent(applicationContext, AActivity::class.java))
            data?.title.equals("Activity Lifecycle") -> startActivity(Intent(applicationContext, FirstActivity::class.java))
            data?.title.equals("Custom Dial Phone") -> startActivity(Intent(applicationContext, DialerActivity::class.java))
        }
    }

    private fun initComponent() {
        initActionBar()
        initRecyclerView()
    }

    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar?.title = "Home"
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = findViewById(R.id.rv_menu_list)
        recyclerView?.setDefaultDecoration()
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = layoutManager
        adapter = AiRecyclerViewAdapter(this, R.layout.item_menu, menuList)
        adapter?.setRecyclerListener(this)
        recyclerView?.adapter = adapter
    }

    private fun setDataToUI() {
        val recyclerViewMenu = MenuData()
        recyclerViewMenu.title = "Recycler View"
        recyclerViewMenu.subTitle = "Contoh Penggunaan Recycler View"

        val navigationMenu = MenuData()
        navigationMenu.title = "Navigation Menu"
        navigationMenu.subTitle = "Contoh Navigation Activity Android"

        val lifecycleMenu = MenuData()
        lifecycleMenu.title = "Activity Lifecycle"
        lifecycleMenu.subTitle = "Contoh Activity Lifecycle"

        val dialerMenu = MenuData()
        dialerMenu.title = "Custom Dial Phone"
        dialerMenu.subTitle = "Contoh Dial Phone dengan Custom Call Screen"

        menuList.add(recyclerViewMenu)
        menuList.add(navigationMenu)
        menuList.add(lifecycleMenu)
        menuList.add(dialerMenu)
        adapter?.notifyDataSetChanged()
    }
}
