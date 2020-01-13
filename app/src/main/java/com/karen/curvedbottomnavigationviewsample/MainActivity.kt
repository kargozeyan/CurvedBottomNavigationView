package com.karen.curvedbottomnavigationviewsample

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

import com.karen.curvedbottomnavigationview.NavigationItem
import com.karen.curvedbottomnavigationview.OnItemClickedListener
import com.karen.curvedbottomnavigationview.dp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        curved_bnv.fill(
            true, NavigationItem("Text 1", R.drawable.ic_android_black_24dp),
            NavigationItem("Text 2", R.drawable.ic_android_black_24dp),
            NavigationItem("Text 3", R.drawable.ic_android_black_24dp),
            NavigationItem("Text 4", R.drawable.ic_android_black_24dp)
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
        curved_bnv.setOnItemClickListener(object : OnItemClickedListener {
            override fun onItemClicked(item: NavigationItem) {

            }

            override fun onItemReClicked(item: NavigationItem) {
                /** Handle item ReClicked*/
            }

            override fun onCenterItemClicked() {
                /** Handle CenterItem Clicked*/
            }

            override fun onCenterItemReClicked() {
                /** Handle CenterItem ReClicked*/
            }
        })

    }
}
