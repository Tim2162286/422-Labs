package com.ser422.lab5.crunchify_spring;

import org.springframework.web.bind.annotation.*;

/**
 * @author Crunchify, LLC
 * 
 */
@RestController
@RequestMapping(value = "/crunchify", produces = "application/json", method = RequestMethod.GET)
public class FtoCService {

	@RequestMapping("/ftocservice")
	public String convertFtoC(@RequestParam(value = "precision",required = false) String precision) {
		return convertFtoCfromInput(98.24f,precision);
	}

	@RequestMapping("/ftocservice/{f}")
	public String convertFtoCfromInput(@PathVariable("f") float f, @RequestParam(value = "precision",required = false) String precision) {

		float celsius;
		celsius = (f - 32) * 5 / 9;
		String cOut = Float.toString(celsius);
		if(precision!=null && precision.matches("\\d+"))
			cOut=String.format("%."+precision+"f",celsius);

		return "{\"F Value\":"+f+",\"C Value\":"+cOut+"}";
	}
}