package com.ruihe.community.web;

import com.ruihe.community.dto.CommentDTO;
import com.ruihe.community.dto.QuestionDTO;
import com.ruihe.community.enums.CommentTypeEnum;
import com.ruihe.community.model.Question;
import com.ruihe.community.service.CommentService;
import com.ruihe.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "/question";
    }
}