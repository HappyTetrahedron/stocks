package de.njsm.stocks.server.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement
public class EanNumber extends Data implements SqlAddable, SqlRemovable {

    public int id;
    public String eanCode;
    public int identifiesFood;

    public EanNumber() {
    }

    @Override
    public void fillAddStmt(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, eanCode);
        stmt.setInt(2, identifiesFood);
    }

    @Override
    public String getAddStmt() {
        return "INSERT INTO EAN_Number (number, identifies) VALUES (?,?)";
    }

    @Override
    public void fillRemoveStmt(PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, id);
    }

    @Override
    public String getRemoveStmt() {
        return "DELETE FROM EAN_Number WHERE ID=?";
    }
}
