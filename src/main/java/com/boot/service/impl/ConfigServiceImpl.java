package com.boot.service.impl;

import com.boot.entity.Config;
import com.boot.mapper.ConfigMapper;
import com.boot.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表
 服务实现类
 * </p>
 *
 * @author 
 * @since 2025-07-01
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
