package com.example.demo.domain.concavehullFactory;

import com.example.demo.mapper.DrivingRoadMapper;
import com.vanke.spider.dal.gis.dao.DrivingRoadMapper;
import com.vanke.spider.dal.model.gis.Vertice;
import com.vanke.spider.dal.param.regional_analysis.DrivingConcavehullParam;
import com.vanke.spider.dal.param.regional_analysis.DrivingVerticeParam;
import com.vanke.spider.dal.to.regional_analysis.ConcavehullVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 驾车范围计算
 * Created by ltj on 2019/8/5
 */
@Component
public class DrivingConcavehullHandle extends AbstractConcavehull {

    @Autowired
    private DrivingRoadMapper drivingRoadMapper;

    @Override
    protected Vertice getNearestVertice(ConcavehullVo vo) {
        DrivingVerticeParam param = new DrivingVerticeParam();
        BeanUtils.copyProperties(vo, param);
        return drivingRoadMapper.getNearestVertice(param);
    }

    @Override
    protected String getRealConcavehull(ConcavehullVo vo, Integer verticeId, Long remainingTime) throws Exception{
        DrivingConcavehullParam param = new DrivingConcavehullParam();
        BeanUtils.copyProperties(vo, param);
        param.setVerticeId(verticeId);
        param.setTime(remainingTime);
        return drivingRoadMapper.getDrivingConcavehull(param);
    }
}
