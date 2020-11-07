package andrew.ren.springbootsample;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.net.URL;
import javax.servlet.Filter;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.config.DaemonConfiguration;
import com.amazonaws.xray.emitters.Emitter;
import com.amazonaws.xray.plugins.EKSPlugin;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;

@Configuration
public class XrayConfig {

  @Bean
  public Filter TracingFilter() {
    return new AWSXRayServletFilter("redisbenchmark");
  }
  
  static {
    
    try {
      DaemonConfiguration config = new DaemonConfiguration();
      config.setDaemonAddress("xray-service.default:2000");
      Emitter emitter = Emitter.create(config);
      AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EKSPlugin()).withEmitter(emitter);

      URL ruleFile = XrayConfig.class.getResource("/sampling-rules.json");
      builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));
      
      AWSXRay.setGlobalRecorder(builder.build());
    }
    catch(Exception e) {
      //error
    }

  }
}