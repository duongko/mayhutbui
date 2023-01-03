package Vacuum2;

import java.util.Random;

public class NhanThuc {
	State state;
	int[][] a, d;
	boolean[][] b;

	public NhanThuc(State state) {
		this.state = state;
		this.a = state.a;
		this.d = state.d;
		this.b = state.b;
	}

	boolean kiemTraXungQuanh(int i, int j) { // kiểm tra xung quanh rác
		// có tường
		if (checkTuong(i, j - 1) && checkTuong(i, j + 1) && checkTuong(i + 1, j) && checkTuong(i - 1, j)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean checkTuong(int i, int j) { // Kiểm tra tường
		try {
			if (a[i][j] == 10) // Gán tường là số 10
				return true;
			else
				return false;
		} catch (Exception e) {
			return true;
		}
	}

	public void kiemTraBui() { // Kiem tra số bụi sẽ hút được
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				d[i][j] = a[i][j]; // Tạo 1 mảng mới bằng môi trường để kiểm tra
			}
		}
		Agent.a = d; // Cho agent kiem tra.
		if (!kiemTraXungQuanh(0, 0)) {
			int d = 0;
			while (d <= a.length * a[0].length * 5) {
				state.move(Agent.x, Agent.y, this.d);
				if (state.demRac != state.rac) {
					state.rac = state.demRac;
				}
				d++;
			}
		}
	}

	Random rd = new Random(4);

	public int huong(int x, int y, int[][] a) {
		int h = 0;// kiem tra xung
					// quanh co
		// rac;
		if (!checkTuong(x - 1, y) && !checkBoolean(x - 1, y) && a[x - 1][y] == 1) { // check
			// len
			h = 3;
		} else if (!checkTuong(x, y - 1) && !checkBoolean(x, y - 1) && a[x][y - 1] == 1) { // check
			// trai
			h = 1;
		} else if (!checkTuong(x, y + 1) && !checkBoolean(x, y + 1) && a[x][y + 1] == 1) { // check
			// phai
			h = 0;
		} else if (!checkTuong(x + 1, y) && !checkBoolean(x + 1, y) && a[x + 1][y] == 1) { // check
			// xuong
			h = 2;

		} else {
//			 h = (rd.nextInt(4) == state.h2) ? rd.nextInt(4) : state.h2; // Đi theo 1
			// hướng với xác suất 75%
			h = (rd.nextInt(4));
		}
		return h;

	}

	public boolean checkBoolean(int x, int y) { // Kiem tra vị trí đã qua.
		try {
			if (state.b[x][y]) {
				return true; // đã qua.
			} else {
				return false; // chưa qua.
			}
		} catch (Exception e) {
			return true;
		}

	}

}
