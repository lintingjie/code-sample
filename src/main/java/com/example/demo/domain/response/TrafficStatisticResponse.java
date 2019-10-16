package com.example.demo.domain.response;

import com.vanke.spider.bean.TrafficFacilityModel;
import com.vanke.spider.dal.to.CityHeatMap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author lintingjie
 * @since 2019/8/26
 */
@Getter
@Setter
public class TrafficStatisticResponse {

    //热力图
    private List<CityHeatMap> cityHeatMapList;

    //交通设施poi
    private List<TrafficFacilityModel> resultList;

}
