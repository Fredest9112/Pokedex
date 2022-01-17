package com.learningkotlin.pokedex.repository.utilities

import android.content.Context
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.learningkotlin.pokedex.R
import com.learningkotlin.pokedex.repository.constants.Constants
import com.squareup.picasso.Picasso

class Utilities {

    fun setStatsLayout(textView: TextView?, progressBar: ProgressBar?, stat: Int){
        textView?.text = stat.toString()
        progressBar?.max = 260
        progressBar?.setProgress(stat, true)
    }

    fun bindPicture(picture: String, image:ImageView?){
        Picasso.get().load(picture).fit().into(image)
    }

    fun setBackGroundColorOnType(cardView: CardView?,
                                         context: Context?, type: String){
        when (type.lowercase()) {
            Constants.NORMAL -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.normal))
            Constants.FIRE -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.fire))
            Constants.WATER -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.water))
            Constants.ELECTRIC -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.electric
                )
            )
            Constants.GRASS -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.grass))
            Constants.ICE -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.ice))
            Constants.FIGHTING -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.fighting
                )
            )
            Constants.POISON -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.poison
                )
            )
            Constants.GROUND -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.ground
                )
            )
            Constants.FLYING -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.flying
                )
            )
            Constants.PSYCHIC -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.psychic
                )
            )
            Constants.BUG -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.bug))
            Constants.ROCK -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.rock))
            Constants.GHOST -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.ghost))
            Constants.DRAGON -> cardView?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.dragon
                )
            )
            Constants.DARK -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.dark))
            Constants.STEEL -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.steel))
            Constants.FAIRY -> cardView?.setCardBackgroundColor(ContextCompat.getColor(context!!, R.color.fairy))
        }
    }
}