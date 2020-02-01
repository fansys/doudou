package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.User;
import com.doudou.dao.mapper.UserMapper;
import com.doudou.dao.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User queryByOpenId(String openId) {
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("open_id",openId));
    }

    @Override
    public void updateUserByOpenId(User user, String openId) {
        int result = this.baseMapper.update(user, new QueryWrapper<User>().eq("open_id", openId));
        Assert.isTrue(result > 0,"更新数据失败");
    }
}