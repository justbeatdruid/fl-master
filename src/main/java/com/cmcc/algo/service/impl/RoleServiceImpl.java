package com.cmcc.algo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmcc.algo.entity.Role;
import com.cmcc.algo.mapper.RoleMapper;
import com.cmcc.algo.service.IRoleService;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

