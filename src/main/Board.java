package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Board {

	public static final int ROW = 15;
	public static final int COLUMN = 15;

	private int[][] grid;

	/**
	 * Lấy giá trị tại point
	 */
	public int getGrid(Point point) {
		return grid[point.x][point.y];
	}

	/**
	 * Set gia trị value tạo (x, y)
	 */
	public void setGrid(int x, int y, int value) {
		grid[x][y] = value;
	}

	/**
	 * Lấy giá trị tạo điểm (x, y)
	 */
	public int getGrid(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Set giá trị value tạo point
	 */
	public void setGrid(Point point, int value) {
		this.grid[point.x][point.y] = value;
	}

	/**
	 * Kiểm tra xem có thể đi từ source đến mark
	 */
	public boolean isConnect(Point source, Point mark) {

		Board board = new Board(this);
		Queue<Point> queue = new LinkedList<>();
		queue.add(new Point(source));

		while (!queue.isEmpty()) {
			Point head = queue.remove();
			for (int i = 0; i < 4; i++) {
				Point point = head.add(Point.MOVES[i]);
				if (point.equal(mark))
					return true;
				if (board.isAvaiableMove(point)) {
					board.setGrid(point, 1);
					queue.add(point);
				}
			}
		}
		return false;
	}

	/**
	 * Lấy hiệu số đường đi tối đa của self và enemy
	 */
	public int getDiffrenceMaxMove(Point self, Point enemy) {
		return getMaximumMoveAvaiable(self) - getMaximumMoveAvaiable(enemy);
	}

	/**
	 * Kiểm tra đã kết thúc game tạo point
	 */
	public boolean isEndGame(Point point) {
		if (countMovesAvaiable(point) == 0)
			return true;
		return false;
	}

	/**
	 * Lấy hiệu số khoảng cách tới tâm của self và enemy
	 */
	public int getDiffrenceCenter(Point self, Point enemy) {
		return enemy.getCenterDitance() - self.getCenterDitance();
	}

	/*
	private int move(boolean isSelfTurn, Queue<Point> selfQueue, Queue<Point> enemyQueue) {
		if (isSelfTurn) {
			ArrayList<Point> points = new ArrayList<>();
			while (!selfQueue.isEmpty()) {
				Point head = selfQueue.remove();
				for (int i = 0; i < 4; i++) {
					Point point = head.add(Point.MOVES[i]);
					if (board.isAvaiableMove(point)) {
						points.add(point);
						selfMove += board.countMovesAvaiable(point);
						board.setGrid(point, 1);
					}
				}
			}
			selfQueue.addAll(points);
			isSelfTurn = false;
		} else {
			ArrayList<Point> points = new ArrayList<>();
			while (!enemyQueue.isEmpty()) {
				Point head = enemyQueue.remove();
				for (int i = 0; i < 4; i++) {
					Point point = head.add(Point.MOVES[i]);
					if (board.isAvaiableMove(point)) {
						points.add(point);
					}
				}
			}
			enemyQueue.addAll(points);
			isSelfTurn = true;
		}
	}
	*/
	
	public int get(boolean isSelfTurn, Point self, Point enemy) {

		Board board = new Board(this);

		int selfMove = 0;
		int enemyMove = 0;

		Queue<Point> selfQueue = new LinkedList<>();
		Queue<Point> enemyQueue = new LinkedList<>();

		selfQueue.add(self);
		enemyQueue.add(enemy);

		while (!(selfQueue.isEmpty() || enemyQueue.isEmpty())) {
			if (isSelfTurn) {
				ArrayList<Point> points = new ArrayList<>();
				while (!selfQueue.isEmpty()) {
					Point head = selfQueue.remove();
					for (int i = 0; i < 4; i++) {
						Point point = head.add(Point.MOVES[i]);
						if (board.isAvaiableMove(point)) {
							points.add(point);
							selfMove += board.countMovesAvaiable(point);
							board.setGrid(point, 1);
						}
					}
				}
				selfQueue.addAll(points);
				isSelfTurn = false;
			} else {
				ArrayList<Point> points = new ArrayList<>();
				while (!enemyQueue.isEmpty()) {
					Point head = enemyQueue.remove();
					for (int i = 0; i < 4; i++) {
						Point point = head.add(Point.MOVES[i]);
						if (board.isAvaiableMove(point)) {
							points.add(point);
							enemyMove += board.countMovesAvaiable(point);
							
							board.setGrid(point, 2);
						}
					}
				}
				enemyQueue.addAll(points);
				isSelfTurn = true;
			}
		}
		return selfMove - enemyMove;
	}

	/**
	 * Hiệu số các bước có thể di chuyển của self và enemy theo lượt
	 */
	public int getDifferenceMove(boolean isSelfTurn, Point self, Point enemy) {

		Board board = new Board(this);

		int selfMove = 0;
		int enemyMove = 0;

		Queue<Point> selfQueue = new LinkedList<>();
		Queue<Point> enemyQueue = new LinkedList<>();

		selfQueue.add(self);
		enemyQueue.add(enemy);

		while (!(selfQueue.isEmpty() && enemyQueue.isEmpty())) {
			if (isSelfTurn) {
				ArrayList<Point> points = new ArrayList<>();
				while (!selfQueue.isEmpty()) {
					Point head = selfQueue.remove();
					for (int i = 0; i < 4; i++) {
						Point point = head.add(Point.MOVES[i]);
						if (board.isAvaiableMove(point)) {
							points.add(point);
							selfMove++;
							board.setGrid(point, 1);
						}
					}
				}
				selfQueue.addAll(points);
				isSelfTurn = false;
			} else {
				ArrayList<Point> points = new ArrayList<>();
				while (!enemyQueue.isEmpty()) {
					Point head = enemyQueue.remove();
					for (int i = 0; i < 4; i++) {
						Point point = head.add(Point.MOVES[i]);
						if (board.isAvaiableMove(point)) {
							points.add(point);
							enemyMove++;
							board.setGrid(point, 2);
						}
					}
				}
				enemyQueue.addAll(points);
				isSelfTurn = true;
			}
		}
		return selfMove - enemyMove;
	}

	/**
	 * Đếm số lượng ô trống còn lại
	 */
	public int getSpaceMove() {
		int count = 0;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (grid[i][j] == 0) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Lấy số lượng bước tối đa có thể di chuyển tại source
	 */
	public int getMaximumMoveAvaiable(Point source) {
		Board board = new Board(this);
		Queue<Point> queue = new LinkedList<>();
		queue.add(source);
		int count = 0;
		while (!queue.isEmpty()) {
			Point point = queue.remove();
			for (int i = 0; i < 4; i++) {
				Point nextPoint = point.add(Point.MOVES[i]);
				if (board.isAvaiableMove(nextPoint)) {
					int value = board.countMovesAvaiable(nextPoint);
					count += value;
					board.setGrid(nextPoint, 1);
					queue.add(nextPoint);
				}
			}
		}
		return count;
	}

	/**
	 * Đếm số lượng ô có thể di chuyển tại point
	 */
	public int countMovesAvaiable(Point point) {
		int count = 0;
		for (int i = 0; i < 4; i++)
			if (isAvaiableMove(point.add(Point.MOVES[i])))
				count++;
		return count;
	}

	/**
	 * Kiểm tra có thể di chuyển đến point
	 */
	public boolean isAvaiableMove(Point point) {
		return point.isInsideBoard() && grid[point.x][point.y] == 0;
	}

	public boolean isAvaiableMove(int x, int y) {
		return new Point(x, y).isInsideBoard() && grid[x][y] == 0;
	}

	/**
	 * Khởi tạo bàn chơi mới
	 */
	public Board() {
		this.grid = new int[15][15];

		for (int i = 0; i < 5; i++) {
			int rdx = new Random().nextInt(ROW);
			int rdy = new Random().nextInt(COLUMN);
			this.grid[rdx][rdy] = 3;
			this.grid[ROW - 1 - rdx][COLUMN - 1 - rdy] = 3;
		}

		grid[0][0] = 1;
		grid[ROW - 1][COLUMN - 1] = 2;
	}

	/*
	 * Khởi tạo bàn chơi từ bàn chơi có sẵn
	 */
	public Board(Board board) {
		this.grid = new int[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				this.grid[i][j] = board.getGrid(i, j);
			}
		}
	}
}
