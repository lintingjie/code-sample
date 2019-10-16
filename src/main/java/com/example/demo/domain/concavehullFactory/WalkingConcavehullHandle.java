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
 * 步行范围计算
 * Created by ltj on 2019/8/5
 */
@Component
public class WalkingConcavehullHandle extends AbstractConcavehull {

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
        return walkingBikingRoadMapper.getWalkingConcavehull(param);
    }
}
