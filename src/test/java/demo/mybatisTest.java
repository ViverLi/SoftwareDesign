package demo;

import java.io.FileReader;
import java.io.Reader;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;

public class mybatisTest {
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private static ThreadLocal<SqlSession> session;

	public static void main(String[] args) {
		try {
			System.out.println("user.dir:"+System.getProperty("user.dir"));			
			reader =new FileReader(System.getProperty("user.dir")+"/src/main/resources/config/mybatisConfig.xml");
			//reader =new FileReader("config/mybatisConfig.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			session = new ThreadLocal<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SqlSession session1= sqlSessionFactory.openSession();
		UserMapper userMapper=session1.getMapper(UserMapper.class);
		User user=new User();
		user.setUsername("ccc");
		user.setPassword("123456");
		userMapper.insert(user);
		session1.close();
	}

}
