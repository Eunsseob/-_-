package com.example.myapplication

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityOptionBinding
import com.google.android.material.tabs.TabLayoutMediator

class OptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityOptionBinding

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        val fragments: List<Fragment>
        init {
            fragments = listOf(FourFragment(), SixFragment(), SevenFragment(), EightFragment())
            Log.d("kkang", "fragments size : ${fragments.size}")
        }
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "설정"

        // Set up ViewPager2 with the adapter
        val pagerAdapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = pagerAdapter

        // Connect TabLayout and ViewPager2
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "프로필 설정"
                1 -> "알림 설정"
                2 -> "스타일 변경"
                3 -> "문의하기"
                else -> "Tab ${position + 1}"
            }
        }.attach()
    }

}

