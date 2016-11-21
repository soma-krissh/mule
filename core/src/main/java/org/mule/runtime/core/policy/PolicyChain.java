/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core.policy;

import static org.mule.runtime.core.api.lifecycle.LifecycleUtils.initialiseIfNeeded;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.core.api.Event;
import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.construct.FlowConstructAware;
import org.mule.runtime.core.api.lifecycle.LifecycleUtils;
import org.mule.runtime.core.api.processor.MessageProcessorChain;
import org.mule.runtime.core.api.processor.Processor;
import org.mule.runtime.core.processor.chain.DefaultMessageProcessorChainBuilder;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract policy chain for handling the message processor associated to a policy.
 *
 * @since 4.0
 */
public abstract class PolicyChain
    implements Initialisable, Startable, FlowConstructAware, Stoppable, Disposable, Processor {


  @Inject
  private MuleContext muleContext;

  private List<Processor> processors;
  private MessageProcessorChain processorChain;
  private FlowConstruct flowConstruct;

  public void setProcessors(List<Processor> processors) {
    this.processors = processors;
  }

  @Override
  public final void initialise() throws InitialisationException {
    initialiseIfNeeded(processors, muleContext, flowConstruct);
    processorChain = new DefaultMessageProcessorChainBuilder().chain(this.processors).build();
    processorChain.setMuleContext(muleContext);
    processorChain.setFlowConstruct(flowConstruct);
    processorChain.initialise();
  }

  @Override
  public void start() throws MuleException {
    processorChain.start();
  }

  @Override
  public void dispose() {
    processorChain.dispose();
  }

  @Override
  public void stop() throws MuleException {
    processorChain.stop();
  }

  @Override
  public Event process(Event event) throws MuleException {
    return processorChain.process(event);
  }

  @Override
  public void setFlowConstruct(FlowConstruct flowConstruct) {
    this.flowConstruct = flowConstruct;
  }

}
