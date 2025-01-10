package com.example.myapplication

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NameChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navUserNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        val navView: BottomNavigationView = binding.navView
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_memo, R.id.navigation_calendar, R.id.navigation_edit, R.id.navigation_history), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Navigation Header Updates
        val headerView = binding.mainDrawerView.getHeaderView(0)
        val userImageView = headerView.findViewById<ImageView>(R.id.userImageView)
        navUserNameTextView = headerView.findViewById(R.id.userNameTextView)

        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        val savedUserName = sharedPreferences.getString("userName", "Admin")
        navUserNameTextView.text = savedUserName

        val byteArray = intent.getByteArrayExtra("image")
        byteArray?.let {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            userImageView.setImageBitmap(bitmap)
        }

        // Update name from received intent
        intent.getStringExtra("newUserName")?.let {
            saveUserProfile(it)
            navUserNameTextView.text = it
        }

        // Set Navigation Item Selected Listener
        binding.mainDrawerView.setNavigationItemSelectedListener(this)
    }

    private fun saveUserProfile(newUserName: String) {
        val sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userName", newUserName)
        editor.apply()
    }

    override fun onNameChanged(newName: String) {
        navUserNameTextView.text = newName
        saveUserProfile(newName)
    }

    class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments: List<Fragment> = listOf(OneFragment(), TwoFragment(), ThreeFragment())

        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu2 -> openWebPage("https://www.notion.so/453f7e0a781f4e49b4a05054f40f78c3")
            R.id.menu4 -> openWebPage("https://www.tukorea.ac.kr")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openWebPage(url: String) {
        val webPage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No application can handle this request. Please install a web browser.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return when (item.itemId) {
            R.id.develop1 -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.develop2 -> {
                startActivity(Intent(this, DevelopActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        playSelectedRingtone()
    }

    private fun playSelectedRingtone() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedRingtone = sharedPreferences.getString("selectedRingtone", "기본")

        // 여기서 선택된 효과음에 따라 재생 로직을 구현하세요.
        when (selectedRingtone) {
            "기본" -> {
                // 기본 효과음 재생
            }
            "물방울" -> {
                // 물방울 효과음 재생
            }
            // 다른 효과음에 대한 경우도 처리하세요.
            else -> {
                // 다른 효과음 재생
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("앱 종료")
        builder.setMessage("정말로 앱을 종료하시겠습니까?")
        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        builder.setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
