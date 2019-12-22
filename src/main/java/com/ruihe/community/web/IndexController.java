package com.ruihe.community.web;

import com.ruihe.community.dto.PaginationDTO;
import com.ruihe.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search
                        ) {

        PaginationDTO paginationDTOS = questionService.list(search, page, size);
        model.addAttribute("paginationDTOS", paginationDTOS);
        model.addAttribute("search", search);
        return "index";
    }
}
