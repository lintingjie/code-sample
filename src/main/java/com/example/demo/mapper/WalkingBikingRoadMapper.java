package com.example.demo.mapper;

import com.vanke.spider.dal.model.gis.Vertice;
import com.vanke.spider.dal.model.gis.WalkingBikingRoad;
import com.vanke.spider.dal.base.BaseMapper;
import com.vanke.spider.dal.param.regional_analysis.ConcavehullParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* <p>
*  步行骑行路网
* </p>
*
* @author lintingjie
* @since 2019-08-02
*/
@Repository
public interface WalkingBikingRoadMapper extends BaseMapper<WalkingBikingRoad> {

    /**
     * 获取拓扑中离起始点最近的顶点
     * @param lng
     * @param lat
     * @return
     */
    Vertice getNearestVertice(@Param("lng") Double lng, @Param("lat") Double lat);

    Boolean isPointInPolygon(@Param("lng") Double lng, @Param("lat") Double lat, @Param("polygon") String polygon);

    /**
     * 获取步行路网范围
     * @param param
     * @return
     */
    String getWalkingConcavehull(ConcavehullParam param) throws Exception;

    /**
     * 获取骑行路网范围
     * @param param
     * @return
     */
    String getBikingConcavehull(ConcavehullParam param) throws Exception;

}

