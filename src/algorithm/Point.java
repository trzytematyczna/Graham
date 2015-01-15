package algorithm;

public class Point implements Comparable<Point>{

	double x;
	double y;
	double r;
	double theta;
	
	public Point(double x, double y) {
		this.x=x;
		this.y=y;
		this.r=Math.sqrt((x*x+y*y));
		this.theta=Math.atan2(y,x);
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	public double getTheta() {
		return theta;
	}
	public void setTheta(double theta) {
		this.theta = theta;
	}
	@Override
	public int compareTo(Point p) {
		if(this.r > p.r){
			return 1;
		}
		if(this.r < p.r){
			return -1;
		}
		return 0;
	}
	
	
	
	
}
