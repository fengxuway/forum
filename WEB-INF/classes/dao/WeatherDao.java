package dao;

import java.util.List;
import java.util.Set;

import pagination.PageBean;

import entity.*;

public interface WeatherDao extends BaseDao<Weather> {
	/**
	 * 根据城市名称获取天气
	 * @param city 城市名称
	 * @return Weather对象
	 */
	Weather findByCityName(String city);
} 
 