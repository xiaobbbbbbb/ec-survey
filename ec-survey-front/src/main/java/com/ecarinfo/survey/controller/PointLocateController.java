package com.ecarinfo.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "locate")
public class PointLocateController {
	
	@RequestMapping(value="pointLocate" )
	public String index(@RequestParam(value="areaId",defaultValue="0", required=false) Integer areaId, Model model){
		
		return "locate/pointLocate";
	}

}