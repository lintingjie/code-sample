package com.example.demo.domain.request;


import com.vanke.spider.dal.param.regional_analysis.StationTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * 区域概览请求基本参数
 * @author lintingjie
 * @since 2019/8/6
 */
@Getter
@Setter
public class TrafficPageRequest extends BaseOverviewRequest {

    @NotNull
    private StationTypeEnum stationType;



}
