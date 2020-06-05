package com.cmcc.algo.common.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import static com.cmcc.algo.constant.PageConstant.PAGENUM;
import static com.cmcc.algo.constant.PageConstant.STEP;

public class PageUtil {

    public static Page getPageByRequest(String request){
        int pageNum = (Integer) JSONUtil.parseObj(request).get(PAGENUM);
        int step = (Integer) JSONUtil.parseObj(request).get(STEP);

        return new Page(pageNum, step);
    }

}
