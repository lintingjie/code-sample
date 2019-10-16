package com.example.demo.controller;


import com.example.demo.domain.request.ConcavehullRequest;
import com.example.demo.domain.request.DrivingConcavehullRequest;
import com.example.demo.domain.service.IConcavehullService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.geo.LatLongUtil;
import util.model.Poi;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 驾车、骑行、步行可达范围接口
 *
 * @author lintingjie
 * @since 2019-08-02
 */
@RestController
@RequestMapping("/concavehullFactory")
public class ConcavehullController {

    @Autowired
    private IDistrictsService districtsService;

    @Autowired
    private IConcavehullService concavehullService;

    @PostMapping(value = "/drive")
    @RequiresPermissions( value={"regional.analysis.read","businessPerspective.businessAnalyse.read"}, logical = Logical.OR)
    @ApiOperation(value = "v2.10 驾车范围")
    public ApiBaseResponse drivingPolygon(@RequestBody @Valid DrivingConcavehullRequest request) {
        validateLocation(request.getLat(), request.getLng(), request.getCity());
        DrivingConcavehullVo vo = new DrivingConcavehullVo();
        BeanUtils.copyProperties(request, vo);
        List<Poi> concavehull = concavehullService.getDrivingPolygon(vo);
        return ResponseBuilder.setResponse(concavehull);

    }

    @PostMapping(value = "/bike")
    @RequiresPermissions( value={"regional.analysis.read","businessPerspective.businessAnalyse.read"}, logical = Logical.OR)
    @ApiOperation(value = "v2.10 骑行范围")
    public ApiBaseResponse bikingPolygon(@RequestBody @Valid ConcavehullRequest request) {
        validateLocation(request.getLat(), request.getLng(), request.getCity());
        ConcavehullVo vo = new ConcavehullVo();
        BeanUtils.copyProperties(request, vo);
        List<Poi> concavehull = concavehullService.getBikingPolygon(vo);
        return ResponseBuilder.setResponse(concavehull);

    }

    @PostMapping(value = "/walk")
    @RequiresPermissions( value={"regional.analysis.read","businessPerspective.businessAnalyse.read"}, logical = Logical.OR)
    @ApiOperation(value = "v2.10 步行范围")
    public ApiBaseResponse walkingPolygon(@RequestBody @Valid ConcavehullRequest request) {
        validateLocation(request.getLat(), request.getLng(), request.getCity());
        ConcavehullVo vo = new ConcavehullVo();
        BeanUtils.copyProperties(request, vo);
        List<Poi> concavehull = concavehullService.getWalkingPolygon(vo);
        return ResponseBuilder.setResponse(concavehull);

    }



    private void validateLocation(Double lat, Double lng, String city){
        String countyCode = LatLongUtil.getCodeByLatLon(lat.toString(), lng.toString());
        if(Objects.equals(countyCode, city)){
            //中山、东莞区县编码和城市编码一样，跳过校验
            return;
        }
        Districts districts = districtsService.queryByIdAndParentId(countyCode, city);
        if (DataUtil.isEmpty(districts)) {
            throw VankeExCodeEnum.throwExceptionWithEnum(VankeExCodeEnum.ADDRESS_IS_NOT_URBAN_AREA);
        }

    }
}
