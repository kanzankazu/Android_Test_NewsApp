package com.salt.core.util

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.salt.core.ext.delayFun

fun TextView.typing(
    text: String,
    isRepeatForever: Boolean = false,
    delayMillis: Long = 100,
    isNewTextAlwaysClear: Boolean = true,
    isRepeatForeverAlwaysClear: Boolean = true,
    handlerRunnablePair: Pair<Handler, Runnable>? = null,
    onFinish: (() -> Unit)? = null,
): Pair<Handler, Runnable> {
    handlerRunnablePair?.second?.let { handlerRunnablePair.first.removeCallbacks(it) }
    if (isNewTextAlwaysClear) setText("")
    val handler = Handler(Looper.getMainLooper())
    val task: Runnable = object : Runnable {
        override fun run() {
            if (isRepeatForeverAlwaysClear) setText("")
            text.forEachIndexed { index, c ->
                handler.postDelayed({
                    append(c.toString())
                    if (index == text.lastIndex && !isRepeatForever) {
                        context.delayFun { onFinish?.invoke() }
                    }
                }, index * delayMillis)
            }
            if (isRepeatForever) handler.postDelayed(this, text.length * delayMillis)
        }
    }

    handler.post(task)
    return Pair(handler, task)
}
