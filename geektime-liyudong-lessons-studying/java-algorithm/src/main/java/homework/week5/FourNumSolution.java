package homework.week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class FourNumSolution {

        public List<List<Integer>> fourSum(int[] nums, int target) {
            Arrays.sort(nums);
            int length = nums.length;
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            int third,fourth;
            for (int first = 0; first <=length - 4;first++){
                if (first>0&&nums[first]==nums[first-1]){
                    continue;
                }

                for (int second = first+1; second<=length-3;second++){
                    if (second>first+1&&nums[second]==nums[second-1]){
                        continue;
                    }

                    third = second+1;
                    fourth = length -1;
                    while (third<fourth){
                        if (third>second+1&&nums[third]==nums[third-1]){
                            third++;
                            continue;
                        }

                        if (nums[first]+nums[second]+nums[third]+nums[fourth]>target){
                            fourth--;
                        }else if (nums[first]+nums[second]+nums[third]+nums[fourth]<target){
                            third++;
                        }else{
                            result.add(new ArrayList<>(Arrays.asList(nums[first],nums[second],nums[third],nums[fourth])));
                            third++;
                            fourth--;
                        }
                    }
                }
            }
            return result;
        }

        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            int m = maze.length,n = maze[0].length,i,j,k,x,y,z;
            Queue<int[]> queue = null;
            queue.add(start);
            while(!queue.isEmpty()){
                i = queue.peek()[0];
                j = queue.peek()[1];
                queue.poll();
                for (k =0;k<4;++k){
                    x=i;
                    y=j;
                    z = 0;
                }
            }
            return  true;
        }

}
