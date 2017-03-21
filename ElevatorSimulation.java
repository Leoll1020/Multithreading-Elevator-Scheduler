/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;


public class ElevatorSimulation
{
	private Elevator E0;
	private Elevator E1;
	private Elevator E2;
	private Elevator E3;
	private Elevator E4;
	
	private BuildingManager BM;
	private int totalTime;
	private int rate;
	private ArrayList<ArrayList<PassengerArrival>> arrivalPattern=new ArrayList<ArrayList<PassengerArrival>>();
	
	
	public void start() throws FileNotFoundException{
		BM=new BuildingManager();
		// read ElevatorConfig
		try{
		Scanner inFile=new Scanner(new File("ElevatorConfig.txt"));
		int lineNumber=0;
		String line;
		while (inFile.hasNextLine()){
			lineNumber++;
			if (lineNumber==1){
				this.totalTime=Integer.valueOf(inFile.nextLine());
				continue;
			}
			if (lineNumber==2){
				this.rate=Integer.valueOf(inFile.nextLine());
				continue;
			}
			BuildingFloor currentFloor=new BuildingFloor();
			BM.addFloor(currentFloor);
			line = inFile.nextLine();
			String[] stringArray=line.split(";");
			ArrayList<PassengerArrival> currentFloorArrival=new ArrayList<PassengerArrival> ();
			for (String oneBehaviour:stringArray){
				String[] oneBehaviourElements=oneBehaviour.split(" ");
				PassengerArrival thisArrival=new PassengerArrival(Integer.valueOf(oneBehaviourElements[0]),
					Integer.valueOf(oneBehaviourElements[1]),Integer.valueOf(oneBehaviourElements[2]));
				currentFloorArrival.add(thisArrival);
			}
			arrivalPattern.add(currentFloorArrival);
		}
		
		
		for (int i=0; i<arrivalPattern.size(); i++){
			System.out.println("Floor "+i+" Config:");
			for (int j=0; j<arrivalPattern.get(i).size();j++){
				System.out.println("People: " + arrivalPattern.get(i).get(j).getNumPassengers()+" To: "+
					arrivalPattern.get(i).get(j).getDestinationFloor()+" Time: "+arrivalPattern.get(i).get(j).getTimePeriod());
				
			}
		}
		System.out.println("Reading file complete. Start simulation...");
		inFile.close();}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		
		E0=new Elevator(0,BM, rate);
		E1=new Elevator(1,BM, rate);
		E2=new Elevator(2,BM, rate);
		E3=new Elevator(3,BM, rate);
		E4=new Elevator(4,BM, rate);
			
		Thread T0=new Thread(E0);
		Thread T1=new Thread(E1);
		Thread T2=new Thread(E2);
		Thread T3=new Thread(E3);
		Thread T4=new Thread(E4);
		
		T0.start();
		T1.start();
		T2.start();
		T3.start();
		T4.start();
		
		
		
		// Create requests
		while (SimClock.getTime() <= totalTime){
			try
			{
				SimClock.tick();
				Thread.sleep(rate);
				
			}catch (InterruptedException e){
				e.printStackTrace();
			}
//			System.out.println(oneClock.getTime());
			
			ReentrantLock lock = new ReentrantLock();
			
			lock.lock();
			for (int from = 0; from < 5; from++){
				for (PassengerArrival arrival: arrivalPattern.get(from)){
					if (arrival.getExpectedTimeOfArrival() == 0){
						BM.setRequest(from, arrival.getDestinationFloor(), 
							arrival.getNumPassengers() );
						
						System.out.format("Time:%-3d, Request Generated: From: %d, To: %d, Psg: %d\n", 
							(SimClock.getTime()-1), from, arrival.getDestinationFloor(), 
							arrival.getNumPassengers());
					}
					
					arrival.updateTimeArrival();
				}
			}
			lock.unlock();
			
			
		}
		
//		System.out.println(BM.toString());
		
		T0.interrupt();
		T1.interrupt();
		T2.interrupt();
		T3.interrupt();
		T4.interrupt();
		printBuildingState();
		System.out.println("End of Simulation");
		
		return;
		
		
		
	}
	
	
	
	public void printBuildingState(){
		System.out.println(BM.toString());
		System.out.println(E0.toString());
		System.out.println(E1.toString());
		System.out.println(E2.toString());
		System.out.println(E3.toString());
		System.out.println(E4.toString());
		
		
		
		
		
	}
	
	

}
