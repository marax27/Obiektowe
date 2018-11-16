package Shapes;

public class Pos2D {
	public Pos2D(int _x, int _y) {
		x = _x;
		y = _y;
	}

	public static Pos2D sum(Pos2D a, Pos2D b){
		return new Pos2D(a.x+b.x, a.y+b.y);
	}

	public static Pos2D diff(Pos2D a, Pos2D b){
		return new Pos2D(b.x-a.x, b.y-a.y);
	}

	public String toString(){
		return String.format("(%d, %d)", x, y);
	}
	
	public int x, y;
}
