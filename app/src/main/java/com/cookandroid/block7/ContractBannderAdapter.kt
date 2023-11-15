package com.cookandroid.block7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ContractBannerAdapter : RecyclerView.Adapter<ContractBannerAdapter.BannerViewHolder>() {
    public val banners = listOf(
        R.drawable.contract_banner_lus,
        R.drawable.contract_banner_lucy
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
