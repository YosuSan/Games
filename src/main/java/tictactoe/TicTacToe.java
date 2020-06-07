package tictactoe;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.commons.io.FileUtils;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

@SuppressWarnings({ "unused" })
public class TicTacToe extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labels[][];
	private int field[][];
	private HashMap<String, ImageIcon> map;
	private int player = 1;
	private boolean IAActive;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe frame = new TicTacToe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TicTacToe() {
		map = new HashMap<String, ImageIcon>();
		checkImages();
		map.put("happy", new ImageIcon("images/happy.png"));
		map.put("blood", new ImageIcon("images/blood.png"));
		map.put("cross", new ImageIcon("images/cross.png"));
		map.put("circle", new ImageIcon("images/circle.png"));
		setTitle("TIC-TAC-TOE                                                     By Yosu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0, 0, 0));
		initGame();
		int option = JOptionPane.showConfirmDialog(null, "Do you want to play with computer?", "Solo or multiplayer",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		IAActive = (option == JOptionPane.YES_OPTION) ? true : false;

	}

	private void initGame() {
		initField();
		labels = new JLabel[3][3];
		player = 1;
		for (int i = 0; i < labels.length; i++) {
			for (int j = 0; j < labels[i].length; j++) {

				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setBorder(new LineBorder(Color.DARK_GRAY));
				label.setIcon(map.get("happy"));
				label.setName(i + ":" + j);
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (label.getIcon().equals(map.get("happy")))
							label.setIcon(map.get("blood"));
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						int i = Integer.parseInt(label.getName().split(":")[0]);
						int j = Integer.parseInt(label.getName().split(":")[1]);
						boolean moved = false;
						if (label.getIcon().equals(map.get("blood")) && player == 1) {
							label.setIcon(map.get("cross"));
							field[i][j] = player;
							moved = true;
						} else if (label.getIcon().equals(map.get("blood")) && player == 2) {
							label.setIcon(map.get("circle"));
							field[i][j] = player;
							moved = true;
						}
						if (moved) {
							if (checkWinner()) {
								int option = JOptionPane.showConfirmDialog(null,
										"The winner is the player " + player + "\nDo you want to reset?", "WINNER",
										JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (option == JOptionPane.YES_OPTION)
									resetGame();
							} else if (!checkMoves()) {
								int option = JOptionPane.showConfirmDialog(null,
										"There aren't more moves\nDo you want to reset?", "TIE",
										JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (option == JOptionPane.YES_OPTION)
									resetGame();
							} else if (!IAActive)
								player = (player == 1) ? 2 : 1;
							else {
								IAMove();
								player = 2;
								if (checkWinner()) {
									int option = JOptionPane.showConfirmDialog(null,
											"The winner is the player " + player + "\nDo you want to reset?", "WINNER",
											JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (option == JOptionPane.YES_OPTION)
										resetGame();
								} else if (!checkMoves()) {
									int option = JOptionPane.showConfirmDialog(null,
											"There aren't more moves\nDo you want to reset?", "TIE",
											JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (option == JOptionPane.YES_OPTION)
										resetGame();

								}
								player = 1;
							}
						}
					}
				});
				labels[i][j] = label;
				contentPane.add(labels[i][j]);
			}
		}

	}

	private void resetGame() {
		player = 1;
		contentPane.removeAll();
		contentPane.revalidate();
		initGame();
	}

	private boolean checkWinner() {
		if (field[0][0] == player && field[0][1] == player && field[0][2] == player)
			return true;
		if (field[1][0] == player && field[1][1] == player && field[1][2] == player)
			return true;
		if (field[2][0] == player && field[2][1] == player && field[2][2] == player)
			return true;
		if (field[0][0] == player && field[1][0] == player && field[2][0] == player)
			return true;
		if (field[0][1] == player && field[1][1] == player && field[2][1] == player)
			return true;
		if (field[0][2] == player && field[1][2] == player && field[2][2] == player)
			return true;
		if (field[0][0] == player && field[1][1] == player && field[2][2] == player)
			return true;
		if (field[2][0] == player && field[1][1] == player && field[0][2] == player)
			return true;

		return false;
	}

	private void checkImages() {
		File fHappy = new File("images/happy.png");
		File fBlood = new File("images/blood.png");
		File fCross = new File("images/cross.png");
		File fCircle = new File("images/circle.png");

		String happy = "https://img.icons8.com/officel/80/000000/lol.png";
		String blood = "https://img.icons8.com/color/80/000000/blood-drawing.png";
		String cross = "https://img.icons8.com/flat_round/80/000000/self-destruct-button--v1.png";
		String circle = "https://img.icons8.com/dusk/80/000000/accuracy.png";
		if (!fHappy.exists() || !fBlood.exists() || !fCross.exists() || !fCircle.exists()) {
			try {
				FileUtils.copyURLToFile(new URL(happy), fHappy);
				FileUtils.copyURLToFile(new URL(blood), fBlood);
				FileUtils.copyURLToFile(new URL(cross), fCross);
				FileUtils.copyURLToFile(new URL(circle), fCircle);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkMoves() {
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field[i].length; j++)
				if (field[i][j] == 0)
					return true;
		return false;
	}

	private void initField() {
		field = new int[3][3];
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field[i].length; j++)
				field[i][j] = 0;
	}

	private void IAMove() {
		if (!IAMoveH("win"))
			if (!IAMoveV("win"))
				if (!IAMoveD("win"))
					if (!IAMoveH(""))
						if (!IAMoveV(""))
							if (!IAMoveD(""))
								IAMoveAny();
	}

	private boolean IAMoveAny() {
		while (true) {
			int rand = (int) Math.floor(Math.random() * 5);
			switch (rand) {
			case 0:
				if (field[1][1] == 0) {
					field[1][1] = 2;
					labels[1][1].setIcon(map.get("circle"));
					return true;
				}
			case 1:
				if (field[0][0] == 0) {
					field[0][0] = 2;
					labels[0][0].setIcon(map.get("circle"));
					return true;
				}
			case 2:
				if (field[2][2] == 0) {
					field[2][2] = 2;
					labels[2][2].setIcon(map.get("circle"));
					return true;
				}
			case 3:
				if (field[0][2] == 0) {
					field[0][2] = 2;
					labels[0][2].setIcon(map.get("circle"));
					return true;
				}
			case 4:
				if (field[2][0] == 0) {
					field[2][0] = 2;
					labels[2][0].setIcon(map.get("circle"));
					return true;
				}
			}
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (field[i][j] == 0) {
						field[i][j] = 2;
						labels[i][j].setIcon(map.get("circle"));
						return true;
					}
				}
			}
		}
	}

	private boolean IAMoveD(String move) {
		int check = 1;
		if (move.equals("win"))
			check = 2;
		int status = 0;
		if (field[0][0] == check)
			status++;
		if (field[1][1] == check)
			status++;
		if (field[2][2] == check)
			status++;
		if (status == 2) {
			if (field[0][0] == 0) {
				field[0][0] = 2;
				labels[0][0].setIcon(map.get("circle"));
				return true;
			}
			if (field[1][1] == 0) {
				field[1][1] = 2;
				labels[1][1].setIcon(map.get("circle"));
				return true;
			}
			if (field[2][2] == 0) {
				field[2][2] = 2;
				labels[2][2].setIcon(map.get("circle"));
				return true;
			}
		}
		status = 0;
		if (field[0][2] == check)
			status++;
		if (field[1][1] == check)
			status++;
		if (field[2][0] == check)
			status++;
		if (status == 2) {
			if (field[0][2] == 0) {
				field[0][2] = 2;
				labels[0][2].setIcon(map.get("circle"));
				return true;
			}
			if (field[1][1] == 0) {
				field[1][1] = 2;
				labels[1][1].setIcon(map.get("circle"));
				return true;
			}
			if (field[2][0] == 0) {
				field[2][0] = 2;
				labels[2][0].setIcon(map.get("circle"));
				return true;
			}
		}
		return false;
	}

	private boolean IAMoveH(String move) {
		int check = 1;
		if (move.equals("win"))
			check = 2;

		for (int i = 0; i < field.length; i++) {
			int status = 0;
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == check)
					status++;
				if (status == 2) {
					for (int j2 = 0; j2 < field.length; j2++) {
						if (field[i][j2] == 0) {
							field[i][j2] = 2;
							labels[i][j2].setIcon(map.get("circle"));
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean IAMoveV(String move) {
		int check = 1;
		if (move.equals("win"))
			check = 2;

		for (int i = 0; i < field.length; i++) {
			int status = 0;
			for (int j = 0; j < field[i].length; j++) {
				if (field[j][i] == check)
					status++;

				if (status == 2) {
					for (int i2 = 0; i2 < field.length; i2++) {
						if (field[i2][i] == 0) {
							field[i2][i] = 2;
							labels[i2][i].setIcon(map.get("circle"));
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private void printField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++)
				System.out.print("[" + field[i][j] + "]");
			System.out.println();
		}
		System.out.println("**************");
	}
}
