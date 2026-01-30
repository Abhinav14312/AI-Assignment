import java.util.*;

class PuzzleNode {
    int[][] state;
    int x, y;
    int g, h;
    PuzzleNode parent;

    PuzzleNode(int[][] state, int x, int y, int g, PuzzleNode parent) {
        this.state = state;
        this.x = x;
        this.y = y;
        this.g = g;
        this.parent = parent;
        this.h = calculateHeuristic();
    }

    int calculateHeuristic() {
        int dist = 0;
        int[][] goal = {{1,2,3},{4,5,6},{7,8,0}};

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(state[i][j] != 0) {
                    int val = state[i][j];
                    for(int r=0;r<3;r++) {
                        for(int c=0;c<3;c++) {
                            if(goal[r][c] == val)
                                dist += Math.abs(i-r) + Math.abs(j-c);
                        }
                    }
                }
            }
        }
        return dist;
    }

    boolean isGoal() {
        int[][] goal = {{1,2,3},{4,5,6},{7,8,0}};
        return Arrays.deepEquals(state, goal);
    }
}

public class PuzzleSolver {

    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};

    public static List<int[][]> solve(int[][] start) {

        PriorityQueue<PuzzleNode> open =
            new PriorityQueue<>(Comparator.comparingInt(a -> a.g + a.h));

        Set<String> closed = new HashSet<>();

        int sx=0, sy=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(start[i][j]==0) { sx=i; sy=j; }

        open.add(new PuzzleNode(start, sx, sy, 0, null));

        while(!open.isEmpty()) {
            PuzzleNode curr = open.poll();

            if(curr.isGoal()) {
                return buildPath(curr);
            }

            closed.add(Arrays.deepToString(curr.state));

            for(int i=0;i<4;i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];

                if(nx>=0 && nx<3 && ny>=0 && ny<3) {
                    int[][] newState = new int[3][3];
                    for(int r=0;r<3;r++)
                        newState[r] = curr.state[r].clone();

                    newState[curr.x][curr.y] = newState[nx][ny];
                    newState[nx][ny] = 0;

                    if(!closed.contains(Arrays.deepToString(newState)))
                        open.add(new PuzzleNode(newState, nx, ny, curr.g+1, curr));
                }
            }
        }
        return new ArrayList<>();
    }

    static List<int[][]> buildPath(PuzzleNode node) {
        List<int[][]> path = new ArrayList<>();
        while(node != null) {
            path.add(node.state);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
