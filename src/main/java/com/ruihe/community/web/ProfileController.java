package com.ruihe.community.web;

import com.ruihe.community.dto.PaginationDTO;
import com.ruihe.community.model.User;
import com.ruihe.community.service.NotificationService;
import com.ruihe.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if(user==null) {
            return "redirect:https://github.com/login/oauth/authorize?client_id=f0ccd422426329cd3018&redirect_uri=http://localhost:8080/callback&scope=user&state=1";
        }

        if("questions".equals(action)) {
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            model.addAttribute("paginationDTOS", paginationDTO);
        }
        if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
            model.addAttribute("paginationDTOS", paginationDTO);
        }
        return "profile";
    }
}