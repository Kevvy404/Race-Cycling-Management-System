package cycling;

import java.io.Serializable;

public class Race implements Serializable {
    
  /**
   * Declares the types for the variables.
   */

  private int raceId;
  private String name;
  private String description;
  private int numberOfStages;
  private double totalLengthOfRace;
 
  /**
   * Sets the default values for the variables.
   */

  Race() {
    this.raceId = 0;
    this.name = "";
    this.description = "";
    this.numberOfStages = 0;
    this.totalLengthOfRace = 0.0;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param raceId      The ID of the race being queried.
   * @param name        Race's name.
   * @param description Race's description (can be null).
   */

  Race(int raceId, String name, String description) {
    this.raceId = raceId;
    this.name = name;
    this.description = description;
    this.numberOfStages = 0;
    this.totalLengthOfRace = 0.0;
  }

  /**
   * Getters for the 'Race' variables.
   */

  public int getRaceId() {
    return this.raceId;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }
    
  public int getNumberOfStages() {
    return this.numberOfStages;
  }
    
  public double getTotalLengthOfRace() {
    return this.totalLengthOfRace;
  }

  /**
   * Setters for the 'Race' variables.
   */

  public void setRaceId(int raceId) {
    this.raceId = raceId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
