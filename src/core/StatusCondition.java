package core;

public enum StatusCondition {
	PARALYZE,
	BURN,
	FREEZE,
	POISON,
	SLEEP,
	NONE;

	public String toLabel() {
		switch(this) {
		case PARALYZE:
			return "PRLZ";
		case BURN:
			return "BRN";
		case FREEZE:
			return "FRZ";
		case POISON:
			return "PSN";
		case SLEEP:
			return "SLP";
		default:
			return "";
		}
	}
}
