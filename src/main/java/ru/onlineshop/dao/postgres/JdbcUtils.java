package ru.onlineshop.dao.postgres;


import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
    private static Logger log = Logger.getLogger(JdbcUtils.class.getName());

    private JdbcUtils() {
    }

    public static void closeQuietly(ResultSet rs) {
        // реализует итератор
        if (rs != null) {
            try {
                rs.close();
                log.trace("result set closed");
            } catch (SQLException e) {
                log.warn("Cannot close result set", e);
                // NOP
            }
        }
    }

    public static void closeQuietly(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
                log.trace("statement closed");
            } catch (SQLException e) {
                log.warn("Cannot close statement", e);
                // NOP
            }
        }
    }

    public static void closeQuietly(Connection conn) {
        // закрывается физическое соединенние
        if (conn != null) {
            try {
                conn.close();
                log.trace("Connection closed");
            } catch (SQLException e) {
                log.warn("Cannot close connection", e);
                // NOP
            }
        }
    }

    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                // NOP
            }
        }
    }
}
