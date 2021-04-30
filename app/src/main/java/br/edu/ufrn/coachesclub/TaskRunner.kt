package br.edu.ufrn.coachesclub

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TaskRunner {
    private val executor: Executor =
        Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun <R> executeAsync(callable: Callable<R>, callback: (result: R?) -> Unit) {
        executor.execute {
            val result: R = callable.call()
            handler.post { callback(result) }
        }
    }
}