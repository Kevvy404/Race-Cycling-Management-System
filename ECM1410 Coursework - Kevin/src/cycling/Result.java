package cycling;

import java.io.Serializable;

import java.time.LocalTime;

public class Result implements Serializable {

  /**
   * Declares the types for the variables.
   */

  private int stageId;
  private int riderId;
  private LocalTime[] checkpoints;

  /**
   * Sets the default values for the variables.
   */

  Result() {
    this.stageId = 0;
    this.riderId = 0;
    this.checkpoints = null;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param stageId The ID of the stage being queried.
   * @param riderId The ID of the rider being queried.
   * @param checkpoints An array of times at which the rider reached each of the
   *                    segments of the stage, including the start time and the
   *                    finish line.
   */

  Result(int stageId, int riderId, LocalTime... checkpoints) {
    this.stageId = stageId;
    this.riderId = riderId;
    this.checkpoints = checkpoints;
  }

  /**
   * Getters for the 'Result' variables.
   */

  public int getStageId() {
    return this.stageId;
  }

  public int getRiderId() {
    return this.riderId;
  }

  public LocalTime[] getCheckpoints() {
    return this.checkpoints;
  }

  /**
   * Setters for the 'Result' variables.
   */

  public void setStageId(int stageId) {
    this.stageId = stageId;
  }

  public void setRiderId(int riderId) {
    this.riderId = riderId;
  }

  public void setCheckpoints(LocalTime... checkpoints) {
    this.checkpoints = checkpoints;
  }

}
