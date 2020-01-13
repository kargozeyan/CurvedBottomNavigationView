package com.karen.curvedbottomnavigationviewsample

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.karen.curvedbottomnavigationview.dp
import kotlinx.android.synthetic.main.sample_item.view.*

class SampleAdapter() : RecyclerView.Adapter<SampleAdapter.SampleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder =
        SampleHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.sample_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        val list = listOf(
            "#3f88c5",
            "#e03616",
            "#fff689",
            "#5998c5"
        )
        holder.card.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, getRandomSize())
        holder.card.setCardBackgroundColor(Color.parseColor(list.random()))
    }


    override fun getItemCount(): Int = 1000

    private fun getRandomSize() = (200.dp..300.dp).random()

    inner class SampleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.sample_card
    }
}