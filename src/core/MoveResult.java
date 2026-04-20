package core;

public enum MoveResult {
	SUCCESS,
	MISSED,
	FAILED,
	SUPER,
	NOT_VERY,
	IMMUNE, 
	OHKO;

	public boolean successful() {
		return (this != MISSED && this != FAILED && this != IMMUNE);
	}
}
