package com.example.demo.domain.concavehullFactory;

import com.example.demo.mapper.WalkingBikingRoadMapper;
import com.vanke.spider.dal.gis.dao.WalkingBikingRoadMapper;
import com.vanke.spider.dal.model.gis.Vertice;
import com.vanke.spider.dal.param.regional_analysis.ConcavehullParam;
import com.vanke.spider.dal.to.regional_analysis.ConcavehullVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 骑行范围计算
 *
 * @author lintingjie
 * @since 2019-08-05
 */
@Component
public class BikingConcavehullHandle extends AbstractConcavehull {

    @Autowired
    WalkingBikingRoadMapper walkingBikingRoadMapper;

    @Override
    protected Vertice getNearestVertice(ConcavehullVo vo) {
        return walkingBikingRoadMapper.getNearestVertice(vo.getLng(), vo.getLat());
    }

    @Override
    protected String getRealConcavehull(ConcavehullVo vo, Integer verticeId, Long remainingTime) throws Exception {
        ConcavehullParam param = new ConcavehullParam();
        BeanUtils.copyProperties(vo, param);
        param.setVerticeId(verticeId);
        param.setTime(remainingTime);
        return walkingBikingRoadMapper.getBikingConcavehull(param);
    }
}
