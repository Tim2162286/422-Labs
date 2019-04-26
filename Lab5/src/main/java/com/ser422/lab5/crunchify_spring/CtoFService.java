package com.ser422.lab5.crunchify_spring;

/**
 * @author Crunchify.com
 *  * Description: This program converts unit from Centigrade to Fahrenheit.
 * 
 */

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/crunchify", produces = "application/xml", method = RequestMethod.GET)
public class CtoFService {

	@RequestMapping("/ctofservice")
	public String convertCtoF(@RequestParam(value = "precision",required = false) String precision) {
		return convertCtoFfromInput(36.8, precision);
	}

	@RequestMapping("/ctofservice/{c}")
	public String convertCtoFfromInput(@PathVariable("c") Double c, @RequestParam(value = "precision",required = false) String precision) {
		Double fahrenheit;
		Double celsius = c;
		fahrenheit = ((celsius * 9) / 5) + 32;
		String fOut = fahrenheit.toString();
		if(precision!=null && precision.matches("\\d+"))
			fOut=String.format("%."+precision+"f",fahrenheit);

		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fOut;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
}