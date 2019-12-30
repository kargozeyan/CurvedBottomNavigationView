package com.karen.curvedbottomnavigationviewsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karen.curvedbottomnavigationview.NavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar.fill(
            true, NavigationItem("Text 1", R.drawable.ic_android_black_24dp),
            NavigationItem("Text 2", R.drawable.ic_android_black_24dp),
            NavigationItem("Text 3", R.drawable.ic_android_black_24dp),
            NavigationItem("Text 4", R.drawable.ic_android_black_24dp)
        )
        bar.setCenterItemIcon(R.drawable.ic_android_black_24dp)
        bar.setItemInactiveColor("#00ff1a")
        bar.setItemActiveColor("#abcdef")
        bar.setBackgroundColor("#123456")

    }
}
