package Vacuum2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Swing extends JFrame {
	int[][] a;
	public int width;
	public int heigth;
	JButton[][] buttons;
	JLabel d;
	Environment environment = new Environment(5, 5);
	JTextField t, t1;
	JPanel p1, p2;
	Thread s1, s2;
	Agent agent;
	Agent agent2;
	String rac = "b.jpg";
	String may = "p.jpg";
	String tuong = "gach.jpg";

	public Swing() {
		setTitle("Vaccum");
		setLocation(400, 200);
		setLayout(new BorderLayout());
		p1 = center(environment.getWidth(), environment.getHeigth());
		add(p1, BorderLayout.CENTER);
		p2 = south();
		add(p2, BorderLayout.SOUTH);
		pack();
		agent = new Agent(environment);
		agent2 = new Agent(environment);
	}

	private JPanel center(int width, int heigth) {
		JPanel p0 = new JPanel();
		p0.setLayout(new BorderLayout());
		a = environment.a;
		buttons = new JButton[width][heigth];
		JPanel p = new JPanel(new GridLayout(width, heigth));
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				JButton b = new JButton();
				buttons[i][j] = b;
				buttons[i][j].setPreferredSize(new Dimension(60, 60));
				b.setBackground(Color.WHITE);
				b.setBorder(BorderFactory.createEtchedBorder());
				p.add(b);
			}
		}
		p0.add(p, BorderLayout.CENTER);
		environment.addEnvironment();
		agent = new Agent(environment);
		addMatrix();
		return p;

	}

	private JPanel south() {
		JPanel p1 = new JPanel(new FlowLayout());
		p1.setBackground(Color.YELLOW);
		JButton b1 = new JButton("Run");
		JLabel l1 = new JLabel("Dài:");
		t = new JTextField(4);
		t1 = new JTextField(4);
		t.setText(environment.width + "");
		t1.setText(environment.heigth + "");
		JLabel l2 = new JLabel("Rộng:");
		JButton b2 = new JButton("Ok");
		p1.add(l1);
		p1.add(t);
		p1.add(l2);
		p1.add(t1);
		p1.add(b2);
		p1.add(b1);
		JLabel l = new JLabel("Điểm:");
		p1.add(l);
		d = new JLabel("0");
		p1.add(d);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				width = Integer.parseInt(t.getText());
				heigth = Integer.parseInt(t1.getText());
				environment = new Environment(width, heigth);
				environment.setSoRac(width * heigth - (width + heigth) * 2);
				int soTuong = (width > heigth) ? width : heigth;
				environment.setSoTuong(soTuong);
				setP1(center(width, heigth));
				d.setText("0");
				agent = new Agent(environment);
			}
		});
		JButton b3 = new JButton("Stop");
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		p1.add(b3);
		agent = new Agent(environment);
		return p1;

	}

	private void stop() {
		if (s1 != null && s2 != null) {
			s1 = null; // dừng
			s2 = null;
		}

	}

	Runnable runSwing = new Runnable() {

		@Override
		public void run() {
			while (true) {
				move2();
				diem(Agent.diem);
				if (agent.state.stop && buttons[0][0].getIcon() != null) {
					JOptionPane.showMessageDialog(new JDialog(), "Thanh cong!");
					break;
				}
			}
		}
	};
	Runnable runAgent = new Runnable() {

		@Override
		public void run() {
			agent.run();
		}
	};

	private void start() {
		stop();
		s1 = new Thread(runAgent);
		s2 = new Thread(runSwing);
		d.setText("0");
		s2.start();
		s1.start();
	}

	private String huong(int h) {
		switch (h) {
			case 0:
				may = "p.jpg";
				break;
			case 1:
				may = "t.jpg";
				break;
			case 2:
				may = "x.jpg";
				break;
			case 3:
				may = "l.jpg";
				break;

			default:
				break;
		}
		return may;
	}

	public void setP1(JPanel p1) {
		remove(this.p1);
		this.p1 = p1;
		add(p1, BorderLayout.CENTER);
		pack();
	}

	private void addMatrix() {
		buttons[0][0].setIcon(new ImageIcon(may));
		addTuong();
		addRac();

	}

	private void move2() {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (Agent.a[i][j] == -1) {
					buttons[i][j].setIcon(new ImageIcon(huong(agent.state.h2)));
				} else if (Agent.a[i][j] == 0) {
					buttons[i][j].setIcon(null);
				}
			}
		}

	}

	private void addTuong() {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 10) {
					buttons[i][j].setIcon(new ImageIcon(tuong));
				}
			}

		}
	}

	private void addRac() {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 1) {
					buttons[i][j].setIcon(new ImageIcon(rac));
				}
			}
		}
	}

	private void diem(int de) {
		d.setText(de + "");
	}

}
