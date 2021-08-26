package homework.week9;

import java.util.Comparator;
import java.util.PriorityQueue;

public class maxSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] pair1, int[] pair2) {
                return pair1[0] != pair2[0] ?
                        pair2[0] - pair1[0] :
                        pair2[1] - pair1[1];
            }
        });
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{nums[i], i});
        }
        ans[0] = pq.peek()[0];
        for (int i = k; i < nums.length; ++i) {
            pq.offer(new int[]{nums[i], i});
            while (true) {
                assert pq.peek() != null;
                if (!(pq.peek()[1] <= i - k)) {
                    break;
                }
                pq.poll();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
    }

}
