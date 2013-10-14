import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason A Smith <jas7553>
 * 
 */
public class RecursiveMemoization extends LcsSolver {

	private final Map<String, String> cache = new HashMap<String, String>();

	public RecursiveMemoization() {
		super();
	}

	@Override
	public String lcs() {
		cache.clear();
		performanceMonitor.resetRecursiveCallCount();

		return lcs(x, y);
	}

	private String lcs(char[] x, char[] y) {
		return lcs(x, y, x.length, y.length);
	}

	private String lcs(char[] x, char[] y, int i, int j) {
		performanceMonitor.makeRecursiveCall();

		if (i == 0 || j == 0) {
			return "";
		}

		String key = new StringBuilder().append(x).append("_").append(y).toString();
		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		String lcs;

		char[] xSub = Arrays.copyOf(x, x.length - 1);
		char[] ySub = Arrays.copyOf(y, y.length - 1);

		if (x[i - 1] == y[j - 1]) {
			lcs = lcs(xSub, ySub) + x[i - 1];

		} else {
			String lcsSub1 = lcs(x, ySub);
			String lcsSub2 = lcs(xSub, y);

			lcs = lcsSub1.length() > lcsSub2.length() ? lcsSub1 : lcsSub2;
		}

		cache.put(key, lcs);

		return lcs;
	}

	@Override
	public int lcsLength() {
		return lcs().length();
	}

	public static void main(String... args) {
		LcsSolver solver = new RecursiveMemoization();

		String lcs = solver.lcs("AGGTAB", "GXTXAYB");
		System.out.println("LCS: " + lcs);
		System.out.println("Recusive call count: " + solver.getPerformanceMonitor().getRecursiveCallCount());
		System.out.println("Took: " + solver.getPerformanceMonitor().getElapsedTimeMillis() + "ms");
		solver.getPerformanceMonitor().resetRecursiveCallCount();
		System.out.println();

		int lcsLength = solver.lcsLength("AGGTAB", "GXTXAYB");
		System.out.println("LCS length: " + lcsLength);
		System.out.println("Recusive call count: " + solver.getPerformanceMonitor().getRecursiveCallCount());
		System.out.println("Took: " + solver.getPerformanceMonitor().getElapsedTimeMillis() + "ms");
		solver.getPerformanceMonitor().resetRecursiveCallCount();
	}
}
