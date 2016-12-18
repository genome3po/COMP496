/*Name: Jeffrey Rubi
 * 'Channel' class helper for PriorityQueue class
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class ChannelComparator implements Comparator<ArrayList<Point>> {

	@Override
	public int compare(ArrayList<Point> a, ArrayList<Point> b) {
		if(a.size() < b.size())
			return -1;
		if(a.size() >= b.size())
			return 1;
		else
			return 0;
	}//end method
}//end 'PointComparator' class
