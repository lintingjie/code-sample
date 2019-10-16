package com.example.demo.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.domain.request.BaseOverviewRequest;
import com.example.demo.domain.request.TrafficPageRequest;
import com.example.demo.domain.service.ITrafficService;
import com.vanke.core.bean.ApiBaseResponse;
import com.vanke.core.exception.VankeBusinessException;
import com.vanke.core.util.DataUtil;
import com.vanke.spider.api.base.ResponseBuilder;
import com.vanke.spider.dal.param.regional_analysis.TrafficFacilityParam;
import com.vanke.spider.dal.to.regional_analysis.TrafficFacilityBean;
import com.vanke.spider.domain.IDistrictsService;
import com.vanke.spider.domain.regional_analysis.request.BaseOverviewRequest;
import com.vanke.spider.domain.regional_analysis.request.TrafficPageRequest;
import com.vanke.spider.domain.regional_analysis.response.TrafficStatisticResponse;
import com.vanke.spider.domain.regional_analysis.service.ITrafficService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author lintingjie
 * @since 2019/8/26
 */
@RestController
@RequestMapping("/traffic")
public class TrafficController {

    @Autowired
    private IDistrictsService districtsService;

    @Autowired
    private ITrafficService trafficService;

    @PostMapping(value = "statistic")
    @RequiresPermissions("regional.analysis.read")
    @ApiOperation(value = "v2.11 交通设施poi集合及统计")
    public ApiBaseResponse getTrafficStatistic(@RequestBody @Valid BaseOverviewRequest request) throws IOException {
        request.validate(districtsService);
        TrafficFacilityParam param = new TrafficFacilityParam();
        BeanUtils.copyProperties(request, param);

        TrafficStatisticResponse response = trafficService.getTrafficStatisticByCache(param);

        return ResponseBuilder.setResponse(response);
    }

    @PostMapping(value = "detail/{pageSize}/{pageNo}")
    @RequiresPermissions("regional.analysis.read")
    @ApiOperation(value = "v2.11 交通设施详情分页")
    public ApiBaseResponse getTrafficDetail(@RequestBody @Valid TrafficPageRequest request, @PathVariable Integer pageSize, @PathVariable Integer pageNo) throws IOException {
        request.validate(districtsService);
        if(DataUtil.isEmpty(request.getLat()) || DataUtil.isEmpty(request.getLng())){
            throw new VankeBusinessException("param_error", "搜索点经纬度不能为空");
        }
        TrafficFacilityParam param = new TrafficFacilityParam();
        BeanUtils.copyProperties(request, param);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        Page<TrafficFacilityBean> response = trafficService.getTrafficDetail(param);

        return ResponseBuilder.setResponse(response);
    }
}
