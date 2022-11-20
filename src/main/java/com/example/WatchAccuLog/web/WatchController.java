package com.example.WatchAccuLog.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	/*
	@RequestMapping(value = "/index")
	public String index(Model model) {
		Map<String, Integer> graphData = new TreeMap<>();
		List<Watch> wList = (List<Watch>) wRepository.findAll();

		for (int i = 0; i < wList.size(); i++) {
			graphData.put(wList.get(i).getBrand(), graphData.get(wList.get(i).getBrand()) + wList.get(i).getAccuracy());
		}
		model.addAttribute("graphData", graphData);
		return "index";
	}*/
}