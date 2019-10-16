package com.example.demo.domain.concavehullFactory;

import com.google.common.collect.Maps;
import com.vanke.spider.dal.to.regional_analysis.ConcavehullVo;
import com.vanke.spider.enums.VehicleSpeedEnum;
import com.vanke.spider.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import util.model.Poi;

import java.util.List;
import java.util.Map;


@Slf4j
public class ConcavehullFactory {

    static Map<Enum, Class<? extends AbstractConcavehull>> vehicleMap = Maps.newHashMap();

    static {
        vehicleMap.put(VehicleSpeedEnum.WALK, WalkingConcavehullHandle.class);
        vehicleMap.put(VehicleSpeedEnum.BIKE, BikingConcavehullHandle.class);
        vehicleMap.put(VehicleSpeedEnum.DRIVE, DrivingConcavehullHandle.class);
    }

    public static List<Poi> getConcavehull(ConcavehullVo vo, VehicleSpeedEnum vehicleSpeedEnum) {
        Class clazz = vehicleMap.get(vehicleSpeedEnum);
        AbstractConcavehull concavehull = (AbstractConcavehull) SpringContextHolder.getBean(clazz);
        return concavehull.getConcavehullResult(vo, vehicleSpeedEnum);
    }
}