package cycling;

import java.io.Serializable;

public class Team implements Serializable {
    
  /**
   * Declares the types for the variables.
   */

  private int teamId;
  private String name;
  private String description;

  /**
   * Sets the default values for the variables.
   */

  Team() {
    this.teamId = 0;
    this.name = "";
    this.description = "";
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param teamId The ID of the team being queried.
   * @param name The name of the team.
   * @param description The description of the team.
   */

  Team(int teamId, String name, String description) {
    this.teamId = teamId;
    this.name = name;
    this.description = description;
  }

  /**
   * Getters for the 'Team' variables.
   */

  public int getTeamId() {
    return this.teamId;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  /**
   * Setters for the 'Team' variables.
   */

  public void setTeamId(int teamId) {
    this.teamId = teamId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
