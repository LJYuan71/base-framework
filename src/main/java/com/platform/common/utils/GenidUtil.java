package com.platform.common.utils;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 产生一个唯一ID
 */
public class GenidUtil {
	/**
	 * ID校正（集群部署时的应用服务器编号）
	 */
	private static long adjust = 1;
	/**
	 * ID当前值
	 */
	private static long nextId = 0;
	/**
	 * 当前ID的上限
	 */
	private static long lastId = -1;
	private static JdbcTemplate jdbcTemplate;
	
	
	private static void init(){
		jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String path = AppUtil.getClasspath()+ "properties/app.properties".replace("/", File.separator);
		String strAdjust=FileUtil.readFromProperties(path, "genId.adjust");
		if(strAdjust!=null){
			adjust = Integer.parseInt(strAdjust);
		}
	}

	/**
	 * 获取下一段ID范围，并记录该ID计算机的bound
	 */
	private static  void getNextIdBlock() {
		if(jdbcTemplate==null){
			init();
		}
		Long bound=-1L;
		Integer incremental=-1;
		String sql="SELECT bound,incremental FROM SYS_GENID T WHERE T.ID=?";
		String upSql="UPDATE SYS_GENID  SET BOUND=? WHERE ID=?";
		try{
			Map map = jdbcTemplate.queryForMap(sql, new Object[] { adjust });
			bound = Long.parseLong(map.get("bound").toString());
			incremental = Integer.parseInt(map.get("incremental").toString());
			nextId = bound;
			lastId = bound + incremental;
			jdbcTemplate.update(upSql, new Object[] { lastId , adjust });
		}
		catch(org.springframework.dao.EmptyResultDataAccessException e){
			insertNewComputer();
		}
	}
	
	/**
	 * 不存在该计算机编号的则插入一条记录
	 */
	private static void insertNewComputer(){
		try{
			String sql="INSERT INTO SYS_GENID (id,incremental,bound) VALUES("+adjust+",10000,10000)";
			jdbcTemplate.update(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 产生一个唯一ID。 使用同步，防止重复，测试方法见main方法
	 * 
	 */
	public static synchronized long genId() {
		if (lastId <= nextId) {
			getNextIdBlock();
		}
		long _nextId = nextId++;
		return _nextId + adjust*100000000000000L;
	}
	


	/**
	 * 产生guid
	 * 
	 * @return
	 */
	public static final String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 测试类 使用两个线程进行测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 50; i++) {
		}

		// final Set<Long> set=Collections.synchronizedSet(new HashSet<Long>());
		// //final Set<Long> set=new HashSet<Long>();
		// Thread td1=new Thread(){
		// public void run() {
		// try {
		// int i=0;
		// while(i<10000)
		// {
		// Long id=genId();
		// //Long id=new Long(i);
		// //demo.add(id);
		// set.add(id);
		// i++;
		//
		// }
		// } catch (Exception e) {
		// }
		// }
		// };
		// //
		// Thread td2=new Thread(){
		// public void run() {
		// try {
		// int i=0;
		// while(i<10000)
		// {
		// Long id=genId();
		// //Long id=new Long(i);
		// set.add(id);
		// i++;
		// }
		// } catch (Exception e) {
		// }
		// }
		// };
		// long start=System.currentTimeMillis();
		// td1.start();
		// td2.start();
		// td1.join();
		// td2.join();
		// long end=System.currentTimeMillis();
		//

	}


	

}