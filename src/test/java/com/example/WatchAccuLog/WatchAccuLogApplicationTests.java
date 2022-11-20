package com.example.WatchAccuLog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.WatchAccuLog.model.Watch;
import com.example.WatchAccuLog.model.WatchRepository;
import com.example.WatchAccuLog.web.WatchController;

@SpringBootTest
class WatchAccuLogApplicationTests {
	@Autowired
	WatchController wController;

	@Autowired
	WatchRepository wRepository;

	// Controller instanced properly
	@Test
	void contextLoads() {
		assertThat(wController).isNotNull();
	}

	// Watch addition to db implemented correctly
	@Test
	void newWatch() {
		Watch watch = new Watch("Seiko", "6R15", -5, null);
		wRepository.save(watch);
		assertThat(watch.getId()).isNotNull();
	}

	// Single entry removal (delete by ID)
	@Test
	void deleteWatch() {
		Watch watch = new Watch("Seiko", "6R15", -5, null);
		wRepository.save(watch);
		wRepository.deleteById(watch.getId());
		assertThat(wRepository.count()).isEqualTo(310L);
	}

	// Total removal of a watch (all entries)
	@Transactional
	@Test
	void killWatch() {
		wRepository.save(new Watch("Seiko", "6R15", -5, null));
		wRepository.deleteByBrandAndCaliber("Seiko", "6R15");
		assertThat(wRepository.count()).isEqualTo(310L);
	}

}
