package com.platform.permissions.sysuser.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.platform.core.pager.Pager;
import com.platform.core.query.QueryFilter;
import com.platform.permissions.sysuser.mapper.SysUserMapper;
import com.platform.permissions.sysuser.model.SysUser;
@Repository
public class SysUserDao{
	@Resource
	private SysUserMapper mapper;
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 根据id、idcard查询是否已存在相同的身份号码
	 */
	public List<SysUser> checkIdcard(Long id,String idcard){
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("idcardEquals", idcard);
		map.put("notid", id);
		queryFilter.setFilters(map);
		List<SysUser> sysUsers=queryAll(queryFilter).getDataList();
		return sysUsers;
	}
	/**
	 * 根据id、account查询是否已存在相同帐号
	 */
	public List<SysUser> checkAccount(Long id,String account){
		QueryFilter queryFilter=new QueryFilter();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("accountEquals", account);
		map.put("notid", id);
		queryFilter.setFilters(map);
		List<SysUser> sysUsers=queryAll(queryFilter).getDataList();
		return sysUsers;
	}
	/**
	 * 根据id、idcard查询是否已存在相同的身份号码
	 
	public boolean checkIdcard(Long id,String idcard){
		boolean ret=false;
		Map<String, Object> filter=new HashMap<String, Object>();
		filter.put("idcardEquals", idcard);
		filter.put("notid", id);
		List<SysUser> sysUserList=mapper.queryAll(filter, new RowBounds());
		if(sysUserList.size()>0){
			ret=true;
		}
		return ret;
	}*/
	/**
	 * 根据帐号获取用户
	 */
	public SysUser getByAccount(String account){
		SysUser sysUser=null;
		Map<String, Object> filter=new HashMap<String, Object>();
		filter.put("accountEquals", account);
		List<SysUser> sysUserList=mapper.queryAll(filter, new RowBounds());
		if(sysUserList.size()==1){
			sysUser=sysUserList.get(0);
		}
		return sysUser;
	}
	/**
	 * 添加
	 */
	public boolean add(SysUser obj) {
		return mapper.add(obj);
	}
	/**
	 * 修改
	 */
	public boolean update(SysUser obj) {
		return mapper.update(obj);
	}
	/**
	 * 根据id获取
	 */
	public SysUser getById(Long id) {
		return (SysUser) mapper.getById(id);
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
	public Pager<SysUser> queryAll(QueryFilter queryFilter) {
		Pager<SysUser> returnPager=new Pager<SysUser>();
		List<SysUser> list=null;
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
