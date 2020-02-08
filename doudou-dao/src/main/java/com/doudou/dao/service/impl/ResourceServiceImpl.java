package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.mapper.ResourceMapper;
import com.doudou.dao.service.IResourceService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, DataResource> implements IResourceService {

    /**
     * @param resourceId
     * @return
     */
    @Override
    public DataResource getResource(String resourceId) {
        Assert.hasText(resourceId,"resourceId is required");
        return getOne(new QueryWrapper<DataResource>().eq("resource_id",resourceId));
    }

    @Override
    public void updateResourceRemainingNum(String resourceId,Integer remainingNum) {
        Assert.notNull(remainingNum,"剩余数量不能为空");
        DataResource dataResource = getResource(resourceId);
        DataResource updateBean = new DataResource();
        updateBean.setRemainingNum(remainingNum);
        boolean updateResult = update(updateBean, new QueryWrapper<DataResource>()
            .eq("resource_id", dataResource.getResourceId())
            .eq("remaining_num", dataResource.getRemainingNum()));
        Assert.isTrue(updateResult,"更新失败");
    }

    @Override
    public int countResourceNum(String clientId) {
        Assert.hasText(clientId,"clientId is required");
        return count(new QueryWrapper<DataResource>().eq("client_id",clientId));
    }

    @Override
    public IPage<DataResource> pageResource(String clientId, IPage<DataResource> pageQuery) {
        return page(pageQuery,new QueryWrapper<DataResource>().eq("client_id",clientId));
    }

    @Override
    public List<DataResource> getResourceByResourceIdList(List<String> resourceIdList) {
        return list(new QueryWrapper<DataResource>().in("resource_id", resourceIdList));
    }
}
