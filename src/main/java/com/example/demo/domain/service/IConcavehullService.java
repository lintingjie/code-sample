package com.example.demo.domain.service;


import com.vanke.spider.dal.to.regional_analysis.ConcavehullVo;
import com.vanke.spider.dal.to.regional_analysis.DrivingConcavehullVo;
import util.model.Poi;

import java.util.List;

/**
 * 可达圈
 */
public interface IConcavehullService {

    /**
     * 驾车可达圈
     * @param vo
     * @return
     */
    List<Poi> getDrivingPolygon(DrivingConcavehullVo vo);

    /**
     * 骑行可达圈
     * @param vo
     * @return
     */
    List<Poi> getBikingPolygon(ConcavehullVo vo);

    /**
     * 步行可达圈
     * @param vo
     * @return
     */
    List<Poi> getWalkingPolygon(ConcavehullVo vo);


}
