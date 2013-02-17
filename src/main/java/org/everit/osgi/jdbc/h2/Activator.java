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

import java.util.Dictionary;
import java.util.Hashtable;

import org.h2.Driver;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Registers a {@link DataSourceFactory} for H2 database driver.
 */
public class Activator implements BundleActivator {

    private ServiceRegistration h2DataSourceFactorySR;

    @Override
    public void start(final BundleContext context) throws Exception {
        DataSourceFactory h2DataSourceFactory = new H2DataSourceFactory();
        Dictionary<String, String> props = new Hashtable<String, String>();

        Bundle h2Bundle = FrameworkUtil.getBundle(Driver.class);
        String version = "unknown";
        if (h2Bundle != null) {
            version = h2Bundle.getVersion().toString();
        }
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, Driver.class.getName());
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, "H2");
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, version);
        h2DataSourceFactorySR = context.registerService(DataSourceFactory.class.getName(),
                h2DataSourceFactory, props);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        h2DataSourceFactorySR.unregister();

    }

}
