package com.vsrstudio.growhabit.logging

import android.util.Log
import com.vsrstudio.growhabit.crash.CrashProvider
import timber.log.Timber.Tree

class ReleaseTree(val crash: CrashProvider) : Tree() {

    private val MAX_LOG_LENGTH = 8000

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return !listOf(Log.VERBOSE, Log.DEBUG, Log.INFO).contains(priority)
    }

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            if (priority == Log.ERROR && t != null) {
                t.printStackTrace()
                crash.report(t)
            }

            if (tag != null && message != null) {
                if (message.length < MAX_LOG_LENGTH) {
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, message)
                    } else {
                        Log.println(priority, tag, message)
                    }
                    return
                }

                var i = 0
                val length = message.length
                while (i < length) {
                    var newLine = message.indexOf('\n', i)
                    newLine = if (newLine != -1) newLine else length
                    do {
                        val end = Math.min(newLine, i + MAX_LOG_LENGTH)
                        val part = message.substring(i, end)
                        if (priority == Log.ASSERT) {
                            Log.wtf(tag, part)
                        } else {
                            Log.println(priority, tag, part)
                        }
                        i = end
                    } while (i < newLine)
                    i++
                }
            }
        }
    }

}