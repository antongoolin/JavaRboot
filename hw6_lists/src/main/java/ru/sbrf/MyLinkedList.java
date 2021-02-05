package ru.sbrf;
public class MyLinkedList<T extends Comparable<T>> implements MyList<T>, MyQueue<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    @Override
    public void add(T item) {
        // Создаем новый элемент и присваеваем переданному значению
        Node<T> node = new Node<>();
        node.item = item;

        //Если голова ещё не задана - то её присваиваем новому элементу
        if (head == null) {
            head = node;
        }
        // Хвост заменяется на новый
        Node<T> tempTail = tail;
        tail = node;

        // Связываем старый хвост и новый хвост между собой
        if (tempTail != null) {
            tempTail.next = tail;
        }
        tail.prev = tempTail;
        // Двигаемся по счетчику
        size++;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        if (node != null)
            return node.item;
        return null;

    }

    private Node<T> getNode(int index) {
        if (!checkItemIndex(index))
            return null;
        Node<T> node;
        if (index > (size / 2)) {
            node = tail;
            if (index == size - 1)
                return node;
            for (int i = size - 2; i >= index; i--) {
                node = node.prev;
            }
            return node;
        } else {
            node = head;
            if (index == 0)
                return node;
            for (int i = 1; i <= index; i++) {
                node = node.next;
            }
            return node;
        }
    }

    private boolean checkItemIndex(int index) {
        return index >= 0 && index < size;
        //throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
    }

    @Override
    public boolean remove(T item) {
        for (int i = 0; i < this.size(); i++) {
            Node<T> node = getNode(i);
            if (node != null) {
                if (node.item.equals(item)) {
                    Node<T> next = node.next;
                    Node<T> prev = node.prev;

                    if (prev == null) {
                        head = next;
                    } else {
                        prev.next = next;
                        node.prev = null;
                    }

                    if (next == null) {
                        tail = prev;
                    } else {
                        next.prev = prev;
                        node.next = null;
                    }

                    node.item = null;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T poll() {
        T item = get(0);
        remove(item);
        return item;
    }

    @Override
    public void sort() {
        boolean wasChange = true;
        while (wasChange) {
            wasChange = false;
            Node<T> first = head;
            Node<T> second = head.next;
            while (second != null) {
                wasChange = wasChange || compareAndReplaceItem(first, second);
                first = second;
                second = second.next;
            }
        }
    }

    public void sortList() {
        Node<T> current = head;
        Node<T> index;
        T temp;

        if (head != null) {
            while (current != null) {
                //Node index will point to node next to current
                index = current.next;

                while (index != null) {
                    //If current node's data is greater than index's node data, swap the data between them
                    if (current.item.compareTo(index.item) > 0) {
                        temp = current.item;
                        current.item = index.item;
                        index.item = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
    }

    private boolean compareAndReplaceItem(Node<T> first, Node<T> second) {
        if (second.item.compareTo(first.item) < 0) {
            second.prev = first.prev;
            if (second.prev == null) {
                head = second;
            }
            first.next = second.next;
            if (first.next == null) {
                tail = first;
            }
            second.next = first;
            first.prev = second;
            return true;
        }
        return false;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

    }
}