import java.util.*;

public class City{
		private String name = "";
		private String alias = "";
		private boolean containsLaboon = false;
		private boolean outCity = false;
		public ArrayList<Road>  routes = new ArrayList<Road>();
		
		public City(String nameOfCity){
			this.name = nameOfCity;
			if(name.equals("Sennott")){
				containsLaboon = true;
			}
		}
		
		public City(String nameOfCity, String oppName, boolean inOrOut){
			this.name = nameOfCity;
			this.alias = oppName;
			this.outCity = inOrOut;
		}
		
		public String getName(){
			return name;
		}
		
		public String getAlias(){
			return alias;
		}
		
		public boolean outsideCity(){
			return outCity;
		}
		
		public int addRoute(Road road){
			routes.add(road);
			return routes.size();
		}
		
		public Road findDirection(Random randum){
			return routes.get(randum.nextInt(routes.size()));
		}		
}
