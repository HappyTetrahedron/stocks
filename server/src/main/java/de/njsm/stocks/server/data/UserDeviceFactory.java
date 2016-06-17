package de.njsm.stocks.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDeviceFactory extends DataFactory {

    public static UserDeviceFactory f = new UserDeviceFactory();

    @Override
    public String getQuery() {
        return "SELECT * FROM User_device";
    }

    @Override
    public Data createData(ResultSet rs) throws SQLException {
        UserDevice d = new UserDevice();
        d.id = rs.getInt("ID");
        d.name = rs.getString("name");
        d.userId = rs.getInt("belongs_to");
        d.lastUpdate = rs.getTimestamp("last_poll");
        return d;
    }
}
