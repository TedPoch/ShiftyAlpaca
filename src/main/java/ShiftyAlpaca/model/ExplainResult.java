package ShiftyAlpaca.model;

import javax.persistence.*;
import java.io.Serializable;

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
@Entity
@Table(name = "explain_results")
public class ExplainResult implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long result_id;

  //Sequence number that shows in which order tables are joined.
  private int id;
  //What kind of SELECT the table comes from.
  private String select_type;
  //Alias name of table (table)
  private String queried_table;
  //How rows are found from the table (join type)
  private String type;
  //Existing keys in table that could be used to find rows in the table
  private String possible_keys;
  //Name of the key that was used for this query (key)
  private String queried_key;
  //How many bytes of the key that was used (shows if we are using
  // only parts of the multi-column key).
  private int key_len;
  //The reference that is used to as the key value.
  private String ref;
  //Extra info about this join
  private String Extra;

  public ExplainResult(){}

  public Long getResult_id() {
    return result_id;
  }

  public void setResult_id(Long result_id) {
    this.result_id = result_id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSelect_type() {
    return select_type;
  }

  public void setSelect_type(String select_type) {
    this.select_type = select_type;
  }

  public String getQueried_table() {
    return queried_table;
  }

  public void setQueried_table(String queried_table) {
    this.queried_table = queried_table;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPossible_keys() {
    return possible_keys;
  }

  public void setPossible_keys(String possible_keys) {
    this.possible_keys = possible_keys;
  }

  public String getQueried_key() {
    return queried_key;
  }

  public void setQueried_key(String queried_key) {
    this.queried_key = queried_key;
  }

  public int getKey_len() {
    return key_len;
  }

  public void setKey_len(int key_len) {
    this.key_len = key_len;
  }

  public String getRef() {
    return ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public String getExtra() {
    return Extra;
  }

  public void setExtra(String extra) {
    Extra = extra;
  }

  /**Uncomment this block if we add functionality for ANALYZE statements
 *
  //An estimate of how many rows found in the table for each key lookup
  private int rows;
  //An observation-based counterpart of the rows column. It shows how
  // many rows were actually read from the table.
  private int r_rows;
  //This is a percentage estimate of the table rows that will be filtered
  // by the condition.
  private float filtered;
  //An observation-based counterpart of the filtered column. It shows which
  // fraction of rows was left after applying the WHERE condition.
  private float r_filtered;
*/

}
