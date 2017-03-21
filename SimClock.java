/*
 * Yiqiao Zhao: 38332452
 * Chen Lu: 51398516
 */
public class SimClock
{
	private static int simTime;
	
	SimClock(){
		simTime=0;
	}
	
	public static void tick() throws InterruptedException{
		simTime++;
	}
	
	public static int getTime(){
		return simTime;
	}
	
}
