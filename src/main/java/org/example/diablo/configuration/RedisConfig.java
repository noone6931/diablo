package org.example.diablo.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Configuration
public class RedisConfig {

    // Redis 3.2.8 哨兵模式的属性类
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.sentinel3")
    public SentinelProperties sentinelProperties() {
        return new SentinelProperties();
    }

    @Bean(name = "sentinelConnectionFactory")
    @Primary
    public LettuceConnectionFactory sentinelConnectionFactory(SentinelProperties properties) {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration(
                properties.getMaster(), properties.getNodes()
        );
        sentinelConfig.setPassword(properties.getPassword());
        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean(name = "sentinelRedisTemplate")
    @Primary
    public StringRedisTemplate sentinelRedisTemplate(
            @Qualifier("sentinelConnectionFactory") LettuceConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    // Redis 6.2 Cluster模式的属性类
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.cluster6")
    public ClusterProperties clusterProperties() {
        return new ClusterProperties();
    }

    @Bean(name = "clusterConnectionFactory")
    public LettuceConnectionFactory clusterConnectionFactory(ClusterProperties properties) {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(properties.getNodes());
        clusterConfig.setPassword(properties.getPassword());
        clusterConfig.setMaxRedirects(properties.getMaxRedirects());
        return new LettuceConnectionFactory(clusterConfig);
    }

    @Bean(name = "clusterRedisTemplate")
    public StringRedisTemplate clusterRedisTemplate(
            @Qualifier("clusterConnectionFactory") LettuceConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
    // 哨兵模式属性类
    public static class SentinelProperties {
        private String master;
        private Set<String> nodes;
        private String password;
        private Duration timeout;

        public String getMaster() { return master; }
        public void setMaster(String master) { this.master = master; }
        public Set<String> getNodes() { return nodes; }
        public void setNodes(Set<String> nodes) { this.nodes = nodes; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Duration getTimeout() { return timeout; }
        public void setTimeout(Duration timeout) { this.timeout = timeout; }
    }

    // Cluster模式属性类
    public static class ClusterProperties {
        private List<String> nodes;
        private String password;
        private Integer maxRedirects;
        private Duration timeout;

        public List<String> getNodes() { return nodes; }
        public void setNodes(List<String> nodes) { this.nodes = nodes; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Integer getMaxRedirects() { return maxRedirects; }
        public void setMaxRedirects(Integer maxRedirects) { this.maxRedirects = maxRedirects; }
        public Duration getTimeout() { return timeout; }
        public void setTimeout(Duration timeout) { this.timeout = timeout; }
    }
}
