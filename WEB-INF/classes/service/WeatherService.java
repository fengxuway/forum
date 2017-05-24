package service;

import java.util.List;
import java.util.Set;

import pagination.PageBean;

import entity.Attention;
import entity.User;
import entity.Weather;

public interface WeatherService extends BaseService<Weather> {
	/**
	 * 将天气情况获取并写入到数据库，用于在定时器中运行
	 */
	void saveWeather() throws Exception;
	
	/**
	 * 获取太原的天气情况
	 * @return Weather的对象
	 */
	Weather getWeather();
	
}
 