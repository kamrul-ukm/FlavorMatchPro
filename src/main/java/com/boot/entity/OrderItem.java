package com.boot.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单项表
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单项ID，主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单ID，外键关联orders表
     */
    private Integer orderId;

    /**
     * 美食ID，外键关联food表
     */
    private Integer foodId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品下单时单价（单位：元）
     */
    private BigDecimal price;

    @TableField(exist = false)
    private Food food;
}
