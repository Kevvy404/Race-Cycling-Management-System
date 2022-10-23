package cycling;

import java.io.Serializable;

public class Segment implements Serializable {
    
  /**
   * Declares the types for the variables.
   */

  private int stageId;
  private int segementId;
  private double location;
  private SegmentType type;
  private double averageGradient;
  private double length;

  /**
   * Sets the default values for the variables.
   */

  Segment() {
    this.segementId = 0;
    this.stageId = 0;
    this.location = 0.0;
    this.type = null;
    this.averageGradient = 0.0;
    this.length = 0.0;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param segementId The ID of the segment being queried.
   * @param stageId The ID of the stage being queried.
   * @param location The location of the segement.
   */

  Segment(int segementId, int stageId, double location) {
    this.segementId = segementId;
    this.stageId = stageId;
    this.location = location;
    this.type = null;
    this.averageGradient = 0.0;
    this.length = 0.0;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param segementId The ID of the segment being queried.
   * @param stageId The ID of the stage being queried.
   * @param location The location of the segement.
   * @param type The segment type.
   */

  Segment(int segementId, int stageId, double location, SegmentType type) {
    this.segementId = segementId;
    this.stageId = stageId;
    this.location = location;
    this.type = type;
    this.averageGradient = 0.0;
    this.length = 0.0;
  }

  /**
   * Sets the default values for the variables.
   * 
   * @param segementId The ID of the segment being queried.
   * @param stageId The ID of the stage being queried.
   * @param location The location of the segement.
   * @param type The segment type.
   * @param averageGradient The average gradient of the segment.
   * @param length The length of the segment
   */

  Segment(int segementId, int stageId, double location, SegmentType type, double averageGradient,
      double length) {
    this.segementId = segementId;
    this.stageId = stageId;
    this.location = location;
    this.type = type;
    System.out.println("type of segment in constructor: " + type);
    this.averageGradient = averageGradient;
    this.length = length;
  }

  /**
   * Getters for the 'Segment' variables.
   */

  public int getStageId() {
    return this.stageId;
  }

  public int getSegmentId() {
    return this.segementId;
  }

  public double getLocation() {
    return this.location;
  }

  public SegmentType getType() {
    return this.type;
  }

  public double getAverageGradient() {
    return this.averageGradient;
  }

  public double getLength() {
    return this.length;
  }

  /**
   * Setters for the 'Segment' variables.
   */

  public void setStageId(int stageId) {
    this.stageId = stageId;
  }

  public void setSegmentId(int segmentId) {
    this.segementId = segmentId;
  }

  public void setLocation(double location) {
    this.location = location;
  }

  public void setType(SegmentType type) {
    this.type = type;
  }

  public void setAverageGradient(double averageGradient) {
    this.averageGradient = averageGradient;
  }

  public void setLength(double length) {
    this.length = length;
  }
}
