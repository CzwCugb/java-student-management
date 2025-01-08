package LoginIn;
import java.sql.*;

public class MySqlConnector {

    private static String url = "jdbc:mysql://localhost:3306/school?";
    public static String sqlUser = "root";
    private static String sqlPassword = "YourPassword";
    private static Connection connection = null;
    private static Statement statement = null;

    public MySqlConnector() {
        try {
            connection = DriverManager.getConnection(url, sqlUser, sqlPassword);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //在java中增删改查的相关函数
    public ResultSet sqlQuery(String sql) throws SQLException {
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

    public void sqlUpdate(String table_, String column_,String Value_,String where_) throws SQLException {
        if(Value_.isEmpty()) return;
        if(!Value_.equals("NULL")){
            Value_ = "'" + Value_ + "'";
        }
        String sql = "UPDATE " + table_ + " SET " + column_ + " = " + Value_ + " " + where_;
        statement.executeUpdate(sql);
    }

    public void sqlInsert(String table_,String[] values_) throws SQLException{
        String sql = "INSERT INTO " + table_ + " VALUES(";
        for(int i = 0 ; i < values_.length ; i ++){
            if(values_[i] != "null"){
                sql = sql + "'" + values_[i] + "'";
            }else{
                sql = sql + values_[i];
            }
            if(i != values_.length -1) sql = sql + ",";
        }
        sql = sql + ") ";
        statement.executeUpdate(sql);
    }

    public void sqlDelete(String table_,String where_) throws SQLException{
        String sql = "DELETE FROM  " + table_;
        sql = sql + " " + where_;
        statement.executeUpdate(sql);
    }

}
