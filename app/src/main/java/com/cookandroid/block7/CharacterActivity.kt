package com.cookandroid.block7

import DatabaseHelper
import GameCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterActivity : BaseActivity() {
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var charactersList: List<GameCharacter>
    private lateinit var sortedCharacters: List<GameCharacter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character)

        val db = DatabaseHelper(this)
        charactersList = db.getAllCharacters()

        sortedCharacters = charactersList.sortedWith(Comparator { c1, c2 ->
            when {
                c1.ownership && !c2.ownership -> -1  // c1만 보유 중인 경우
                !c1.ownership && c2.ownership -> 1   // c2만 보유 중인 경우
                c1.grade != c2.grade -> c2.grade - c1.grade
                else -> c1.name.compareTo(c2.name)
            }
        })


        // RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.characterRecyclerView)
        characterAdapter = CharacterAdapter(sortedCharacters)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = characterAdapter

        val close_button:ImageButton = findViewById(R.id.close_button)
        close_button.setOnClickListener{
            finish()
        }

        characterAdapter.setItemClickListener(object : CharacterAdapter.ItemClickListener {
            override fun onItemClicked(character: GameCharacter) {
                val fragment = CharacterDetailFragment.newInstance(character)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }
}

class CharacterDetailFragment : Fragment() {

    private lateinit var character: GameCharacter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val character = arguments?.getSerializable("characterData") as GameCharacter?

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        val character = arguments?.getSerializable("characterData") as GameCharacter?

        val characterImage: ImageView = view.findViewById(R.id.character_image)
        val characterNameTextView: TextView = view.findViewById(R.id.character_name)
        val characterTypeTextView: TextView = view.findViewById(R.id.type)
        val characterLevelTextView: TextView = view.findViewById(R.id.level)
        val characterHpTextView: TextView = view.findViewById(R.id.hp_text)
        val characterAttackpowerTextView: TextView = view.findViewById(R.id.attackpower_text)
        val characterDefenseTextView: TextView = view.findViewById(R.id.defense_text)
        val characterSpeedTextView: TextView = view.findViewById(R.id.speed_text)
        val characterCriticalchanceTextView: TextView = view.findViewById(R.id.critical_chance_text)
        val characterCriticaldamageTextView: TextView = view.findViewById(R.id.critical_damage_text)
        val characterBreakthroughText: TextView = view.findViewById(R.id.breakthrough_text)
        val characterBreakthroughStoneText: TextView = view.findViewById(R.id.breakthrough_stone_text)
        characterNameTextView.text = character?.name
        characterTypeTextView.text = character?.type
        characterLevelTextView.text = "Lv ${character?.level.toString()}/10"
        characterHpTextView.text = character?.health.toString()
        characterAttackpowerTextView.text = character?.attackPower.toString()
        characterDefenseTextView.text = character?.defense.toString()
        characterSpeedTextView.text = character?.speed.toString()
        characterCriticalchanceTextView.text = "${character?.criticalChance.toString()}%"
        characterCriticaldamageTextView.text = "${character?.criticalDamage.toString()}%"
        characterBreakthroughText.text = character?.breakthrough.toString()
        characterBreakthroughStoneText.text = character?.breakthroughStone.toString()

        val star = character?.grade

        val textColor = when(star) {
            5 -> ContextCompat.getColor(requireContext(), R.color.yellow) // 노랑색 리소스 ID
            4 -> ContextCompat.getColor(requireContext(), R.color.purple) // 보라색 리소스 ID
            else -> characterNameTextView.currentTextColor // 기본 색상 유지
        }
        characterNameTextView.setTextColor(textColor)




        val localContext = context
        val resourceName = character?.imgresid
        if (localContext != null) {
            val imageResId = localContext.resources.getIdentifier(resourceName, "drawable", localContext.packageName)
            if (imageResId != 0) {
                characterImage.setImageResource(imageResId)
            } else {
                // 이미지 리소스를 찾지 못한 경우의 처리
            }
        }

        val closeButton = view.findViewById<ImageButton>(R.id.close_button)
        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        return view
    }

    companion object {
        fun newInstance(character: GameCharacter): CharacterDetailFragment {
            val fragment = CharacterDetailFragment()
            val args = Bundle()
            args.putSerializable("characterData", character)
            fragment.arguments = args
            return fragment
        }
    }
}

