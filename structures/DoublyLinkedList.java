package structures;

public class DoublyLinkedList<T> {

    protected Node<T> head;
    protected Node<T> tail;
    protected int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void insertEmptyList(T _data) {
        try {
            Node<T> node = new Node<T>(_data, 0);
            head = node;
            tail = node;
            size++;
        } catch (Exception e) {
            System.out.println("List isn't empty!");
        }
    }

    public void insertHead(T _data) {
        try {
            Node<T> node = new Node<T>(_data, 0);
            node.next = head;
            head.prev = node;
            head = node;
            size++;
            Node<T> pointer = node.next;
            rearrangeIndexes(pointer, 1, size, true);
        } catch (Exception e) {
            System.out.println("List is empty!");
        }
    }

    public void insertTail(T _data) {
        try {
            Node<T> node = new Node<T>(_data, size);
            node.prev = tail;
            tail.next = node;
            tail = node;
            size++;
        } catch (Exception e) {
            System.out.println("List is empty!");
        }
    }

    public void insertMiddle(T _data, int _index) {
        try {
            Node<T> node = new Node<T>(_data, _index);
            Node<T> pointer = find(_index);
            node.next = pointer;
            node.prev = pointer.prev;
            pointer.prev.next = node;
            pointer.prev = node;
            rearrangeIndexes(pointer, pointer.index, size, true);
            size++;
        } catch (Exception e) {
            System.out.println("Index out of bounds!");
        }
    }

    public void remove(int _index) {
        try {
            if (_index == 0) {
                if (size == 1) {
                    head = null;
                    tail = null;
                } else {
                    Node<T> pointer = head;
                    pointer.next.prev = null;
                    head = pointer.next;
                    rearrangeIndexes(pointer.next, 1, size, false);
                }
                size--;
            } else if (_index == size - 1) {
                Node<T> pointer = tail;
                pointer.prev.next = null;
                tail = pointer.prev;
                size--;
            } else {
                Node<T> pointer = find(_index);
                pointer.prev.next = pointer.next;
                pointer.next.prev = pointer.prev;
                rearrangeIndexes(pointer.next, pointer.index + 1, size, false);
                size--;
            }
        } catch (Exception e) {
            System.out.println("Index out of bounds!");
        }
    }

    public Node<T> find(int _index) {
        try {
            Node<T> current = head;
            while (_index > 0) {
                _index--;
                current = current.next;
            }
            return current;
        } catch (Exception e) {
            System.out.println("Index out of bounds!");
            return null;
        }
    }

    private void rearrangeIndexes(Node<T> pointer, int left, int right, boolean toIncrease) {
        if (toIncrease) {
            for (int i = left; i < right; i++) {
                pointer.index++;
                pointer = pointer.next;
            }
        } else {
            for (int i = left; i < right; i++) {
                pointer.index--;
                pointer = pointer.next;
            }
        }
    }

    // public String getList() {
    // var elemArray = new String[size];
    // Node<T> pointer = head;
    // for (int j = 0; j < size; j++) {
    // elemArray[j] = pointer.data; // + "|" + pointer.index;
    // pointer = pointer.next;
    // }
    // return String.join(" ", elemArray);
    // }

    public int getSize() {
        return size;
    }
}
