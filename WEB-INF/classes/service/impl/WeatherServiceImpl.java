package service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import pagination.PageBean;

import dao.*;
import entity.*;
import service.*;
import util.GETWeather;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {
	@Autowired
	private WeatherDao weatherDao;
	@Override
	public Weather get(Serializable id) {
		return weatherDao.get(id);
	}

	@Override
	public List<Weather> getAll() {
		return weatherDao.getAll();
	}

	@Override
	public Serializable save(Weather o) {
		return weatherDao.save(o);
	}

	@Override
	public void delete(Weather o) {
		weatherDao.delete(o);
	}

	@Override
	public void update(Weather o) {
		weatherDao.update(o);
	}

	@Override
	public void saveOrUpdate(Weather o) {
		weatherDao.saveOrUpdate(o);
	}

	@Override
	public void saveWeather() {
		String[] result = GETWeather.getContent("太原");
		if(result.length >= 5){
			Weather w = weatherDao.findByCityName("太原");
			if(w != null){
				w.setDegree(result[0]);
				w.setClimate(result[1]);
				w.setImage(result[2]);
				w.setWind(result[3]);
				w.setHumidity(result[4]);
				w.setTime(new Date());
			}else{
				w = new Weather(result[0], result[1], result[2], result[3], result[4], new Date());
				save(w);
			}
		}else{
			throw new RuntimeException();
		}
	}

	@Override
	public Weather getWeather() {
		return weatherDao.findByCityName("太原");
	}
	
	
	
}
