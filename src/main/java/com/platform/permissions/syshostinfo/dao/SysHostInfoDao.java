package com.platform.permissions.syshostinfo.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.syshostinfo.mapper.SysHostInfoMapper;
import com.platform.permissions.syshostinfo.model.SysHostInfo;
@Repository
public class SysHostInfoDao{
	@Resource
	private SysHostInfoMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 添加
	 */
	public boolean add(SysHostInfo obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysHostInfo obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysHostInfo getById(Long id) {
		return (SysHostInfo) mapper.getById(id);
	}
	/**
	 * 根据id删除
	 */
	public boolean delById(Long id) {
		return mapper.delById(id);
	}
	/**
	 * 查询
	 * needPage 是否分页
	 * orderField	排序		id asc
	 */
	public Pager<SysHostInfo> queryAll(QueryFilter queryFilter) {
		Pager<SysHostInfo> returnPager=new Pager<SysHostInfo>();
		List<SysHostInfo> list=null;
		if(queryFilter.isNeedPage()){
			//分页
			Pager pager=queryFilter.getPager();
			//获取分页数据
			list=mapper.queryAll(queryFilter.getFilters(),new RowBounds(pager.getBeginRum(),pager.getEndRum()));
			returnPager.setDataList(list);
			//获取总条数
			Integer count=mapper.queryTotal(queryFilter.getFilters());
			returnPager.setCount(count);
		}else {
			//不分页
			list=mapper.queryAll(queryFilter.getFilters(),new RowBounds());
			returnPager.setDataList(list);
		}
		return returnPager;
	}
}
