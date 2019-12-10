package main;

import java.util.ArrayList;

public class AI {

	private final int MINIMUM = -9999999;
	private final int MAXIMUM = 9999999;
	private final int SCORE = 1000000;

	private final int DEPTH_CONNECT = 15;
	private final int DEPTH_DISCONNECT = 15;

	private Board board;

	public Point getNextPoint(Point self, Point enemy, Board board, boolean isConnect) {
		this.board = new Board(board);
		Point result = new Point();
		int max = MINIMUM;
		for (int i = 0; i < 4; i++) {
			Point point = self.add(Point.MOVES[i]);
			if (board.isAvaiableMove(point)) {
				this.board.setGrid(point, 1);

				int value;
				if (isConnect)
					value = minimax(1, false, point, enemy, MINIMUM, MAXIMUM);
				else
					value = maximum(1, point);

				this.board.setGrid(point, 0);

				if (max < value) {
					max = value;
					result = point;
				}
			}
		}
		if (isConnect)
			System.out.println(result + ": " + max);
		return result;
	}

	private int maximum(int depth, Point self) {

		if (board.isEndGame(self))
			return -SCORE + depth;

		if (depth == DEPTH_DISCONNECT)
			return board.getMaximumMoveAvaiable(self);

		int max = MINIMUM;
		for (int i = 0; i < 4; i++) {
			Point point = self.add(Point.MOVES[i]);
			if (board.isAvaiableMove(point)) {
				board.setGrid(point, 1);
				int value = maximum(depth + 1, point);
				board.setGrid(point, 0);
				max = Math.max(max, value);
			}
		}
		return max;
	}

	private int minimax(int depth, boolean isSelfTurn, Point self, Point enemy, int alpha, int beta) {

		if (board.isEndGame(self))
			return -SCORE + depth;

		if (board.isEndGame(enemy))
			return SCORE - depth;

		if (!board.isConnect(self, enemy))
			return board.getDiffrenceMaxMove(self, enemy) * 10;

		if (depth == DEPTH_CONNECT)
			return board.getDifferenceMove(isSelfTurn, self, enemy);

		if (isSelfTurn) {
			int max = MINIMUM;
			for (int i = 0; i < 4; i++) {
				Point point = self.add(Point.MOVES[i]);
				if (board.isAvaiableMove(point)) {
					board.setGrid(point, 1);
					int value = minimax(depth + 1, false, point, enemy, alpha, beta);
					board.setGrid(point, 0);
					max = Math.max(max, value);
					if (max >= beta)
						return max;
					alpha = Math.max(alpha, max);
				}
			}
			return max;
		} else {
			int min = MAXIMUM;
			for (int i = 0; i < 4; i++) {
				Point point = enemy.add(Point.MOVES[i]);
				if (board.isAvaiableMove(point)) {
					board.setGrid(point, 2);
					int value = minimax(depth + 1, true, self, point, alpha, beta);
					board.setGrid(point, 0);
					min = Math.min(min, value);
					if (min <= alpha)
						return min;
					beta = Math.min(beta, min);
				}
			}
			return min;
		}
	}
}