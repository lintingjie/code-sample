package com.example.demo.mapper;

import com.vanke.spider.dal.model.gis.DrivingRoad;
import com.vanke.spider.dal.base.BaseMapper;
import com.vanke.spider.dal.model.SpiderTrafficFacility;
import com.vanke.spider.dal.model.gis.Vertice;
import com.vanke.spider.dal.param.regional_analysis.DrivingConcavehullParam;
import com.vanke.spider.dal.param.regional_analysis.DrivingVerticeParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
*  驾车路网
* </p>
*
* @author lintingjie
* @since 2019-08-02
*/
@Repository
public interface DrivingRoadMapper extends BaseMapper<DrivingRoad> {

    /**
     * 获取拓扑中离起始点最近的顶点
     * @param param
     * @return
     */
    Vertice getNearestVertice(DrivingVerticeParam param);

    /**
     * 获取路网范围
     * @param param
     * @return
     */
    String getDrivingConcavehull(DrivingConcavehullParam param) throws Exception;

    /**
     * 多边形范围查找
     * @param polygon
     * @return
     */
    List<SpiderTrafficFacility> queryByPolygon(@Param("polygon") String polygon);

    List<SpiderTrafficFacility> queryByPolygonList(@Param("polygonStrList") List<String> polygonStrList);
}

