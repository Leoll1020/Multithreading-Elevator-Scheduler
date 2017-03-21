/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
public class BuildingFloor
{
	private int[] totalDestinationRequests; //update when new request spawns
	private int[] arrivedPassengers;		//update when new request spawns
	private int[] passengerRequests;		//update when new request spawns
	private int approachingElevator;
	
	BuildingFloor(){
		this.totalDestinationRequests=new int[ ]{0,0,0,0,0};
		this.arrivedPassengers=new int[]{0,0,0,0,0};
		this.passengerRequests=new int[]{0,0,0,0,0};
		this.approachingElevator=-1;
	}
	
	
	// getters
	public int[] getPassengerRequests(){
		return passengerRequests;
	}
	
	
	public int getPassengerRequests(int to){
		return passengerRequests[to];
	}
	 
	// setters
	public void setApproachingElevator (int eid){
		approachingElevator = eid;
	}
	public void setNewRequest(int to, int amount){
		// add new request to records
		totalDestinationRequests[to] += amount;
		passengerRequests[to] += amount;
	}
	public void setNewArrival(int from, int amount){
		arrivedPassengers[from] += amount;
	}
	public void removeRequest(int to){
		passengerRequests[to] = 0;
	}
	
	
	
	
	public synchronized void resetPassengerRequests(){
		// reset the passenger requests of the current floor
		for (int i=0;i<totalDestinationRequests.length;i++){
			passengerRequests[i]=0;
		}
	}
	
	
	public boolean isBuildingFree(){
		// return true if no passenger is requesting elevator at this floor
		for (int i=0;i<totalDestinationRequests.length;i++){
			if (passengerRequests[i]!=0){
				return false;
			}
		}
		return true;
	}
	
	
	
	// print 
	public String toStringCurrent(){
		// return a string represent the current passenger request
		String result = "";
		for (int i = 0; i < 5; i++){
			result += String.format("%-3d|", passengerRequests[i]);
		}
		return result;
	}
	
	public String toStringApproching(){
		// return a string represent the elevator that is approaching
		return "Approching Elevator ID: " + approachingElevator + "\n";
		
	}
	
	public String toStringTotalD(){
		// return a string represent the total passenger request to depart from this floor
		String result = "";
		for (int i = 0; i < 5; i++){
			result += String.format("%-3d|", totalDestinationRequests[i]);
		}
		return result;
	}
	
	public String toStringTotalA(){
		//return a string represent the total passenger arrive at this floor
		String result = "";
		for (int i = 0; i < 5; i++){
			result += String.format("%-3d|", arrivedPassengers[i]);
		}
		return result;
	}
	
	
}
