package structures;

public class Stack<T> extends DoublyLinkedList<T> {

    public Stack() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(T element) {
        if (size == 0) {
            this.insertEmptyList(element);
        } else {
            this.insertHead(element);
        }
    }

    public T dequeue() {
        try {
            Node<T> value = this.find(0);
            this.remove(0);
            return value.getData();
        } catch (Exception e) {
            return null;
        }

    }

    public Node<T> peek(int index) {
        if (size > 0) {
            // Node<T> value = this.find(0);
            // return value.getData();
            return this.find(index);
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }
}
