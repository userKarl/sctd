package com.sc.td.common.utils.page;

import com.sc.td.common.utils.StringUtils;

public class PageInfo {

	public static final int index = 0;// 索引

	public static final int pageno = 1;// 页码

	public static final int size = 20;// 每页数量

	/**
	 * 获取页码
	 * 
	 * @param strindex
	 * @return
	 */
	public static int getPageNo(String strpageno) {
		try {
			if (StringUtils.isNotBlank(strpageno)) {
				return Integer.parseInt(strpageno);
			}
		} catch (Exception e) {
			return pageno;
		}
		return pageno;
	}

	/**
	 * 获取每页数量
	 * 
	 * @param strindex
	 * @return
	 */
	public static int getSize(String strsize) {
		try {
			if (StringUtils.isNotBlank(strsize)) {
				return Integer.parseInt(strsize);
			}
		} catch (Exception e) {
			return size;
		}
		return size;
	}

	/**
	 * 获取索引
	 * 
	 * @param _pageno
	 * @param _size
	 * @return
	 */
	public static int getIndex(int _pageno, int _size) {
		int _index = 0;
		try {
			_index = (_pageno - 1) * _size;
		} catch (Exception e) {
			return index;
		}
		return _index;
	}
}
