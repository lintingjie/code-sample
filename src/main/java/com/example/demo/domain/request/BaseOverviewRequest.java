package com.example.demo.domain.request;

import com.example.demo.utils.BoundaryUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vanke.core.exception.VankeBusinessException;
import com.vanke.core.util.DataUtil;
import com.vanke.spider.dal.model.Districts;
import com.vanke.spider.domain.IDistrictsService;
import com.vanke.spider.enums.SearchTypeEnum;
import com.vanke.spider.enums.VankeExCodeEnum;
import com.vanke.spider.util.KaleidoscopeUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import util.geo.BoundaryUtil;
import util.geo.LatLongUtil;
import util.model.Poi;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 区域概览请求基本参数
 * @author lintingjie
 * @since 2019/8/6
 */
@Getter
@Setter
public class BaseOverviewRequest {

    /**
     * 城市编码
     */
    @NotBlank
    private String city;
    /**
     * 区县编码
     */
    private String county;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM")
    @NotNull
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM")
    @NotNull
    private Date endTime;

    @NotNull
    private SearchTypeEnum type;

    /**
     * 公交范围
     */
    private List<List<Poi>> poisList;

    /**
     * 圆形查找范围（经纬度，半径）
     */
    private Double lat;
    private Double lng;
    //覆盖半径（米）
    private BigDecimal coverageRadius;

    /**
     * 多边形范围/凸包
     */
    private List<Poi> pois;

    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }
        return new DateTime(endTime).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
    }

    public void validate(IDistrictsService districtsService){
        if(SearchTypeEnum.bus.equals(type)){
            if(DataUtil.isEmpty(poisList)){
                throw new VankeBusinessException("param_error", "poisList不能为空");
            }

        }
        if(SearchTypeEnum.polygon.equals(type)){
            if(DataUtil.isEmpty(pois)){
                throw new VankeBusinessException("param_error", "pois不能为空");
            }
            Poi avgPoi = KaleidoscopeUtil.avgLatLng(pois);
            Boolean isInBoundary = BoundaryUtil.isInBoundary(this.getCity(), avgPoi);
            if(!isInBoundary){
                throw VankeExCodeEnum.throwExceptionWithEnum(VankeExCodeEnum.AVG_LAT_LNG_NOT_CITY);
            }
        }

        if(SearchTypeEnum.round.equals(type)){
            if(DataUtil.isEmpty(coverageRadius)){
                throw new VankeBusinessException("param_error", "coverageRadius不能为空");
            }
            if(DataUtil.isEmpty(lat)){
                throw new VankeBusinessException("param_error", "lat不能为空");
            }
            if(DataUtil.isEmpty(lng)){
                throw new VankeBusinessException("param_error", "lng不能为空");
            }

            Boolean isInBoundary = BoundaryUtil.isInBoundary(this.getCity(), new Poi(lat, lng));
            if(!isInBoundary){
                throw VankeExCodeEnum.throwExceptionWithEnum(VankeExCodeEnum.ADDRESS_IS_NOT_URBAN_AREA);
            }
        }
    }



}
