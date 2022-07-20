package net.penguincoders.budgetplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BudgetPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetPlannerApplication.class, args);
    }

}
