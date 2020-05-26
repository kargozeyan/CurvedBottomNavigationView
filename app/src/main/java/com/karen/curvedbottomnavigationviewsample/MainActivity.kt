package com.karen.curvedbottomnavigationviewsample

import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.karen.curvedbottomnavigationview.NavigationItem
import com.karen.curvedbottomnavigationview.OnItemClickedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        curved_bnv.fill(
            true, NavigationItem("Text 1", R.drawable.ic_android_black_24dp,21312),
            NavigationItem("Text 2", R.drawable.ic_android_black_24dp, 3212132),
            NavigationItem("Text 3", R.drawable.ic_android_black_24dp,231),
            NavigationItem("Text 4", R.drawable.ic_android_black_24dp,3)
        )
        fab_radius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        curved_bnv.setCenterItemIcon(R.drawable.ic_android_black_24dp)
        curved_bnv.setItemInactiveColor("#abc123")
        curved_bnv.setItemActiveColor("#def456")
        curved_bnv.setCenterButtonBackgroundColor(R.color.colorPrimary)
        curved_bnv.setOnItemClickListener(object : OnItemClickedListener {
            override fun onItemClicked(item: NavigationItem) {
                Log.e("TAG", "It cl ${item.text}")
            }

            override fun onItemReClicked(item: NavigationItem) {
                Log.e("TAG", "it cl re ${item.text}")
            }

            override fun onCenterItemClicked() {
                Log.e("TAG", "ce it cl ")
            }

            override fun onCenterItemReClicked() {
                Log.e("TAG", "ce it cl re")
            }
        })

    }
}
