package com.cafe.Cafe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class cafeController {
        
        @GetMapping(path= "/sale")
        public ModelAndView Sale(){
                ModelAndView mnv = new ModelAndView("Sale");
                return mnv;
        }
        @RequestMapping(path="/sale/new_cashier")
        public ModelAndView New_cashier(){
                ModelAndView mnv1 = new ModelAndView("New_cashier");
                return mnv1;
        }
        @RequestMapping(path="/sale/new_category")
        public ModelAndView New_category(){
                ModelAndView mnv2 = new ModelAndView("New_category");
                return mnv2;
        }


}
