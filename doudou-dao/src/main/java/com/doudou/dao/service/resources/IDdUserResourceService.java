package com.doudou.dao.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.resources.DdUserResource;

import java.util.Map;

/**
 * <p>
 * 用户兑换资源表 服务类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface IDdUserResourceService extends IService<DdUserResource> {

    Map<String, Object> getList(Page page,String userId,Integer type);
}