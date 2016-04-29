/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core.transport;

import org.mule.runtime.core.api.MuleException;
import org.mule.runtime.core.api.context.WorkManager;
import org.mule.runtime.core.api.context.WorkManagerSource;

/**
 * WorkManagerSource for connector dispatcher work manager
 */
public class DispatcherWorkManagerSource implements WorkManagerSource
{

    private final AbstractConnector connector;

    public DispatcherWorkManagerSource(AbstractConnector connector)
    {
        this.connector = connector;
    }

    @Override
    public WorkManager getWorkManager() throws MuleException
    {
        return connector.getDispatcherWorkManager();
    }
}
