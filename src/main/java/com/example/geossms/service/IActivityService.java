package com.example.geossms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.geossms.entity.Activity;



/**
 *
 * @author 吴斌文
 * @since 2023-05-06
 */
public interface IActivityService extends IService<Activity> {

    Page<Activity> findPage(Page<Activity> page,String name);


    void deleteNormalActivity(Integer activityId, Integer normalId);
    void setNormalActivity(Integer activityId, Integer normalId);
}
