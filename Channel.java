/*Name: Jeffrey Rubi
 * COMP496 Project 1: Channel Allocation
 * Main class
 */

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Channel {
	
	//'Point' class for the intervals
	private PointComparator comparator = new PointComparator();
	private PriorityQueue<Point> intervals = new PriorityQueue<Point>(100, comparator);
	private ArrayList<Point> intervals1 = new ArrayList<Point>();
	
	private ArrayList<Point> channel1 = new ArrayList<Point>();
	private ArrayList<Point> channel2 = new ArrayList<Point>();

	public Channel() throws FileNotFoundException {
		readInter();
		fixInter();
	}
	
	private void readInter() throws FileNotFoundException {
		
		//Counter for the 'interval ArrayList'
		
		File file = new File("input.txt");

		Scanner input = new Scanner(file);

		while (input.hasNext()) {

			int x = input.nextInt();
			int y = input.nextInt();
			
			intervals.add(new Point(x, y));
		}
		
		//Close 'input.txt'
		input.close();
		
	}//end readInter() method
	
	public boolean checkChannel() {
		if (channel1.size() <= channel2.size()) 
			return true;
		else
			return false;
	}
	
	public void fixInter () {
		while(intervals.size() > 0) {
			intervals1.add(intervals.remove());
		}
	}

	// Main Method 
	public static void main (String[] args) throws FileNotFoundException{
		
		//Create an instance of this class. I didn't want to mess with Static/nonStatic method.
		Channel jep = new Channel();
		
		
		/**
		Iterator<Point> iterator = jep.intervals1.iterator();
		while(iterator.hasNext()) {
			Point element = iterator.next();
			System.out.println("Your x value is: " + element.getX() + " and your y value is: " + element.getY());
		} **/
		
		/*'state' keeps track of which channel the current index is using.
		 * When 'state' is 0 then it's in channel 1, when 'state' is 1 it's in channel 2.
		 * 
		 */
		int state = 0;
		
		for (int i =0; i < jep.intervals1.size(); i++) {
			
			/**used for debugging**/
			//System.out.println("Your x value is: " + jep.intervals1.get(i).getX() + " and your y value is: " + 
			//	jep.intervals1.get(i).getY());
			
			if (i == 0)
				jep.channel1.add(jep.intervals1.get(i));
			
			else if (i == (jep.intervals1.size() - 1)) {
				if (jep.checkChannel() && 
				   (jep.channel1.get(jep.channel1.size()-1).getY() < jep.intervals1.get(i).getX())) {
					
					jep.channel1.add(jep.intervals1.get(i));
				}
				else {
					jep.channel2.add(jep.intervals1.get(i));
			
				}
			}
			
			else if (jep.intervals1.get(i).getX() > jep.intervals1.get(i-1).getY()) {
				if (jep.checkChannel() &&
				   (jep.channel1.get(jep.channel1.size()-1).getY() < jep.intervals1.get(i).getX()) &&
				   state == 0) 
					
					jep.channel1.add(jep.intervals1.get(i));
				else {
					jep.channel2.add(jep.intervals1.get(i));
					state = 1;
			
				}
			}
			else {
				if (jep.checkChannel() &&
				   (jep.channel1.get(jep.channel1.size()-1).getY() < jep.intervals1.get(i).getX()) &&
				   state == 0) 
					
					jep.channel1.add(jep.intervals1.get(i));
				else {
					jep.channel2.add(jep.intervals1.get(i));
					state = 1;
			
				}
			}
				
		}//end 'for' loop
		
		System.out.println("LUC uses 2 channels");
		System.out.print("Channel 1: ");
		Iterator<Point> iterator1 = jep.channel1.iterator();
		while(iterator1.hasNext()) {
			Point element = iterator1.next();
			System.out.print("(" + element.getX() + ", " + element.getY() + ")");
		}
		
		System.out.print("\nChannel 2: ");
		Iterator<Point> iterator2 = jep.channel2.iterator();
		while(iterator2.hasNext()) {
			Point element = iterator2.next();
			System.out.print("(" + element.getX() + ", "  + element.getY() + ")");
		}
		
	}//end Main() method
	
}//end 'Channel' class

