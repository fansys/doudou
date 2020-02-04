package com.doudou.dao.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdUserSign;
import com.doudou.dao.mapper.resources.DdUserSignMapper;
import com.doudou.dao.service.resources.IDdUserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户签到表 服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdUserSignServiceImpl extends
            ServiceImpl<DdUserSignMapper, DdUserSign> implements IDdUserSignService {

    @Autowired
    private DdUserSignMapper userSignMapper;


    @Override
    public DdUserSign getByUserId(String userId) {
        DdUserSign ddUserSign =
                userSignMapper.selectOne(new QueryWrapper<DdUserSign>().eq("user_id", userId));
        return ddUserSign;
    }
}
