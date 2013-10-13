/**
 * @author Jason A Smith <jas7553>
 * 
 */
public class DynamicProgramming extends LcsSolver {

	private static enum DIRECTION {
		LEFT, UP, UP_LEFT;
	}

	private DIRECTION[][] b;
	private int[][] c;

	public DynamicProgramming() {
		super();
	}

	public DynamicProgramming(String x, String y) {
		super(x, y);
	}

	@Override
	public void setXY(String x, String y) {
		super.setXY(x, y);

		b = new DIRECTION[m + 1][n + 1];
		c = new int[m + 1][n + 1];
	}

	@Override
	public String lcs() {
		if (!hasBuiltTables()) {
			buildTables();
		}

		return lcs(x.length, y.length);
	}

	private String lcs(int i, int j) {
		if (i == 0 || j == 0) {
			return "";
		}

		switch (b[i][j]) {
		case UP_LEFT:
			return lcs(i - 1, j - 1) + x[i - 1];
		case UP:
			return lcs(i - 1, j);
		case LEFT:
			return lcs(i, j - 1);
		default:
			throw new RuntimeException("Should not happen");
		}
	}

	@Override
	public int lcsLength() {
		if (!hasBuiltTables()) {
			buildTables();
		}

		return c[m][n];
	}

	@Override
	public void reset() {
		b = new DIRECTION[m + 1][n + 1];
		c = new int[m + 1][n + 1];
	}

	private void buildTables() {
		for (int i = 0; i < m + 1; i++) {
			c[i][0] = 0;
		}

		for (int j = 0; j < n + 1; j++) {
			c[0][j] = 0;
		}

		for (int i = 1; i < m + 1; i++) {
			for (int j = 1; j < n + 1; j++) {
				if (x[i - 1] == y[j - 1]) {
					c[i][j] = c[i - 1][j - 1] + 1;
					b[i][j] = DIRECTION.UP_LEFT;
				} else if (c[i - 1][j] >= c[i][j - 1]) {
					c[i][j] = c[i - 1][j];
					b[i][j] = DIRECTION.UP;
				} else {
					c[i][j] = c[i][j - 1];
					b[i][j] = DIRECTION.LEFT;
				}
			}
		}
	}

	private boolean hasBuiltTables() {
		return b[m][n] != null;
	}

	public void displayS() {
		System.out.print("  j ");
		for (int i = 0; i < c[0].length; i++) {
			System.out.print(i + " ");
		}
		System.out.println();

		System.out.print("i     ");
		for (int i = 0; i < y.length; i++) {
			System.out.print(y[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < c.length; i++) {
			System.out.print(i + " ");
			if (i == 0) {
				System.out.print(" ");
			} else {
				System.out.print(x[i - 1]);
			}
			System.out.print(" ");
			for (int j = 0; j < c[0].length; j++) {
				System.out.print(c[i][j] + " ");
			}
			System.out.println();
		}
	}
}