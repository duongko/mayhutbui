package Vacuum2;

import java.util.Random;

public class Environment {
	int[][] a;
	boolean[][] b;
	public int width;
	public int heigth;
	final int V = -1;// agent
	final int T = 10; // Tường
	final int R = 1; // Bụi
	public int soTuong = 9;
	public int soRac = 10;

	public Environment(int width, int heigth) {
		this.width = width;
		this.heigth = heigth;
		a = new int[width][heigth];
		b = new boolean[width][heigth];
		a[0][0] = V;
		b[0][0] = true;
	}

	public void addEnvironment() {
		addTuong();
		addBui();
	}

	public int getHeigth() {
		return heigth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public void setSoTuong(int soTuong) {
		this.soTuong = soTuong;
	}

	public void setSoRac(int soRac) {
		this.soRac = soRac;
	}

	private void addTuong() { // Tạo tường
		Random rd = new Random();
		int dem = 0;
		while (dem < soTuong) {
			int i = rd.nextInt(width);
			int j = rd.nextInt(heigth);
			if (!b[i][j]) {
				a[i][j] = T;
				b[i][j] = true;
				dem++;
			}
		}
	}

	public void print() {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\n------------------------");

	}

	private void addBui() { // tạo bụi
		Random rd = new Random();
		int dem = 0;
		while (dem < soRac) {
			int i = rd.nextInt(width);
			int j = rd.nextInt(heigth);
			if (!b[i][j]) {
				a[i][j] = R;
				b[i][j] = true;
				dem++;
			}
		}
	}
}
