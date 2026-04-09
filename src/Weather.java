
public class Weather {
	private WeatherCondition condition;
	private int duration;
	
	public WeatherCondition getWeather() {
		return this.condition;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void decrement() {
		this.duration--;
		if(this.duration == 0) {
			condition = WeatherCondition.CLEAR;
		}
	}
}
