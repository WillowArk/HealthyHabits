package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.backend.users.User;

public class MySqlUserUtils extends MySqlUtils {
    static final String TABLE_NAME = "Users";
    static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (ID int NOT NULL AUTO_INCREMENT, " +
            "username varchar(255) NOT NULL, DOB varchar(10), " +
            "Constraint PK_User PRIMARY KEY (ID));";
    static final String INSERT_USER_TEMPLATE = "INSERT INTO " + TABLE_NAME + " (username, DOB) VALUES ('%s', '%s');";
    static final String QUERY_USER_TEMPLATE = "SELECT ID FROM " + TABLE_NAME + " WHERE username='%s';";

    public MySqlUserUtils() throws SQLException {
        super();
    }

    public MySqlUserUtils(Connection conn) {
        super(conn);
    }

    public boolean create_table() {
        return executeUpdate(CREATE_TABLE_SQL);
    }

    public boolean insertUser(User user) {
        return insertUser(user.username, user.dateOfBirth);
    }

    public boolean insertUser(String username, String dateOfBirth) {
        String sqlUpdateQuery = String.format(INSERT_USER_TEMPLATE, username, dateOfBirth);
        return executeUpdate(sqlUpdateQuery);
    }

    public int findUserId(String username) {
        try(Statement stmt = conn.createStatement()) {
            String sqlQuery = String.format(QUERY_USER_TEMPLATE, username);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) {
                return rs.getInt("ID");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int countUsers() {
        return countRows(TABLE_NAME);
    }

    public static void main(String[] args) throws SQLException {
        MySqlUserUtils mySqlUserUtils = new MySqlUserUtils();
        String dateOfBirth = "01/01/2001";
        int nUsers = mySqlUserUtils.countUsers();
        System.out.println("old nUsers = " + nUsers);

        String new_username = "user" + String.valueOf(nUsers + 1);
        mySqlUserUtils.insertUser(new_username, dateOfBirth);
        int user_id = mySqlUserUtils.findUserId(new_username);
        System.out.println("user_id = " + user_id);

        nUsers = mySqlUserUtils.countUsers();
        System.out.println("new nUsers = " + nUsers);
    }
}

