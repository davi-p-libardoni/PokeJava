package move;

public enum MoveResult {
	SUCCESS,
	MISSED,
	FAILED,
	SUPER,
	NOT_VERY,
	IMMUNE,
	COULDNT_MOVE,
	OHKO;

	public boolean successful() {
		return (this != MISSED && this != FAILED && this != IMMUNE && this != COULDNT_MOVE);
	}
}
