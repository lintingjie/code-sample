package com.example.demo.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.collect.Lists;
import com.vanke.core.util.CollectionUtils;
import com.vanke.core.util.DataUtil;
import com.vanke.spider.dal.base.BaseEsModel;
import com.vanke.spider.dal.es.EsClient;
import com.vanke.spider.dal.es.model.Bucket;
import com.vanke.spider.dal.model.SpiderTrafficFacility;
import com.vanke.spider.dal.param.regional_analysis.TrafficFacilityParam;
import com.vanke.spider.dal.to.regional_analysis.TrafficFacilityBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author lintingjie
 * @create 2019/6/18
 */
@Service
@Slf4j
public class TrafficFacilityMapperEs extends EsClient<BaseEsModel> {

    public List<SpiderTrafficFacility> queryList(TrafficFacilityParam param) throws IOException {
        List<SpiderTrafficFacility> trafficFacilityList= Lists.newArrayList();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String response = super.queryList(param, "traffic_facility");
        List objectList = (List) JSONPath.read(response, "$.hits.hits");
        if(CollectionUtils.isEmpty(objectList)){
            return Collections.emptyList();
        }
        for (Object object : objectList) {
            String source =JSONPath.read(object.toString(), "_source").toString();
            SpiderTrafficFacility trafficFacility =  mapper.readValue(source, SpiderTrafficFacility.class);
            trafficFacilityList.add(trafficFacility);
        }

        return trafficFacilityList;
    }

    public List<Bucket> queryCount(TrafficFacilityParam param) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String response = super.queryList(param, "traffic_facility");
        if(DataUtil.isEmpty(response)){
            return Lists.newArrayList();
        }
        String jsonStr = JSONPath.read(response, "$.aggregations.station_type.buckets").toString();
        return JSONArray.parseArray(jsonStr, Bucket.class);
    }

    public Page<TrafficFacilityBean> queryPage(TrafficFacilityParam param) throws IOException {
        Page<TrafficFacilityBean> page = new Page<>(param.getPageNo(), param.getPageSize());
        List<TrafficFacilityBean> trafficFacilityList= Lists.newArrayList();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String response = super.queryList(param, "traffic_facility");
        List objectList = (List) JSONPath.read(response, "$.hits.hits");
        Integer count = (Integer) JSONPath.read(response, "$.aggregations.count.value");
        page.setTotal(count);
        if(CollectionUtils.isEmpty(objectList)){
            return page;
        }
        for (Object object : objectList) {
            String source =JSONPath.read(object.toString(), "_source").toString();
            String sort =JSONPath.read(object.toString(), "sort").toString();
            JSONArray sortArray = JSONArray.parseArray(sort);
            String distance = ((BigDecimal)sortArray.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            TrafficFacilityBean bean =  mapper.readValue(source, TrafficFacilityBean.class);
            bean.setDistance(distance);
            trafficFacilityList.add(bean);
        }
        page.setRecords(trafficFacilityList);
        return page;
    }


    public void db2ES(List<SpiderTrafficFacility> trafficFacilityList){
        super.batchAdd(trafficFacilityList,"traffic_facility");
    }

}