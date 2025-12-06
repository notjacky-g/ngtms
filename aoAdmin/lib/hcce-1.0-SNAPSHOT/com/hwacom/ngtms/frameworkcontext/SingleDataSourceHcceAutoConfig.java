package com.hwacom.ngtms.frameworkcontext;

import com.hwacom.ngtms.hcce.frameworkcontext.HazelcastConfig;
import com.hwacom.ngtms.hcce.frameworkcontext.HcceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({NgtmsScan.class, NgtmsConfig.class, AppConfig.class, OnlineDataBaseConfig.class, SchedulerConfig.class, HttpClientConfig.class, HazelcastConfig.class, HcceConfig.class})
public class SingleDataSourceHcceAutoConfig {}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\SingleDataSourceHcceAutoConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */