package com.ocnyang.mainnavmodule.by_activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.ocnyang.mainnavmodule.R
import kotlinx.android.synthetic.main.activity_home.*

class NotificationActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                startActivity(Intent(this, HomeActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                startActivity(Intent(this, DashboardActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        message.setText(R.string.title_notifications)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        navigation.selectedItemId = R.id.navigation_notifications
    }
}
