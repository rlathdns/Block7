package com.cookandroid.block7

import GameCharacter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(private val characters: List<GameCharacter>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var listener: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterImage: ImageView = itemView.findViewById(R.id.characterImage)
        val charactergradetag: ImageView = itemView.findViewById(R.id.character_grade_tag)
        val charactername: TextView = itemView.findViewById(R.id.character_name)
        val star: ImageView = itemView.findViewById(R.id.character_star)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]

        // 이미지 리소스 ID 불러오기
        val imageResId = holder.itemView.context.resources.getIdentifier(
            character.imgresid,
            "drawable",
            holder.itemView.context.packageName
        )
        holder.characterImage.setImageResource(imageResId)

        if (character.ownership) {
            holder.itemView.alpha = 1.0f  // 보유중
        } else {
            holder.itemView.alpha = 0.5f  // 보유중이지 않음
        }


        when(character.grade) {
            5 -> {
                holder.charactergradetag.setBackgroundResource(R.color.yellow)
                holder.star.setImageResource(R.drawable.star5)
            }
            4 -> {
                holder.charactergradetag.setBackgroundResource(R.color.purple)
                holder.star.setImageResource(R.drawable.star4)
            }
        }

        holder.charactername.setText(character.name)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(character)
        }
    }

    override fun getItemCount() = characters.size

    interface ItemClickListener {
        fun onItemClicked(character: GameCharacter)
    }

}
