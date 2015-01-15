package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import algorithm.Graham;
import algorithm.Point;

public class GrahamTest {

	String csvFile = "C://Users//MZ//Documents//mra//agh//algo//graham//punktyPrzykladowe.csv";
	Graham graham;
	
	@Before
	public void init(){
		this.graham = new Graham(csvFile);
	}
	
	@Test
	public void test() {
		
		Stack<Point> st = this.graham.go();
		System.out.println("====Graham====");
		for(Point p : st){
			System.out.println(p.getX()+","+p.getY());
		}
//		st.push(1);
//		st.push(2);
//		st.push(3);
//		System.out.println(st.firstElement());
//		System.out.println(st.size());
//		System.out.println(st.lastElement());
//		System.out.println(st.get(st.size()-2));
	}

}
