package cycling;

import java.io.Serializable;

public class Rider implements Serializable {
    
  /**
   * Declares the types for the variables.
   */

  private int riderId;
  private int teamId;
  private String name;
  private int yearOfBirth;

  /**
   * Sets the default values for the variables.
   */

  Rider() {
    this.riderId = 0;
    this.teamId = 0;
    this.name = "";
    this.yearOfBirth = 0;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param riderId The ID of the rider being queried.
   * @param teamId The ID of the team being queried.
   * @param name The name of the rider.
   * @param yearOfBirth The year of birth of the rider
   */

  Rider(int riderId, int teamId, String name, int yearOfBirth) {
    this.riderId = riderId;
    this.teamId = teamId;
    this.name = name;
    this.yearOfBirth = 0;
  }

  /**
   * Getters for the 'Rider' variables.
   */

  public int getRiderId() {
    return this.riderId;
  }

  public int getTeamId() {
    return this.teamId;
  }

  public String getName() {
    return this.name;
  }

  public int getYearOfBirth() {
    return this.yearOfBirth;
  }

  /**
   * Setters for the 'Rider' variables.
   */

  public void setRiderId(int riderId) {
    this.riderId = riderId;
  }

  public void setTeamId(int teamId) {
    this.teamId = teamId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }       
}
