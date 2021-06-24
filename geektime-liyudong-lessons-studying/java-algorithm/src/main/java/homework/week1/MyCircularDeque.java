package homework.week1;

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */
public class MyCircularDeque {
    //数组初始大小
    private int size;
    //队列数组
    private int[] deque;
    //队列头部指针
    private int front;
    //队列尾部指针
    private int rear;

    public static void main(String[] args) {
        MyCircularDeque myDeque = new MyCircularDeque(3);
        myDeque.insertFront(1);
        myDeque.insertLast(3);
        myDeque.getFront();
        myDeque.getRear();
        myDeque.isFull();
        myDeque.deleteLast();
        myDeque.getRear();
        myDeque.deleteFront();
        myDeque.isEmpty();
    }

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        this.size = k + 1;
        this.deque = new int[size];
        this.front = 0;
        this.rear = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (isFull()){
            return false;
        }
        this.front = (front - 1 +size)%size;
        deque[front] = value;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (isFull()){
            return false;
        }
        deque[rear] = value;
        this.rear = (rear + 1)%size;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (isEmpty()){
            return  false;
        }
        this.front = (front + 1)%size;
        return  true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (isEmpty()){
            return  false;
        }
        this.rear = (rear -1 + size)%size;
        return  true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if (isEmpty()){
            return -1;
        }
        return deque[front];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if (isEmpty()){
            return -1;
        }
        return deque[(rear-1+size)%size];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return front == rear;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return (rear+1)% size == front;
    }
}
