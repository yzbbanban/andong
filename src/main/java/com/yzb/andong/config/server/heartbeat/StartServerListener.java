package com.yzb.andong.config.server.heartbeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @author 88403636_徐航
 * @since 16:34 2019/3/14
 */
@Component
public class StartServerListener implements ApplicationListener<ApplicationReadyEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            new Server(9995, 60).run();
            logger.info("begin start");
        }
    }
}
