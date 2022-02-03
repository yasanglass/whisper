package glass.yasan.whisper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A [ViewModel] designed to handle one-time events.
 *
 * @see addToEventQueue
 * @see events
 */
open class WhisperViewModel<T> constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    companion object {
        private const val TAG = "WhisperViewModel"
    }

    private val eventsChannel = Channel<T?>()
    val events = eventsChannel.receiveAsFlow()

    private val eventQueue = ArrayList<T?>()

    fun addToEventQueue(
        events: ArrayList<T?>,
        delayMillis: Long? = null,
    ) = viewModelScope.launch(coroutineDispatcher) {
        delayMillis?.let {
            delay(it)
        }
        eventQueue.addAll(events)
        Log.v(TAG, "addToEventQueue: queue = $eventQueue")
        tryRunEventQueue()
    }

    fun addToEventQueue(
        event: T?,
        delayMillis: Long? = null,
    ) = viewModelScope.launch(coroutineDispatcher) {
        delayMillis?.let {
            delay(it)
        }
        eventQueue.add(event)
        Log.v(TAG, "addToEventQueue: queue = $eventQueue")
        tryRunEventQueue()
    }

    private fun tryRunEventQueue() = viewModelScope.launch(coroutineDispatcher) {
        if (!runningQueue && eventQueue.isNotEmpty()) {
            Log.v(TAG, "tryRunEventQueue: Starting queue")
            runEventQueue()
        } else {
            Log.v(TAG, "tryRunEventQueue: NOT starting queue")
        }
    }

    private var runningQueue = false

    private fun runEventQueue() {
        viewModelScope.launch(coroutineDispatcher) {
            runningQueue = true
            Log.v(TAG, "runEventQueue: start")
            val q = ArrayList<T?>().apply { addAll(eventQueue) }
            for (e in q) {
                Log.v(TAG, "runEventQueue: eventQueue = $eventQueue")
                eventsChannel.send(e)
                eventQueue.removeFirst()
                if (e != null) delay(100)
                eventsChannel.send(null)
            }
            runningQueue = false
            Log.v(TAG, "runEventQueue: finish")
            tryRunEventQueue()
        }
    }
}
