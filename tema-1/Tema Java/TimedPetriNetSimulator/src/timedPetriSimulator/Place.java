package timedPetriSimulator;

import java.util.ArrayList;

public class Place {
	String name;
	Integer numberOfTokens;
	ArrayList<Integer> nextTransitions;

	public Place() {
	}

	public Place(final String name, final Integer numberOfTokens, final ArrayList<Integer> nextTransitions) {
		this.name = name;
		this.numberOfTokens = numberOfTokens;
		this.nextTransitions = nextTransitions;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public Integer getNumberOfTokens()
	{
		return this.numberOfTokens;
	}

	public void setNumberOfTokens(final Integer numberOfTokens)
	{
		this.numberOfTokens = numberOfTokens;
	}

	public ArrayList<Integer> getNextTransitions()
	{
		return nextTransitions;
	}

	public void setNextTransitions(final ArrayList<Integer> nextTransitions)
	{
		this.nextTransitions = nextTransitions;
	}

}
