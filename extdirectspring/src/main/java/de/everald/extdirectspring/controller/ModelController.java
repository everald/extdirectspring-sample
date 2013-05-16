package de.everald.extdirectspring.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;
import de.everald.extdirectspring.objects.GridObject;

@Controller
@RequestMapping("/scripts")
public class ModelController {

	@RequestMapping("/sample/app/model/GridObject.js")
	public void createGridModel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelGenerator.writeModel(request, response, GridObject.class, OutputFormat.EXTJS4, true);
	}
}
