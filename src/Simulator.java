import com.stone.Elevator;

import java.util.Scanner;
import java.util.Timer;

public class Simulator
{
	Scanner scanner = new Scanner(System.in);
	int simulationDuration;

	public int setSimulationDuration()
	{
		System.out.print("Enter length of simulation (sec): ");
		int duration = scanner.nextInt();
		return duration;
	}
	public void startSimulation() throws InterruptedException
	{
		simulationDuration = setSimulationDuration();
		Timer timer = new Timer();
		// timer.schedule(new RiderRequest(2, RiderRequest.RequestDirection.UP), 0, 1000);


		for (int sec = simulationDuration; sec > 0; sec--) {
			System.out.println(sec);
			Thread.sleep(1000);
		}
		timer.cancel();
	}

	// TODO: Implement
	/*  returns random elevator within CSF building
		used for simulation
	public Elevator GetRandomElevator()
	{
		int randomElevator = (int) (Math.random() * CSF.getNumElevators() + 1);
		return CSF.GetElevatorByID(randomElevator);
	} */

}
