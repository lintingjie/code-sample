package com.example.demo.domain.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 可达圈请求参数
 * @author lintingjie
 * @since 2019/8/2
 */
@Getter
@Setter
public class ConcavehullRequest {

    /**
     * 城市编码
     */
    @NotBlank
    private String city;
    /**
     * 耗时，单位分钟
     */
    @NotNull
    private Integer time;
    /**
     * 起始点经度
     */
    @NotNull
    private Double lng;
    /**
     * 起始点维度
     */
    @NotNull
    private Double lat;

}
