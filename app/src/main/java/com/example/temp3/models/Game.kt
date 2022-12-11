package com.example.temp3.models

import com.example.temp3.utils.DEFAULT_ICONS

class Game(private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    var numPairsFound=0
    var moves=0
    private var prevFlipped:Int=-1
    private var prevPrevFlipped:Int=-1

    fun flipCard(position: Int):Boolean {
        var requiredUpdate=false
        val card=cards[position]
        if(!card.isMatched){
            if(position==prevFlipped || position==prevPrevFlipped){
                //do nothings
            }else{
                requiredUpdate=true
                moves++
                if(prevFlipped==-1){
                    card.isFaceUp=true
                    prevFlipped=position
                }else{
                    if(cards[prevFlipped].identifier==card.identifier && prevPrevFlipped==-1){
                        cards[prevFlipped].isMatched=true
                        card.isMatched=true
                        card.isFaceUp=true

                        numPairsFound++
                        prevFlipped=-1
                    }else{
                        if(prevPrevFlipped==-1) prevPrevFlipped=prevFlipped
                        else{
                            cards[prevPrevFlipped].isFaceUp=false
                            cards[prevFlipped].isFaceUp=false
                            prevPrevFlipped=-1
                        }
                        card.isFaceUp=true
                        prevFlipped=position
                    }
                }
            }
        }
        return requiredUpdate
    }

    init {
        val images= DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages=(images +images).shuffled()
        cards=randomizedImages.map{ MemoryCard(it) }
    }

}