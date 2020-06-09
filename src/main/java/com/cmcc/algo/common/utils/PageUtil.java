package com.cmcc.algo.common.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import static com.cmcc.algo.constant.PageConstant.PAGENUM;
import static com.cmcc.algo.constant.PageConstant.STEP;

public class PageUtil {

    public static Page getPageByRequest(String request){
        long pageNum = JSONUtil.parseObj(request).getLong(PAGENUM);
        long step = JSONUtil.parseObj(request).getLong(STEP);

        return new Page(pageNum, step);
    }

}
