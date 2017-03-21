/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
import java.util.ArrayList;

//import java.util.concurrent.locks.*;

public class BuildingManager
{
	private BuildingFloor[] floors;
	private int currentHeight=0;
	//private Lock missionCheckLock;

	BuildingManager(){
		floors=new BuildingFloor[5];
	}
		
	public void addFloor(BuildingFloor f){
		floors[currentHeight]=f;
		currentHeight++;
	}
	public int getHeight(){
		return floors.length;
	}
	
	public synchronized int[] returnNewMission(int currentFloor){  
		//The return array is an array of SIX int, array[0] is 
		//the floor that has new passengers, the last 5 int are the number 
		// of passengers to each floor
		//going up and going down are separated 
		//remove the request, mark as handled
		int[] answer=new int[]{-1,0,0,0,0,0};
		
		//generate a list of floor number sorted by the distance to
		// the currentFloor
		ArrayList<Integer> index = new ArrayList<Integer>();
		index.add(currentFloor);
		for (int delta=1; delta < floors.length; delta++){
			int floorNum = delta + currentFloor; 
			if (floorNum >= 0 && floorNum < floors.length){
				index.add(floorNum);
			}
			floorNum = currentFloor - delta;
			if (floorNum >= 0 && floorNum < floors.length){
				index.add(floorNum);
			}
		}
		
		//for every floor number in index, find the requests if exist
		for (int i: index){
			if (floors[i].isBuildingFree()==false){
				answer[0]=i;
				boolean hasAnswer = false;
				
				//find all going-up requests
				for (int j=i; j<floors.length; j++){
					answer[j+1]=floors[i].getPassengerRequests()[j];
					removeRequest(i, j);
					if (answer[j+1] != 0)
						hasAnswer = true;
				}

				if (hasAnswer){
					return answer;
				}
				
				//find all going-down requests
				for (int j =i; j > -1; j--){
					answer[j+1]=floors[i].getPassengerRequests()[j];
					removeRequest(i, j);
				}
				
				return answer;
			}
		}
		return answer;
	}
	
	
	public synchronized void setAppoarchingElevator(int eid, int floor){
		floors[floor].setApproachingElevator(eid);
	}
	
	public synchronized void setRequest(int from, int to, int amount){
		// add new request to records
		floors[from].setNewRequest(to, amount);
	}
	
	public synchronized void setNewArrival(int from, int to, int amount){
		floors[to].setNewArrival(from, amount);
	}
	
	public synchronized void removeRequest(int from, int to){
		floors[from].removeRequest(to);
	}
	
	public String toString(){
		String result= toStringCurrent();
		result += "----------------------------\n";
		result += toStringTotalD();
		result += "----------------------------\n";
		// result += toStringApproaching();
		 result += toStringTotalA();
		 result += "----------------------------\n";
		
		return result;
		
	}
	
	public String toStringApproaching(){
		String result = "Approaching elevators\n";
		for (int i = 0; i < 5; i++){
			result += "\t Floor " + i + ", " +floors[i].toStringApproching();
		}
		return result;
	}
	
	public String toStringCurrent(){
		// return a string represent the current requests
		String result = "Current Passenger Request\n";
		result += "    To | 0 | 1 | 2 | 3 | 4 |\n";
		for (int i = 0; i < 5; i++){
			result += "From " + i + " |";
			result += floors[i].toStringCurrent();
			result += "\n";
		}
		return result;
	}
	
	public String toStringTotalD(){
		// return a string represent the total departure
		String result = "Total Passenger Request\n";
		result += "    To | 0 | 1 | 2 | 3 | 4 |\n";
		for (int i = 0; i < 5; i++){
			result += "From " + i + " |";
			result += floors[i].toStringTotalD();
			result += "\n";
		}
		return result;
	}
	
	public String toStringTotalA(){
		String result = "Total Passenger Arrival\n";
		result += "  From | 0 | 1 | 2 | 3 | 4 |\n";
		for (int i = 0; i < 5; i++){
			result += "  To " + i + " |";
			result += floors[i].toStringTotalA();
			result += "\n";
		}
		return result;
	}
	
}
