package cn.hc.service;

import cn.hc.mapper.CityMapper;
import cn.hc.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public City findCityByName(String cityName) {
        return cityMapper.findByName(cityName);
    }

}
