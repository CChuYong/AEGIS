package chuyong.aegis.storage;

import chuyong.aegis.cache.AEGISConfig;

import java.sql.*;
import java.util.UUID;

public class MySQLHandler {
    private Connection connection;
    public void initialize(){
        try{
           Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+AEGISConfig.MYSQL_ADDRESS+":"+AEGISConfig.MYSQL_PORT+"/"+AEGISConfig.MYSQL_DATABASE, AEGISConfig.MYSQL_USERNAME, AEGISConfig.MYSQL_PASSWORD);
            try(PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS AEGIS_DEVICE (uuid char(36) PRIMARY KEY, type INT, name VARCHAR(255))")){
                stmt.executeUpdate();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public String getName(UUID uuid, int deviceType){
        try(PreparedStatement stmt = connection.prepareStatement("SELECT name FROM AEGIS_DEVICE WHERE uuid=? AND type=?")){
            stmt.setString(1, uuid.toString());
            stmt.setInt(2, deviceType);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next())
                    return rs.getString(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return uuid.toString();
    }
}
