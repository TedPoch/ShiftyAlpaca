//package ShiftyAlpaca.router;
//
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
///** We define our ClientDataSourceRouter to extend the Spring
// * AbstractRoutingDataSource. We implement the necessary
// * determineCurrentLookupKey method to query our ClientDatabaseContextHolder
// * and return the appropriate key.
// *
// * The AbstractRoutingDataSource implementation handles the rest of the work
// * for us and transparently returns the appropriate DataSource.
// */
//public class QueryDataSourceRouter extends AbstractRoutingDataSource {
//
//  @Override
//  protected Object determineCurrentLookupKey() {
//    return QueryDatabaseContextHolder.getQueryDatabase();
//  }
//}
