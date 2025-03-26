package com.project.dicodingplayground.practice_modul.dependencyinjection.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor


class AppExecutors {

    private class MainThreadExecutor: Executor {
        val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

    }
}