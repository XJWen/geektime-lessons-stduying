package homework.week5;

public class minEatingSpeedSolution {

    public int minEatingSpeed(int[] piles, int h) {
        int maxValue = 1;
        for (int pile : piles){
            maxValue = Math.max(maxValue,pile);
        }
        int left=1,reight=maxValue;

        while(left<reight){
            int middle = left + (reight-left)/2;

            if (calculateSum(piles,middle)>h){
                left = middle +1;
            }else{
                reight = middle;
            }
        }

        return  left;
    }

    private int calculateSum(int[] piles,int speed) {
        int sum = 0;
        for (int pile : piles){
            sum += (pile+speed-1)/speed;
        }
        return sum;
    }

}
