package com.boot.service.impl;

import com.boot.entity.Food;
import com.boot.mapper.FoodMapper;
import com.boot.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 美食表 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

}
