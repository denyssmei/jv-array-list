package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int ZERO_INDEX = 0;
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE
            = "Index %d out of bounds for length %d";
    private T[] array;
    private int size;

    public ArrayList() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(T value) {
        resizeArr();
        array[size++] = value;
    }

    @Override
    public void add(T value, int index) {
        resizeArr();
        if (index < size && index >= ZERO_INDEX) {
            array = addingElementInside(value, index, array);
            size++;
        } else if (index == size) {
            add(value);
        } else {
            throw new ArrayListIndexOutOfBoundsException(String
                    .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index, size));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return array[index];
        }
        throw new ArrayListIndexOutOfBoundsException(String
                .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index, size));
    }

    @Override
    public void set(T value, int index) {
        if (index >= 0 && index < size) {
            array[index] = value;
        } else {
            throw new ArrayListIndexOutOfBoundsException(String
                    .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index, size));
        }
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(String
                    .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index, size));
        }
        T[] newArray = (T[]) new Object[array.length];
        System.arraycopy(array, ZERO_INDEX,
                newArray, ZERO_INDEX, index);
        System.arraycopy(array, index + 1, newArray,
                index, array.length - index - 1);
        T returnValue = array[index];
        array = newArray;
        size--;
        return returnValue;
    }

    @Override
    public T remove(T element) {
        int indexOfElement = -1;
        for (int i = 0; i < size; i++) {
            if ((array[i] == null && element == null)
                    || (array[i] != null && array[i].equals(element))) {
                indexOfElement = i;
                break;
            }
        }
        if (indexOfElement == -1) {
            throw new NoSuchElementException();
        }
        return remove(indexOfElement);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == ZERO_INDEX;
    }

    private T[] addingElementInside(T value, int index, T[] oldArray) {
        T[] newArray = (T[]) new Object[array.length];
        System.arraycopy(oldArray, ZERO_INDEX,
                newArray, ZERO_INDEX, index);
        newArray[index] = value;
        System.arraycopy(oldArray, index, newArray,
                index + 1, oldArray.length - index - 1);
        return newArray;
    }

    private void resizeArr() {
        if (size == array.length) {
            T[] newArray = (T[]) new Object[(int) (array.length * 1.5)];
            System.arraycopy(array, ZERO_INDEX,
                    newArray, ZERO_INDEX, array.length);
            array = newArray;
        }
    }
}
