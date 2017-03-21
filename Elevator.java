/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
import java.util.ArrayList;


public class Elevator implements Runnable 
{
	private int EID;
	private int currentFloor=0;
	private int numPassengers=0;
	private int totalLoadedPassengers=0;
	private int totalUnloadedPassengers=0;
	private ArrayList<ElevatorEvent> moveQueue;
	//private int[] passengersDestinations;
	private BuildingManager MBCopy;
	private int rate;
	
	Elevator(int elevatorID, BuildingManager MB, int rate){
		this.EID=elevatorID;
		this.MBCopy=MB;
		this.moveQueue=new ArrayList<ElevatorEvent>();
		this.rate=rate;
	}
	
	
	
	public void run()
	{
		while (!Thread.currentThread().isInterrupted()){
			int[] newMission=MBCopy.returnNewMission(currentFloor);
			// check if elevator is idle and there exist a new request
			while ((moveQueue.size()==0) && (newMission[0]!=-1) ){		
				if (Thread.currentThread().isInterrupted()){
					moveQueue.clear();
					return;
					
				}
				int from = newMission[0];
				
				int sumPsg = 0; 
				for (int i = 0; i< MBCopy.getHeight(); i++){
					sumPsg += newMission[i+1];
					
				}
				//Creating loading passenger events
				ElevatorEvent loadEvent=new ElevatorEvent(currentFloor,currentFloor, from,
					"Load",sumPsg);		
				
				
				moveQueue.add(loadEvent);
				
				//Creating unloading passenger events
				
				int lastStop = from;
				// going up
				for (int to = from; to< MBCopy.getHeight();to++){
					if (newMission[to+1]!=0){
						ElevatorEvent unloadEvent=new ElevatorEvent(from, lastStop, to,		
							"Unload", newMission[to+1]);	
						
						moveQueue.add(unloadEvent);	
						lastStop = to;
						System.out.format("Time:%-3d, Request Handled, EID%2d, From:%d->%d\n", 
							(SimClock.getTime()-1), EID,from, to);
						
					}			
				}
				lastStop = from;
				// going down
				for (int to = from; to > -1;to--){
					if (newMission[to+1]!=0){
						ElevatorEvent unloadEvent=new ElevatorEvent(from, lastStop, to,		
							"Unload", newMission[to+1]);	
						
						moveQueue.add(unloadEvent);	
						lastStop = to;
						System.out.format("Time:%-3d, Request Handled, EID%2d, From:%d->%d\n", 
							(SimClock.getTime()-1), EID,from, to);
					}		
				}
				
				//MBCopy.setAppoarchingElevator(EID, currentFloor);
				//System.out.println(MBCopy.toString());
			}
			
			
			int baseTime=SimClock.getTime();
			// If there exist a move event, make the thread sleep for the expected time
			for (int i=0; i<moveQueue.size();i++){
				if (Thread.currentThread().isInterrupted()){
					moveQueue.clear();
					return;
					
				}
				/// EDIT UNFINISHED
				MBCopy.setAppoarchingElevator(EID, currentFloor);
				
				int time = moveQueue.get(i).getExpectedArrival();
				int from = moveQueue.get(i).getOrigin();
				int to   = moveQueue.get(i).getDestination();
				int stop = moveQueue.get(i).getLastStop();
				int psg  = moveQueue.get(i).getHeadCount();
				String purpose  = moveQueue.get(i).getPurpose();
				baseTime += time;
				
				try
				{
					
					Thread.sleep(time*rate);
					
					if (purpose.equals("Load")){
						totalLoadedPassengers += psg;
						numPassengers += psg;
						currentFloor = to;
					}
					else{
						numPassengers -= psg;
						totalUnloadedPassengers += psg;
						currentFloor = to;
						MBCopy.setNewArrival(from, to, psg);
					}
					
					System.out.format("Time:%-3d, EID%2d, %-6s, move:%d->%d, request:%d->%d, %dpsg; %dpsg remain.\n",
						baseTime-1, EID, purpose, stop, to, from, to, psg, numPassengers);
//					System.out.format("Elevator %-2d %-6s at floor %-2d at time %-3d. %-2d passengers in elevator now.\n",
//						EID,moveQueue.get(i).getPurpose(),moveQueue.get(i).getDestination(),baseTime,moveQueue.get(i).getHeadCount());
//					MBCopy.getFloor(i).resetPassengerRequests();
				}
				catch (InterruptedException e)
				{
					//e.printStackTrace();
					moveQueue.clear();
					return;
				}
			}
			moveQueue.clear();
			
		}	
	}
	
	public int getEID(){
		return this.EID;
	}
	
	
	
	public String toString(){
		// print the current status of elevator
		String result = "";
		result += "Elevator ID: " + EID + "\n";
		result += String.format("\t%-30s %5d\n", "Current Loaded Passenger:", numPassengers);
//		result += "\t\tDestinations: | 0 | 1 | 2 | 3 | 4 |\n\t\t  Passengers: |";
//		for (int i =0; i < 5; i++){
//			result += String.format("%3d|", passengersDestinations[i]);
//		}
		result += String.format("\t%-30s %5d\n", "Total Loaded Passenger:", totalLoadedPassengers);
		result += String.format("\t%-30s %5d\n", "Total Unloaded Passenger:", totalUnloadedPassengers);

		
		
		return result;
	}
}
