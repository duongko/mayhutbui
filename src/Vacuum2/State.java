package Vacuum2;

import java.util.Random;

public class State {
	int demRac, soRac; // Số rác đã biết.
	int[][] a, c, d;
	int h2 = 0; // Hướng agent
	Random rd;
	int diem;
	boolean[][] b; // Mảng vị trí đã đi qua.
	public boolean stop; // Dừng và kết thúc
	NhanThuc nhanThuc;

	public State(Environment e) {
		this.a = e.a;
		this.soRac = e.soRac; // số rác đã cho
		rd = new Random();
		c = new int[a.length][a[0].length]; // Tạo đường về
		b = new boolean[a.length][a[0].length]; // Kiểm tra đường đi
		d = new int[a.length][a[0].length]; // dùng để kiểm tra bụi
		nhanThuc = new NhanThuc(this);
		taoBooleanVitri(); // Tạo mảng vị trí
		nhanThuc.kiemTraBui(); // Biết được số bụi cần dọn
		reset(); // Khởi tạo lại gía trị

	}

	public boolean checkStop() { // Kiểm ra số rác có thể hút được để kết thúc
									// quá
									// trình tìm.
		if (demRac == rac) {
			return true;
		} else {
			return false;
		}
	}

	int rac = 0;

	private void taoDuongVe() { // Tạo đường về
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 10) {
					c[i][j] = 10;
				}
				if (a[i][j] == 1) {
					c[i][j] = 1;
				}
			}

		}
	}

	private void taoBooleanVitri() { // tạo mảng boolean kiểm tra vị trí đã đi
										// và tường = true;
		b = new boolean[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 10) {
					b[i][j] = true;
				}
			}
		}
	}

	private void reset() { // Đặt lai giá trị
	
		taoBooleanVitri();
		Agent.x = 0;
		Agent.y = 0;
		Agent.a = a;
		diem = 0;
		demRac = 0;
	}

	public void run() {
		checkVe = false;
		while (!checkStop()) {
			try {
				if (nhanThuc.kiemTraXungQuanh(0, 0)) { // xung quanh 0,0 có
					break;
				}
				Thread.sleep(300);
				move(Agent.x, Agent.y, a);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
		
			e1.printStackTrace();
		}
		taoDuongVe(); // Tạo đường về
		if (checkStop()) {
			Agent.a = c;
			checkVe = true;
			taoBooleanVitri(); // Tạo mảng kiểm tra vị trí mới
			while (true) {
				try {
					Thread.sleep(300);
					move(Agent.x, Agent.y, Agent.a);
					if (Agent.x == 0 && Agent.y == 0) {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stop = true;
		}
	}

	private int clean(int x, int y, int[][] a) {
		if (a[x][y] == 1) {
			a[x][y] = 0;
			a[x][y] = -1;
			return demRac++;
		} else {
			a[x][y] = -1;
			return demRac;
		}
	}

	private void resetBoolean(int x, int y) { // Kiểm tra xung quanh nếu đã đi
		int LEFT = y - 1;
		int RIGHT = y + 1;
		int UP = x - 1;
		int DOWN = x + 1;
		// Nếu xung quanh đã đi thì gán lại false.
		if (checkBoolean(x, RIGHT) && checkBoolean(x, LEFT) && checkBoolean(DOWN, y) && checkBoolean(UP, y)) {
			if (!checkTuong(x, LEFT)) {
				b[x][LEFT] = false;
			}
			if (!checkTuong(x, RIGHT)) {
				b[x][RIGHT] = false;
			}
			if (!checkTuong(UP, y)) {
				b[UP][y] = false;
			}
			if (!checkTuong(DOWN, y)) {
				b[DOWN][y] = false;
			}
		}

	}

	private boolean checkTuong(int x, int y) { // Kiểm tra có tường hay k
		return nhanThuc.checkTuong(x, y);
	}

	private boolean checkBoolean(int x, int y) { // Kiêm tra vi trí đã đi

		return nhanThuc.checkBoolean(x, y);
	}

	boolean checkVe = true; // Bằng false sẽ tạo đường về

	private void ve(int x, int y) { // Tạo đường về
		c[x][y] = 1;

//		if (!checkVe) {
//			c[x][y] = 1;
//		} else {
//		c[x][y] = 1;
//		}
//		c[x][y] = 0;
	}

	private void state(int x, int y, int h, int[][] a) { // Trạng thái agent
		ve(Agent.x, Agent.y); // Tạo đường về
		b[x][y] = true; // Vị trí đã qua
		h2 = h; // hướng agent
		clean(Agent.x, Agent.y, a); // Kiểm tra vị trí hiện tại
		print(a);
	}

	public void move(int x, int y, int[][] a) { // duy chuyển của agent
		int LEFT = y - 1;
		int RIGHT = y + 1;
		int UP = x - 1;
		int DOWN = x + 1;
		int h = nhanThuc.huong(x, y, a);
		switch (h) {
		case 0: // phai
			if (!checkBoolean(x, RIGHT) && !checkTuong(x, RIGHT)) {
				diem(x, y + 1, a);
				Agent.right(x, y);
				state(x, y, 0, a);

			} else {
				resetBoolean(x, y);
				move(x, y, a);
			}
			break;
		case 1: // trai
			if (!checkBoolean(x, LEFT) && !checkTuong(x, LEFT)) {
				diem(x, y - 1, a);
				Agent.left(x, y);
				state(x, y, 1, a);
			} else {
				resetBoolean(x, y);
				move(x, y, a);
			}
			break;
		case 2: // xuong
			if (!checkBoolean(DOWN, y) && !checkTuong(DOWN, y)) {
				diem(x + 1, y, a);
				Agent.down(x, y);
				state(x, y, 2, a);
			} else {
				resetBoolean(x, y);
				move(x, y, a);
			}
			break;
		case 3: // len
			if (!checkBoolean(UP, y) && !checkTuong(UP, y)) {
				diem(x - 1, y, a);
				Agent.up(x, y);
				state(x, y, 3, a);
			} else {
				resetBoolean(x, y);
				move(x, y, a);
			}
			break;

		default:
			break;
		}

	}

	private void diem(int x, int y, int[][] a) { // Hàm tính điểm
		if (!checkVe) {
			if (a[x][y] == 1) {
				diem = diem + 100;
			} else {
				diem = diem - 10;
			}
			Agent.diem = diem;
		}
	}

	public void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				System.out.print(b[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\n------------------------");

	}
}
