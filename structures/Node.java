package structures;

public class Node<T> {
    protected T data;
    protected int index;
    Node<T> prev;
    Node<T> next;

    Node(T _data, int _index) {
        data = _data;
        index = _index;
        prev = null;
        next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T value) {
        data = value;
    }
}