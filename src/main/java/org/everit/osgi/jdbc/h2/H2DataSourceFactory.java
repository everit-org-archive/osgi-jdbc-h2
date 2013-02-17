package org.everit.osgi.jdbc.h2;

/*
 * Copyright (c) 2011, Everit Kft.
 *
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.jdbc.DataSourceFactory;

public class H2DataSourceFactory implements DataSourceFactory {

    @Override
    public DataSource createDataSource(Properties props) throws SQLException {
        return createJdbcDataSource(props);
    }

    protected JdbcDataSource createJdbcDataSource(Properties props) throws SQLException {
        String url = null;
        String user = null;
        String password = null;
        String description = null;
        
        for (String key : props.stringPropertyNames()) {
            if (DataSourceFactory.JDBC_URL.equals(key)) {
                url = props.getProperty(key);
            } else if (DataSourceFactory.JDBC_USER.equals(key)) {
                user = props.getProperty(key);
            } else if (DataSourceFactory.JDBC_PASSWORD.equals(key)) {
                password = props.getProperty(key);
            } else if (DataSourceFactory.JDBC_DESCRIPTION.equals(key)) {
                description = props.getProperty(key);
            } else {
                throw new SQLException("Unsupported JDBC property for H2: " + key);
            }            
        }
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        if (url != null) {
            jdbcDataSource.setURL(url);
        }
        if (user != null) {
            jdbcDataSource.setUser(user);
        }
        if (password != null) {
            jdbcDataSource.setPassword(password);
        }
        if (description != null) {
            jdbcDataSource.setDescription(description);
        }
        return jdbcDataSource;
    }

    @Override
    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
        return createJdbcDataSource(props);
    }

    @Override
    public XADataSource createXADataSource(Properties props) throws SQLException {
        return createJdbcDataSource(props);
    }

    @Override
    public Driver createDriver(Properties props) throws SQLException {
        if (props != null && props.size() > 0) {
            throw new SQLException("H2 JDBC driver does not support any property to be set");
        }
        org.h2.Driver driver = new org.h2.Driver();
        return driver;
    }

}
