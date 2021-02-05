package ru.sbrf;

public interface MyQueue<T extends Comparable<T>> extends MyList<T >{
    // Отдать первый в очереди элемент и его из очереди удалить
    // Если нет - отдаем Null
    T poll();
}
