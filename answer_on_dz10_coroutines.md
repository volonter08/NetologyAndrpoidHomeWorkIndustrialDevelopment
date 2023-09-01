# Ответы на вопросы для выполнения ДЗ №10 #
## Ответы на тему: Cancelation:
### Ответ на 1-ый вопрос:

В функции main строка <-- не сработает
```kotlin
fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        launch {
            delay(500)
            println("ok") // <--
        }
        launch {
            delay(500)
            println("ok")
        }
    }
    delay(100)
    job.cancelAndJoin()
}
```
А все потому что хоть и была вызвана функция **cancelAndJoin()**, и по идее родительская короутина должна дождаться выполнения своих дочерних короутин для завершения работы, однако
отмена родительской короутины происходит во премя приостановки функции **delay()**, которая в таком случае вызывает исключения **CancellationException**, которое короутина сама обрабатывает, но в таком случае выполнение кода <-- не представляется возможным. 
___
### Ответ на 2-ый вопрос:
В функции main строка <-- не сработает.
```kotlin
fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        val child = launch {
            delay(500)
            println("ok") // <--
        }
        launch {
            delay(500)
            println("ok")
        }
        delay(100)
        child.cancel()
    }
    delay(100)
    job.join()
}
```
Причина такая же что и в предыдущей задаче, отмена **child**  происходит во премя приостановки функции **delay()**, вызывающая исключение **CancellationException**
## Ответы на тему: Exception Handling:
### Ответ на 1-ый вопрос:
___
В функции main строка <-- не сработает.
```kotlin
fun main() {
    with(CoroutineScope(EmptyCoroutineContext)) {
        try {
            launch {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
```
Причина заключается в том, что в момент выброса исключения, блок try - catch будет успешно уже сработан в main потоке. Если бы мы использовали suspend fun исключение бы обработалось.
___
### Ответ на 2-ой вопрос:

В функции main строка <-- сработает.
``` kotlin
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            coroutineScope {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
```
**coroutineScope()** является suspend fun и создает короутину, выбрасывающая "наверх" исключения в случае его возникновения в ней же самой или дочерней короутине. Т.е в нашем случае catch сможет перехватить **Exception("something bad happened")**
___
### Ответ на 3-ий вопрос:

В функции main строка <-- не сработает.
``` kotlin
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            supervisorScope {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
```
**supervisorScope()** является suspend fun и создает короутину, которая в отлчие от 
**coroutineScope()** не выбрасывает "наверх" исключения в случае его возникновения в ней же самой или дочерней короутине. Т.е в нашем случае catch не перехватит **Exception("something bad happened")**
___
### Ответ на 4-ый вопрос:

В функции main строка <-- не сработает.
``` kotlin
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            coroutineScope {
                launch {
                    delay(500)
                    throw Exception("something bad happened") // <--
                }
                launch {
                    throw Exception("something bad happened")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    Thread.sleep(1000)
}
```
**coroutineScope()** является suspend fun и создает короутину, выбрасывающая наверх исклбючения в случае его возникновения в ней же самой или дочерней короутине. В момент, когда строка <-- должна сработать **coroutineScope()** выбросит наверх исключение, возникающей во второй дочерней короутине, далее это исключение обработается в блоке **catch**. Из-за срабатывания исключения в 2-ой дочерней короутине, родительская короутина перейдет в состояние отмены, в следствие которогой 1-ая дочерняя короутина также впадет в ссостояние отмены, из-за чего строка <-- просто напросто не успеет сработать.
___
### Ответ на 5-ый вопрос:

В функции main строка <--1 сработает , <--2 не сработает.
``` kotlin
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            supervisorScope {
                launch {
                    delay(500)
                    throw Exception("something bad happened") // <--1
                }
                launch {
                    throw Exception("something bad happened")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--2
        }
    }
    Thread.sleep(1000)
}
```
<--2 не сработает по той-же причине,что и в вопросе №3. А <--1 сработает за счет особенности **SupervisorJob**, которая не отменяется в случае возникновения исключения в дочерней короутине, из -за чего продолжают свою работу и остальные дочерние короутины. А значит строка <--1 сработает, несмотря на выброс исключения в 2-ой длочерней короутине.
___
### Ответ на 6-ый вопрос:

В функции main строка <-- не сработает.
``` kotlin
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        CoroutineScope(EmptyCoroutineContext).launch {
            launch {
                delay(1000)
                println("ok") // <--
            }
            launch {
                delay(500)
                println("ok")
            }
            throw Exception("something bad happened")
        }
    }
    Thread.sleep(1000)
}
```
<-- не сработает из за выброса **Exception("something bad happened")**, которой вызовет отмену короутины, которая вызовет отмену всех дочерних короутин.
___
### Ответ на 7-ый вопрос:

В функции main строка <-- не сработает.
``` kotlin
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        CoroutineScope(EmptyCoroutineContext + SupervisorJob()).launch {
            launch {
                delay(1000)
                println("ok") // <--
            }
            launch {
                delay(500)
                println("ok")
            }
            throw Exception("something bad happened")
        }
    }
    Thread.sleep(1000)
}
```
<-- не сработает, хоть короутина и работает в контексте **supervisor**, однако выброс 
**Exception("something bad happened")** в любом случае вызовет отмену короутины, в которой оно выбросилось, что привет к отказу ее дочерних короутин, а значит строка <-- не успеет сработать. Хотя и без **throw Exception("something bad happened")** она не сработала бы, 
потому что к тому моменты main поток уже завершил бы свою работу.

