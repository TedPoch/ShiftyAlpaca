package ShiftyAlpaca.repository;

import ShiftyAlpaca.model.ExplainResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultRowMapper implements RowMapper<ExplainResult> {
  @Override
  public ExplainResult mapRow(ResultSet rs, int i) throws SQLException {

    ExplainResult result = new ExplainResult();
    result.setId(rs.getInt("id"));
    result.setSelect_type(rs.getString("select_type"));
    result.setQueried_table(rs.getString("table"));
    result.setType(rs.getString("type"));
//    List<String> possKeys = Arrays.asList(
//            rs.getString("possible_keys").split(","));
    result.setPossible_keys(rs.getString("possible_keys"));
    result.setQueried_key(rs.getString("key"));
    result.setKey_len(rs.getInt("key_len"));
    result.setRef(rs.getString("ref"));
    result.setExtra(rs.getString("Extra"));

/** Uncomment this row if we add functionality for ANALYZE statements
    result.setRows(rs.getInt("rows"));
    result.setR_rows(rs.getInt("r_rows"));
    result.setFiltered(rs.getFloat("filtered"));
    result.setR_filtered(rs.getFload("r_filtered"));
*/
    return result;
  }
}
