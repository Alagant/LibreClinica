package org.akaza.openclinica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppointmentController {
    @RequestMapping(value = "/appointments/{subjectId}", method = RequestMethod.GET)
    public String appointments(@PathVariable("subjectId") long subjectId, Model model) {
        model.addAttribute("subjectId", subjectId);
        return "admin/appointments";
    }
}
