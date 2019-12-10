package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private Timer timer;
	private boolean isConnect = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private int turn;
	private Board board;

	private Point red;
	private Point green;

	public Main() {
		createView();
		createGame();
	}

	private void redControl() {
	}

	private void greenControl() {

		if (isConnect)
			if (!board.isConnect(red, green))
				isConnect = false;

		AI ai = new AI();
		Point point = ai.getNextPoint(green, red, board, isConnect);
		changeTurn(point);
	}

	private void randomAI(Point source) {
		while (true) {
			int move = new Random().nextInt(4);
			Point point = source.add(Point.MOVES[move]);
			if (board.isAvaiableMove(point)) {
				changeTurn(point);
				break;
			}
		}
	}

	private void createGame() {

		board = new Board();
		red = new Point();
		green = new Point(Board.ROW - 1, Board.COLUMN - 1);

		addEventForPlayer();
		refresh();

		turn = new Random().nextInt(2) + 1;
		turn = 1;
		System.out.println("Turn : " + turn);

		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (board.isEndGame(green)) {
					JOptionPane.showMessageDialog(null, "Red Win");
					System.out.println("Red win");
					timer.stop();
					return;
				}

				if (board.isEndGame(red)) {
					JOptionPane.showMessageDialog(null, "Green Win");
					System.out.println("Green win");
					timer.stop();
					return;
				}

				if (turn == 2)
					greenControl();
				else
					redControl();
			}
		});
		timer.start();
	}

	private void changeTurn(Point point) {
		if (turn == 1) {
			turn = 2;
			board.setGrid(point, 1);
			red = point;
		} else {
			turn = 1;
			green = point;
			board.setGrid(point, 2);
		}
		refresh();
	}

	private void addEventForPlayer() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {

				int move = -1;

				if (event.getKeyCode() == KeyEvent.VK_LEFT)
					move = 0;
				else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
					move = 1;
				else if (event.getKeyCode() == KeyEvent.VK_UP)
					move = 2;
				else if (event.getKeyCode() == KeyEvent.VK_DOWN)
					move = 3;

				if (move == -1)
					return;

				Point point;
				if (turn == 2)
					point = green.add(Point.MOVES[move]);
				else
					point = red.add(Point.MOVES[move]);

				if (!board.isAvaiableMove(point))
					return;

				changeTurn(point);
			}
		});
	}

	private void createView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 20, 1000, 1000);
		// setBounds(450, 20, 800, 800);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(15, 15, 0, 0));
	}

	private void refresh() {

		contentPane.removeAll();

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {

				String str = String.valueOf(board.getGrid(i, i));
				JLabel label = new JLabel(str);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBorder(new LineBorder(Color.black, 1));
				label.setFont(new Font("Tahoma", Font.PLAIN, 20));
				label.setOpaque(true);
				contentPane.add(label);

				if (board.getGrid(i, j) == 1)
					label.setBackground(Color.red);
				else if (board.getGrid(i, j) == 2)
					label.setBackground(Color.green);
				else if (board.getGrid(i, j) == 3)
					label.setBackground(Color.gray);

				if (i == red.x && j == red.y)
					label.setBorder(new LineBorder(Color.black, 10));
				if (i == green.x && j == green.y)
					label.setBorder(new LineBorder(Color.black, 10));
			}
		}

		contentPane.revalidate();
		contentPane.repaint();
	}

}
