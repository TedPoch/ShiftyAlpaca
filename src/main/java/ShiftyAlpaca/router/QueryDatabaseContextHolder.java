package ShiftyAlpaca.router;

import org.springframework.util.Assert;

/** The context holder implementation is a container that stores the current
 *  context as a ThreadLocal reference. In addition to holding the reference,
 *  it should contain static methods for setting, getting, and clearing it.
 *
 *  AbstractRoutingDatasource will query the ContextHolder for the Context
 *  and will then use the context to look up the actual DataSource.
 *
 *  It’s critically important to use ThreadLocal here so that the context is
 *  bound to the currently executing thread. It’s essential to take this
 *  approach so that behavior is reliable when data access logic spans multiple
 *  data sources and uses transactions:
 *
 */
public class QueryDatabaseContextHolder {

  private static ThreadLocal<QueryDatabase> CONTEXT =
          new ThreadLocal<>();

  public static QueryDatabase getQueryDatabase() {
    return CONTEXT.get();
  }

  public static void set(QueryDatabase queryDB) {
    Assert.notNull(queryDB, "Query Database cannot be null");
    CONTEXT.set(queryDB);
  }

  public static void clear(){
    CONTEXT.remove();
  }
}
