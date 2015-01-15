package algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import javax.management.RuntimeErrorException;

public class Graham {
	
	LinkedList<Point> collectionQ;
	Stack<Point> stack;
	
	public Graham(String path){
		collectionQ = new LinkedList<Point>();
		stack = new Stack<Point>();
		//R=Math.sqrt(x^2+y^2)
		//theta=Math.atan2(y,x)
		//TODO: wczytanie z pliku
		readFromFile(path);
	}
	
	private void readFromFile(String csvFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	 
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
		        // use comma as separator
				String[] coord = line.split(cvsSplitBy);
//				System.out.println(coord[0]+" "+coord[1]);
				Point p = new Point(Double.valueOf(coord[0]), Double.valueOf(coord[1]));
				collectionQ.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}

	public Stack<Point> go(){
		if(collectionQ.size()<3) throw new RuntimeException("Too little points");
		Point p0 = smallestPoint();
		Collections.sort(collectionQ);
		throwDuplicates();
		stack.push(p0);
		stack.push(collectionQ.get(0));
		stack.push(collectionQ.get(1));
		stack.push(collectionQ.get(2));
		for(int i=3; i<collectionQ.size(); i++){
			while(stack.size()>=2 && 
					onRight(this.collectionQ.get(i), stack.get(stack.size()-1), stack.get(stack.size()-2))){
				stack.pop();
			}
			stack.push(this.collectionQ.get(i));
		}
		return stack;
	}

	private boolean onRight(Point pi, Point top, Point nextToTop) {
		double det = top.x*nextToTop.y*1 + top.y*1*pi.x+1*nextToTop.x*pi.y
				-pi.x*nextToTop.y*1-pi.y*1*top.x-1*nextToTop.x*top.y;
		if(det>0){
			return false;
		}
//		else if (det<0){
			return true;
//		}
		
	}

	private void throwDuplicates() {
		Point prev = null;
		boolean first =true;
		for(int i=0; i<this.collectionQ.size(); i++){
			Point p = this.collectionQ.get(i);
			if(first){
				first = false;
				prev = p;
			}
			if(prev.r == p.r && prev.theta == p.theta){
				this.collectionQ.remove(p);
			}
		}
	}

	private Point smallestPoint() {
		Point res = null;
		boolean first = true;
		for (Point p : this.collectionQ){
			if(first){
				first = false;
				res = p;
			}
			if(res.y > p.y){
				res = p;
			}
			else if (res.y == p.y){
				if(res.x > p.x){
					res = p;
				}
			}
		}
		return res;
	}
}
