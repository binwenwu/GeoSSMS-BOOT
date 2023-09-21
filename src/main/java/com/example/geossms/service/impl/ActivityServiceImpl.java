package com.example.geossms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.geossms.entity.Activity;
import com.example.geossms.mapper.ActivityMapper;
import com.example.geossms.service.IActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author 吴斌文
 * @since 2023-05-06
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Override
    public Page<Activity> findPage(Page<Activity> page, String name) {
        return activityMapper.findPage(page,name);
    }

    @Transactional
    @Override
    public void setNormalActivity(Integer activityId, Integer normalId) {
        activityMapper.deleteNormalActivity(activityId, normalId);
        activityMapper.setNormalActivity(activityId, normalId);
    }

    @Transactional
    @Override
    public void deleteNormalActivity(Integer activityId, Integer normalId) {
        activityMapper.deleteNormalActivity(activityId, normalId);
    }

}
