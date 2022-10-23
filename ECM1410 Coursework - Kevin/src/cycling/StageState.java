package cycling;

public enum StageState {
    /**
     * A stage will be in this type during stage preparation.
     */
    PREPARATION_STATE,

    /**
     * A stage will be in this type while it is waiting for results.
     */
    WAITING_FOR_RESULTS,

    /**
     * A stage will be in this type when it has concluded.
     */
    CONCLUDED,
}
