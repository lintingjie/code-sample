package com.example.demo.domain.concavehullFactory;

import com.example.demo.utils.PostGisUtil;
import com.vanke.core.util.DataUtil;
import com.vanke.spider.dal.model.gis.Vertice;
import com.vanke.spider.dal.to.regional_analysis.ConcavehullVo;
import com.vanke.spider.enums.VehicleSpeedEnum;
import lombok.extern.slf4j.Slf4j;
import util.geo.GeoHashUtil;
import util.geo.LocationMapUtil;
import util.geo.MapUtil;
import util.geo.PostGisUtil;
import util.model.Poi;

import java.util.List;


@Slf4j
public abstract class AbstractConcavehull {

    public List<Poi> getConcavehullResult(ConcavehullVo vo, VehicleSpeedEnum vehicleSpeedEnum) {
        //获取路网离起始点最近的顶点
        Vertice vertice = this.getNearestVertice(vo);
        //起始点到顶点的距离
        Double distance = GeoHashUtil.getDistance(vertice.getLat(), vertice.getLng(), vo.getLat(), vo.getLng());
        Double time = distance/vehicleSpeedEnum.getSpeed();
        Long remainingTime = vo.getTime()*60 - Math.round(time);
        if(remainingTime <= 0){
            //默认范围
            Double distanceInMeter = (vo.getTime()*60)*vehicleSpeedEnum.getSpeed();
            //默认返回一个六边形
            return LocationMapUtil.getHexagon(vo.getLng(), vo.getLat(), distanceInMeter);
        }
        //根据顶点获取范围
        String polygon = null;
        try {
            polygon = this.getRealConcavehull(vo, vertice.getId(), remainingTime);
        } catch (Exception e) {
            log.error(e.getMessage());
            //默认范围
            Double distanceInMeter = (vo.getTime()*60)*vehicleSpeedEnum.getSpeed();
            //默认返回一个六边形
            return LocationMapUtil.getHexagon(vo.getLng(), vo.getLat(), distanceInMeter);
        }
        //将polygon字符串转成集合对象
        List<Poi> poiList = PostGisUtil.polygonStrToList(polygon);

        if(DataUtil.isEmpty(poiList)){
            //默认范围
            Double distanceInMeter = (vo.getTime()*60)*vehicleSpeedEnum.getSpeed();
            //默认返回一个六边形
            return LocationMapUtil.getHexagon(vo.getLng(), vo.getLat(), distanceInMeter);
        }
        //如果起点不在范围内，则返回默认值
        if(!MapUtil.isPtInPoly(new Poi(vo.getLat(), vo.getLng()), poiList)){
            //默认范围
            Double distanceInMeter = (vo.getTime()*60)*vehicleSpeedEnum.getSpeed();
            //默认返回一个六边形
            return LocationMapUtil.getHexagon(vo.getLng(), vo.getLat(), distanceInMeter);
        }

        return poiList;
    }


    protected abstract Vertice getNearestVertice(ConcavehullVo vo);

    protected abstract String getRealConcavehull(ConcavehullVo vo, Integer verticeId, Long remainingTime) throws Exception;


}