/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.processors;

import org.mule.api.agent.Agent;
import org.mule.api.context.ContainerContext;
import org.mule.api.endpoint.Endpoint;
import org.mule.api.model.Model;
import org.mule.api.service.Service;
import org.mule.api.transformer.Transformer;
import org.mule.api.transport.Connector;
import org.mule.endpoint.MuleEndpoint;
import org.mule.util.MuleObjectHelper;
import org.mule.util.ObjectNameHelper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * <code>MuleObjectNameProcessor</code> is used to set spring ids to Mule object
 * names so the the bean id and name property on the object don't both have to be
 * set.
 */

public class MuleObjectNameProcessor implements BeanPostProcessor
{
    private boolean overwrite = false;

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException
    {
        if (MuleObjectHelper.class.getName().equals(s))
        {
            return o;
        }
        
        if (o instanceof Connector)
        {
            if (((Connector)o).getName() == null || overwrite)
            {
                ((Connector)o).setName(s);
            }
        }
        else if (o instanceof Transformer)
        {
            if (((Transformer)o).getName() == null || overwrite)
            {
               ((Transformer)o).setName(s);
            }
        }
        else if (o instanceof Endpoint)
        {
            // spring uses the class name of the object as the name if no other
            // id is set; this is no good for endpoints
            if ((((Endpoint)o).getName() == null || overwrite)
                && !MuleEndpoint.class.getName().equals(s))
            {
                final Endpoint endpoint = (Endpoint) o;
                final String name = ObjectNameHelper.getEndpointName(endpoint);
                endpoint.setName(name);
            }
        }
        else if (o instanceof Service)
        {
            if (((Service)o).getName() == null || overwrite)
            {
                ((Service)o).setName(s);
            }
        }
        else if (o instanceof Model)
        {
            if (((Model)o).getName() == null || overwrite)
            {
                ((Model)o).setName(s);
            }
        }
        else if (o instanceof Agent)
        {
            ((Agent)o).setName(s);
        }
        else if (o instanceof ContainerContext)
        {
            ((ContainerContext)o).setName(s);
        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException
    {
        return o;
    }

    public boolean isOverwrite()
    {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite)
    {
        this.overwrite = overwrite;
    }

}
