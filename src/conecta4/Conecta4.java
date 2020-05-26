package conecta4;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings({ "serial", "unused" })
public class Conecta4 extends JFrame implements Runnable {

	private JPanel contentPane;
	private int field[][];
	private JPanel panels[][];
	private int player;
	private int fRow = 0;
	private int fColumn = 0;

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
		panels = new JPanel[field.length][field[0].length];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel.setName(i + ":" + j);
				panel.setBorder(new LineBorder(Color.DARK_GRAY));
				panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int column = Integer.parseInt(panel.getName().split(":")[1]);
						changeColor(column);
					}
				});
				panels[i][j] = panel;
				contentPane.add(panel);
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

	private void changeColor(int column) {
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
		fallingColor(fRow, fColumn);
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

	private void fallingColor(int row, int column) {
		for (int i = 0; i < row; i++) {

			if (player == 1)
				panels[i][column].setBackground(Color.BLUE);
			else
				panels[i][column].setBackground(Color.RED);

			sleep(200);
			panels[i][column].setBackground(Color.WHITE);

		}
		if (player == 1)
			panels[row][column].setBackground(Color.BLUE);
		else
			panels[row][column].setBackground(Color.RED);
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
					if (i > 3 && j < field[i].length - 3)
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
