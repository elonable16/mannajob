package com.mannajob.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.mannajob.domain.NaverLoginBO;
import com.mannajob.service.KakaoService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;
	
	@Autowired
    private KakaoService kakao;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}

	//�α��� ù ȭ�� ��û �޼ҵ�
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session) {
		// ���̹����̵�� ���� URL�� �����ϱ� ���Ͽ� naverLoginBOŬ������ getAuthorizationUrl�޼ҵ� ȣ��
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		//redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		model.addAttribute("naverurl", naverAuthUrl);
		
		String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=2ac1c0b75032a241c45fa9363396eaf0&redirect_uri=http://192.168.0.60:8080/redirect&response_type=code";
		model.addAttribute("kakaourl", kakaoAuthUrl);
		return "login";
	}

	//���̹� �α��� ������ callbackȣ�� �޼ҵ�
	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		System.out.println("callback ȣ��");
		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		//1. �α��� ����� ������ �о�´�.
		apiResult = naverLoginBO.getUserProfile(oauthToken); // String������ json������
		/**
		 * apiResult json ���� {"resultcode":"00", "message":"success",
		 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
		 **/
		//2. String������ apiResult�� json���·� �ٲ�
		System.out.println(apiResult);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		//3. ������ �Ľ�
		//Top���� �ܰ� _response �Ľ�
		JSONObject response_obj = (JSONObject) jsonObj.get("response");
		//response�� nickname�� �Ľ�
		String nickname = (String) response_obj.get("nickname");
		System.out.println(nickname);
		//4.�Ľ� �г��� �������� ����
		session.setAttribute("sessionId", nickname); // ���� ����
		model.addAttribute("result", apiResult);
		return "/join/add";
	}
	
	//īī�� �α��� redirect
	@RequestMapping(value = "/redirect")
	public String login(@RequestParam("code") String code, HttpSession session) {
		String access_Token = kakao.getAccessToken(code);
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
	    System.out.println("login Controller : " + userInfo);
	    
	    //    Ŭ���̾�Ʈ�� �̸����� ������ �� ���ǿ� �ش� �̸��ϰ� ��ū ���
	    if (userInfo.get("email") != null) {
	        session.setAttribute("userId", userInfo.get("email"));
	        session.setAttribute("access_Token", access_Token);
	    }

		return "/join/add";
	} 
	
	// �α׾ƿ�
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) throws IOException {
		System.out.println("����� logout");
		session.invalidate();
		return "redirect:main.jsp";
	}

}