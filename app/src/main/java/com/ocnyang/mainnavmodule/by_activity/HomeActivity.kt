package com.ocnyang.mainnavmodule.by_activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.ocnyang.mainnavmodule.R
import kotlinx.android.synthetic.main.activity_home.*

/**
 * 用 Activity 来搭建主导航的不同模块
 *
 * 1. 每个模块的逻辑相对独立
 * 2. 模块和子页面之间数据传递比较清晰
 * 3. 需要复用底部导航栏（当然有需要，底部导航栏也可以不一样）
 * 4. 顶部 Toolbar 相对独立，不同模块设置成不同的 Toolbar 比较简单
 * 5. 相对 Fragment 搭建来讲，不需要考虑太多事情和逻辑，Fragment 各种显示隐藏的控制和状态恢复还有各种意想不到的 Bug 定让人头疼不已
 * 6. 不能保证各模块页面的状态能保存下来。在切换过程中有销毁页面的情况。（比如A启动B,B启动C。这时C再切换到B,这时就会销毁C,C的状态就不能保存了）
 *    当然如果你的各模块对页面状态不关心 | 页面是一下静态功能显示 | 每次打开页面都希望刷新，那也就无所谓了
 *
 * 要不要用 Activity 搭建主导航页面，1、2 (也注意第6条) 两条是你考虑的重点，如果你的项目前两条需求比较硬朗，可以考虑这种方式
 */
class HomeActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                startActivity(Intent(this, DashboardActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                startActivity(Intent(this, NotificationActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        message.text = getString(R.string.title_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        navigation.selectedItemId = R.id.navigation_home
    }

    /**
     * 优雅的退出程序
     * 应用中唯一一个退出口（HomeActivity 处于栈中最底部的一个）
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent !=null){
            val isExtra = intent.getBooleanExtra(TAG_EXIT, false)
            if (isExtra){
                finish()
            }
        }
    }
}
