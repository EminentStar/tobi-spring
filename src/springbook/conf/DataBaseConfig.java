package springbook.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@MapperScan( // Mybatis
//  value = {
//    "com.bulabula.api.repository",
//    "com.bulabula.admin.repository",
//    "com.bulabula.batch.repository",
//    "com.bulabula.migration.repository",
//    "com.bulabula.repository",
//    "com.bulabula.batchkafka.repository"
//  })
public class DataBaseConfig {
}
