package com.example.demo.domain.response;

import lombok.Getter;
import lombok.Setter;


/**
 * @author lintingjie
 * @since 2019/8/6
 */
@Getter
@Setter
public class TrafficCountResponse {

    private Long busCount;
    private Long subwayCount;
}
