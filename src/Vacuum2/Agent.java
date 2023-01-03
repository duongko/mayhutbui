package Vacuum2;

public class Agent {
	static int[][] a;
	static int x = 0;
	static int y = 0;
	static int diem, h2;
	State state;

	public Agent(Environment e) {
		a = e.a;
		state = new State(e);
	}

	public void run() {
		state.run();
	}

	public static void right(int i, int j) {
		a[i][j] = 0;
		x = i;
		y = ++j;
	}

	public static void left(int i, int j) {
		a[i][j] = 0;
		x = i;
		y = --j;
	}

	public static void up(int i, int j) {
		a[i][j] = 0;
		x = --i;
		y = j;
	}

	public static void down(int i, int j) {
		a[i][j] = 0;
		x = ++i;
		y = j;

	}
}
