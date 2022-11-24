package com.example.WatchAccuLog;

import java.time.LocalDate;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.WatchAccuLog.model.UserEntity;
import com.example.WatchAccuLog.model.UserRepository;
import com.example.WatchAccuLog.model.Watch;
import com.example.WatchAccuLog.model.WatchRepository;

@SpringBootApplication
public class WatchAccuLogApplication {
	private static final Logger log = LoggerFactory.getLogger(WatchAccuLogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WatchAccuLogApplication.class, args);
	}

	// generating a random number in between -25 and + 35 used to populate the table
	// with initial values. This range is considered to be "standard" level
	// of accuracy for mechanical watches
	private int generateRandom() {
		Random random = new Random();
		return random.nextInt(35 - (-25)) + (-25);
	}

	// initial db population for testing and showcase purposes
	@Bean
	public CommandLineRunner demo(WatchRepository repository, UserRepository urepository) {
		return (args) -> {

			// getting today's date as a single var for all the watches added (today)
			LocalDate start = LocalDate.now();

			log.info("Populating DB with preset values");

			// preset watches are added within a loop
			// accuracy is randomized per watch
			// dates increased automatically to simulate a daily usage
			for (int i = 0; i < 31; i++) {
				repository.save(new Watch("Seiko", "6R27", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Omega", "8806", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Longines", "ETA 2892-2", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Tag Heuer", "Valjoux 7750", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Tissot", "ETA 2824-2", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Rolex", "3135", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Breitling", "ETA 2892-2", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Oris", "400", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Orient", "F6922", generateRandom(), start.plusDays(i)));
				repository.save(new Watch("Certina", "ETA 2824-2", generateRandom(), start.plusDays(i)));
			}

			// user/user and admin/admin profile creation
			/*urepository.save(
					new UserEntity("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER"));
			urepository.save(
					new UserEntity("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN"));
					*/
			log.info("Fetching all added watches");
			for (Watch watch : repository.findAll()) {
				log.info(watch.toString());
			}
		};
	}

}
