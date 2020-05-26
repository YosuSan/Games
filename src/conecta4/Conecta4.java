package conecta4;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

@SuppressWarnings({ "serial", "unused" })
public class Conecta4 extends JFrame implements Runnable {

	private JPanel contentPane;
	private int field[][];
	private JLabel labelField[][];
	private int player;
	private int fRow = 0;
	private int fColumn = 0;
	HashMap<String, ImageIcon> dots;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Conecta4 frame = new Conecta4();
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
	public Conecta4() {
		initField();
		dots = new HashMap<String, ImageIcon>();
		dots.put("white", new ImageIcon("images\\white.png"));
		dots.put("blue", new ImageIcon("images\\blue.png"));
		dots.put("red", new ImageIcon("images\\red.png"));
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(7, 0, 0, 0));

		startGame();
		

	}

	private void startGame() {
		initField();
		player = 1;
		setTitle("Turn of player => " + player
				+ "                                                                                          By Yosu");
		labelField = new JLabel[field.length][field[0].length];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				JLabel label = new JLabel();
				label.setIcon(dots.get("white"));
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setName(i + ":" + j);
				label.setBorder(new LineBorder(Color.GRAY));
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int column = Integer.parseInt(label.getName().split(":")[1]);
						addChip(column);
					}
				});
				labelField[i][j] = label;
				contentPane.add(label);
			}
		}
		contentPane.revalidate();
	}

	private void initField() {
		field = new int[7][8];
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field[i].length; j++)
				field[i][j] = 0;
	}

	private void addChip(int column) {
		boolean changed = false;
		for (int i = field.length - 1; i >= 0 && !changed; i--) {
			if (field[i][column] == 0) {
				fRow = i;
				fColumn = column;
				Thread t = new Thread(this);
				t.start();
				field[i][column] = player;
				changed = true;
			}
		}
	}

	@Override
	public void run() {
		fallingChip(fRow, fColumn);
		if (checkPlayer()) {
			int option = JOptionPane.showConfirmDialog(null,
					"Congratulations player " + player + " you win\nDo you want to restart?", "WINNER",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				player = 1;
				contentPane.removeAll();
				startGame();
			}
		} else {
			if (moreMoves()) {
				player = (player == 1) ? 2 : 1;
				setTitle("Turn of player => " + player
						+ "                                                                                          By Yosu");
			} else {
				int option = JOptionPane.showConfirmDialog(null, "There aren't more moves \nDo you want to restart?",
						"TIE", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					player = 1;
					contentPane.removeAll();
					startGame();
				}
			}
		}
	}

	private boolean moreMoves() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == 0)
					return true;
			}
		}
		return false;
	}

	private void fallingChip(int row, int column) {
		for (int i = 0; i < row; i++) {

			if (player == 1)
				labelField[i][column].setIcon(dots.get("blue"));
			else
				labelField[i][column].setIcon(dots.get("red"));

			sleep(100);
			labelField[i][column].setIcon(dots.get("white"));

		}
		if (player == 1)
			labelField[row][column].setIcon(dots.get("blue"));
		else
			labelField[row][column].setIcon(dots.get("red"));
	}

	private boolean checkPlayer() {
//		print();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == player) {
					if (j < field[i].length - 3)
						if (field[i][j + 1] == player && field[i][j + 2] == player && field[i][j + 3] == player)
							return true;
					if (i < field.length - 3)
						if (field[i + 1][j] == player && field[i + 2][j] == player && field[i + 3][j] == player)
							return true;
					if (i < field.length - 3 && j < field[i].length - 3)
						if (field[i + 1][j + 1] == player && field[i + 2][j + 2] == player
								&& field[i + 3][j + 3] == player)
							return true;
					if (i > 2 && j < field[i].length - 3)
						if (field[i - 1][j + 1] == player && field[i - 2][j + 2] == player
								&& field[i - 3][j + 3] == player)
							return true;
				}
			}
		}
		return false;
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	private void print() {
		System.out.println("********************************");
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++)
				System.out.print("[" + field[i][j] + "]");
			System.out.println();
		}
		System.out.println("********************************");
	}

}
