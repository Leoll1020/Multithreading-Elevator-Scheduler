/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
public class ElevatorEvent
{
	private int origin;
	private int lastStop;
	private int destination;
	private int expectedArrival;
	private String purpose; //Load or Unload
	private int headCount; // number of passenger to load or unload
	
	ElevatorEvent(int origin, int lastStop, int destination, String purpose, int headCount){
		this.origin = origin;
		this.lastStop = lastStop;
		this.destination=destination;
		this.expectedArrival=calculateExpectedArrival();
		this.purpose=purpose;
		this.headCount=headCount;
	}
	
	// getters
	public int getOrigin(){
		return origin;
	}
	
	public int getDestination(){
		return destination;
	}
	
	public int getLastStop(){
		return lastStop;
	}
	
	public int getExpectedArrival(){
		return expectedArrival;
	}
	
	public String getPurpose(){
		return purpose;
	}
	
	public int getHeadCount(){
		return headCount;
	}
	
	public int calculateExpectedArrival(){
		return Math.abs(lastStop - destination) * 5 + 10;
	}
	
}
