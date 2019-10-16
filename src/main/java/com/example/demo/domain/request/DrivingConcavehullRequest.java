package com.example.demo.domain.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 驾车范围请求参数
 *
 * @author lintingjie
 * @since 2019/8/2
 */
@Getter
@Setter
public class DrivingConcavehullRequest extends ConcavehullRequest {


    /**
     * 1工作日，0非工作日
     */
    @NotBlank
    private String workday;
    /**
     * 时段：1早高峰，2平峰，3晚高峰
     */
    @NotBlank
    private String timeQuantum;


}
