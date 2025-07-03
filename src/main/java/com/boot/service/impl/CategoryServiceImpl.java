package com.boot.service.impl;

import com.boot.entity.Category;
import com.boot.mapper.CategoryMapper;
import com.boot.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知公告类别 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
