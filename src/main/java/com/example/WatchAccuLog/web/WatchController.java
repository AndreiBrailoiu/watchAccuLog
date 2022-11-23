package com.example.WatchAccuLog.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.WatchAccuLog.model.Watch;
import com.example.WatchAccuLog.model.WatchRepository;

@Controller
public class WatchController {
	@Autowired
	private WatchRepository wRepository;
	private Watch watch;

	// basic replacement of login form
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	// main endpoint showcasing the db contents
	@RequestMapping(value = "/watchlist")
	public String list(Model model) {
		model.addAttribute("watches", wRepository.findAll());
		return "watchlist";
	}

	// opens the add a new watch endpoint
	@RequestMapping(value = "/watchadd")
	public String add(Model model) {
		model.addAttribute("watch", new Watch());
		return "watchadd";
	}

	// save the newly added watch to the db
	@RequestMapping(value = "/watchsave", method = RequestMethod.POST)
	public String save(Watch watch) {
		wRepository.save(watch);
		return "redirect:watchlist";
	}

	// delete selected watch by id
	@RequestMapping(value = "/watchdelete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long watchId, Model model) {
		wRepository.deleteById(watchId);
		return "redirect:../watchlist";
	}

	// delete all entries - admin restricted
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/watchdeleteall", method = RequestMethod.GET)
	public String purge(Model model) {
		wRepository.deleteAll();
		return "watchlist";
	}

	// remove all entries related to the selected watch.
	// transactional annotation handles the exeptions thrown when calling
	// a method that deletes multiple rows.
	@Transactional
	@RequestMapping(value = "/watchdestroy/{id}", method = RequestMethod.GET)
	public String destroy(@PathVariable("id") Long watchId, Model model) {
		// fetching the watch with corresponding id to access brand and caliber info
		watch = wRepository.findById(watchId).get();
		wRepository.deleteByBrandAndCaliber(watch.getBrand(), watch.getCaliber());
		return "redirect:../watchlist";
	}

	// adds a new measurement for the selected watch
	@RequestMapping(value = "/newday/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		model.addAttribute("watch", wRepository.findById(id));
		return "newday";
	}

	// collecting data for the graph
	@RequestMapping(value = "/index")
	public String gChart(Model model) {
		Map<String, Integer> chartData = new TreeMap<>();
		for (Watch watch : wRepository.findAll()) {
			chartData.put(watch.getBrand(), watch.getAccuracy());
		}
		// model.addAttribute("watches", chartData);
		model.addAttribute("watches", wRepository.findAll());
		return "index";
	}

	// RESTful service to showcase all watches currently saved in the DB;
	@RequestMapping(value = "/watches", method = RequestMethod.GET)
	public @ResponseBody List<Watch> bookListRest() {
		return (List<Watch>) wRepository.findAll();
	}

	// RESTful service to see the selected watch (by ID);
	@RequestMapping(value = "/watch/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Watch> findBookRest(@PathVariable("id") Long watchid) {
		return wRepository.findById(watchid);
	}
}