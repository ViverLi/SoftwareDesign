package util;

import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class SqlSessionFactoryUtil {
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private static ThreadLocal<SqlSession> session;
	
	static{
		try {
			System.out.println("user.dir:"+System.getProperty("user.dir"));			
			reader =new FileReader(System.getProperty("user.dir")+"/config/mybatisConfig.xml");
			//reader =new FileReader("config/mybatisConfig.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			session = new ThreadLocal<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static void openSession(){
		SqlSession s = session.get();
		if(s==null)
			session.set(sqlSessionFactory.openSession());
		
	}
	
	public static void beginTransaction(){
		SqlSession s = session.get();
		if(s!=null){
			try {
				s.getConnection().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			openSession();
			beginTransaction();
		}
	}

	public static void commit(){
		SqlSession s = session.get();
		if(s!=null){
				s.commit();
		}else{
			openSession();
			beginTransaction();
			commit();
		}
		
	}
	
	public static void clearCache(){
		SqlSession s = session.get();
		if(s!=null){
			s.clearCache();
		}else{
			openSession();
			clearCache();
		}
	}
	
	public static void rollback(){
		SqlSession s = session.get();
		if(s!=null){
			s.rollback();
		}
	}
	
	public static void close(){
		SqlSession s = session.get();
		if(s!=null){
			s.close();
		}
	}
	
	public static Object getMapper(Class mapper){
		try {
			SqlSession s = session.get();
			if(s!=null)
				return s.getMapper(mapper);
			else{
				openSession();
				return getMapper(mapper);
			}
		} finally {
			
		}
	}
	
	

}
