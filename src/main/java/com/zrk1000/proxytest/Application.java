package com.zrk1000.proxytest;

import com.zrk1000.proxytest.annotation.ServiceScan;
import com.zrk1000.proxytest.model.User;
import com.zrk1000.proxytest.service.TestService;
import com.zrk1000.proxytest.service.UserService;
import com.zrk1000.proxytest.spring.MyBeanDefinitionRegistryPostProcessor;
import com.zrk1000.proxytest.spring.ServiceScannerConfigurer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Collections;

/**
 *
 * @author zrk
 * @date 2016年12月15日 下午4:48:12
 */
//@MapperScan("com.zrk1000.proxytest.mapper")
//@ImportResource("spring/spring-context.xml")
@ServiceScan("com.zrk1000.proxytest.service")
@RestController
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	


	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		TestService bean = applicationContext.getBean(TestService.class);
		System.out.println(bean.toString());
	}
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Autowired
	private UserService userService;

	@Autowired
	private TestService testService;

	@RequestMapping("/user")
	public User user(){
		
		User result = userService.getUser("Tom ");
		return result;
	}
	@RequestMapping("/say")
	public String say(){
		String result = testService.say("Tom ", Collections.<String, Object>singletonMap("name",100000L));
		return result;
	}
}
