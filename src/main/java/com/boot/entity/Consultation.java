package com.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 咨询表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 咨询唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户唯一标识（关联用户表）
     */
    private Integer userId;

    @TableField(exist = false)
    private User user;

    /**
     * 用户咨询的问题
     */
    private String question;

    /**
     * 管理员的回复
     */
    private String reply;

    /**
     * 咨询时间
     */
    private Date createdAt;


    @TableField(exist = false)
    private List<Consultation> consultations;
    @TableField(exist = false)
    private String status;


    private String raw;


}
