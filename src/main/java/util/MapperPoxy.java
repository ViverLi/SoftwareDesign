/**
 * 
 */
package util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
/**
 * @author Tao
 * @param <T>
 * @param <T>
 *
 */
public class MapperPoxy implements InvocationHandler {
	@SuppressWarnings("rawtypes")
	private static final Map<Class, Object> BEANMAP=new HashMap<>();
	private Class<?> mapperType;
	public MapperPoxy(Class<?> mapperType) {
		this.mapperType = mapperType;
	}
	public Object getProxy(){
		Object mapper=null;
		if(BEANMAP.containsKey(mapperType))
			mapper= BEANMAP.get(mapperType);
		else
			mapper= createMapper(mapperType);
		return  mapper;
	}

	@SuppressWarnings( "unchecked" )
	private <T> Object createMapper(Class<T> mapperType) {
		SqlSession session=SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
		try {
			Object mapper= session.getMapper(mapperType);	
			BEANMAP.put(mapperType,Proxy.newProxyInstance(mapper.getClass().getClassLoader(),mapper.getClass().getInterfaces(), this));
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			session.close();
		}
		return (T)BEANMAP.get(mapperType);
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception  {
		
		SqlSession session=SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
		Object mapperObject= session.getMapper(mapperType);
		Method newMethod=null;
		if(args!=null)
		{
			@SuppressWarnings("rawtypes")
			Class[] classArray=new Class[args.length];
			for (int i=0;i<args.length;i++) {
				classArray[i]=args[i].getClass();
			}
			newMethod=mapperObject.getClass().getMethod(method.getName(), classArray);
		}else
		{
			newMethod=mapperObject.getClass().getMethod(method.getName());
		}
		Object result= null;
		try {
			result=  newMethod.invoke(mapperObject, args);
			session.commit();
		} catch (Exception e) {
			
			String argStr="";
			if(args!=null)
			{
				for (Object object : args) {
					argStr+=object.toString()+"	";
				}
			}
			e.printStackTrace();
			throw new Exception("sql error:"+newMethod.getName()+"  args:"+argStr,e);
			
		}finally {
			session.close();
		}
		return result;
		
	}
}
