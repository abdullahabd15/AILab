package com.ai.project.ailab

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.project.ailab.activity_lifecycle.FirstActivity
import com.ai.project.ailab.custom_call_screen.DialerActivity
import com.ai.project.ailab.data.MenuData
import com.ai.project.ailab.login.LoginActivity
import com.ai.project.ailab.login.RegisterUserActivity
import com.ai.project.ailab.navigation_activty.AActivity
import com.ai.project.ailab.recyclerView.RecyclerViewSampleActivity
import com.ai.project.libui.AiActivity
import com.ai.project.libui.AiRecyclerView
import com.ai.project.libui.AiRecyclerViewAdapter
import com.ai.project.libui.RecyclerViewItem
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.concurrent.thread

class HomeActivity : AiActivity(), RecyclerViewItem.ItemListener<MenuData> {
    private var recyclerView: AiRecyclerView? = null
    private var adapter: AiRecyclerViewAdapter<MenuData>? = null
    private var menuList: MutableList<MenuData> = ArrayList()
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isPressedTwice = false

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            initObject()
            initComponent()
            getDataFromIntent()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_logout) {
            auth.signOut()
            googleSignInClient.signOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Handler().postDelayed({
            isPressedTwice = false
        }, 2000)
        showAlertToast("Tekan sekali lagi untuk keluar")
        if (!isPressedTwice) {
            isPressedTwice = true
        } else {
            moveTaskToBack(true)
        }
    }

    private fun initObject() {
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
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

    private fun getDataFromIntent() {
        val bundle = intent.extras
        user = bundle?.getParcelable("firebaseUser") as FirebaseUser
    }

    private fun setDataToUI() {
        tv_user_name.text = "Mr. " + user.displayName
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
