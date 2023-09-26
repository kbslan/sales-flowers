package com.dy.sales.flowers.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * nothing to say
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/26 22:42
 */
@Getter
@Setter
@ToString
public class BarStatisticsVo implements Serializable {

    /**
     * 包花数量/损坏数量
     */
    private String flowerPerDamage;
    /**
     * 品种数量
     */
    private Integer categoryAmount;
    /**
     * 报损原因数量
     */
    private Long damageReasonAmount;
    /**
     * 品种
     */
    private List<String> categories = new ArrayList<>();
    /**
     * 包花数量
     */
    private List<Long> packageAmounts = new ArrayList<>();
    /**
     * 损坏数量
     */
    private List<Long> damageAmounts = new ArrayList<>();

    /**
     * 开始时间
     */
    private LocalDateTime start;
    /**
     * 结束时间
     */
    private LocalDateTime end;

}
