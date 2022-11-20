package co.websarva.wings.android.kotlincoroutine

import kotlinx.coroutines.*
import java.time.LocalDateTime

//cancellableなメソッドを用いた場合のcooperative化
fun mainCooperative1() = runBlocking { //Create a blocking coroutine that executes in current thread
    println("Main program starts: ${Thread.currentThread().name}")

    val job: Job = launch {
        for (i in 0..500) {
            print("$i.")
            //Thread.sleep(50)
            delay(50)
        }
    }

    delay(200) //Let's print a few values before we cancel
    //job.cancel()
    //job.join()
    job.cancelAndJoin()

    println("\nMain program ends: ${Thread.currentThread().name}")
}


//CoroutineScope.isActiveをもちいた場合のcooperative化
fun mainCooperative2() = runBlocking { //Create a blocking coroutine that executes in current thread
    println("Main program starts: ${Thread.currentThread().name}")

    val job: Job = launch(Dispatchers.Default) {
        for (i in 0..500) {
            if (!isActive) {
                break
                //return@launch
            }
            print("$i.")
            Thread.sleep(1)
            //delay(50)
        }
    }

    delay(10) //Let's print a few values before we cancel
    job.cancelAndJoin()

    println("\nMain program ends: ${Thread.currentThread().name}")
}


//Jobがキャンセルされた場合の例外処理
fun mainHandleException() =
    runBlocking { //Create a blocking coroutine that executes in current thread
        println("Main program starts: ${Thread.currentThread().name}")

        val job: Job = launch(Dispatchers.Default) {
            try {
                for (i in 0..500) {
                    print("$i.")
                    delay(5)
                }
            } catch (ex: CancellationException) {
                print("\nException caught safely")
            } finally {
                delay(100)
                print("\nClose resources in finally 2")
            }
        }

        delay(10) //Let's print a few values before we cancel
        job.cancelAndJoin()

        println("\nMain program ends: ${Thread.currentThread().name}")
    }


//Finally内でのsuspendingFunctionの実行
fun mainSuspendFunInFinally() =
    runBlocking { //Create a blocking coroutine that executes in current thread
        println("Main program starts: ${Thread.currentThread().name}")

        val job: Job = launch(Dispatchers.Default) {
            try {
                for (i in 0..500) {
                    print("$i.")
                    delay(5)
                }
            } catch (ex: CancellationException) {
                print("\nException caught safely: ${ex.message}")
            } finally {
                withContext(NonCancellable) {
                    delay(1000)
                    print("\nClose resources in finally")
                }
            }
        }

        delay(10) //Let's print a few values before we cancel
        //ｊob.cancelAndJoin()
        job.cancel(CancellationException("My own crash message"))
        job.join()
        println("\nMain program ends: ${Thread.currentThread().name}")
    }


//TimeOut
fun mainTimeOut() =
    runBlocking { //Create a blocking coroutine that executes in current thread
        println("Main program starts: ${Thread.currentThread().name}")

        withTimeout(2){
            for(i in 0..500){
                print("$i.")
                delay(5)
            }
        }

        println("\nMain program ends: ${Thread.currentThread().name}")
    }

//TimeOutNull
fun mainTimeOutNull() =
    runBlocking { //Create a blocking coroutine that executes in current thread
        println("Main program starts: ${Thread.currentThread().name}")

        val result: String? = withTimeoutOrNull(10){
            for(i in 0..5000){
                val localDataTime = LocalDateTime.now()
                println("$i. $localDataTime")
                delay(1)
            }
            "work has done"
        }

        print("Result: $result")

        println("\nMain program ends: ${Thread.currentThread().name}")
    }