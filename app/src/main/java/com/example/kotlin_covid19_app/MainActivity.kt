package com.example.kotlin_covid19_app

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlin_covid19_app.common.PREFERENCE_NAME
import com.example.kotlin_covid19_app.ui.dashboard.DashboardFragment
import com.example.kotlin_covid19_app.ui.home.HomeFragment
import com.example.kotlin_covid19_app.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private  fun init(){
        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        supportActionBar?.hide()

        val tab : Int = preference.getInt("KEY-TAB",0)
        when(tab){
            0 ->{
                openFragment(HomeFragment())
            }
            1 ->{
                openFragment(DashboardFragment())
            }
            2 ->{
                openFragment(NotificationsFragment())
            }
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_home ->{
//                viewPager.currentItem = 0
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 0)
                prefEditor.apply()
                openFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.navigation_dashboard ->{
//                viewPager.currentItem = 1
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 1)
                prefEditor.apply()
                openFragment(DashboardFragment())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.navigation_notifications ->{
//                viewPager.currentItem = 2
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 2)
                prefEditor.apply()
                openFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true;
            }
        }
        false
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}