package com.andreikslpv.common_impl

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.andreikslpv.common.AlertDialogConfig
import com.andreikslpv.common.CommonUi
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Default implementation of [CommonUi] to display toasts and dialogs.
 * Dialogs are displayed only when activity is started. Otherwise they are
 * postponed.
 */
class AndroidCommonUi(
    private val applicationContext: Context
) : CommonUi, ActivityRequired {

    private var currentActivity: FragmentActivity? = null
    private var isStarted = false
    private val dialogRecords = mutableListOf<DialogRecord>()

    override fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override suspend fun alertDialog(config: AlertDialogConfig): Boolean = suspendCancellableCoroutine {
        val record = DialogRecord(config, it)
        dialogRecords.add(record)
        if (isStarted) {
            startDialog(record)
        }
        it.invokeOnCancellation {
            record.dialog?.dismiss()
            dialogRecords.remove(record)
        }
    }

    override fun onActivityCreated(activity: FragmentActivity) {
        this.currentActivity = activity
    }

    override fun onActivityStarted() {
        isStarted = true
        dialogRecords.forEach { startDialog(it) }
    }

    override fun onActivityStopped() {
        isStarted = false
        dialogRecords.forEach { it.dialog?.dismiss() }
    }

    override fun onActivityDestroyed() {
        if (this.currentActivity?.isFinishing == true) {
            this.dialogRecords.clear()
        }
        this.currentActivity = null
    }

    private fun startDialog(record: DialogRecord) {
        val activity = this.currentActivity ?: return
        val builder = MaterialAlertDialogBuilder(activity)
            .setTitle(record.config.title)
            .setMessage(record.config.message)
            .setCancelable(true)
            .setOnCancelListener {
                record.continuation.resume(false)
                dialogRecords.remove(record)
            }
            .setPositiveButton(record.config.positiveButton) { _, _ ->
                record.continuation.resume(true)
                dialogRecords.remove(record)
            }
        if (record.config.negativeButton != null) {
            builder.setNegativeButton(record.config.negativeButton) { _, _ ->
                record.continuation.resume(false)
                dialogRecords.remove(record)
            }
        }
        val dialog = builder.create()
        record.dialog = dialog
        dialog.show()
    }

    private class DialogRecord(
        val config: AlertDialogConfig,
        val continuation: CancellableContinuation<Boolean>,
        var dialog: Dialog? = null,
    )
}