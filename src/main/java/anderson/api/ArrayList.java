package anderson.api;

public interface ArrayList<E> {
    boolean add(E element);
    void add(int index, E element);
    void clear();
    boolean contains(E element);
    E get(int index);
    int indexOf(E element);
    int size();
    boolean remove(E element);
    E remove(int index);
}
