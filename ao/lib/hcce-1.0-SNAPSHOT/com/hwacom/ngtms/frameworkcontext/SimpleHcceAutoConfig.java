package com.hwacom.ngtms.frameworkcontext;

import com.hwacom.ngtms.hcce.frameworkcontext.HazelcastConfig;
import com.hwacom.ngtms.hcce.frameworkcontext.HcceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({NgtmsScan.class, NgtmsConfig.class, AppConfig.class, OnlineDataBaseConfig.class, HistoryDataBaseConfig.class, SchedulerConfig.class, HttpClientConfig.class, HazelcastConfig.class, HcceConfig.class})
public class SimpleHcceAutoConfig {}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\SimpleHcceAutoConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */