package maze;

import java.util.List;

/**
 * https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/
 */
public class MinimumSpanningTree {

    private static int minKey(int[] key, boolean[] mstSet) {

        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int i = 0; i < key.length; i++) {
            if (!mstSet[i] && key[i] < min) {
                min = key[i];
                min_index = i;
            }
        }

        return min_index;
    }

    public static int[] primMST(int[][] matrix) {
        int[] parent = new int[matrix.length];
        int[] key = new int[matrix.length];
        boolean[] mstSet = new boolean[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < matrix.length - 1; i++) {

            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int j = 0; j < matrix.length; j++) {
                if (matrix[u][j] != 0 && !mstSet[j] && matrix[u][j] < key[j]) {
                    parent[j] = u;
                    key[j] = matrix[u][j];
                }
            }
        }

        return parent;
    }
}
