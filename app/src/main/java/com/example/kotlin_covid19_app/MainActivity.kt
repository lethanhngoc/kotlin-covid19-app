package com.example.kotlin_covid19_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.kotlin_covid19_app.di.*
import com.example.kotlin_covid19_app.util.Constant
import com.example.kotlin_covid19_app.ui.dashboard.DashboardActivity
import com.example.kotlin_covid19_app.ui.dashboard.DashboardFragment
import com.example.kotlin_covid19_app.ui.home.HomeFragment
import com.example.kotlin_covid19_app.ui.notifications.NotificationsFragment
import com.example.kotlin_covid19_app.util.PREFERENCE_NAME
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MainActivity : AppCompatActivity() {

    private val calConfig: CalligraphyConfig by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this);

        startKoin {
            androidContext(this@MainActivity)
            modules(networkModule)
            modules(persistenceModule)
            modules(repositoryModule)
            modules(appModule)
            modules(viewModelModule)
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        CalligraphyConfig.initDefault(calConfig)
        Hawk.init(applicationContext).setLogInterceptor { message ->
            if (BuildConfig.DEBUG) {
                Log.d("Hawk", message)
            }
        }.build()

        setContentView(R.layout.activity_main)

        init()
    }

    private  fun init(){
        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        supportActionBar?.hide()

        val tab : Int = preference.getInt("KEY-TAB",0)
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
//        when(tab){
//            0 ->{
//                startActivity(Intent(applicationContext,DashboardActivity::class.java))
//            }
//            1 ->{
//                startActivity(Intent(applicationContext,DashboardActivity::class.java))
//            }
//            2 ->{
//                startActivity(Intent(applicationContext,DashboardActivity::class.java))
//            }
//        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_home ->{
//                viewPager.currentItem = 0
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 0)
                prefEditor.apply()
                startActivity(Intent(applicationContext,DashboardActivity::class.java))
                return@OnNavigationItemSelectedListener true;
            }
            R.id.navigation_dashboard ->{
//                viewPager.currentItem = 1
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 1)
                prefEditor.apply()
                startActivity(Intent(applicationContext,DashboardActivity::class.java))
                return@OnNavigationItemSelectedListener true;
            }
            R.id.navigation_notifications ->{
//                viewPager.currentItem = 2
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 2)
                prefEditor.apply()
                startActivity(Intent(applicationContext,DashboardActivity::class.java))
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