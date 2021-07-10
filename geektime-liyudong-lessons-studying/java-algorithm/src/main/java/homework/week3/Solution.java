package homework.week3;

public class Solution {

    private int n, m;

    public void solve(char[][] board) {
        n = board.length;
        if (n == 0) {
            return;
        }
        m = board[0].length;
        //搜索上下边界的'0'
        for (int i = 1; i < m - 1; i++) {
            dfs(board, 0, i);
            dfs(board, n - 1, i);
        }
        // 搜索左右边界的'O'
        for (int i = 0; i < n; i++) {
            dfs(board, i, 0);
            dfs(board, i, m - 1);
        }
        //标记，做填充
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'M') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfs(char[][] board, int x, int y) {
        if (x < 0 || x >= n || y < 0 || y >= m || board[x][y] != 'O') {
            return;
        }
        //标记字符为M
        board[x][y] = 'M';
        //搜索四个方向
        //上
        dfs(board, x + 1, y);
        //下
        dfs(board, x - 1, y);
        //右
        dfs(board, x, y + 1);
        //左
        dfs(board, x, y - 1);
    }

}
