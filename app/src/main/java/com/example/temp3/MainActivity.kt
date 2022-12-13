package com.example.temp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp3.models.BoardSize
import com.example.temp3.models.Game
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewBoard:RecyclerView
    private lateinit var pairsTextView:TextView
    private lateinit var moveTextView: TextView
    private lateinit var gameLayout:RelativeLayout
    private lateinit var game:Game
    private lateinit var adapter: RecyclerViewBoardAdapter
    private var boardSize: BoardSize = BoardSize.EASY

    companion object{
        private const val TAG="MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBoard=findViewById(R.id.recycler_view)
        pairsTextView =findViewById(R.id.txt_pairs)
        moveTextView=findViewById(R.id.txt_moves)
        gameLayout=findViewById(R.id.game_layout)

        setupGame()



    }

    fun setupGame(){
        game= Game(boardSize)
        adapter= RecyclerViewBoardAdapter(this,boardSize,game.cards,object:RecyclerViewBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
                if(game.flipCard(position)){
                    moveTextView.setText("Moves: ${game.moves}")
                    pairsTextView.setText("Pairs: ${game.numPairsFound}/${boardSize.getNumPairs()}")
                    recyclerViewBoard.adapter?.notifyDataSetChanged()

                    if(game.numPairsFound==boardSize.getNumPairs()){
                        Snackbar.make(gameLayout,"Whoa! You won the game",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }

        })
        when(boardSize){
            BoardSize.EASY->{
                moveTextView.text="EASY: 4x2"
                pairsTextView.text="Pairs: 0/4"
            }
            BoardSize.MEDIUM->{
                moveTextView.text="MEDIUM: 6x3"
                pairsTextView.text="Pairs: 0/9"
            }
            BoardSize.HARD->{
                moveTextView.text="HARD: 6x4"
                pairsTextView.text="Pairs: 0/12"
            }
        }

        recyclerViewBoard.adapter=adapter
        recyclerViewBoard.setHasFixedSize(true)
        recyclerViewBoard.layoutManager=GridLayoutManager(this,boardSize.getWidth())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_refresh->{
                //do the process
                if(game.moves>0 && game.numPairsFound!=boardSize.getNumPairs()){
                    showAlertDialog("Quit you current game ?",null,View.OnClickListener {
                        setupGame()
                    })
                }else{
                    setupGame()
                }
                return true
            }
            R.id.selectGameSize->{
                showNewSizeDialog()
                return true
            }
            R.id.createCustomGame->{
                createCustomGameDialog()
                return true
            }
            R.id.downloadGame->{
                //showNewSizeDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(title:String, view: View?, positiveClickListener: View.OnClickListener){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("cancel",null)
            .setPositiveButton("ok"){ _,_ ->
                positiveClickListener.onClick(null)
            }.show()
    }

    private fun showNewSizeDialog(){
        val boardSizeView=LayoutInflater.from(this).inflate(R.layout.dialog_board_size,null)
        val radioGroupSize=boardSizeView.findViewById<RadioGroup>(R.id.radioGroup)

        when(boardSize){
            BoardSize.EASY->radioGroupSize.check(R.id.easyRadioBtn)
            BoardSize.MEDIUM->radioGroupSize.check(R.id.medRadioBtn)
            BoardSize.HARD->radioGroupSize.check(R.id.hardRadioBtn)
        }

        showAlertDialog("Choose new Size",boardSizeView,View.OnClickListener {
            boardSize= when(radioGroupSize.checkedRadioButtonId){
                R.id.easyRadioBtn-> BoardSize.EASY
                R.id.medRadioBtn->BoardSize.MEDIUM
                else->BoardSize.HARD
            }

            setupGame()

        })
    }

    private fun createCustomGameDialog() {
        val boardSizeView=LayoutInflater.from(this).inflate(R.layout.dialog_board_size,null)
        val radioGroupSize=boardSizeView.findViewById<RadioGroup>(R.id.radioGroup)

        when(boardSize){
            BoardSize.EASY->radioGroupSize.check(R.id.easyRadioBtn)
            BoardSize.MEDIUM->radioGroupSize.check(R.id.medRadioBtn)
            BoardSize.HARD->radioGroupSize.check(R.id.hardRadioBtn)
        }

        showAlertDialog("Choose new Size",boardSizeView,View.OnClickListener {
            boardSize= when(radioGroupSize.checkedRadioButtonId){
                R.id.easyRadioBtn-> BoardSize.EASY
                R.id.medRadioBtn->BoardSize.MEDIUM
                else->BoardSize.HARD
            }

            setupGame()

        })
    }

}
