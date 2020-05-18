package com.easyarch.utils.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.easyarch.utils.info.JdbcInfo;
import com.easyarch.utils.info.MongoInfo;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement //开启事务管理
@PropertySource("classpath:config.properties") // config.properties
@MapperScan("com.easyarch.dao.mapper")
@ComponentScan(basePackages = "com.easyarch.*")
public class DataConfig {

    @Resource
    private JdbcInfo jdbcInfo;
    @Value("${mybatis.mapperSource}")
    private String source ;
    @Value("${mybatisSource}")
    private String config;
    @Resource
    private MongoInfo mongoInfo;

    @Bean
    public DataSource getDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcInfo.getUrl());
        dataSource.setUsername(jdbcInfo.getUsername());
        dataSource.setPassword(jdbcInfo.getPassword());
        dataSource.setDriverClassName(jdbcInfo.getDriver());
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(jdbcInfo.getTypeAliasesPackage());
//        sqlSessionFactoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(source));
//        sqlSessionFactoryBean.setConfigLocation(
//                new PathMatchingResourcePatternResolver().getResource(config));
        return sqlSessionFactoryBean;
    }

//    @Bean
//    public MongoTemplate getTemplate(){
//        String defaultDataBaseName = "admin";
//        //address
//        ServerAddress address = new ServerAddress(mongoInfo.getHost(), mongoInfo.getPort());
//        //client
//        MongoCredential mongoCredential = MongoCredential.createCredential(
//                mongoInfo.getUsername(),
//                defaultDataBaseName,
//                mongoInfo.getUserpwd().toCharArray());
//        List<MongoCredential> mongoCredentials = new ArrayList<>();
//        mongoCredentials.add(mongoCredential);
//        MongoClient client = new MongoClient(address,mongoCredentials);
//        //factory
//        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(client, defaultDataBaseName);
//        //template
//        return new MongoTemplate(factory);
//    }

//    @Bean
//    public MongoTemplate getTemplate(){
//        String host ="47.93.225.242";
//        int port = 27017;
//        String username = "admin";
//        String password = "admin";
//        String defaultDataBaseName = "admin";
//
//        //address
//        ServerAddress address = new ServerAddress(host, port);
//
//        //client
//        MongoCredential mongoCredential = MongoCredential.createCredential(username, defaultDataBaseName, password.toCharArray());
//        List<MongoCredential> mongoCredentials = new ArrayList<>();
//        mongoCredentials.add(mongoCredential);
//        MongoClient client = new MongoClient(address,mongoCredentials);
//
//        //factory
//        SimpleMongoClientDbFactory factory = new SimpleMongoClientDbFactory(client, defaultDataBaseName);
//
//        //template
//        MongoTemplate template = new MongoTemplate(factory);
//
//        return template;
//    }


}
