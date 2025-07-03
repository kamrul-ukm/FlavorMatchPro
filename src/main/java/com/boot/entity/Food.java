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
 * 美食表
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 美食ID，主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 美食名称
     */
    private String name;

    /**
     * 美食描述
     */
    private String description;

    /**
     * 价格（单位：元）
     */
    private BigDecimal price;

    /**
     * 图片链接
     */
    private String img;

    private Integer categoryId;

    @TableField(exist = false)
    private Category category;


}
