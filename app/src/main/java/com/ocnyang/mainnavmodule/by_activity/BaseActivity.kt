package com.ocnyang.mainnavmodule.by_activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast
import com.ocnyang.mainnavmodule.R

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/8/14 17:47.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/
open class BaseActivity : AppCompatActivity() {

    val TAG_EXIT: String = "exit_app"
    var mIsExit: Boolean = false

    override fun onPause() {
        overridePendingTransition(0, 0) //取消页面返回的动画
        super.onPause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                //双击返回键 退出应用
                var intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(TAG_EXIT, true)
                startActivity(intent)
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
     * 另外一种退出方式
     * 每次退出应用时，先返回到 HomeActivity 再由它退出应用。
     * 这种方式，无需再在 HomeActivity 重写 onNewIntent()
     * 但是要重写 HomeActivity 的 onKeyDown() 来处理返回键的事件，具体可以参考上面
     */
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            startActivity(Intent(this,HomeActivity::class.java))
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

}