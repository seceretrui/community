package com.ruihe.community.web;

import com.ruihe.community.dto.AccessTokenDTO;
import com.ruihe.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.setClient_id}")
    private String clientId;

    @Value("${github.setClient_secret}")
    private  String clientSecret;

    @Value("${github.setRedirect_uri}")
    private String redirectUri;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                          @RequestParam(name="state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String token = githubProvider.getAccesToken(accessTokenDTO);
        githubProvider.getUser(token);
        return "index";
    }
}
