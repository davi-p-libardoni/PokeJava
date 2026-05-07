package battle;

public class Weather {
	private WeatherCondition condition;
	private int duration;
	
	public Weather() {
		this.condition = WeatherCondition.CLEAR;
	}
	
	public void setWeather(WeatherCondition w) {
		this.condition = w;
		this.duration = 5;
	}
	
	public void setWeather(WeatherCondition w, int duration) {
		this.condition = w;
		this.duration = duration;
	}
	
	public WeatherCondition getWeather() {
		return this.condition;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void decrement() {
		if(this.condition == WeatherCondition.CLEAR) return;
		this.duration--;
		if(this.duration == 0) {
			condition = WeatherCondition.CLEAR;
		}
	}
}
