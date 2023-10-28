package com.andreikslpv.profile.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextPaint
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.properties.Delegates

object StringToIconConverter {

    private const val USER_PHOTO_DIMENSIONS = 256
    private const val TEXT_SIZE = 48f

    fun convert(context: Context, filename: String, inputText: String?): Uri {
        val drawable = TextIconDrawable().apply {
            text = getCharForIcon(inputText)
            textColor = Color.WHITE
        }

        return bitmapToFile(
            drawable.toBitmap(USER_PHOTO_DIMENSIONS, USER_PHOTO_DIMENSIONS),
            filename,
            context,
        )
    }

    private fun getCharForIcon(inputText: String?): String {
        return if (!inputText.isNullOrBlank()) inputText[0].toString().uppercase()
        else "A"
    }

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap: Bitmap, filename: String, context: Context): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(context)

        // Initialize a new file instance to save bitmap object


        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "$filename.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse("file://" + file.absolutePath)
    }


    class TextIconDrawable : Drawable() {
        private var alpha = 255
        private var textPaint = TextPaint().apply {
            textAlign = Paint.Align.CENTER
        }
        var text by Delegates.observable("") { _, _, _ -> invalidateSelf() }
        var textColor by Delegates.observable(Color.BLACK) { _, _, _ -> invalidateSelf() }

        private fun fitText(width: Int) {
            textPaint.textSize = TEXT_SIZE
            val widthAt48 = textPaint.measureText(text)
            textPaint.textSize = TEXT_SIZE / widthAt48 * width.toFloat() / 3f
        }

        override fun draw(canvas: Canvas) {
            val width = USER_PHOTO_DIMENSIONS
            val height = USER_PHOTO_DIMENSIONS
            fitText(width)
            textPaint.color = ColorUtils.setAlphaComponent(textColor, alpha)
            canvas.drawText(text, width / 2f, height * 0.66f, textPaint)
        }

        override fun setAlpha(alpha: Int) {
            this.alpha = alpha
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            textPaint.colorFilter = colorFilter
        }

        @Deprecated(
            "Deprecated in Java",
            ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
        )
        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    }

}