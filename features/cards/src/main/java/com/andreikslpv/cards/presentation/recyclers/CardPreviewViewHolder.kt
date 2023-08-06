package com.andreikslpv.cards.presentation.recyclers

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.presentation.databinding.ItemCardPreviewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class CardPreviewViewHolder(val binding: ItemCardPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(card: CardFeatureModel) {
        //println("AAA lang = ${binding.root.context.resources.configuration.locales.get(0).displayName}")
        binding.itemTitle.text = card.name
        println("AAA card.imageUrl = ${card.imageUrl}")

        Glide.with(itemView)
            .load("https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=172593&type=card")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
//                    e?.let {
//                       it.causes.forEach {cause->
//                           println("AAA onLoadFailed = ${cause}")
//                       }
//                    }
                    println("AAA ${e?.rootCauses}")
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    println("AAA onResourceReady")
                    return true
                }

            })
            .into(binding.itemImage)


//        Glide.with(itemView)
//            .asBitmap()
//            .load(card.imageUrl)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .into(object : CustomTarget<Bitmap?>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
//                ) {
//                    binding.itemImage.setImageBitmap(resource);
//                    binding.itemImage.buildDrawingCache();
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//
//            })


//        val temp = if (card.imageUrl.contains("http://"))
//            card.imageUrl.replace("http://", "https://")
//        else card.imageUrl
////        val uri = parse(card.imageUrl)
//        Glide.with(itemView)
//            .load(card.imageUrl)
////            .disallowHardwareConfig()
//            .centerCrop()
//            .into(binding.itemImage)


//        println("AAA card.imageUrl = ${temp}")
////        val uri = parse(card.imageUrl)
//        binding.itemImage.load(temp) {
//            placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
//        }
    }
}