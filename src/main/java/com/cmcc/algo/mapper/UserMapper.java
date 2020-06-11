package com.cmcc.algo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmcc.algo.entity.User;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author hjy
 * @since 2020-05-25
 */
public interface UserMapper extends BaseMapper<User> {
     User findByUserName(String username);
}
