package com.example.demo.domain.service.impl;

import com.example.demo.domain.concavehullFactory.ConcavehullFactory;
import com.example.demo.domain.service.IConcavehullService;
import com.vanke.core.cache.template.CacheTemplate;
import com.vanke.core.util.DataUtil;
import com.vanke.spider.common.RedisConstant;
import com.vanke.spider.dal.to.regional_analysis.ConcavehullVo;
import com.vanke.spider.dal.to.regional_analysis.DrivingConcavehullVo;
import com.vanke.spider.domain.regional_analysis.service.IConcavehullService;
import com.vanke.spider.domain.regional_analysis.concavehull.ConcavehullFactory;
import com.vanke.spider.enums.VehicleSpeedEnum;
import com.vanke.spider.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.model.Poi;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lintingjie
 * @since 2019/8/14
 */
@Service
public class ConcavehullServiceImpl implements IConcavehullService {

    @Autowired
    private CacheTemplate cacheTemplate;

    @Override
    public List<Poi> getDrivingPolygon(DrivingConcavehullVo vo) {

        String key = RedisCacheUtil.getKey(String.format(RedisConstant.PG_POLYGON_DRIVE, vo.getCity(), vo.getWorkday(), vo.getTimeQuantum(), vo.getLat(), vo.getLng(), vo.getTime()));
        List<Poi> concavehull = cacheTemplate.valueGet(key, List.class, Poi.class);
        if(DataUtil.isEmpty(concavehull)){
            concavehull = ConcavehullFactory.getConcavehull(vo, VehicleSpeedEnum.DRIVE);
            cacheTemplate.valueSet(key, concavehull, 1L, TimeUnit.HOURS);
        }
        return concavehull;
    }

    @Override
    public List<Poi> getBikingPolygon(ConcavehullVo vo) {
        String key = RedisCacheUtil.getKey(String.format(RedisConstant.PG_POLYGON_BIKE, vo.getCity(), vo.getLat(), vo.getLng(), vo.getTime()));
        List<Poi> concavehull = cacheTemplate.valueGet(key, List.class, Poi.class);
        if(DataUtil.isEmpty(concavehull)){
            concavehull = ConcavehullFactory.getConcavehull(vo, VehicleSpeedEnum.BIKE);
            cacheTemplate.valueSet(key, concavehull, 1L, TimeUnit.HOURS);
        }
        return concavehull;
    }

    @Override
    public List<Poi> getWalkingPolygon(ConcavehullVo vo) {
        String key = RedisCacheUtil.getKey(String.format(RedisConstant.PG_POLYGON_WALK, vo.getCity(), vo.getLat(), vo.getLng(), vo.getTime()));
        List<Poi> concavehull = cacheTemplate.valueGet(key, List.class, Poi.class);
        if(DataUtil.isEmpty(concavehull)){
            concavehull = ConcavehullFactory.getConcavehull(vo, VehicleSpeedEnum.WALK);
            cacheTemplate.valueSet(key, concavehull, 1L, TimeUnit.HOURS);
        }
        return concavehull;
    }
}
