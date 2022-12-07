package com.smchoi.matzip.controller;

import com.smchoi.matzip.entities.data.PlaceEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.smchoi.matzip.controller.HomeController")
@RequestMapping(value = "/")
public class HomeController {
    @RequestMapping(value = "/",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getIndex() {
        PlaceEntity place = new PlaceEntity();
        place.setName("김밥천국")
                .setHomepage("https://www.kimbabheaven.co.kr")
                .setLatitude(0)
                .setLongitude(0);

        ModelAndView modelAndView = new ModelAndView("/index");
        return modelAndView;
    }
}
