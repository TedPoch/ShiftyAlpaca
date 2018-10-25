package ShiftyAlpaca.router;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/** We need a Map of contexts to DataSource objects to configure our
 *  AbstractRoutingDataSource. We can also specify a default DataSource
 *  to use if there is no context set. The DataSources we use can come
 *  from anywhere but will typically be either created at runtime or
 *  looked up using JNDI.
 */
@Configuration
public class QueryRoutingConfiguration {

  /** Alpha will be the primary db where user query history and results are stored
   *
   * @return - a datasource build for the database for query/result storage
   */
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.alpha")
  public DataSource alphaDatasource() {
    return DataSourceBuilder.create().build();
  }

  /** Test database against which to run EXPLAIN
   *
   * @return - a datasource build for the targeted test DB
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.beta")
  public DataSource betaDatasource() {
    return DataSourceBuilder.create().build();
  }

  /** Creates the Map of available database connections that can be established
   *
   * @return  - routing information for the available target DBs for analyzer
   */
  @Bean
  public DataSource queryDatasource() {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(QueryDatabase.TEST_ALPHA,
            alphaDatasource());
    targetDataSources.put(QueryDatabase.TEST_BETA,
            betaDatasource());

    QueryDataSourceRouter queryDatasourceRouting
            = new QueryDataSourceRouter();
    queryDatasourceRouting.setTargetDataSources(targetDataSources);
    queryDatasourceRouting.setDefaultTargetDataSource(alphaDatasource());
    return queryDatasourceRouting;
  }
}