package gradlelab;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleScheduledJob {

    @Scheduled(fixedRate = 5000) // Executes every 5 seconds
    public void performScheduledTask() {
        // Your scheduled task logic goes here
        System.out.println("Simple scheduled job executed!");
    }
}