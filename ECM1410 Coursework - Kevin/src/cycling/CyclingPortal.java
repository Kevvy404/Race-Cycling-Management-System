package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
  * @author Kevin Liu
  * @version 1.0
  * date: 15/07/2022
  * reason for change: Initial Version
  *
  * @author Kevin Liu
  * @version 1.1
  * date: 22/07/2022
  * reason for change: Gone back and added the method 'viewRaceDetails' 
  *
  * @author Kevin Liu
  * @version 1.2
  * date: 29/07/2022
  * reason for change: Completed 2 methods ('registerRiderResultsInStage', 'getRiderResultsInStage')
  *
  * @author Kevin Liu
  * @version 1.3
  * date: 30/07/2022
  * reason for change: Completed 2 methods ('getRiderAdjustedElapsedTimeInStage'
  *, 'deleteRiderResultsInStage')
  *
  * @author Kevin Liu
  * @version 1.4
  * date: 01/08/2022
  * reason for change: Completed 4 methods ('getRidersRankInStage', 'eraseCyclingPortal', 
  * 'saveCyclingPortal', 'loadCyclingPortal')
  *
  * @author Kevin Liu
  * @version 1.5
  * date:02/08/2022
  * reason for change: Completed 3 methods ('getRankedAdjustedElapsedTimesInStage', 
  * 'getRidersPointsInStage()', 'getRidersMountainPointsInStage()')
  *
  * @author Kevin Liu
  * @version 1.6
  * date: 03/08/2022
  * reason for change: Tidying up code (commenting debug code out)
*/

