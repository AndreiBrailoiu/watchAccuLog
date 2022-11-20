package com.example.WatchAccuLog.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WatchRepository extends CrudRepository<Watch, Long> {
	// method added to quickly handle a watch removal from the collection;
	// all entries related to the selected watch will be purged from db.
	List<Watch> deleteByBrandAndCaliber(@Param("brand") String brand, @Param("caliber") String caliber);

	/*
	 * not supported operations; deprecated or incompatible with newer Spring
	 * versions
	 * 
	 * @Modifying
	 * 
	 * @Query("delete from Watch w where w.brand=:brand and w.caliber=:caliber")
	 * List<Long> customDelete(@Param("brand") String brand, @Param("caliber")
	 * String caliber);
	 */
}
