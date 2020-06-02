package com.cmcc.algo.common.utils;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * @author jarain
 * 分页
 */
@Component
public class PageHelp {


	public static PageUtils getPageUtils(PageInfo<?> pageInfo) {

		Long total = pageInfo.getTotal();
		int size = pageInfo.getPageSize();
		int curPage = pageInfo.getPageNum();

    	return new PageUtils(pageInfo.getList(),total.intValue(),size, curPage);
    }
}
