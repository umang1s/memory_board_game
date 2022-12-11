package com.example.temp3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.temp3.models.BoardSize
import com.example.temp3.models.MemoryCard
import kotlin.math.min

class RecyclerViewBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cardImages: List<MemoryCard>,
    private val cardClickListener:CardClickListener
) : RecyclerView.Adapter<RecyclerViewBoardAdapter.ViewHolder>() {

    companion object{
        private const val MARGIN_SIZE=10
        private const val TAG="RecyclerViewBoardAdapter"
    }

    interface CardClickListener{
        fun onCardClicked(position: Int)
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
            val memoryCard=cardImages[position]
            imageButton.setImageResource(if(cardImages[position].isFaceUp) memoryCard.identifier else R.drawable.ic_launcher_background)
            imageButton.alpha=if(memoryCard.isMatched) 0.4f else 1.0f

            ViewCompat.setBackgroundTintList(imageButton,if(cardImages[position].isMatched) ContextCompat.getColorStateList(context,R.color.gray)else null)
            imageButton.setOnClickListener{
                cardClickListener.onCardClicked(position)
            }
        }
    }

}