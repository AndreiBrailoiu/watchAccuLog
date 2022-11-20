package com.example.WatchAccuLog.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Watch {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String brand, caliber;
	private int accuracy;
	// date creation and pattern imposed to avoid incompatibility
	// between html and thymeleaf formats
	// additional configurations added to the application.properties file
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate start;

	public Watch() {
		super();
	}

	public Watch(String brand, String caliber, int accuracy, LocalDate start) {
		super();
		this.brand = brand;
		this.caliber = caliber;
		this.accuracy = accuracy;
		this.start = start;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCaliber() {
		return caliber;
	}

	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

}