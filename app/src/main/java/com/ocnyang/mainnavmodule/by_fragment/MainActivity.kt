package com.ocnyang.mainnavmodule.by_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.widget.Toast
import com.ocnyang.mainnavmodule.R
import com.ocnyang.mainnavmodule.by_fragment.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 使用 Fragment 搭建主导航页面的各模块（常采取的方式）
 *
 * 1. 底部导航栏，菜单栏（如果需要菜单栏统一，可以直接继承 AppCompatActivity）保持一致较为简单
 * 2. 各导航模块之间切换，页面显示速度较快（支持 Fragment 论的人的观点，其实这种差别微小的狠你是感觉不到的）
 * 3. 易于保存恢复各模块页面的状态（因为各模块都是通过显示隐藏来控制的且不涉及销毁，所以各页面的状态能保存下来）
 *
 * 一般情况下，第3条是大家选择 Fragment 搭建的理由
 */
class MainActivity : FragmentActivity() , DashboardFragment.OnListFragmentInteractionListener{
    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {
        Toast.makeText(this,"点击了："+item.content+"-"+item.details,Toast.LENGTH_SHORT).show()
    }

    var mCurrentIndex: String? = null
    var mHomeFragment: HomeFragment? = null
    var mDashboardFragment: DashboardFragment? = null
    var mNotificationFragment: DashboardFragment? = null

    val HOME_FRAGMENT = "home"
    val DASHBOARD_FRAGMENT = "dashboard"
    val NOTIFICATION_FRAGMENT = "notification"
    var mContext: Context? = null
    var mFragmentManager: FragmentManager? = null
    var mTransaction: FragmentTransaction? = null

    /**
     * 这里的底部导航控件，大家可以根据需要设置成自己的（我这里是偷懒用AS的代码模板直接生成的）
     *
     * 注意：在Fragment切换时，对应的导航图标做好相应的变换就行了
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setTabFragment(HOME_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                setTabFragment(DASHBOARD_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                setTabFragment(NOTIFICATION_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        mFragmentManager = supportFragmentManager

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //这里判别：初次打开页面 or 从其他地方恢复页面
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState)
        } else {
            setTabFragment(HOME_FRAGMENT)
        }
    }

    private fun setTabFragment(homE_FRAGMENT: String) {
        if (!homE_FRAGMENT.equals(mCurrentIndex)) {
            switchToFragment(homE_FRAGMENT)
        }
    }

    /**
     * 如果因为一些原因（比如页面崩溃）页面被销毁在这里恢复之前的页面
     */
    private fun restoreFragment(savedInstanceState: Bundle) {
        mCurrentIndex = savedInstanceState.getString("index")
        mHomeFragment = mFragmentManager?.findFragmentByTag(HOME_FRAGMENT) as HomeFragment?
        mDashboardFragment = mFragmentManager?.findFragmentByTag(DASHBOARD_FRAGMENT) as DashboardFragment?
        mNotificationFragment = mFragmentManager?.findFragmentByTag(NOTIFICATION_FRAGMENT) as DashboardFragment?
        switchToFragment(mCurrentIndex)
    }

    /**
     * Fragment 页面切换
     *
     * 如果使用的其他底部导航控件，有必要的话记得在这里改变底部导航对应的显示
     */
    private fun switchToFragment(index: String?) {
        mTransaction = mFragmentManager?.beginTransaction()
        hideAllFragments(mTransaction)
        when (index) {
            HOME_FRAGMENT -> {showHomeFragment()}
            DASHBOARD_FRAGMENT -> showDashboardFragment()
            NOTIFICATION_FRAGMENT -> showNotificationFragment()
        }
        mCurrentIndex = index
        mTransaction?.commit()
    }

    private fun showNotificationFragment() {
        if (mNotificationFragment ==null){
            mNotificationFragment = DashboardFragment.newInstance(1)
            mTransaction?.add(R.id.frame_content_main,mNotificationFragment,NOTIFICATION_FRAGMENT)
        }else{
            mTransaction?.show(mNotificationFragment)
        }
    }

    private fun showDashboardFragment() {
        if (mDashboardFragment == null) {
            mDashboardFragment = DashboardFragment.newInstance(1)
            mTransaction?.add(R.id.frame_content_main, mDashboardFragment, DASHBOARD_FRAGMENT)
        }else{
            mTransaction?.show(mDashboardFragment)
        }
    }

    private fun showHomeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance("", "")
            mTransaction?.add(R.id.frame_content_main, mHomeFragment, HOME_FRAGMENT)
        } else {
            mTransaction?.show(mHomeFragment)
        }
    }

    private fun hideAllFragments(fragmentTransaction: FragmentTransaction?) {
        if (mHomeFragment!=null){
            fragmentTransaction?.hide(mHomeFragment)
        }
        if(mDashboardFragment!=null){
            fragmentTransaction?.hide(mDashboardFragment)
        }
        if (mNotificationFragment!=null){
            fragmentTransaction?.hide(mNotificationFragment)
        }
    }

    /**
     * Activity 被销毁时，记录当前显示的页面
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString("index",mCurrentIndex)
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val TAG_EXIT: String = "exit_app"
    }

    var mIsExit: Boolean = false
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                //双击返回键 退出应用
                this.finish()
            } else {
                //第一次点击返回键 提示
                Toast.makeText(this, getString(R.string.more_click_exit), Toast.LENGTH_SHORT).show()
                mIsExit = true
                android.os.Handler().postDelayed(Runnable { mIsExit = false }, 2000)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 优雅的退出程序
     * 应用中唯一一个退出口（用以其他地方退出程序）
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
