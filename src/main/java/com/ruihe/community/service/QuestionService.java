package com.ruihe.community.service;

import com.ruihe.community.dto.PaginationDTO;
import com.ruihe.community.dto.QuestionDTO;
import com.ruihe.community.exception.CustomizeErrorCode;
import com.ruihe.community.exception.CustomizeException;
import com.ruihe.community.mapper.QuestionExtMapper;
import com.ruihe.community.mapper.QuestionMapper;
import com.ruihe.community.mapper.UserMapper;
import com.ruihe.community.model.Question;
import com.ruihe.community.model.QuestionExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer total = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(total, page, size);
        if (page < 1) {
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> list = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDTO> dtoList = list.stream().map(question -> {
            QuestionDTO questionDto = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(userMapper.selectByPrimaryKey(questionDto.getCreator()));
            return questionDto;
        }).collect(Collectors.toList());
//        for (Question question: list) {
//            QuestionDTO questionDto = new QuestionDTO() ;
//            BeanUtils.copyProperties(question, questionDto);
//            questionDto.setUser(userMapper.selectByPrimaryKey(questionDto.getCreator()));
//            dtoList.add(questionDto);
//        }
        paginationDTO.setData(dtoList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer total = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(total, page, size);
        if (page < 1) {
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page-1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<QuestionDTO> dtoList = new ArrayList<>();
        List<Question> list = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        for (Question question: list) {
            QuestionDTO questionDto = new QuestionDTO() ;
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(userMapper.selectByPrimaryKey(questionDto.getCreator()));
            dtoList.add(questionDto);
        }
        paginationDTO.setData(dtoList);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(userMapper.selectByPrimaryKey(questionDTO.getCreator()));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        try {
            if (question.getId() == null) {
                // 创建
                question.setGmtCreate(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreate());
                question.setViewCount(0);
                question.setLikeCount(0);
                question.setCommentCount(0);
                questionMapper.insert(question);
            } else {
                // 更新

                Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
                if (dbQuestion == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }

                if (dbQuestion.getCreator().longValue() != question.getCreator().longValue()) {
                    throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
                }

                Question updateQuestion = new Question();
                updateQuestion.setGmtModified(System.currentTimeMillis());
                updateQuestion.setTitle(question.getTitle());
                updateQuestion.setDescription(question.getDescription());
                updateQuestion.setTag(question.getTag());
                QuestionExample example = new QuestionExample();
                example.createCriteria()
                        .andIdEqualTo(question.getId());
                int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
                if (updated != 1) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
            }
        } catch (ConstraintViolationException conEx) {
            System.out.println(conEx);
        }

    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(q, dto);
            return dto;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}