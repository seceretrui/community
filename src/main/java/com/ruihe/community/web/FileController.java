package com.ruihe.community.web;

import com.ruihe.community.dto.FileDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seceretrui 2019/12/20/16:06
 */
@RestController
public class FileController {
    @RequestMapping("/file/upload")
    public FileDTO upload() {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("上传成功");
        fileDTO.setUrl("/images/cat.jpeg");
        return fileDTO;
    }
}
