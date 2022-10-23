import cycling.CyclingPortal;
import cycling.DuplicatedResultException;
import cycling.StageType;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidCheckpointsException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.MiniCyclingPortalInterface;
import cycling.SegmentType;

import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.1
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		// TODO replace BadMiniCyclingPortal by CyclingPortal
		
		MiniCyclingPortalInterface portal1 = new CyclingPortal();
		//MiniCyclingPortalInterface portal2 = new CyclingPortal();

		/** 
		assert (portal1.getRaceIds().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";
		assert (portal1.getTeams().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";

		try {
			portal1.createTeam("TeamOne", "My favorite");

			portal2.createTeam("TeamOne", "My favorite");
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}
		*/

		/*
		assert (portal1.getTeams().length == 1)
				: "Portal1 should have one team.";

		assert (portal2.getTeams().length == 1)
				: "Portal2 should have one team.";
		*/

		/*
		CyclingPortal portal = new CyclingPortal();

		System.out.println("Creating a new team...");
		portal.createTeam("Team1", "First Team Created");
		*/

		/**
		 * Testing Races
		 */

		try 
		{
			portal1.createRace("race1", "Wye Valley");
			System.out.println("Race 1 added");
		}
		catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		try 
		{
			portal1.createRace("race2", "Dartmoor");
			System.out.println("Race 2 added");
		}
		catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		try 
		{
			portal1.createRace("race3", "Monmouth");
			System.out.println("Race 3 added");
		}
		catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		/**
		 * Testing exceptions
		 */

		try 
		{
			portal1.createRace("race1", "Not in the Wye Valley");
			System.out.println("Race 1 added");
		}
		catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		try 
		{
			portal1.createRace(" ", "Not in the Wye Valley");
			System.out.println("Race 4 added");
		}
		catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		/**
		 * Testing 'getRaceIds' method
		 */

		int[] a = portal1.getRaceIds();
		System.out.println("Printing the IDs of each individual team...");
		for (int j:a) {
			System.out.println(j);
		}

		/**
		 * Testing 'removeRaceById' method
		 */

		try
		{
			portal1.removeRaceById(1);
			System.out.println("Removing raceId 1");
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		/**
		 * Testing exception
		 */

		try
		{
			portal1.removeRaceById(1);
			System.out.println("Removing raceId 1");
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		int[] b = portal1.getRaceIds();
		System.out.println("Printing the IDs of each individual team...");
		for (int j:b) {
			System.out.println(j);
		}

		try
		{
			System.out.println("Viewing RaceId: 0 - " + portal1.viewRaceDetails(0));
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		/**
		 * Testing Stages
		 */

		try 
		{
			portal1.addStageToRace(0, "Stage1", "sprint", 10.0, LocalDateTime.now(), StageType.FLAT);
			portal1.addStageToRace(0, "Stage2", "hill climb ", 7.5, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);
			portal1.addStageToRace(0, "Stage3",  "flat", 8.0, LocalDateTime.now(), StageType.FLAT);
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}
		catch (IllegalNameException e)
		{
			e.printStackTrace();
		}
		catch (InvalidNameException e)
		{
			e.printStackTrace();
		}
		catch (InvalidLengthException e)
		{
			e.printStackTrace();
		}
		
		try 
		{
			int c = portal1.getNumberOfStages(0);
			System.out.println("Number of stages in race 1: " + c + " stages");
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			int[] d = portal1.getRaceStages(0);
			System.out.println("Race 1 stages: ");
			for (int j:d) 
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		try
		{
			double l = portal1.getStageLength(1);
			System.out.println("Length of stage 2: " + l);
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Viewing RaceId: 0 - " + portal1.viewRaceDetails(0));
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		try
		{
			portal1.removeStageById(1);
			System.out.println("Removing stage with ID 1");
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		try
		{
			int[] d = portal1.getRaceStages(0);
			System.out.println("Race 1 stages: ");
			for (int j:d) 
			{
			System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		try 
		{
			int f = portal1.addCategorizedClimbToStage(0, 7.0, SegmentType.C3, 0.5, 10.0);
			System.out.println("Categorized climb added to stage with ID 0; ID: " + f);
			int f2 = portal1.addCategorizedClimbToStage(1, 4.0, SegmentType.HC, 1.0, 8.0);
			System.out.println("Categorized climb added to stage with ID 1; ID: " + f2);

		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}
		catch (InvalidLocationException e)
		{
			e.printStackTrace();
		}
		catch (InvalidStageStateException e)
		{
			e.printStackTrace();
		}
		catch (InvalidStageTypeException e)
		{
			e.printStackTrace();
		}

		try 
		{
			int g = portal1.addIntermediateSprintToStage(0, 5.0);
			System.out.println("Intermediate sprint added to stage with ID 0; ID: " + g);
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}
		catch (InvalidLocationException e)
		{
			e.printStackTrace();
		}
		catch (InvalidStageStateException e)
		{
			e.printStackTrace();
		}
		catch (InvalidStageTypeException e)
		{
			e.printStackTrace();
		}

		try
		{
			portal1.removeSegment(1);
			System.out.println("Removing segment with ID 0");
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}
		catch (InvalidStageStateException e)
		{
			e.printStackTrace();
		}

		try
		{
			portal1.concludeStagePreparation(0);
			System.out.println("Concluding stage with ID 0");
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}
		catch (InvalidStageStateException e)
		{
			e.printStackTrace();
		}

		try
		{
			int[] h = portal1.getStageSegments(0);
			System.out.println("Getting segements from stage with ID 0" );
			for (int j:h) 
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e) 
		{
			e.printStackTrace();
		}

		try 
		{
			portal1.createTeam("Team1", "this is team 1");
			portal1.createTeam("Team2", "this is team 2");
			portal1.createTeam("Team3", "this is team 3");
		}
		catch (IllegalNameException e)
		{
			e.printStackTrace();
		}
		catch (InvalidNameException e)
		{
			e.printStackTrace();
		}

		int[] i = portal1.getTeams();
		System.out.println("Getting list of teams" );
		for (int j:i) 
		{
			System.out.println(j);
		}

		try
		{
			portal1.removeTeam(1);
			System.out.println("Removing team 2 in position 1");
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		int[] k = portal1.getTeams();
		System.out.println("Getting list of teams" );
		for (int j:k) 
		{
			System.out.println(j);
		}

		try
		{
			portal1.createRider(0, "Harry", 1999);
			portal1.createRider(0, "John", 1989);
			portal1.createRider(0, "Ben", 1997);
			portal1.createRider(0, "Nathan", 2000);
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}

		try
		{
			portal1.removeRider(2);
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			int[] l = portal1.getTeamRiders(0);
			System.out.println("Getting team 1 rider ids" );
			for (int j:l) 
			{
				System.out.println(j);
			}

			int[] m = portal1.getTeamRiders(2);
			System.out.println("Getting team 3 rider ids" );
			for (int j:m) 
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Registering rider 1 results to stage 1");
			portal1.registerRiderResultsInStage(0, 0, LocalTime.parse("00:00:00.000"), LocalTime.parse("01:30:23.920"), LocalTime.parse("01:31:02.632"));
			System.out.println("Registering rider 2 results to stage 1");
			portal1.registerRiderResultsInStage(0, 1, LocalTime.parse("00:00:00.000"), LocalTime.parse("01:12:49.521"), LocalTime.parse("01:31:02.760"));
			System.out.println("Registering rider 3 results to stage 1");
			portal1.registerRiderResultsInStage(0, 2, LocalTime.parse("00:00:00.000"), LocalTime.parse("01:23:12.843"), LocalTime.parse("01:19:25.930"));
			System.out.println("Registering rider 2 results to stage 1");
			portal1.registerRiderResultsInStage(0, 3, LocalTime.parse("00:00:00.000"), LocalTime.parse("01:08:19.35"), LocalTime.parse("01:22:33.511"));

		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}
		catch (DuplicatedResultException e)
		{
			e.printStackTrace();
		}
		catch (InvalidCheckpointsException e)
		{
			e.printStackTrace();
		}
		catch (InvalidStageStateException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Returning rider 4 results in stage 1: ");
			LocalTime[] n = portal1.getRiderResultsInStage(0,3);
			for (LocalTime j:n)
			{
				System.out.println(j);
			}

			System.out.println("Returning rider 2 results in stage 1: ");
			LocalTime[] o = portal1.getRiderResultsInStage(0,1);
			for (LocalTime j:o)
			{
				System.out.println(j);
			}

		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Returning adjusted elapsed times for stage 1: ");
			LocalTime a0 = portal1.getRiderAdjustedElapsedTimeInStage(0,0);
			LocalTime a1 = portal1.getRiderAdjustedElapsedTimeInStage(0,1);
			LocalTime a2 = portal1.getRiderAdjustedElapsedTimeInStage(0,2);
			LocalTime a3 = portal1.getRiderAdjustedElapsedTimeInStage(0,3);

			System.out.println("Adjusted Elapsed Time 1: " + a0);
			System.out.println("Adjusted Elapsed Time 2: " + a1);
			System.out.println("Adjusted Elapsed Time 3: " + a2);
			System.out.println("Adjusted Elapsed Time 4: " + a3);

		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try 
		{
			System.out.println("Deleting Rider 4 results stage 1");
			portal1.deleteRiderResultsInStage(0, 3);
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Returning ranks in stage 1: ");
			int[] p = portal1.getRidersRankInStage(0);
			for (int j:p)
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Returning adjusted ranks in stage 1: ");
			LocalTime[] q = portal1.getRankedAdjustedElapsedTimesInStage(0);
			for (LocalTime j:q)
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Returning points in stage 1: ");
			int[] r = portal1.getRidersPointsInStage(0);
			for (int j:r)
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}

		try
		{
			System.out.println("Returning Mountain ranks in stage 1: ");
			int[] s = portal1.getRidersMountainPointsInStage(0);
			for (int j:s)
			{
				System.out.println(j);
			}
		}
		catch (IDNotRecognisedException e)
		{
			e.printStackTrace();
		}
	}
}
