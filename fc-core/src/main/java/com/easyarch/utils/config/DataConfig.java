package com.easyarch.utils.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.easyarch.utils.info.JdbcInfo;
import com.easyarch.utils.info.MongoInfo;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

import static java.util.Collections.singletonList;

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

    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create(
                MongoClientSettings.builder()
                        .credential(MongoCredential.createCredential("admin", "admin", "admin".toCharArray()))
                        .applyToClusterSettings(settings  -> {
                            settings.hosts(singletonList(new ServerAddress("47.93.225.242", 27017)));
                        }).build());
    }

    @Bean
    public MongoTemplate mongoTemplate() {

        return new MongoTemplate(new SimpleMongoClientDbFactory(MongoClients.create(), "admin"));
    }
}
