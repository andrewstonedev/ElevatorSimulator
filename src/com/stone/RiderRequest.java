// import java.util.TimerTask;
package com.stone;

public class RiderRequest
{
	public enum RequestDirection {
		UP, DOWN
	}

	RequestDirection requestDirection;
	int requestedOnFloor;

	public RiderRequest(int _requestedOnFloor, RequestDirection direction)
	{
		this.requestedOnFloor = _requestedOnFloor;
		this.requestDirection = direction;
	}


}

