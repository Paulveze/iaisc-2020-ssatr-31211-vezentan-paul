package timedPetriSimulator;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(final String[] args) throws JsonParseException, JsonMappingException, IOException {
//load PN from json file
		final ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		final PetriNet petriNet = mapper.readValue(
				new File("C:\\Users\\Paul\\Desktop\\Tema Java\\TimedPetriNetSimulator\\petriNet.json"),
				PetriNet.class);

		// extract the start place based on the index given in the .json file and add it
		// to the currentPlaces list
		final Place startPlace = petriNet.getPlaces().get(petriNet.getStartPlace());
		final HashSet<Place> currentPlaces = new HashSet<Place>();
		ArrayList<Place> placesToBeAdded = new ArrayList<Place>();
		ArrayList<Place> placesToBeRemoved = new ArrayList<Place>();
		currentPlaces.add(startPlace);
		System.out.println("st pl tokens" + startPlace.getNumberOfTokens());

		// write the PNs current status each second

		new java.util.Timer().schedule(

				new java.util.TimerTask() {
					@Override
					public void run() {
						try
						{
							petriNet.printStatus();
						} catch (final IOException e)
						{
							e.printStackTrace();
						}
					};
				}, 1000, 1000);
		while (!currentPlaces.isEmpty())
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{

				e.printStackTrace();
			}

			for (final Place currentPlace : currentPlaces)
			{
				System.out.println("CURRENT PLACE: " + currentPlace.getName());
				final ArrayList<Transition> nextTransitions = petriNet.getNextTransitionsForPlace(currentPlace);
				for (final Transition transition : nextTransitions)
				{
					final ArrayList<Place> transitionPrePlaces = petriNet.getPrePracesForTransition(transition);
					if (petriNet.transitionCanBeFired(transitionPrePlaces))
					{
						System.out.println("TPP0" + transitionPrePlaces.get(0).getName());
						petriNet.removeTokensFromPlaces(transitionPrePlaces);
						placesToBeRemoved = transitionPrePlaces;
						final ArrayList<Place> nextPlaces = petriNet.getNextPlacesForTransition(transition);
						for (Place place : nextPlaces)
						{
							System.out.println(place.getName());
						}
						placesToBeAdded = nextPlaces;
						if (transition.getMinTime() != 0 && transition.getMaxTime() != 0)
						{
							System.out.println("Random time transition");
							final Random random = new Random();
							final int minTime = transition.getMinTime();
							final int randomDelay = random.nextInt((transition.getMaxTime() - minTime) + 1) + minTime;

							ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

							Runnable task = new Runnable()
							{
								@Override
								public void run()
								{
									petriNet.addTokensToPlaces(nextPlaces);
								}
							};

							scheduler.schedule(task, randomDelay, TimeUnit.SECONDS);
							scheduler.shutdown();
						}
						else if (transition.getMinTime() != 0)
						{
							ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

							Runnable task = new Runnable()
							{
								@Override
								public void run()
								{
									petriNet.addTokensToPlaces(nextPlaces);
								}
							};

							scheduler.schedule(task, transition.getMinTime(), TimeUnit.SECONDS);
							scheduler.shutdown();
						} else
							{
							petriNet.addTokensToPlaces(nextPlaces);
							}

					}
				}
			}
			currentPlaces.removeAll(placesToBeRemoved);
			currentPlaces.addAll(placesToBeAdded);
			System.out.println("Add token in [0] : " + placesToBeAdded.get(0).getName());

		}
	}
}
