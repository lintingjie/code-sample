package com.example.demo.mapper;

import com.vanke.spider.dal.base.BaseMapper;
import com.vanke.spider.dal.model.SpiderTrafficFacility;
import com.vanke.spider.dal.model.gis.Vertice;
import com.vanke.spider.dal.param.regional_analysis.DrivingConcavehullParam;
import com.vanke.spider.dal.param.regional_analysis.DrivingVerticeParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
*  交通设施 Mapper接口
* </p>
*
* @author lintingjie
* @since 2019-05-28
*/
public interface TrafficFacilityMapperGis extends BaseMapper<SpiderTrafficFacility> {

    Vertice getNearestVertice(DrivingVerticeParam param);

    String getDrivingDistance(DrivingConcavehullParam param);

    List<SpiderTrafficFacility> queryByPolygon(@Param("polygon") String polygon);

    List<SpiderTrafficFacility> queryByPolygonList(@Param("polygonStrList") List<String> polygonStrList);
}

