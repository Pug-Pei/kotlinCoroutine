package co.websarva.wings.android.kotlincoroutine

import kotlinx.coroutines.*

//launch
fun mainLaunch() = runBlocking {//Creates a coroutine that blocks the current main thread
    println("Maim program starts: ${Thread.currentThread().name}")//main thread

    val job: Job = launch {//Thread: T1
        println("Fake work starts: ${Thread.currentThread().name}")//thread: T1
        delay(1000) //CoroutineはsuspendされるがT1スレッドはフリー(ブロックされない)
        println("Fake work finished: ${Thread.currentThread().name}")// Either T1 or same thread.
    }
    //delay(2000)// main thread: wait for coroutine to finish(practically not a right way to wait)
    job.join()
    println("Main program ends: ${Thread.currentThread().name}")//main thread
}

//async
fun mainAsync() = runBlocking {//Creates a coroutine that blocks the current main thread
    println("Maim program starts: ${Thread.currentThread().name}")//main thread

    val jobDeferred: Deferred<String> = async {//Thread: T1
        println("Fake work starts: ${Thread.currentThread().name}")//thread: T1
        delay(1000)
        println("Fake work finished: ${Thread.currentThread().name}")// Either T1 or same thread.
        "HOGE FUGE ??"//戻り値
    }
    //jobDeferred.join()
    val text = jobDeferred.await()
    println(text)
    println("Main program ends: ${Thread.currentThread().name}")//main thread
}

//runBlocking
//async
fun mainRunBlocking() = runBlocking {//Creates a coroutine that blocks the current main thread
    println("Maim program starts: ${Thread.currentThread().name}")//main thread

    val jobDeferred: Deferred<String> = async {//Thread: T1
        println("Fake work starts: ${Thread.currentThread().name}")//thread: T1
        delay(1000)
        println("Fake work finished: ${Thread.currentThread().name}")// Either T1 or same thread.
        "HOGE FUGE ??"//戻り値
    }
    //jobDeferred.join()
    val text = jobDeferred.await()
    println(text)
    println("Main program ends: ${Thread.currentThread().name}")//main thread
}

suspend fun myOwnSuspendingFunc(){
    delay(1000)//do something...
}


