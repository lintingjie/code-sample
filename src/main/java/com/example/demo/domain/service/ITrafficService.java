package com.example.demo.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.vanke.spider.dal.param.regional_analysis.TrafficFacilityParam;
import com.vanke.spider.dal.to.regional_analysis.TrafficFacilityBean;
import com.vanke.spider.domain.regional_analysis.response.TrafficStatisticResponse;

import java.io.IOException;

/**
 * @author lintingjie
 * @since 2019/8/26
 */
public interface ITrafficService {

    /**
     * 交通设施poi集合及统计
     * @param param
     * @return
     * @throws IOException
     */
    TrafficStatisticResponse getTrafficStatistic(TrafficFacilityParam param) throws IOException;

    /**
     * 交通设施poi集合及统计（缓存）
     * @param param
     * @return
     * @throws IOException
     */
    TrafficStatisticResponse getTrafficStatisticByCache(TrafficFacilityParam param) throws IOException;

    /**
     * 交通设施详情
     * @param param
     */
    Page<TrafficFacilityBean> getTrafficDetail(TrafficFacilityParam param) throws IOException;

}
