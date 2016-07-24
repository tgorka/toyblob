package com.gorkasoft;


/**
 * Data structure, ToyBlob, backed by plain java arrays(s), with the following methods:
 * - size which returns the number of elements in its collection
 * - add which adds an element to its middle when the ToyBlob's size is even or the end when odd.
 * - remove which removes and returns the element at the end of the collection
 * - toString which pretty-prints the elements
 */
public class ToyBlob<T extends Object> {

    private static final int BUFFER_SIZE = 3;

    private T[] queue;
    private final int bufferSize;

    private int leftBegin;
    private int leftEnd;

    private int rightBegin;
    private int rightEnd;

    /**
     * Initialize structure with default buffer size.
     */
    public ToyBlob() {
        this(BUFFER_SIZE);
    }

    /**
     * Initialize structure with proper buffer size
     * @param bufferSize for an array initialization and extension.
     */
    @SuppressWarnings("unchecked")
    public ToyBlob(int bufferSize) {
        this.bufferSize = bufferSize;

        this.queue = (T[]) new Object[this.bufferSize];

        this.leftBegin = 0;
        this.leftEnd = this.leftBegin;

        this.rightBegin = this.queue.length - 1;
        this.rightEnd = this.rightBegin;
    }

    /**
     * Adding new element
     * @param obj element to add
     */
    public void add(T obj) {
        if (this.size() % 2 == 0) {
            this.addLeft(obj);
        } else {
            this.addRight(obj);
        }
    }

    /**
     * Removing and returning last element
     * @return removed element or null if structure is empty
     */
    public T remove() {
        T removed = null;
        if (this.rightSize() > 0) {
            int index = this.getRightArrayIndex(this.rightSize() - 1);
            removed = this.queue[index];
            this.queue[index] = null; // ready to gc
            if (this.rightEnd == this.queue.length - 1) {
                this.rightEnd = 0;
            } else {
                this.rightEnd++;
            }
            normalizeIfNeeded();
        } else if (this.leftSize() > 0) {
            int index = this.getLeftArrayIndex(this.leftSize() - 1);
            removed = this.queue[index];
            this.queue[index] = null; // ready to gc
            if (this.leftEnd == 0) {
                this.leftEnd = this.queue.length - 1;
            } else {
                this.leftEnd--;
            }
            normalizeIfNeeded();
        }

        return removed;
    }

    /**
     * Get structure size
     * @return elements count in the structure
     */
    public int size() {
        return this.leftSize() + this.rightSize();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        sb.append("[");

        for(int i = 0; i < this.leftSize(); i++) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(",");
            }
            T obj = this.queue[this.getLeftArrayIndex(i)];
            sb.append((obj == null) ? "null" : obj.toString());
        }

        for(int i = 0; i < this.rightSize(); i++) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(",");
            }
            T obj = this.queue[this.getRightArrayIndex(i)];
            sb.append((obj == null) ? "null" : obj.toString());
        }

        sb.append("]");
        return sb.toString();
    }

    private int leftSize() {
        int value = this.leftEnd - this.leftBegin;
        return (value >= 0) ? value : value + this.queue.length;
    }

    private int rightSize() {
        int value = this.rightBegin - this.rightEnd;
        return (value >= 0) ? value : value + this.queue.length;
    }

    private int getLeftArrayIndex(int index) {
        int value = this.leftBegin + index;
        return (value < this.queue.length) ? value : value - this.queue.length;
    }

    private int getRightArrayIndex(int index) {
        int value = this.rightBegin - index;
        return (value >= 0) ? value : value + this.queue.length;
    }

    private void addLeft(T obj) {
        this.queue[this.leftEnd] = obj;
        if (this.leftEnd == this.queue.length - 1) {
            this.leftEnd = 0;
        } else {
            this.leftEnd++;
        }
        this.normalizeIfNeeded();
    }

    private void addRight(T obj) {
        this.queue[this.rightEnd] = obj;
        if (this.rightEnd == 0) {
            this.rightEnd = this.queue.length - 1;
        } else {
            this.rightEnd--;
        }
        this.normalizeIfNeeded();
    }

    @SuppressWarnings("unchecked")
    private void normalizeIfNeeded() {
        // add buffer size if needed
        if (this.leftEnd == this.rightEnd) {
            int leftSize = this.leftSize();
            int rightSize = this.rightSize();

            T[] newArray = (T[]) new Object[this.queue.length + this.bufferSize];

            for (int i = 0; i < leftSize; i++) {
                newArray[i] = this.queue[this.getLeftArrayIndex(i)];
            }

            for (int i = 0; i < rightSize; i++) {
                newArray[newArray.length - 1 - i] = this.queue[this.getRightArrayIndex(i)];
            }

            this.queue = newArray;

            this.leftBegin = 0;
            this.leftEnd = leftSize;

            this.rightBegin = newArray.length - 1;
            this.rightEnd = newArray.length - 1 - rightSize;
        }
        // change middle point if needed
        while (this.leftSize() - this.rightSize() > 1) {
            if (this.rightBegin == this.queue.length - 1) {
                this.rightBegin = 0;
                this.leftBegin++;
            } else if (this.leftBegin == this.queue.length - 1) {
                this.rightBegin++;
                this.leftBegin = 0;
            } else {
                this.rightBegin++;
                this.leftBegin++;
            }
        }
    }

}
