package tsp.euc2d.model;

public class Euc2d {
	private final int cordX;
	private final int cordY;

	public Euc2d(int cordX, int cordY) {
		this.cordX = cordX;
		this.cordY = cordY;
	}

	public int getCordX() {
		return cordX;
	}

	public int getCordY() {
		return cordY;
	}

	@Override
	public String toString() {
		return "Euc2d{" +
				"cordX=" + cordX +
				", cordY=" + cordY +
				'}';
	}
}
