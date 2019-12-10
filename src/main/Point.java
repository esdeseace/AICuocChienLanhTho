package main;

public class Point {

	public static final Point LEFT = new Point(0, -1);
	public static final Point RIGHT = new Point(0, 1);
	public static final Point UP = new Point(-1, 0);
	public static final Point DOWN = new Point(1, 0);

	public static final Point[] MOVES = { LEFT, RIGHT, UP, DOWN };

	public int x;
	public int y;

	/**
	 * Khởi tạo Point mới với tọa độ (0, 0)
	 */
	public Point() {
		x = 0;
		y = 0;
	}

	/**
	 * Kiểm tra bằng nhau
	 */
	public boolean equal(Point point) {
		return point.x == x && point.y == y;
	}

	/**
	 * Khoảng cách tới tâm
	 */
	public int getCenterDitance() {
		return Math.abs(x - 7) + Math.abs(y - 7);
	}

	/**
	 * Khởi tạo Point mới
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Khởi tạo Point mới
	 */
	public Point(Point p) {
		x = p.x;
		y = p.y;
	}

	/**
	 * Cộng 2 point
	 */
	public Point add(Point p) {
		return new Point(x + p.x, y + p.y);
	}

	/**
	 * Kiểm tra point có nằm trong bàn cờ không
	 */
	public boolean isInsideBoard() {
		return x < 15 && x >= 0 && y < 15 && y >= 0;
	}

	@Override
	public String toString() {
		return x + "   " + y;
	}

}
