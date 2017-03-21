/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
public class PassengerArrival
{
	private int numPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	
	PassengerArrival(int numPassengers, int destinationFloor, int timePeriod){
		this.numPassengers=numPassengers;
		this.destinationFloor=destinationFloor;
		this.timePeriod=timePeriod;
		this.expectedTimeOfArrival=timePeriod;
	}
	
	
	// getters
	public int getNumPassengers(){
		return numPassengers;
	}
	
	public int getDestinationFloor(){
		return destinationFloor;
	}
	
	public int getTimePeriod(){
		return timePeriod;
	}
	
	public int getExpectedTimeOfArrival(){
		return expectedTimeOfArrival;
	}
	
	public void updateTimeArrival(){
		if (expectedTimeOfArrival == 0)
			expectedTimeOfArrival = timePeriod;
		expectedTimeOfArrival -=1;
	}
	
	
	
}
