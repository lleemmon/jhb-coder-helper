package com.jhb.config.velocityEngine;

import com.jhb.common.BasePath;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class VelocityEngineConfiguration {
    @Bean
    public VelocityEngine templateVelocityEngine(){
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, BasePath.TEMPLATE_ROOT_RESOURCE_PATH);
        velocityEngine.init(p);
        return velocityEngine;
    }
}
