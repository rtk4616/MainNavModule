package com.ocnyang.mainnavmodule

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ocnyang.mainnavmodule.by_activity.HomeActivity
import com.ocnyang.mainnavmodule.by_fragment.MainActivity
import kotlinx.android.synthetic.main.activity_guide.*

/**
 * 这个页面只是作为一个引导页存在，当跳转到主导航页面时（MainActivity | HomeActivity）将结束此页面
 * 这里主要是为了把上面两个页面假定成主页面，且不能退回到此页面
 */
class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        btn_activity_way.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish() //结束此页面，使不能退回
        }
        btn_fragment_way.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
