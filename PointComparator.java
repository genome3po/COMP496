/*
 * 'Channel' class helper for PriorityQueue class
 */
import java.awt.Point;
import java.util.Comparator;

public class PointComparator implements Comparator<Point> {

	@Override
	public int compare(Point a, Point b) {
		if(a.getY() < b.getY())
			return -1;
		if(a.getY() >= b.getY())
			return 1;
		else
			return 0;
	}//end method
}//end 'PointComparator' class
