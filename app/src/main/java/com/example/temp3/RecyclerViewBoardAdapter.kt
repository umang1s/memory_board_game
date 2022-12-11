package com.example.temp3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import models.BoardSize
import kotlin.math.min

class RecyclerViewBoardAdapter(private val context: Context, private val boardSize: BoardSize) : RecyclerView.Adapter<RecyclerViewBoardAdapter.ViewHolder>() {

    companion object{
        private const val MARGIN_SIZE=10
        private const val TAG="RecyclerViewBoardAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewBoardAdapter.ViewHolder {
        val cardHeight = parent.height/boardSize.getHeight()
        val cardWidth=parent.width/boardSize.getWidth()
        val cardSideLength= min(cardWidth,cardHeight)- (2* MARGIN_SIZE)

        val view = LayoutInflater.from(context).inflate(R.layout.item_card,parent,false)

        val layoutParams=view.findViewById<CardView>(R.id.card_view).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.height=cardSideLength
        layoutParams.width=cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:   ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = boardSize.numCards

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private val imageButton= itemView.findViewById<ImageButton>(R.id.btn_card)

        fun bind(position: Int){
            imageButton.setOnClickListener{
                Log.i("$TAG","clicked on position $position")
            }
        }
    }

}