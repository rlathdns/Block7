package com.cookandroid.block7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    public val banners = listOf(
        R.drawable.event_banner_0001,
        R.drawable.event_banner_0002,
        R.drawable.event_banner_0003,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    fun getRealPosition(position: Int): Int {
        return position % banners.size
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.banner_image)

        fun bind(banner: Int) {
            val realPosition = getRealPosition(adapterPosition)
            imageView.setImageResource(banners[realPosition])
        }
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val realPosition = position % banners.size
        holder.bind(banners[realPosition])

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "배너 $realPosition 클릭됨", Toast.LENGTH_SHORT).show()
        }
    }
}
