### Вопрос:

_По какой причине не завершается работа функции main?_

---

### Ответ:

Все дело в этом куске кода

```Kotlin
 val t1 = thread {
        consumerA.lockFirstAndTrySecond(resourceA, resourceB)
    }
 val t2 = thread {
        consumerB.lockFirstAndTrySecond(resourceB, resourceA)
    }

```

Когда мы выхзываем метод lockFirstAndTrySecond для consumerB как первый first мы передаем resourceB,из-за этого во время того,когда первый поток захочет выполнить метод locksecond,он будет ждать пока свою работу закончит метод lockFirstAndTrySecond consumerB, который в свою очередь для завершения будет ждать завершения lockFirstAndTrySecond consumer A. По итогу у нас выйдет ситуация что оба потока будут находится в статусе ожидания завершения, из-чего main не сможет вывести в консоль нужную нам фразу, ведь работа потока так и не будет завершена. Для исправления необходимо переписать выше приведенный код таким образом:

```Kotlin
val t1 = thread {
       consumerA.lockFirstAndTrySecond(resourceA, resourceB)
   }
val t2 = thread {
       consumerB.lockFirstAndTrySecond(resourceA, resourceB)
   }

```

или же вот так:

```Kotlin
val t1 = thread {
      consumerA.lockFirstAndTrySecond(resourceB, resourceA)
  }
val t2 = thread {
      consumerB.lockFirstAndTrySecond(resourceB, resourceA)
  }

```
