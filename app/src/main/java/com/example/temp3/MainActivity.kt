package com.example.temp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp3.models.BoardSize
import com.example.temp3.models.Game

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewBoard:RecyclerView
    private lateinit var pairsTextView:TextView
    private lateinit var moveTextView: TextView
    private var boardSize: BoardSize = BoardSize.HARD

    companion object{
        private const val TAG="MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBoard=findViewById(R.id.recycler_view)
        pairsTextView =findViewById(R.id.txt_pairs)
        moveTextView=findViewById(R.id.txt_moves)

        val game= Game(boardSize)
        recyclerViewBoard.adapter= RecyclerViewBoardAdapter(this,boardSize,game.cards,object:RecyclerViewBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
                if(game.flipCard(position)){
                    moveTextView.setText("Moves: ${game.moves}")
                    pairsTextView.setText("Pairs: ${game.numPairsFound}/${boardSize.getNumPairs()}")
                    recyclerViewBoard.adapter?.notifyDataSetChanged()
                }
            }

        })

        recyclerViewBoard.setHasFixedSize(true)
        recyclerViewBoard.layoutManager=GridLayoutManager(this,boardSize.getWidth())
    }

}
