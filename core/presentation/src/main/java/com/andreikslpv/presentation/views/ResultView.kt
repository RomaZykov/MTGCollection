package com.andreikslpv.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.presentation.R
import com.andreikslpv.presentation.databinding.CorePresentationPartResultBinding

/**
 * Layout for rendering [Response] results.
 * If [response] has [Response.Success] value -> children are rendered.
 * If [response] has [Response.Loading] value -> progress bar is displayed.
 * If [response] has [Response.Failure] value -> an error message and try-again
 * button is displayed.
 */
class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    /**
     * Current container with data assigned to the view.
     */
    var response: Response<*> = Response.Loading
        set(value) {
            field = value
            notifyUpdates()
        }

    private var tryAgainListener: (() -> Unit)? = null

    private val binding: CorePresentationPartResultBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = CorePresentationPartResultBinding.inflate(inflater, this, false)
        addView(binding.root)

        if (isInEditMode) {
            response = Response.Success("")
        } else {
            binding.resultProgressBar.isVisible = false
            binding.resultErrorContainer.isVisible = false
            binding.internalResultContainer.isVisible = false
            children.forEach {
                it.isVisible = false
            }
            response = Response.Loading
        }

        binding.tryAgainButton.setOnClickListener {
            tryAgainListener?.invoke()
//            if (isAuthError()) {
//                Core.appRestarter.restartApp()
//            } else {
//                tryAgainListener?.invoke()
//            }
        }
    }

    /**
     * Assign try-again listener which is called when [response] has error
     * value ([Response.Failure]) and user presses Try Again button. Usually
     * you need to try ro reload data in the [onTryAgain] callback.
     */
    fun setTryAgainListener(onTryAgain: () -> Unit) {
        this.tryAgainListener = onTryAgain
    }

    private fun notifyUpdates() {
        val response = this.response
        binding.resultProgressBar.isVisible = response is Response.Loading
        binding.resultErrorContainer.isVisible = response is Response.Failure
        binding.internalResultContainer.isVisible = response !is Response.Success

        if (response is Response.Failure) {
            val exception = Throwable(response.errorMessage)
            Core.logger.err(exception)
            binding.resultErrorTextView.text = response.errorMessage
            binding.tryAgainButton.setText(R.string.core_presentation_try_again)
//            binding.tryAgainButton.setText(if (isAuthError()) {
//                R.string.core_presentation_logout
//            } else {
//                R.string.core_presentation_try_again
//            })
        }

        children.forEach {
            if (it != binding.root) {
                it.isVisible = response is Response.Success
            }
        }
    }

//    private fun isAuthError() = response.let {
//        it is Response.Failure && it.exception is AuthException
//    }
}