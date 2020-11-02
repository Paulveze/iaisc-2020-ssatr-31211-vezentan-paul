package timedPetriSimulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PetriNet {
	Integer startPlace;
	ArrayList<Place> places;
	ArrayList<Transition> transitions;

	public PetriNet() {
	}

	public PetriNet(final Integer startPlace, final ArrayList<Place> places, final ArrayList<Transition> transitions) {
		this.startPlace = startPlace;
		this.places = places;
		this.transitions = transitions;
	}

	public Integer getStartPlace()
	{
		return startPlace;
	}

	public void setStartPlace(final Integer startPlace)
	{
		this.startPlace = startPlace;
	}

	public ArrayList<Place> getPlaces()
	{
		return places;
	}

	public void setPlaces(final ArrayList<Place> places)
	{
		this.places = places;
	}

	public ArrayList<Transition> getTransitions()
	{
		return transitions;
	}

	public void setTransitions(final ArrayList<Transition> transitions)
	{
		this.transitions = transitions;
	}

	public void printStatus() throws IOException {
		String petriNetStatusString = "";
		for (final Place place : places)
		{
			petriNetStatusString += place.getName() + " Tokens: " + place.getNumberOfTokens();
			System.out.println(place.getName() + " Tokens: " + place.getNumberOfTokens());
		}
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

		final BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt", true));
		writer.append('\n');
		writer.append(petriNetStatusString);

		writer.close();
	}

	public void removeTokensFromPlaces(final ArrayList<Place> places)
	{
		for (final Place place : places)
		{
			place.setNumberOfTokens(0);
		}
	}

	public void addTokensToPlaces(ArrayList<Place> places)
	{
		for (final Place place : places)
		{
			place.setNumberOfTokens(1);
		}
	}

	public ArrayList<Transition> getNextTransitionsForPlace(final Place place)
	{
		final ArrayList<Transition> nextTransitions = new ArrayList<Transition>();
		for (final Integer index : place.getNextTransitions())
		{
			nextTransitions.add(this.getTransitions().get(index));
		}
		return nextTransitions;
	}

	public ArrayList<Place> getPrePracesForTransition(final Transition transition)
	{
		final ArrayList<Place> prePlaces = new ArrayList<Place>();
		for (final Integer index : transition.getTokensFrom())
		{
			final Place prePlace = this.getPlaces().get(index);
			prePlaces.add(prePlace);
		}
		return prePlaces;
	}

	public boolean transitionCanBeFired(final ArrayList<Place> transitionPrePlaces)
	{
		boolean fireable = true;
		for (final Place prePlace : transitionPrePlaces)
		{
			if ((prePlace.getNumberOfTokens()) == 0)
			{
				fireable = false;
			}
		}
		return fireable;
	}

	public ArrayList<Place> getNextPlacesForTransition(final Transition transition)
	{
		final ArrayList<Place> nextPlaces = new ArrayList<Place>();
		for (final Integer index : transition.getNextPlaces())
		{
			nextPlaces.add(this.getPlaces().get(index));
		}
		return nextPlaces;
	}

}