/**
 * Imports all the resouces necessary for the code to be more efficient
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

//import javax.lang.model.type.NullType;
//import javax.net.ssl.TrustManagerFactory;
//import javax.print.attribute.HashDocAttributeSet;
//import javax.print.attribute.HashPrintJobAttributeSet;

public class CyclingPortal implements MiniCyclingPortalInterface {
  int[][] ptsTable = {
          {50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2},
          {30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2},
          {20, 17, 15, 13, 11, 10,  9,  8, 7, 6, 5, 4, 3, 2, 1},
          {20, 17, 15, 13, 11, 10,  9,  8, 7, 6, 5, 4, 3, 2, 1},
          {20, 17, 15, 13, 11, 10,  9,  8, 7, 6, 5, 4, 3, 2, 1}
      };

  int[][] mtnPtsTable = {
          { 1,  0,  0,  0, 0, 0, 0, 0},
          { 2,  1,  0,  0, 0, 0, 0, 0},
          { 5,  3,  2,  1, 0, 0, 0, 0},
          {10,  8,  6,  4, 2, 1, 0, 0},
          {20, 15, 12, 10, 8, 6, 4, 2}
      };

  /**
   * Declares the type of these variables.
   */

  private int nextRaceId;
  private int nextStageId;
  private int nextSegmentId;
  private int nextTeamId;
  private int nextRiderId;
  private SegmentType SPRINT = null;

  /**
   * Creates 5 arraylists for each crucial sector of the application.
   */

  private ArrayList<Race> races = new ArrayList<Race>();
  private ArrayList<Result> results = new ArrayList<Result>();
  private ArrayList<Rider> riders = new ArrayList<Rider>();
  private ArrayList<Segment> segments = new ArrayList<Segment>();
  private ArrayList<Stage> stages = new ArrayList<Stage>();
  private ArrayList<Team> teams = new ArrayList<Team>();

  /**
   * Sets the default value of these variables.
   */

  public CyclingPortal() {
    this.nextRaceId = 0;
    this.nextStageId = 0;
    this.nextSegmentId = 0;
    this.nextTeamId = 0;
    this.nextRiderId = 0;
  }

  /**
   * getNextRaceId()
   *
   * <p>These methods get the next ID by adding 1 to the next position in the list.
   * It starts at position -1 so that the IDs start at 0 which is the first position
   * in the arraylist
   * 
   * @author Kevin Liu
   * @version 1.0
   */

  private int getNextRaceId() {
    this.nextRaceId++;
    return this.nextRaceId - 1;
  }

  private int getNextStageId() {
    this.nextStageId++;
    return this.nextStageId - 1;
  }

  private int getNextSegmentId() {
    this.nextSegmentId++;
    return this.nextSegmentId - 1;
  }

  private int getNextTeamId() {
    this.nextTeamId++;
    return this.nextTeamId - 1;
  }

  private int getNextRiderId() {
    this.nextRiderId++;
    return this.nextRiderId - 1;
  }

  /**
   * elapsedTime()
   * 
   * <p>I have created this private method which calculates the elapsed time
   * using the rider's times which can then be used to calculate the 
   * points and ranks for that rider.
   * 
   * @param stageId The ID of the stage.
   * @param riderId The ID of the rider.
   * @return The elapsed time.
  */

  private LocalTime elapsedTime(int stageId, int riderId) {
    long difference = 0;
    LocalTime elapsedTime = LocalTime.of(0, 0, 0, 0);

    for (int i = 0;i < results.size(); i++)  {
      if ((results.get(i).getStageId() == stageId) && (results.get(i).getRiderId() == riderId)) {
        LocalTime[] checkpoints = results.get(i).getCheckpoints();
        LocalTime startTime = checkpoints[0];

        // Debugging code 
        //System.out.println("Start time: " + startTime);

        LocalTime finishTime = checkpoints[checkpoints.length - 1];
        // Debugging code 
        //System.out.println("Finish time: " + finishTime);

        difference = Duration.between(startTime, finishTime).toMillis();
        int mill = (int)difference % 1000;
        //System.out.println("milliseconds: " + mill);
        int secs = ((int)difference / 1000) % 60;
        //System.out.println("seconds: " + secs);
        int mins = ((int)difference / 60000) % 60;
        //System.out.println("minutes: " + mins);
        int hours = (int)difference / 3600000;
        //System.out.println("hours: " + hours);

        // Convert to nanoseconds to milliseconds
        elapsedTime = LocalTime.of(hours, mins, secs, mill * 1000000);

      }
    }
    return elapsedTime;
  }

  /**
   * findAdjustedElapsedTime()
   * 
   * <p>I have created this method so that it can be used to calculate the
   * adjusted elapsed time for each rider. This compares the rider's 
   * finish time with the other riders and sets it to the one with the shortest 
   * time if the riders finish times are within 1 second of eachother.
   * 
   * @param stageId The ID of the stage.
   * @param riderId The ID of the rider.
   * @return The adjusted elapsed time.
   */

  private LocalTime findAdjustedElapsedTime(int stageId, int riderId)  {
    int currentRiderId = 0;
    long difference = 0;
    LocalTime adjustedElapsedTime = LocalTime.of(0,0,0,0);
    LocalTime currentRiderElapsedTime = LocalTime.of(0,0,0,0);

    adjustedElapsedTime = elapsedTime(stageId, riderId);

    for (int i = 0; i < results.size(); i++)  {
      for (int j = 0; j < results.size(); j++)  {
        if (results.get(i).getStageId() == stageId)  {
          currentRiderId = results.get(i).getRiderId();
          currentRiderElapsedTime = elapsedTime(stageId, currentRiderId);

          difference = Duration.between(currentRiderElapsedTime, adjustedElapsedTime).toMillis();

          if ((difference < 1000) && (difference > 0))  {
            adjustedElapsedTime = currentRiderElapsedTime;
          }

        }
      }
    }
    return adjustedElapsedTime;
  }

  /**
   * getRaceIds()
   *
   * <p>The method 'getRaceIds' gets the raceIDs by using the method I created earlier to
   * output all the raceIds that are currently assigned to a position in the
   * arraylist.
   * 
   * @return a arraylist containing all the raceIds
   */

  @Override
  public int[] getRaceIds() {

    Race nextRace;
    int[] raceIds = new int[races.size()];

    for (int i = 0; i < races.size(); i++) {
      nextRace = races.get(i);
      //System.out.println("RaceId = " + nextRace.getRaceId());
      raceIds[i] = nextRace.getRaceId();
    }
    return raceIds;
  }

  /**
  * createRace()
  * 
  * <p>The method 'createRace' creates a new race by adding a new race to the arraylist with a name
  * and description of the race inputted by the user. The race is automatically given a unique
  * raceId by using the 'getNextRaceId' method.
  *
  * @param name        Race's name.
  * @param description Race's description (can be null).
  * @throws IllegalNameException If the name already exists in the platform.
  * @throws InvalidNameException If the name is null, empty, has more than 30
  *                              characters, or has white spaces.
  * @return the unique ID of the created race.
  */

  @Override
  public int createRace(String name, String description) throws IllegalNameException,
       InvalidNameException  {

    boolean validRaceName = true;
    boolean legalRaceName = true;
    int newRaceId;

    newRaceId = getNextRaceId();
    Race newRace = new Race(newRaceId, name, description);

    for (int i = 0;i < races.size();i++)  {
      if (races.get(i).getName() == name)  {
        validRaceName = false;
      } else if ((name == null) || (name == "") || (name.length() > 30) || (name.contains(" "))) {
        legalRaceName = false;
      }
    }
    if (validRaceName == false) {
      throw new InvalidNameException();

    } else if (legalRaceName == false) {
      throw new IllegalNameException();

    } else  {
      races.add(newRaceId, newRace);
      return newRaceId;
    }
  }

  /**
  * viewRaceDetails()
   *
   * <p>The method removes the race and all its related information, i.e., stages,
   * segments, and results. * The method 'viewRaceDetails' 
   * 
   * @param raceId The ID of the race being queried.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   * @return Any formatted string containing the race ID, name, description, the
   *         number of stages, and the total length (i.e., the sum of all stages'
   *         length).
   */

  @Override
  public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

    boolean validRaceId = false;
    int numberOfStages = 0;
    double length = 0.0;
    Stage nextStage;

    //System.out.println("RaceID = " + raceId);
    //System.out.println("RaceName = " + Races.get(raceId).getName());

    for (int i = 0;i < races.size(); i++) {
      if (races.get(i).getRaceId() == raceId) {
        validRaceId = true;
      }
    }
    if (validRaceId == false) {
      throw new IDNotRecognisedException();
    }

    for (int i = 0; i < stages.size(); i++) {
      nextStage = stages.get(i);
      //System.out.println("RaceID = " + nextStage.getRaceId());

      if (nextStage.getRaceId() == raceId)  {
        numberOfStages++;
        length = length + nextStage.getLength();
      }
    }

    return races.get(raceId).getName() + " - " + races.get(raceId).getDescription() 
       +  ", number of stages = " + numberOfStages + ", length = " + length;

  }
  
  /**
   * removeRaceById()
   * 
   * <p>The 'removeRaceById' method removes the specified raceID from the 'Race' arraylist 
   * which I created earlier.
   * 
   * @param raceId The ID of the race being queried.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   */

  @Override
  public void removeRaceById(int raceId) throws IDNotRecognisedException {

    boolean validRaceId = false;

    for (int i = 0;i < races.size(); i++) {
      if (races.get(i).getRaceId() == raceId) {
        validRaceId = true;
        races.remove(i);
      }
    }
    if (validRaceId == false) {
      throw new IDNotRecognisedException();
    }
  }

  /**
   * getNumberOfStages()
   * 
   * <p>The 'getNumberOfStages' method gets the number of stages in the specified race by entering
   *  the raceID. It sets the default values for the 'numberOfStages' to 0 and also sets the type 
   * for nextStage as 'Stage'. It iterates through the stage arraylist and gets the stages with  
   * the specified raceID and returns the number of stages.
   *
   * @param raceId The ID of the race being queried.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   * @return The number of stages created for the race.
   */

  @Override
  public int getNumberOfStages(int raceId) throws IDNotRecognisedException  {

    boolean validRaceId = false;
    int numberOfStages = 0;
    Stage nextStage;

    for (int i = 0; i < stages.size(); i++)  {
      nextStage = stages.get(i);
      //System.out.println("StageID = " + nextStage.getStageId());
      if (nextStage.getRaceId() == raceId) {
        validRaceId = true;
        numberOfStages++;
      }
    }
    if (validRaceId == false) {
      throw new IDNotRecognisedException();
    
    } else {
      return numberOfStages;
    }
  }

  /**
   * addStageToRace()
   * 
   * <p>The 'addStageToRace' method adds a new stage to the specified race. The 
   * user inputs the raceId, stageName, description, length, startTime and the 
   * type of stage it is. After the information is inputted then the new stage is 
   * added to the 'Stage' arraylist
   * 
   * @param raceId      The race which the stage will be added to.
   * @param stageName   An identifier name for the stage.
   * @param description A descriptive text for the stage.
   * @param length      Stage length in kilometres.
   * @param startTime   The date and time in which the stage will be raced. It
   *                    cannot be null.
   * @param type        The type of the stage. This is used to determine the
   *                    amount of points given to the winner.
   * @return the unique ID of the stage.
   */

  @Override
  public int addStageToRace(int raceId, String stageName, String description, double length, 
      LocalDateTime startTime, StageType type)
      throws IDNotRecognisedException, IllegalNameException, InvalidNameException, 
      InvalidLengthException  {

    boolean validRaceId = true;
    boolean legalName = true;
    boolean validName = true;
    boolean validLength = true;
    int newStageId;

    for (int i = 0; i < stages.size(); i++)  {
      if (stages.get(i).getStageName() == stageName)  {
        validName = false;
      
      } else if ((stageName == null) || (stageName == "") || (stageName.length() > 30)
          || (stageName.contains(" "))) {
        legalName = false;
      }
    }

    for (int i = 0;i < races.size(); i++)  {
      if (races.get(i).getRaceId() == raceId) {
        validRaceId = true;
      }
    }
    if (validRaceId == false) {
      throw new IDNotRecognisedException();
    }
    if (length < 5.0) {
      validLength = false;
    }
    if (validName == false) {
      throw new InvalidNameException();
    }
    if (legalName == false) {
      throw new IllegalNameException();
    }
    if (validLength == false) {
      throw new InvalidLengthException();
    } else {
      newStageId = getNextStageId();
      //System.out.println("New stageId = " + newStageId);
      Stage newStage = new Stage(raceId, newStageId, stageName, description, length, startTime, 
          type);
      stages.add(newStageId, newStage);
      return newStageId;
    }
  }

  /**
   * getRaceStages()
   * 
   * <p>The 'getRaceStages' method returns the stageIDs from a specified race. I have set the 
   * default values at the
   * beginning of the method and also what type nextStage is. I first count the number of stages 
   * in the specified race then collects the stageIDs from the soecified race and returns the
   * stageIDs.
   * 
   * @param raceId The ID of the race being queried.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   * @return An array of stage IDs ordered (from first to last) by their sequence in the
   *         race or an empty array if none exists.
   */

  @Override
  public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

    boolean validRaceId = false;
    int numberOfStages = 0;
    int j = 0;
    Stage nextStage;
    int[] stageIds = null;

    /*
     * Count the number of stages in this race.
     */

    for (int i = 0; i < stages.size(); i++) {
      nextStage = stages.get(i);
      if (nextStage.getRaceId() == raceId) {
        validRaceId = true;
        numberOfStages++;
      }
    }
    if (validRaceId == false) {
      throw new IDNotRecognisedException();
    }
    /*
     * Collect the stages of this race
     */

    stageIds = new int[numberOfStages];
    for (int i = 0; i < stages.size(); i++) {
      nextStage = stages.get(i);
      if (nextStage.getRaceId() == raceId) {
        stageIds[j] = nextStage.getStageId();
        j++;
      }
    }
    return stageIds;
  }

  /**
   * getStageLenght()
   *
   * <p>The 'getStageLength' method gets the length of the stage from the user inputting
   * the stageID and returns the length of the stage.
   * 
   * @param stageId TThe ID of the stage being queried.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */

  @Override
  public double getStageLength(int stageId) throws IDNotRecognisedException {

    boolean validStageId = false;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) { 
        validStageId = true;
      }
    }
    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }
    Stage requestedStage = stages.get(stageId);
    //System.out.println(requestedStage.getLength());
    return requestedStage.getLength();
  }

  /**
   * removeStageById()
   * 
   * <p>The 'removeStageById' method removes the specified stageID from the 'Stage' arraylist 
   * which I created earlier.
   * 
   * @param stageId The ID of the stage being removed.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */

  @Override
  public void removeStageById(int stageId) throws IDNotRecognisedException {

    boolean validStageId = false;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        validStageId = true;
      }
    }
    if (validStageId == false) {
      throw new IDNotRecognisedException();
    } else {
      stages.remove(stageId);
    }
  }

  /**
   * addCategorizedClimbToStage()
   * 
   * <p>The 'addCategorizedClimbToStage' method adds a categorized climb to a specificied stage
   * by inputting the stageID, location, type of segment, average gradient and the length of 
   * the stage and returns the segmentID.
   * 
   * @param stageId         The ID of the stage to which the climb segment is
   *                        being added.
   * @param location        The kilometre location where the climb finishes within
   *                        the stage.
   * @param type            The category of the climb - {@link SegmentType#C4},
   *                        {@link SegmentType#C3}, {@link SegmentType#C2},
   *                        {@link SegmentType#C1}, or {@link SegmentType#HC}.
   * @param averageGradient The average gradient for the climb.
   * @param length          The length of the climb in kilometre.
   * @return The ID of the segment created.
   * @throws IDNotRecognisedException   If the ID does not match to any stage in
   *                                    the system.
   * @throws InvalidLocationException   If the location is out of bounds of the
   *                                    stage length.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   * @throws InvalidStageTypeException  Time-trial stages cannot contain any
   *                                    segment.
   */

  @Override
  public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, 
      Double averageGradient, Double length) throws IDNotRecognisedException, 
      InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
  
    boolean validStageId = false;
    boolean validStageState = false;
    boolean validStageType = false;
    int indexOfStage = 0;
    Double lengthOfStage = 0.0;
    StageType typeOfStage = StageType.TT;
    StageState stateOfStage = StageState.WAITING_FOR_RESULTS;
    int newSegmentId = -1;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        validStageId = true;
        indexOfStage = i;
        lengthOfStage = stages.get(i).getLength();
        stateOfStage = stages.get(i).getState();
        typeOfStage = stages.get(i).getType();

        // Debugging code 
        //System.out.println("Type of stage: " + typeOfStage);
      }
    }
    if (typeOfStage != StageType.TT) {
      validStageType = true;
    }

    if (stateOfStage == StageState.PREPARATION_STATE) {
      validStageState = true;
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }

    if (lengthOfStage < location) {
      throw new InvalidLocationException();
    }

    if (validStageType == false) {
      throw new InvalidStageTypeException();
    }

    if (validStageState == false) {
      throw new InvalidStageStateException();
    }

    if (validStageId && validStageState && validStageType) {
      newSegmentId = getNextSegmentId();
      Segment newSegment = new Segment(newSegmentId, stageId, location, type, averageGradient, 
          length);
      segments.add(newSegmentId, newSegment);

      /** 
       Debugging code 
       System.out.println("New segment type: " + newSegment.getType());
       System.out.println(Segments);

       for (int i = 0; i < Segments.size(); i++) {
          System.out.print("Add segment >> ");
          System.out.print(Segments.get(i).getSegmentId());
          System.out.print("   ");
          System.out.print(Segments.get(i).getStageId());
          System.out.print("   ");
          System.out.print(Segments.get(i).getLocation());
          System.out.print("   ");
          System.out.print(Segments.get(i).getType());
          System.out.print("   ");
          System.out.print(Segments.get(i).getAverageGradient());
          System.out.print("   ");
          System.out.print(Segments.get(i).getLength());
          System.out.println("   ");
        }
      */
    }
    return newSegmentId;
  }

  /**
   * addIntermediateSprintToStage()
   * 
   * <p>The 'addIntermediateSprintToStage' method adds a intermediate sprint to a specificied stage
   * by inputting the stageID and the location then returns the segmentID.
   * 
   * @param stageId  The ID of the stage to which the intermediate sprint segment
   *                 is being added.
   * @param location The kilometre location where the intermediate sprint finishes
   *                 within the stage.
   * @return The ID of the segment created.
   * @throws IDNotRecognisedException   If the ID does not match to any stage in
   *                                    the system.
   * @throws InvalidLocationException   If the location is out of bounds of the
   *                                    stage length.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   * @throws InvalidStageTypeException  Time-trial stages cannot contain any
   *                                    segment.
   */

  @Override
  public int addIntermediateSprintToStage(int stageId, double location) throws 
      IDNotRecognisedException,InvalidLocationException, InvalidStageStateException,
      InvalidStageTypeException {

    boolean validStageId = true;
    boolean validStageState = true;
    boolean validStageType = true;
    int indexOfStage = 0;
    Double lengthOfStage = 0.0;
    StageState stateOfStage = StageState.WAITING_FOR_RESULTS;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        validStageId = true;
        indexOfStage = i;
        lengthOfStage = stages.get(i).getLength();
        stateOfStage = stages.get(i).getState();
      }
    }

    if (stages.get(indexOfStage).getType() != StageType.TT) {
      validStageType = true;
    }

    if (stateOfStage == StageState.PREPARATION_STATE) {
      validStageState = true;
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }

    if (lengthOfStage < location) {
      throw new InvalidLocationException();
    }

    if (validStageType == false) {
      throw new InvalidStageTypeException();
    }

    if (validStageState == false) {
      throw new InvalidStageStateException();
    }

    int newSegmentId;
    SegmentType type = SPRINT;
    newSegmentId = getNextSegmentId();
    Segment newSegment = new Segment(newSegmentId, stageId, location, type);
    segments.add(newSegmentId, newSegment);
    return newSegmentId;
  }

  /**
   * removeSegment()
   * 
   * <p>The 'removeSegment' method removes the specified segmentID from the 'Segment' arraylist 
   * which I created earlier.
   * 
   * @param segmentId The ID of the stage to be concluded.
   * @throws IDNotRecognisedException   If the ID does not match to any stage in
   *                                    the system.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   */

  @Override
  public void removeSegment(int segmentId) throws IDNotRecognisedException, 
      InvalidStageStateException  {

    boolean validSegmentId = false;
    boolean validStageState = true;

    for (int i = 0; i < segments.size(); i++) {
      if (segments.get(i).getSegmentId() == segmentId) {
        validSegmentId = true;
      }
      int stageId = segments.get(i).getStageId();
      if (stages.get(stageId).getState() == StageState.WAITING_FOR_RESULTS) {
        validStageState = false;
      }
    }
    if (validSegmentId == false) {
      throw new IDNotRecognisedException();
    }

    if (validStageState == false) {
      throw new InvalidStageStateException();
    } else {
      segments.remove(segmentId);
    }
  }

  /**
   * concludeStagePreparation()
   * 
   * <p>The 'concludeStagePreparation' method changes the state of the stage from 
   * 'PREPARATION_STATE' to 'WAITING_FOR_RESULTS'.
   * 
   * @param stageId The ID of the stage being queried.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */

  @Override
  public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, 
      InvalidStageStateException {

    boolean validStageId = false;
    boolean validStageState = true;
    Stage requiredStage;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        validStageId = true;
      } else if (stages.get(i).getState() == StageState.WAITING_FOR_RESULTS) {
        validStageState = false;
      }
    }
    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }
    if (validStageState == false) {
      throw new InvalidStageStateException();
    } else {
      requiredStage = stages.get(stageId);
      requiredStage.setState(StageState.WAITING_FOR_RESULTS);
      stages.add(stageId, requiredStage);
    }
  }

  /**
   * getStageSegments()
   * 
   * <p>The 'getStageSegments' method returns the segmentIDs from a specified stage. I have 
   * set the default values at the beginning of the method and also what type nextSegment is. 
   * I first count the number of segments in the specified stage then collects the segmentIDs 
   * from the soecified stage and returns the segmentIDs.
   * 
   * @param stageId The ID of the stage being queried.
   * @return The list of segment IDs ordered (from first to last) by their location in the
   *         stage.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */

  @Override
  public int[] getStageSegments(int stageId) throws IDNotRecognisedException {

    boolean validStageId = false;
    int numberOfSegments = 0;
    int j = 0;
    Segment nextSegment;
    int[] segmentIds = null;

    /*
     * Count the number of segments in this stage
     */

    for (int i = 0; i < segments.size(); i++) {
      nextSegment = segments.get(i);
      if (nextSegment.getStageId() == stageId) {
        validStageId = true;
        numberOfSegments++;
      }
    }

    /*
     * Collect the segments in the race
     */

    segmentIds = new int[numberOfSegments];
    for (int i = 0; i < segments.size(); i++) {
      nextSegment = segments.get(i);
      if (nextSegment.getStageId() == stageId) {
        validStageId = true;
        segmentIds[j] = nextSegment.getSegmentId();
        j++;
      }
    }
    if (validStageId == false) {
      throw new IDNotRecognisedException();
    } else {
      return segmentIds;
    }
  }

  /**
   * createTeam()
   * 
   * <p>The 'createTeam' method creates a new team. The user inputs the team name and the 
   * description and is automatically assigned a ID as it is added to the 'Team' arraylist
   * then returns the team's ID.
   *
   * @param name        The identifier name of the team.
   * @param description A description of the team.
   * @return The ID of the created team.
   * @throws IllegalNameException If the name already exists in the platform.
   * @throws InvalidNameException If the name is null, empty, has more than 30
   *                              characters, or has white spaces.
   */

  @Override
  public int createTeam(String name, String description) throws IllegalNameException, 
      InvalidNameException {

    boolean validName = true;
    boolean legalName = true;
    int newTeamId;

    for (int i = 0; i < teams.size(); i++) {
      if (teams.get(i).getName() == name) {
        validName = false;
      } else if ((name == null) || (name == "") || (name.length() > 30) || (name.contains(" "))) {
        legalName = false;
      }
    }

    if (validName == false) {
      throw new InvalidNameException();
    } else if (legalName == false) {
      throw new IllegalNameException();
    } else { 
      newTeamId = getNextTeamId();
      Team newTeam = new Team(newTeamId, name, description);
      teams.add(newTeamId, newTeam);
      return newTeamId;
    }
  }

  /**
   * removeTeam()
   * 
   * <p>The 'removeTeam' method removes the specified teamID from the 'Team' arraylist 
   * which I created earlier.
   * 
   * @param teamId The ID of the rider to be removed.
   * @throws IDNotRecognisedException If the ID does not match to any rider in the
   *                                  system.
   */

  @Override
  public void removeTeam(int teamId) throws IDNotRecognisedException {
    boolean validTeamId = false;

    for (int i = 0; i < teams.size(); i++) {
      if (teams.get(i).getTeamId() == teamId) {
        validTeamId = true;
      }
    }
    if (validTeamId == false) {
      throw new IDNotRecognisedException();
    } else {
      teams.remove(teamId);
    }
  }

  /**
   * getTeams()
   * 
   * <p>The method 'getTeams' gets the teamIDs by using the method I created earlier to
   * output all the teamIDs that are currently assigned to a position in the
   * arraylist.
   * 
   * @return A list with team's ID.
   * @throws IDNotRecognisedException If the ID does not match to any team in the
   *                                  system.
   */

  @Override
  public int[] getTeams() {

    Team nextTeam;
    int[] teamIds = new int[teams.size()];

    for (int i = 0; i < teams.size(); i++) {
      nextTeam = teams.get(i);
      //System.out.println("RaceID = " + nextRace.getRaceId());
      teamIds[i] = nextTeam.getTeamId();
    }
    return teamIds;
  }

  /**
   * getTeamRiders()
   * 
   * <p>The 'getTeamRiders' method returns the riderIDs from a specified team. I have set the 
   * default values at the beginning of the method and also what type nextRider is. I first 
   * count the number of riders in the specified team then collects the riderIDs from the 
   * specified team and returns the riderIDs.
   * 
   * @param teamId The ID of the team being queried.
   * @return A list with riders' ID.
   * @throws IDNotRecognisedException If the ID does not match to any team in the
   *                                  system.
   */


  @Override
  public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {

    boolean validTeamId = false;
    int numberOfRiders = 0;
    int j = 0;
    Rider nextRider;
    int[] riderIds = null;

    /*
     * Count the number of riders in this team
     */

    for (int i = 0; i < riders.size(); i++) {
      nextRider = riders.get(i);
      if (nextRider.getTeamId() == teamId) {
        validTeamId = true;
        numberOfRiders++;
      }
    }

    /*
     * Collect the riders of this team
     */

    riderIds = new int[numberOfRiders];
    for (int i = 0; i < riders.size(); i++) {
      nextRider = riders.get(i);
      if (nextRider.getTeamId() == teamId) {
        riderIds[j] = nextRider.getRiderId();
        j++;
      }
    }
    if (validTeamId == false) {
      throw new IDNotRecognisedException();
    } else {
      return riderIds;
    }
  }

  /**
   * createRider()
   * 
   * <p>The 'createRider' method creates a new rider and adds the rider to the team
   * specified. The user inputs the teamID, rider name and also the rider's yearOfBirth,
   * then returns the rider's ID
   * 
   * @param teamId      The ID rider's team.
   * @param name        The name of the rider.
   * @param yearOfBirth The year of birth of the rider.
   * @return The ID of the rider in the system.
   * @throws IDNotRecognisedException If the ID does not match to any team in the
   *                                  system.
   * @throws IllegalArgumentException If the name of the rider is null or empty,
   *                                  or the year of birth is less than 1900.
   */

  @Override
  public int createRider(int teamId, String name, int yearOfBirth)
      throws IDNotRecognisedException, IllegalArgumentException {

    boolean validTeamId = false;
    boolean legalArg = true;
    int newRiderId;

    for (int i = 0; i < teams.size(); i++) {
      if (teams.get(i).getTeamId() == teamId) {
        validTeamId = true;
      } else if ((name == null) || (yearOfBirth < 1900)) {
        legalArg = false;
      }
    }
    if (validTeamId == false) {
      throw new IDNotRecognisedException();
    } else if (legalArg = false) {
      throw new IllegalArgumentException();
    } else {
      newRiderId = getNextRiderId();
      Rider newRider = new Rider(newRiderId, teamId, name, yearOfBirth);
      riders.add(newRiderId, newRider);
      return newRiderId;
    }
  }

  /**
   * removeRider()
   * 
   * <p>The 'removeRider' method removes the specified riderID from the 'Rider' arraylist 
   * which I created earlier.
   * 
   * @param riderId     The ID of the rider.
   * @throws IDNotRecognisedException    If the ID does not match to any rider or
   *                                     stage in the system.
   */

  @Override
  public void removeRider(int riderId) throws IDNotRecognisedException {

    boolean validRiderId = false;

    for (int i = 0; i < riders.size(); i++) {
      if (riders.get(i).getTeamId() == riderId) {
        validRiderId = true;
      }
    }
    if (validRiderId == false) {
      throw new IDNotRecognisedException();
    } else {
      riders.remove(riderId);
    }
  }

  /**
   * registerRiderResultsInStage()
   * 
   * <p>Record the times of a rider in a stage.
   *
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId     The ID of the stage the result refers to.
   * @param riderId     The ID of the rider.
   * @param checkpoints An array of times at which the rider reached each of the
   *                    segments of the stage, including the start time and the
   *                    finish line.
   * @throws IDNotRecognisedException    If the ID does not match to any rider or
   *                                     stage in the system.
   * @throws DuplicatedResultException   Thrown if the rider has already a result
   *                                     for the stage. Each rider can have only
   *                                     one result per stage.
   * @throws InvalidCheckpointsException Thrown if the length of checkpoints is
   *                                     not equal to n+2, where n is the number
   *                                     of segments in the stage; +2 represents
   *                                     the start time and the finish time of the
   *                                     stage.
   * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
   *                                     results". Results can only be added to a
   *                                     stage while it is "waiting for results".
   */

  @Override
  public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
      throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
      InvalidStageStateException {
    boolean validStageId = false;
    boolean validRiderId = false;
    boolean duplicateResult = false;
    boolean validCheckpoints = true;
    boolean validStageState = true;
    int numberOfSegments = 0;
    Segment nextSegment;

    for (int i = 0; i < segments.size(); i++) {
      nextSegment = segments.get(i);
      if (nextSegment.getStageId() == stageId) {
        //System.out.println("nextSegment.getStageId() = " + nextSegment.getStageId() + 
        //", stageId = " + stageId);
        numberOfSegments++;
        //System.out.println("numberOfSegments = " + numberOfSegments);
        validStageId = true;
      }
    }

    for (int j = 0; j < riders.size(); j++) {
      if (riders.get(j).getRiderId() == riderId) {
        validRiderId = true;
      }
    }

    for (int i = 0; i < results.size(); i++) {
      if ((results.get(i).getRiderId() == riderId) && (results.get(i).getStageId() == stageId)) {
        duplicateResult = true;
      }
    }

    if (checkpoints.length != numberOfSegments + 2) {
      validCheckpoints = false;
    }

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        if (stages.get(i).getState() != StageState.WAITING_FOR_RESULTS) {
          validStageState = false;
        }
      }
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }

    if (validRiderId == false) {
      throw new IDNotRecognisedException();
    }

    if (duplicateResult == true) {
      throw new DuplicatedResultException();
    }

    if (validCheckpoints == false) {
      throw new InvalidCheckpointsException();
    }

    if (validStageState == false) {
      throw new InvalidStageStateException();
    } else {
      results.add(new Result(stageId, riderId, checkpoints));
    }
  }

  /**
   * getRiderResultsInStage()
   * 
   * <p>Get the times of a rider in a stage.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage the result refers to.
   * @param riderId The ID of the rider.
   * @return The array of times at which the rider reached each of the segments of
   *         the stage and the total elapsed time. The elapsed time is the
   *         difference between the finish time and the start time. Return an
   *         empty array if there is no result registered for the rider in the
   *         stage.
   * @throws IDNotRecognisedException If the ID does not match to any rider or
   *                                  stage in the system.
   */

  @Override
  public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws 
      IDNotRecognisedException {
    boolean validStageId = false;
    boolean validRiderId = false;
    int arraySize = 0;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        validStageId = true;
      }

      for (int j = 0; j < riders.size(); j++) {
        if (riders.get(j).getRiderId() == riderId) {
          validRiderId = true;
        }
      }
    }

    for (int i = 0; i < results.size(); i++) {
      if ((results.get(i).getRiderId() == riderId) && (results.get(i).getStageId() == stageId)) {
        arraySize = results.get(i).getCheckpoints().length;
      }
    }

    LocalTime[] riderResultsTimes = new LocalTime[arraySize + 1 ];

    for (int i = 0; i < results.size(); i++) {
      if ((results.get(i).getRiderId() == riderId) && (results.get(i).getStageId() == stageId)) {
        int j = 0;

        for (LocalTime t:results.get(i).getCheckpoints()) {
          riderResultsTimes[j] = t;
          j++;
        }
      }
    }

    //System.out.println("elapsed Time: " + elapsedTime(0,3));
    riderResultsTimes[riderResultsTimes.length - 1] = elapsedTime(stageId, riderId);

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }

    if (validRiderId == false) {
      throw new IDNotRecognisedException();
    } else {
      return riderResultsTimes;
    }
  }

  /**
   * getRiderAdjustedElapsedTimeInStage()
   * 
   * <p>For the general classification, the aggregated time is based on the adjusted
   * elapsed time, not the real elapsed time. Adjustments are made to take into
   * account groups of riders finishing very close together, e.g., the peloton. If
   * a rider has a finishing time less than one second slower than the
   * previous rider, then their adjusted elapsed time is the smallest of both. For
   * instance, a stage with 200 riders finishing "together" (i.e., less than 1
   * second between consecutive riders), the adjusted elapsed time of all riders
   * should be the same as the first of all these riders, even if the real gap
   * between the 200th and the 1st rider is much bigger than 1 second. There is no
   * adjustments on elapsed time on time-trials.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage the result refers to.
   * @param riderId The ID of the rider.
   * @return The adjusted elapsed time for the rider in the stage. Return null if 
   *         there is no result registered for the rider in the stage.
   * @throws IDNotRecognisedException   If the ID does not match to any rider or
   *                                    stage in the system.
   */

  @Override
  public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) 
      throws IDNotRecognisedException {

    boolean validStageId = false;
    boolean validRiderId = false;

    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getStageId() == stageId) {
        validStageId = true;
      }
      for (int j = 0; j < riders.size(); j++) {
        if (riders.get(j).getRiderId() == riderId) {
          validRiderId = true;
        }
      }
    }

    LocalTime adjustedElapsedTime = findAdjustedElapsedTime(stageId, riderId);

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }

    if (validRiderId == false) {
      throw new IDNotRecognisedException();
    }
    return adjustedElapsedTime;
  }

  /**
   * deleteRiderResultsInStage()
   * 
   * <p>Removes the stage results from the rider.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage the result refers to.
   * @param riderId The ID of the rider.
   * @throws IDNotRecognisedException If the ID does not match to any rider or
   *                                  stage in the system.
   */

  @Override
  public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

    boolean validRiderId = false;

    for (int j = 0; j < riders.size(); j++) {
      if (riders.get(j).getRiderId() == riderId) {
        validRiderId = true;
      }
    }

    for (int i = 0; i < results.size(); i++) {
      if ((results.get(i).getRiderId() == riderId) && (results.get(i).getStageId() == stageId)) {
        results.remove(i);
      }
    }

    if (validRiderId == false) {
      throw new IDNotRecognisedException();
    }
  }

  /**
   * getRidersRankInStage()
   * 
   * <p>Get the riders finished position in a a stage.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage being queried.
   * @return A list of riders ID sorted by their elapsed time. An empty list if
   *         there is no result for the stage.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */

  @Override
  public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {

    boolean validStageId = false;
    int arraySize = 0;
    int counter = 0;

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        validStageId = true;
        arraySize++;
      } 
    }

    int[] ranking = new int[arraySize];
    LocalTime[] times = new LocalTime[arraySize];

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        ranking[counter] = results.get(i).getRiderId();
        times[counter] = elapsedTime(results.get(i).getRiderId(), stageId);
        counter++;
      }
    }

    int n = times.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (times[j].compareTo(times[j + 1]) > 0) {
                    ;
        }
        {
          LocalTime currentTime = times[j];
          times[j] = times[j + 1];
          times[j + 1] = currentTime;

          int currentRank = ranking[j];
          ranking[j] = ranking[j + 1];
          ranking[j + 1] = currentRank;
        }
      }
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }
    return ranking;
  }

  /**
   * getRankedAdjustedElapsedTimesInStage()
   * 
   * <p>Get the number of points obtained by each rider in a stage.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage being queried.
   * @return The ranked list of points each riders received in the stage, sorted
   *         by their elapsed time. An empty list if there is no result for the
   *         stage. These points should match the riders returned by
   *         {@link #getRidersRankInStage(int)}.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */

  @Override
  public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) 
      throws IDNotRecognisedException {
    boolean validStageId = false;

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        validStageId = true;
      }
    }

    int[] ranking = getRidersRankInStage(stageId);
    LocalTime[] times = new LocalTime[ranking.length];

    if (ranking.length > 0) {
      for (int i = 0; i < ranking.length; i++) {
        times[i] = findAdjustedElapsedTime(stageId, ranking[i]);
      }

      for (int i = ranking.length - 1; i <= 1; i--) {
        if (times[i - 1].until(times[i],ChronoUnit.MILLIS) < 1000) {
          ranking[i] = ranking[i - 1];
        }
      }
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }
    return times;
  }

  /**
   * getRidersPointsInStage()
   * 
   * <p>Get the number of points obtained by each rider in a stage.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage being queried.
   * @return The ranked list of mountain points each riders received in the stage,
   *         sorted by their finish time. An empty list if there is no result for
   *         the stage. These points should match the riders returned by
   *         {@link #getRidersRankInStage(int)}.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */

  @Override
  public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {

    boolean validStageId = false;
    int arraySize = 0;

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        validStageId = true;
        arraySize++;
      }
    }

    arraySize = getRidersRankInStage(stageId).length;
    int[] pts = new int[arraySize];
    StageType type = stages.get(stageId).getType();

    for (int i = 0; i < arraySize; i++) {
      if (i < 15) {
        switch (type) {
          case FLAT:
            pts[i] = ptsTable[0][i];
            break;

          case MEDIUM_MOUNTAIN:
            pts[i] = ptsTable[1][i];
            break;

          case HIGH_MOUNTAIN:
            pts[i] = ptsTable[2][i];
            break;
 
          case TT:
            pts[i] = ptsTable[3][i];
            break;

          case INTERMEDIATE_SPRINT:
            pts[i] = ptsTable[4][i];
            break;
          default:
            break;
        }
      } else {
        pts[i] = 0;
      }
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }
    return pts;
  }

  /**
   * getRidersMountainPointsInStage()
   * 
   * <p>Get the number of mountain points obtained by each rider in a stage.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   * 
   * @param stageId The ID of the stage being queried.
   * @return The ranked list of mountain points each riders received in the stage,
   *         sorted by their finish time. An empty list if there is no result for
   *         the stage. These points should match the riders returned by
   *         {@link #getRidersRankInStage(int)}.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */

  @Override
  public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
    boolean validStageId = false;
    int index = 0;
    int mtnIndex = -1;
    SegmentType mtnType = SegmentType.SPRINT;

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        validStageId = true;
      }
    }

    //for (int i = 0; i < segments.size(); i++) {

    // Debugging code 

    /** 
    System.out.print("Add segment >> ");
    System.out.print(Segments.get(i).getSegmentId());
    System.out.print("   ");
    System.out.print(Segments.get(i).getStageId());
    System.out.print("   ");
    System.out.print(Segments.get(i).getLocation());
    System.out.print("   ");
    System.out.print(Segments.get(i).getType());
    System.out.print("   ");
    System.out.print(Segments.get(i).getAverageGradient());
    System.out.print("   ");
    System.out.print(Segments.get(i).getLength());
    System.out.print("   ");
    System.out.println("   ");
    */

    // }

    // Finding the index to the mountain times
    for (int i = 0; i < segments.size(); i++) {
      if (segments.get(i).getStageId() == stageId) {

        // Debugging code 
        /** 
         * System.out.println("Looking for stage segments: ");
         * System.out.println("value of i: " + i);
         * System.out.println("Segment Id: " + Segments.get(i).getSegmentId());
         * System.out.println("Stage Id: " + Segments.get(i).getStageId());
         * System.out.println("length of segment: " + Segments.get(i).getLength());
         * System.out.println("Looking for segment location: " + Segments.get(i).getLocation());
         * System.out.println("Looking for average gradient: " + 
         * Segments.get(i).getAverageGradient());
         */
        //System.out.println("SegmentId: " + segmentId);
        SegmentType segmentType = segments.get(i).getType();
        //System.out.println("SegmentType: " + segmentType);

        if ((segmentType == SegmentType.C1) || (segmentType == SegmentType.C2) 
             || (segmentType == SegmentType.C3) || (segmentType == SegmentType.C4) 
             || (segmentType == SegmentType.HC)) {
          //System.out.println("mtnIndex found: ");
          mtnIndex = index;
          mtnType = segmentType;
        }
        index++;
      }
    }

    //Debugging code 
    /** 
     * System.out.println("Mtn_index: " + mtn_index);
     * System.out.println("mtnType: " + mtnType);
     * for (int i = 0; i < Segments.size(); i++) {
     * System.out.print("Add segment >> ");
     * System.out.print(Segments.get(i).getSegmentId());
     * System.out.print("   ");
     * System.out.print(Segments.get(i).getStageId());
     * System.out.print("   ");
     * System.out.print(Segments.get(i).getLocation());
     * System.out.print("   ");
     * System.out.print(Segments.get(i).getType());
     * System.out.print("   ");
     * System.out.print(Segments.get(i).getAverageGradient());
     * System.out.print("   ");
     * System.out.print(Segments.get(i).getLength());
     * System.out.println("   ");
     * }
     */

    // Count the number of riders in this stage

    int numOfRiders = 0;
    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        numOfRiders++;
      }
    }

    // Debugging code 

    //System.out.println("Number of riders: " + numOfRiders);

    int[] mtnRiders = new int[numOfRiders];
    int[] mtnPoints =  new int[numOfRiders];
    LocalTime[] mtnStageTimes = new LocalTime[numOfRiders];

    // Getting the riders and their mountain stage times

    index = 0;

    for (int i = 0; i < results.size(); i++) {
      if (results.get(i).getStageId() == stageId) {
        mtnRiders[index] = results.get(i).getRiderId();
        if (mtnIndex >= 0) {
          mtnStageTimes[index] = results.get(i).getCheckpoints()[mtnIndex];
        } else {
          mtnStageTimes[index] = LocalTime.of(0, 0, 0, 0);
        }
        index++;
      }
    }

    // Sort by mountain finish times

    for (int i = 0; i < numOfRiders; i++) {
      for (int j = 0; j < numOfRiders - i - 1; j++) {
        if (mtnStageTimes[j].compareTo(mtnStageTimes[j + 1]) > 0) {
                    ;
        }
        {
          LocalTime currentTime = mtnStageTimes[j];
          mtnStageTimes[j] = mtnStageTimes[j + 1];
          mtnStageTimes[j + 1] = currentTime;

          int currentRank = mtnRiders[j];
          mtnRiders[j] = mtnRiders[j + 1];
          mtnRiders[j + 1] = currentRank;
        }
      }
    }

    // Get mountain mtnPoints

    int[] rankedRiderIds = getRidersRankInStage(stageId);
    int currentRiderId;

    for (int i = 0; i < numOfRiders; i++) {
      currentRiderId = rankedRiderIds[i];

      for (int j = 0; j < numOfRiders; j++) {
        if (mtnRiders[j] == currentRiderId) {
          index = j;

          if (mtnIndex >= 0) {
            if (index < 8) {
              switch (mtnType) {
                case C1:
                  mtnPoints[i] = mtnPtsTable[4][index];
                  break;

                case C2:
                  mtnPoints[i] = mtnPtsTable[3][index];
                  break;

                case C3:
                  mtnPoints[i] = mtnPtsTable[2][index];
                  break;

                case C4:
                  mtnPoints[i] = mtnPtsTable[1][index];
                  break;

                case HC:
                  mtnPoints[i] = mtnPtsTable[5][index];
                  break;

                default:
                  mtnPoints[i] = mtnPtsTable[0][index];
                  break;
              }
            } else {
              mtnPoints[i] = 0;
            }
          } else {
            mtnPoints[i] = 0;
          }
        }
      }
    }

    if (validStageId == false) {
      throw new IDNotRecognisedException();
    }

    return mtnPoints;
  }

  /**
   * eraseCyclingPortal()
   * 
   * <p>Method empties this MiniCyclingPortalInterface of its contents and resets all
   * internal counters.
   */

  @Override
  public void eraseCyclingPortal() {
    races.clear();
    results.clear();
    riders.clear();
    segments.clear();
    stages.clear();
    teams.clear();
  }

  /**
   * saveCyclingPortal()
   * 
   * <p>Method saves this MiniCyclingPortalInterface contents into a serialised file,
   * with the filename given in the argument.
   *
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   *
   * @param filename Location of the file to be saved.
   * @throws IOException If there is a problem experienced when trying to save the
   *                     store contents to the file.
   */

  @Override
  public void saveCyclingPortal(String filename) throws IOException {
    try {
      ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));
      output.writeObject(races);
      output.writeObject(results);
      output.writeObject(riders);
      output.writeObject(segments);
      output.writeObject(stages);
      output.writeObject(teams);
      output.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * loadCyclingPortal()
   * 
   * <p>Method should load and replace this MiniCyclingPortalInterface contents with the
   * serialised contents stored in the file given in the argument.
   * 
   * <p>The state of this MiniCyclingPortalInterface must be unchanged if any
   * exceptions are thrown.
   *
   * @param filename Location of the file to be loaded.
   * @throws IOException            If there is a problem experienced when trying
   *                                to load the store contents from the file.
   * @throws ClassNotFoundException If required class files cannot be found when
   *                                loading.
   */

  @Override
  @SuppressWarnings("unchecked")
  /**
   * The @SuppressWarnings in Java is an annotation that is used to inform the compiler
   * to suppress specified warnings for a certain part of the program.
   */
  public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
    try {
      ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
      races = (ArrayList<Race>) input.readObject();
      riders = (ArrayList<Rider>)input.readObject();
      segments = (ArrayList<Segment>)input.readObject();
      stages = (ArrayList<Stage>)input.readObject();
      teams = (ArrayList<Team>)input.readObject();
      input.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (ClassCastException e) {
      System.out.println(e.getMessage());
    }
  }
}