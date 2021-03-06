package algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

import javax.management.RuntimeErrorException;

public class Graham {
	
	LinkedList<Point> collectionQ;
	Stack<Point> stack;
	
	public Graham(String path){
		collectionQ = new LinkedList<Point>();
		stack = new Stack<Point>();
		readFromFile(path);
	}
	
	private void readFromFile(String csvFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	 
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] coord = line.split(cvsSplitBy);
				Point p = new Point(Double.valueOf(coord[0]), Double.valueOf(coord[1]));
				this.collectionQ.add(p);
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
		
		this.collectionQ.remove(p0);
		Collections.sort(collectionQ, new Comparator<Point>() {
			@Override
			public int compare(Point top, Point next) {
				double det = p0.det(top,next);
				if(det>0){
					return -1;
				}
				if(det<0){
					return 1;
				}
				return 0;
			}
			
		});
		this.collectionQ.addFirst(p0);
		throwDuplicates();
		stack.push(collectionQ.get(0));
		stack.push(collectionQ.get(1));
		stack.push(collectionQ.get(2));
		for(int i=3; i<collectionQ.size(); i++){
			Point p = this.collectionQ.get(i);
			Point top = this.stack.get(this.stack.size()-1);
			Point next =  this.stack.get(this.stack.size()-2);
			while(onRight(p, top, next)){
				stack.pop();
				top = this.stack.get(this.stack.size()-1);
				next =  this.stack.get(this.stack.size()-2);				
			}
			stack.push(this.collectionQ.get(i));
		}
		return stack;
	}

	private boolean onRight(Point pi, Point top, Point nextToTop) {
		double det = pi.det(top, nextToTop);
		return det>=0;
	}

	private void throwDuplicates() {
		LinkedList<Point> toRemove = new LinkedList<Point>();
		Point prev = this.collectionQ.get(1);
		
		for(int i=2; i<this.collectionQ.size(); i++){
			Point p = this.collectionQ.get(i);
			if(isEqual(prev.theta,p.theta)){
				if(prev.r<p.r){
					toRemove.add(prev);
				}
				else{
					toRemove.add(p);
				}
			}
			prev = p;
		}
		this.collectionQ.removeAll(toRemove);
	}

	private boolean isEqual(double theta, double theta2) {
		if(Math.abs(theta-theta2) <= 0.000001) return true;
		return false;
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
			else if (isEqual(res.y, p.y)){
				if(res.x > p.x){
					res = p;
				}
			}
		}
		return res;
	}
}
