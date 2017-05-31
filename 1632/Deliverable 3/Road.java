import java.util.*;

public class Road {
		String name = "";
		private City begin;
		private City end;
		
		public Road(String streetName, City start, City stop){
			this.name = streetName;
			this.begin = start;
			this.end = stop;
		} 
		
		public String getName(){
			return name;
		}
		
		public City getStart(){
			return begin;
		}
		
		public City getEnd(){
			return end;
		}
}