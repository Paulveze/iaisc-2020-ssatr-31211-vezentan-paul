package timedPetriSimulator;

import java.util.ArrayList;

public class Transition {
	int minTime;
	int maxTime;
	ArrayList<Integer> nextPlaces;
	ArrayList<Integer> tokensFrom;

	public Transition() {
	}

	public int getMinTime()
	{
		return minTime;
	}

	public void setMinTime(final int minTime)
	{
		this.minTime = minTime;
	}

	public int getMaxTime()
	{
		return maxTime;
	}

	public void setMaxTime(final int maxTime)
	{
		this.maxTime = maxTime;
	}

	public ArrayList<Integer> getNextPlaces()
	{
		return nextPlaces;
	}

	public void setNextPlaces(final ArrayList<Integer> nextPlaces)
	{
		this.nextPlaces = nextPlaces;
	}

	public ArrayList<Integer> getTokensFrom()
	{
		return tokensFrom;
	}

	public void setTokensFrom(final ArrayList<Integer> tokensFrom)
	{
		this.tokensFrom = tokensFrom;
	}

}
