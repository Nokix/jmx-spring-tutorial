package conteco.gmbh.jmxspringtutorial;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ManagedResource(objectName = "MyMBeans:category=MBean,objectName=testBean", log = true)
public class MyController {
    private  final Log logger = LogFactory.getLog(MyController.class);

    private final Counter counter;

    public MyController(MeterRegistry meterRegistry) {
        this.counter = meterRegistry.counter("testBeanCounter");
    }

    @ManagedAttribute
    public double getCounter() {
        logger.info("get the counter");
        return counter.count();
    }

    @GetMapping("/greeting")
    @ManagedOperation
    public String greeting() {
        logger.info("greet");
        counter.increment();
        return "Hallo";
    }

    @GetMapping("/greeting/{name}")
    @ManagedOperation
    @ManagedOperationParameter(name = "name", description = "Name for greeting")
    public String greetWithName(@PathVariable String name) {
        logger.info("greet "+ name);
        counter.increment();
        return "Hallo " + name;
    }
}
