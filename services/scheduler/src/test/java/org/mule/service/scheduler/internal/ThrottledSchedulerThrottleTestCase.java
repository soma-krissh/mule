/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.service.scheduler.internal;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mule.service.scheduler.ThreadType.CUSTOM;

import org.mule.runtime.core.util.concurrent.Latch;
import org.mule.tck.probe.JUnitLambdaProbe;
import org.mule.tck.probe.PollingProber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;

@Features("Scheduler Throttling")
public class ThrottledSchedulerThrottleTestCase extends BaseDefaultSchedulerTestCase {

  private static final int THROTTLE_SIZE = 2;

  @Test
  @Description("Tests that a task submitted in excess of 'maxConcurrentTasks' waits until another task finishes before executing.")
  public void throttledTask() throws InterruptedException {
    final ScheduledExecutorService scheduler = createExecutor();
    final Latch latch = new Latch();

    for (int i = 0; i < THROTTLE_SIZE; ++i) {
      scheduler.execute(() -> {
        awaitLatch(latch);
      });
    }

    final ExecutorService executor = Executors.newSingleThreadExecutor();
    final Future<?> throttledSubmission = executor.submit(() -> {
      scheduler.execute(() -> {
        // Nothing to do
      });
    });

    sleep(10);
    assertThat(throttledSubmission.isDone(), is(false));

    latch.countDown();

    new PollingProber(100, 10).check(new JUnitLambdaProbe(() -> {
      assertThat(throttledSubmission.isDone(), is(true));
      return true;
    }));
  }

  @Override
  protected ScheduledExecutorService createExecutor() {
    return new ThrottledScheduler(BaseDefaultSchedulerTestCase.class.getSimpleName(), sharedExecutor, 1, sharedScheduledExecutor,
                                  sharedQuartzScheduler, CUSTOM, THROTTLE_SIZE, EMPTY_SHUTDOWN_CALLBACK);
  }

}
