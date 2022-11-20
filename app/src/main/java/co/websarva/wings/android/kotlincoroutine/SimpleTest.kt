package co.websarva.wings.android.kotlincoroutine

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SimpleTest {
     //このテストはうまく動かない、、、
     @Test
     suspend fun myFirstTest() = runBlocking{
          myOwnSuspendingFunc()
          Assert.assertEquals(10,2+8)
     }
}