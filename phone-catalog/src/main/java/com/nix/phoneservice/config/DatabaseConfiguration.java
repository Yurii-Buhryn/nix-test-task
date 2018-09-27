package com.nix.phoneservice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@Slf4j
@Configuration
@EnableConfigurationProperties({LiquibaseProperties.class})
public class DatabaseConfiguration implements EnvironmentAware, InitializingBean {

    public static final String DATABASE_URL = "DATABASE_URL";
    private Environment env;
    private String databaseUrl;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    @Override
    public void afterPropertiesSet() {
        if (env.containsProperty(DATABASE_URL)) {
            databaseUrl = env.getProperty(DATABASE_URL);
        }
        log.info("Active profiles from env: {}  Default profiles: {}", env.getActiveProfiles(), env.getDefaultProfiles());
    }

    @Bean(name = "appDb", destroyMethod = "close")
    @Primary
    public DataSource dataSource() throws URISyntaxException {
        log.debug("Environment database URL: {}", databaseUrl);

        if (StringUtils.isEmpty(databaseUrl)
                && !env.containsProperty("spring.datasource.url")
                && !env.containsProperty("spring.datasource.databaseName")) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                            " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        String dataSourceClassName = env.getProperty("spring.datasource.dataSourceClassName");
        log.debug("DataSourceClassName: {}", dataSourceClassName);
        config.setDataSourceClassName(dataSourceClassName);
        config.setMaximumPoolSize(5);

        // first check if we have DB connection props in Spring Boot YML configuration
        if (hasDbPropsInConfigFile()) {
            // first check if there is a full JDCB connection URL
            if (hasJdbcConnectionUrl()) {
                String url = env.getProperty("spring.datasource.url");
                if (url.startsWith("postgres")) {
                    loadDbCredentialsProps(config, url);
                } else {
                    config.addDataSourceProperty("url", url);
                }
            } else {
                config.addDataSourceProperty("databaseName", env.getProperty("spring.datasource.databaseName"));
                config.addDataSourceProperty("serverName", env.getProperty("spring.datasource.serverName"));
                loadUserPassFromProps(config);
            }
        }
        // then check if DATABASE_URL env set
        else if (!StringUtils.isEmpty(databaseUrl)) {
            // if true, it comes from Heroku
            if (databaseUrl.startsWith("postgres")) {
                loadDbCredentialsProps(config, databaseUrl);
            }
            // else its a normal Postgres JDBC connection string
            else if (databaseUrl.startsWith("jdbc:postgres")) {
                config.addDataSourceProperty("url", databaseUrl);
            } else {
                throw new ApplicationContextException(String.format("Invalid format on DB connection string: %s", databaseUrl));
            }
        } else {
            throw new ApplicationContextException("Datasource not set up correctly");
        }

        return new HikariDataSource(config);
    }

    private void loadUserPassFromProps(HikariConfig config) {
        loadUserPassFromProps(config,
                env.getProperty("spring.datasource.username"),
                env.getProperty("spring.datasource.password"));
    }

    private void loadUserPassFromProps(HikariConfig config, String user, String password) {
        config.addDataSourceProperty("user", user);
        config.addDataSourceProperty("password", password);
    }

    private void loadDbCredentialsProps(HikariConfig config, String databaseUrl) throws URISyntaxException {
        URI dbUri = new URI(databaseUrl);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        log.debug("Heroku DATABASE_URL is now: {}", dbUrl);
        config.addDataSourceProperty("url", dbUrl);
        loadUserPassFromProps(config, username, password);
    }

    private boolean hasDbPropsInConfigFile() {
        return (env.containsProperty("spring.datasource.databaseName") &&
                env.containsProperty("spring.datasource.serverName") &&
                env.containsProperty("spring.datasource.username") ||
                env.containsProperty("spring.datasource.url"));
    }

    private boolean hasJdbcConnectionUrl() {
        return env.containsProperty("spring.datasource.url");
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("appDb") DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        if (env.acceptsProfiles("dev", "prod", "batch")) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }
}
