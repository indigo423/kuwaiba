/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.core.services.scheduling;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * The class that contains the scheduler configuration that provides jobs scheduling capabilities.
 * this class implements SchedulingConfigurer interface that implements the configureTasks method,
 * used to rewrite the spring task schedule.
 * Here we define what kind of scheduler we are going to use, the name of the thread scheduler and the size of the pool.
 *
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Configuration
@EnableScheduling
//@ConditionalOnProperty(name = "spring.scheduler.enabled")
@ConditionalOnProperty(name = "spring.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class SchedulingSpringConfiguration implements SchedulingConfigurer {
    @Value("${spring.task.scheduling.pool.size}")
    private int poolSize;
    /**
     * Register of the taskSchedule's configuration.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
    }
    /**
     * Definition of taskSchedule's configuration.
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadSchedulerJob-");
        scheduler.setPoolSize(poolSize);
        scheduler.initialize();
        return scheduler;
    }
}
