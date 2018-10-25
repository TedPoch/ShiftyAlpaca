package ShiftyAlpaca.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/** This model class will help process the result from an ANALYZE
 * statement run against the respective database.
 *
 * The ANALYZE command adds two additional columns not found in
 * EXPLAIN or EXPLAIN EXTENDED: r_rows & r_filtered (explained below).
 *
 * See MariaDB documention on Explain & Analyze commands for added
 * information on the below columns.
 *
 * https://mariadb.com/kb/en/library/explain/
 */
@Data
@Entity
@Table(name = "explain_results")
public class AnalyzeResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long result_id;

  //Sequence number that shows in which order tables are joined.
  private int id;
  //What kind of SELECT the table comes from.
  private String select_type;
  //Alias name of table
  private String table;
  //How rows are found from the table (join type)
  private String type;
  //Existing keys in table that could be used to find rows in the table
  private List<String> possible_keys;
  //Name of the key that was used for this query
  private String key;
  //How many bytes of the key that was used (shows if we are using
  // only parts of the multi-column key).
  private int key_len;
  //The reference that is used to as the key value.
  private String ref;
  //An estimate of how many rows found in the table for each key lookup
  private int rows;
  //An observation-based counterpart of the rows column. It shows how
  // many rows were actually read from the table.
  private int r_rows;
  //Extra info about this join
  private String Extra;
  //This is a percentage estimate of the table rows that will be filtered
  // by the condition.
  private float filtered;
  //An observation-based counterpart of the filtered column. It shows which
  // fraction of rows was left after applying the WHERE condition.
  private float r_filtered;

}
