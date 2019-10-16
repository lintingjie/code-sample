package com.example.demo.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.domain.response.TrafficStatisticResponse;
import com.example.demo.mapper.TrafficFacilityMapperEs;
import com.vanke.core.cache.template.CacheTemplate;
import com.vanke.core.util.DataUtil;
import com.vanke.spider.bean.TrafficFacilityModel;
import com.vanke.spider.common.RedisConstant;
import com.vanke.spider.dal.es.dao.SpiderTrafficFacilityMapperEs;
import com.vanke.spider.dal.model.SpiderTrafficFacility;
import com.vanke.spider.dal.param.regional_analysis.StationTypeEnum;
import com.vanke.spider.dal.param.regional_analysis.TrafficFacilityParam;
import com.vanke.spider.dal.to.CityHeatMap;
import com.vanke.spider.dal.to.regional_analysis.TrafficFacilityBean;
import com.vanke.spider.domain.regional_analysis.response.TrafficStatisticResponse;
import com.vanke.spider.domain.regional_analysis.service.ITrafficService;
import com.vanke.spider.enums.TrafficFacilityEnum;
import com.vanke.spider.util.KaleidoscopeUtil;
import com.vanke.spider.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lintingjie
 * @since 2019/8/26
 */
@Service
public class TrafficService implements ITrafficService {

    @Autowired
    private TrafficFacilityMapperEs spiderTrafficFacilityMapperEs;

    @Autowired
    private CacheTemplate cacheTemplate;


    @Override
    public TrafficStatisticResponse getTrafficStatistic(TrafficFacilityParam param) throws IOException {

        TrafficStatisticResponse response = new TrafficStatisticResponse();

        param.setTableCodeList(Arrays.asList(StationTypeEnum.BUS.name(), StationTypeEnum.GAS.name(),
                StationTypeEnum.PARK.name(),StationTypeEnum.SUBWAY.name(),StationTypeEnum.TRAIN.name()));
        List<SpiderTrafficFacility> list = spiderTrafficFacilityMapperEs.queryList(param);

        List<CityHeatMap> cityHeatMapList = KaleidoscopeUtil.countHeatMapWithType(list,
                SpiderTrafficFacility::getLat, SpiderTrafficFacility::getLng, SpiderTrafficFacility::getName, SpiderTrafficFacility::getStationType);

        response.setCityHeatMapList(cityHeatMapList);
        response.setResultList(getTypeMapList(list));

        return response;
    }

    /**
     * 按类别封装结果
     * @param collection
     * @return
     */
    private List<TrafficFacilityModel> getTypeMapList(Collection<SpiderTrafficFacility> collection){
        Map<String, List<SpiderTrafficFacility>> facilityListMap = collection.stream().collect(
                Collectors.groupingBy(SpiderTrafficFacility::getStationType));
        List<TrafficFacilityModel> resultList = new ArrayList<>();
        for (String name : facilityListMap.keySet()){
            TrafficFacilityModel<SpiderTrafficFacility> model = new TrafficFacilityModel<>();
            if(DataUtil.isEmpty(name)){
                continue;
            }
            model.setName(TrafficFacilityEnum.valueOf(name).getDesc());
            model.setCode(name);
            model.setLists(facilityListMap.get(name));
            model.setTotalNum(facilityListMap.get(name).size());

            resultList.add(model);
        }
        return resultList;
    }

    @Override
    public TrafficStatisticResponse getTrafficStatisticByCache(TrafficFacilityParam param) throws IOException {

        String key = RedisCacheUtil.getKey(String.format(RedisConstant.REGIONAL_ANALYSIS_TRAFFIC_STATISTIC, param.hashCode()));
        TrafficStatisticResponse result = cacheTemplate.valueGet(key, TrafficStatisticResponse.class);
        if(DataUtil.isEmpty(result)){
            result = this.getTrafficStatistic(param);
            cacheTemplate.valueSet(key, result, 1L, TimeUnit.HOURS);
        }

        return result;
    }

    @Override
    public Page<TrafficFacilityBean> getTrafficDetail(TrafficFacilityParam param) throws IOException {
        Page<TrafficFacilityBean> page = spiderTrafficFacilityMapperEs.queryPage(param);
        return page;
    }
}
