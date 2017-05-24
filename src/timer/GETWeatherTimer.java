package timer;

import org.quartz.*;
import org.springframework.scheduling.quartz.*;
import org.springframework.stereotype.Component;

import entity.*;
import dao.*;
import service.*;
public  class GETWeatherTimer extends QuartzJobBean {
	WeatherService weatherService;
	
	protected void executeInternal(JobExecutionContext jbContent) throws JobExecutionException {
		try {
			weatherService.saveWeather();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WeatherService getWeatherService() {
		return weatherService;
	}
	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
}
