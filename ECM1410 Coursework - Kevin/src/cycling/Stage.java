package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Stage implements Serializable {

  /**
   * Declares the types for the variables.
   */

  private int raceId;
  private int stageId;
  private String stageName;
  private String description;
  private double length;
  private LocalDateTime startTime;
  private StageType type;
  private StageState state;
  public ArrayList<Segment> segments = new ArrayList<>();

  /**
   * Sets the default values for the variables.
   */

  Stage() {
    this.raceId = 0;
    this.stageId = 0;
    this.stageName = "";
    this.description = "";
    this.length = 0.0;
    this.startTime = null;
    this.type = null;
    this.state = StageState.PREPARATION_STATE;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param raceId The ID of the race being queried.
   * @param stageId The ID of the stage being queried.
   * @param stageName The name of the stage.
   * @param description The description of the stage.
   * @param length The length of the stage.
   */

  Stage(int raceId, int stageId, String stageName, String description, double length) {
    this.raceId = raceId;
    this.stageId = stageId;
    this.stageName = stageName;
    this.description = description;
    this.length = length;
    this.startTime = null;
    this.type = null;
    this.state = StageState.PREPARATION_STATE;
  }

  /**
   * Sets the default values for the variables .
   * 
   * @param raceId The ID of the race being queried.
   * @param stageId The ID of the stage being queried.
   * @param stageName The name of the stage.
   * @param description The description of the stage.
   * @param length The length of the stage.
   * @param startTime The start time of the stage.
   * @param type The type of the stage.
   */

  Stage(int raceId, int stageId, String stageName, String description, double length, 
      LocalDateTime startTime, StageType type) {
    this.raceId = raceId;
    this.stageId = stageId;
    this.stageName = stageName;
    this.description = description;
    this.length = length;
    this.startTime = startTime;
    this.type = type;
    this.state = StageState.PREPARATION_STATE;
  }

  /**
   * Getters for the 'Stage' variables.
   */

  public int getRaceId() {
    return this.raceId;
  }

  public int getStageId() {
    return this.stageId;
  }

  public String getStageName() {
    return this.stageName;
  }

  public String getdescription() {
    return this.description;
  }

  public double getLength() {
    return this.length;
  }

  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  public StageType getType() {
    return this.type;
  }

  public StageState getState() {
    return this.state;
  }

  /**
   * Setters for the 'Stage' variables.
   */

  public void setRaceId(int raceId) {
    this.raceId = raceId;
  }

  public void setStageId(int stageId) {
    this.stageId = stageId;
  }
    
  public void setStageName(String stageName) {
    this.stageName = stageName;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public void setType(StageType type) {
    this.type = type;
  }

  public void setState(StageState state) {
    this.state = state;
  }
}
