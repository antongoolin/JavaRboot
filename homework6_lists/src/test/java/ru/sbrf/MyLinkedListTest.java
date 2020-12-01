package ru.sbrf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;


public class MyLinkedListTest {

    private MyList<Integer> list;

    @BeforeEach()
    public void beforeAll() {
        list = new MyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(10);
    }

    @DisplayName("добавлять элементы и корректно давать размер")
    @Test
    public void testAdd() {
        assertEquals(3, list.size());
    }

    @DisplayName("находить элемент по индексу")
    @Test
    public void testGet() {
        assertEquals(Integer.valueOf(2), list.get(1));
    }

    @DisplayName("возвращать null если выходим за размер")
    @Test
    public void testGetWithNull() {
        assertNull(list.get(3));
    }

    @DisplayName("находить удалять по элемент по совпадению")
    @Test
    public void testRemoveFirst() {
        list.add(2);
        boolean result = list.remove(2);
        assertTrue(result);
        assertEquals(Integer.valueOf(10), list.get(1));
        assertEquals(3, list.size());
    }

    @DisplayName("возвращать false если удаление по элементу не удалось")
    @Test
    public void testRemoveFalse() {
        boolean result = list.remove(3);
        assertFalse(result);
        assertEquals(3, list.size());
    }

    @DisplayName("кооректно сортировать Integer")
    @Test
    public void testSort() {
        list.add(6);
        list.add(4);
        // 1,2,10,6,4
        list.sortList();

        assertEquals(5, list.size());
        assertEquals(Integer.valueOf(1), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));
        assertEquals(Integer.valueOf(4), list.get(2));
        assertEquals(Integer.valueOf(6), list.get(3));
        assertEquals(Integer.valueOf(10), list.get(4));
    }

    @DisplayName("возвращать первый в очереди элемент и удалить его из очереди ")
    @Test
    public void testPoll() {
        MyQueue<Integer> queue = new MyLinkedList<>();
        queue.add(6);
        queue.add(4);
        queue.add(9);

        Integer item = queue.poll();

        assertEquals(2, queue.size());
        assertEquals(Integer.valueOf(6), item);
        assertEquals(Integer.valueOf(4), queue.get(0));
        assertEquals(Integer.valueOf(9), queue.get(1));

        queue.poll();

    }

    @DisplayName("возврщать null, если в очереди ничего нет")
    @Test
    public void testPollReturnNull() {
        MyQueue<Integer> queue = new MyLinkedList<>();
        Integer item = queue.poll();
        assertNull(item);

    }

}
