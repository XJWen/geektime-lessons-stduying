package homework.week4;

import java.util.PriorityQueue;

public class MedianFinder {

    private  int count;
    private PriorityQueue<Integer> maxheap;
    private PriorityQueue<Integer> minheap;

    /** initialize your data structure here. */
    public MedianFinder() {
        count = 0;
        maxheap = new PriorityQueue<Integer>((x,y)->(y-x));
        minheap = new PriorityQueue<Integer>();
    }

    public void addNum(int num) {
        count += 1;
        maxheap.offer(num);
        minheap.add(maxheap.poll());

        if ((count & 1)!=0){
            maxheap.add(minheap.poll());
        }
    }

    public double findMedian() {
        double result = 0;
        if ((count & 1) == 0){
            result = (double) (maxheap.peek()+ minheap.peek())/2;
        }else{
            result = (double)maxheap.peek();
        }

        return result;
    }

}
