package com.example.cassandra;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

@Configuration
public class TransactionConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "test_keyspace";
    }

//    @Bean
//    public CqlSession cqlSession() {
//        return CqlSession.builder()
//                .withLocalDatacenter("datacenter1")
//                .build();
//    }

    @Bean
    public CassandraAdminTemplate cassandraAdminTemplate(CassandraConverter converter, CqlSession cqlSession) {
        return new CassandraAdminTemplate(cqlSession, converter);
    }

    @Bean
    public CassandraTemplate cassandraTemplate(CqlSession cqlSession, CassandraConverter converter) {
        return new CassandraTemplate(cqlSession, converter);
    }

    @Bean
    public CassandraMappingContext cassandraMappingContext() {
        return new CassandraMappingContext();
    }
}
