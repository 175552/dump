import java.util.*;
import java.io.*;

public class shortcut{
    public static class edge{
        int from;
        int to;
        int weight;
        edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    public static class query implements Comparator<query>{
        int pos;
        long dist;
        query(int pos, long dist) {
            this.pos = pos;
            this.dist = dist;
        }
        query() {}
        public int compare(query one, query two) {
            if(one.dist < two.dist) {
                return -1;
            } else if(one.dist > two.dist) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    public static long dfs(int cur, int parent, long[] cows, ArrayList<edge>[] edgeList) {
        long total = cows[cur];
        for(edge e : edgeList[cur]) {
            if(e.to == parent) {
                continue;
            }
            if(parents[e.to] == cur) {
                total += dfs(e.to, cur, cows, edgeList);
            }
        }
        return totcows[cur] = total;
    }
    public static void dijkstra(long[] cows, ArrayList<edge>[] edgeList) {
        PriorityQueue<query> pq = new PriorityQueue<>(new query());
        distances[1] = 0;
        pq.add(new query(1, 0));
        while(!pq.isEmpty()) {
            query temp = pq.remove();
            for(edge e : edgeList[temp.pos]) {
                if(temp.dist + e.weight < distances[e.to]) {
                    distances[e.to] = temp.dist + e.weight;
                    pq.add(new query(e.to, distances[e.to]));
                    parents[e.to] = temp.pos;
                }
                if(temp.dist + e.weight == distances[e.to] && temp.pos < parents[e.to]) {
                    parents[e.to] = temp.pos;
                    pq.add(new query(e.to, distances[e.to]));
                }
            }
        }
    }
    static long[] distances;
    static int[] parents;
    static long[] totcows;
    static int n, m, t;
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(new File("shortcut.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
        n = input.nextInt();
        m = input.nextInt();
        t = input.nextInt();
        long[] cows = new long[n + 1];
        distances = new long[n + 1];
        totcows = new long[n + 1];
        parents = new int[n + 1];
        Arrays.fill(distances, Long.MAX_VALUE);
        for(int i = 1; i <= n; i++) {
            parents[i] = i;
        }
        ArrayList<edge>[] edgeList = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) {
            cows[i] = input.nextInt();
        }
        for(int i = 1; i <= n; i++) {
            edgeList[i] = new ArrayList<>();
        }
        for(int i = 0; i < m; i++) {
            int from = input.nextInt();
            int to = input.nextInt();
            int weight = input.nextInt();
            edgeList[from].add(new edge(from, to, weight));
            edgeList[to].add(new edge(to, from, weight));
        }
        dijkstra(cows, edgeList);
        long store = dfs(1, 0, cows, edgeList);
        long max = 0;
        for(int i = 1; i <= n; i++) {
            max = Math.max(max, (distances[i] - t) * totcows[i]);
        }
        out.println(max);
        out.close();
    }
}