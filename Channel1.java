import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Channel1 {
	
	//'Point' class for the intervals
	private PointComparator comparator = new PointComparator();
	private PriorityQueue<Point> intervals = new PriorityQueue<Point>(1, comparator);
	private ArrayList<Point> intervals1 = new ArrayList<Point>(1);
	
	private ChannelComparator compareChannel = new ChannelComparator();
	private PriorityQueue<ArrayList<Point>> arrChannels = new PriorityQueue<ArrayList<Point>>(1, compareChannel);
	private ArrayList<ArrayList<Point>> workChannels = new ArrayList<ArrayList<Point>>(1);
	ArrayList<Point> currChannel = new ArrayList<Point>(1);

	public Channel1() throws FileNotFoundException {
		readInter();
		fixInter();
		//Initialize 2 channels
		workChannels.add(new ArrayList<Point>());
		workChannels.add(new ArrayList<Point>());
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
	
	//Method to  sort the intervals by 'earliest deadline first', uses 'PointComparator' class.
	public void fixInter () {
		while(intervals.size() > 0) {
			intervals1.add(intervals.remove());
		}
	}
	
	//incase intervals in the existing channel overlaps incoming intervals, we have to create a new channel
	public void createChannel() {
		workChannels.add(new ArrayList<Point>());
	}
	
	//Uses priority queue to retrieve lowest used channel.
	public ArrayList<Point> getLowChannel() {
		//Holds original channels
		ArrayList<ArrayList<Point>> tempChannels = new ArrayList<ArrayList<Point>>(2);
		
		while(arrChannels.size() > 0) {
			tempChannels.add(arrChannels.remove());
		}
		
		return tempChannels.get(0);
	}
	
	//I need a way to update the Priority Queue so that it will have updated list from the 'workChannel'
	public void syncChannel() {
		arrChannels.clear(); // I want to make sure that there are no leftover data inside.
		for(int i = 0; i < workChannels.size(); i++) {
			arrChannels.add(workChannels.get(i));
		}
	}

	// Main Method 
	public static void main (String[] args) throws FileNotFoundException{
		
		//Create an instance of this class. I didn't want to mess with Static/nonStatic method.
		Channel1 jep = new Channel1();
		
		for (int i = 0; i < jep.intervals1.size(); i++) {
			
			//Put the 1st interval in the 1st Channel.
			if (i == 0) {
				jep.workChannels.get(i).add(jep.intervals1.get(i));
				jep.syncChannel();
			}
			
			jep.currChannel = jep.getLowChannel(); //Obtain the lowest Unused Channel, which is stored in 'currChannel'
			
			if (jep.currChannel.isEmpty()) {
				jep.currChannel.add(jep.intervals1.get(i));
				jep.syncChannel();
			}
			else if ((jep.currChannel.get(jep.currChannel.size()-1).getY()) < (jep.intervals1.get(i).getX())) {
				jep.currChannel.add(jep.intervals1.get(i));
				jep.syncChannel();
			}
			else {
				for (int j = 0; j < jep.workChannels.size(); i++) { 
					
					if (jep.workChannels.get(j).get(jep.workChannels.get(j).size()-1).getY() < jep.intervals1.get(i).getX()) {
						jep.workChannels.get(j).add(jep.intervals1.get(i));
						jep.syncChannel();
					}
					else {
						//make a channel to add to the list of Channels
						jep.createChannel();
						jep.syncChannel();
						//Obtain the last recently created channel and add the interval
						jep.workChannels.get(jep.workChannels.size()-1).add(jep.intervals1.get(i));
						jep.syncChannel();
					}
					
				}
				
			}
			
		}
		
		System.out.println("LUC uses" + jep.workChannels.size() +"channels");
		for (int k = 0; k < jep.workChannels.size(); k++) {
			System.out.print("Channel " + k+1 + ": ");
			Iterator<Point> iterator = jep.workChannels.get(k).iterator();
			while (iterator.hasNext()) {
				Point element = iterator.next();
				System.out.print("(" + element.getX() + ", " + element.getY() + ")");
			}
		}

		
	}//end Main() method
	
}//end 'Channel' class

