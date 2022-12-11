package com.example.temp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.BoardSize

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewBoard:RecyclerView
    private lateinit var pairsTextView:TextView
    private lateinit var moveTextView: TextView

    private var boardSize:BoardSize=BoardSize.HARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBoard=findViewById(R.id.recycler_view)
        pairsTextView =findViewById(R.id.txt_pairs)
        moveTextView=findViewById(R.id.txt_moves)

        recyclerViewBoard.adapter= RecyclerViewBoardAdapter(this,boardSize)
        recyclerViewBoard.setHasFixedSize(true)
        recyclerViewBoard.layoutManager=GridLayoutManager(this,boardSize.getWidth())


    }

}
