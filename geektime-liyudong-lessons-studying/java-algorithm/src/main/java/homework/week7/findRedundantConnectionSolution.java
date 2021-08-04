package homework.week7;

public class findRedundantConnectionSolution {

    /**
     * 题目:
     * 树可以看成是一个连通且 无环的无向图。
     *
     * 给定往一棵n 个节点 (节点值1～n) 的树中添加一条边后的图。添加的边的两个顶点包含在 1 到 n中间，且这条附加的边不属于树中已存在的边。
     * 图的信息记录于长度为 n 的二维数组 edges，edges[i] = [ai, bi]表示图中在 ai 和 bi 之间存在一条边。
     *
     * 请找出一条可以删去的边，删除后可使得剩余部分是一个有着 n 个节点的树。如果有多个答案，则返回数组edges中最后出现的边。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/redundant-connection
     * */

    public int[] findRedundantConnection(int[][] edges) {
        int[] rp = new int[edges.length+1];
        int[] result = new int[2];
        for (int i = 0; i < edges.length;i++){
            rp[i] = i;
        }
        for (int[] edge:edges){
            int leftRoot = find(edge[0],rp);
            int rightRoot = find(edge[1],rp);

            if (leftRoot != rightRoot){
                rp[leftRoot] = rightRoot;
            }else {
                return edge;
            }
        }
        return result;
    }

    private int find(int n,int[] rp) {
        int num = n;
        while (rp[num]!=num){
            num= rp[num];
        }
        return num;
    }

}
