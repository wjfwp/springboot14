package com.coding404.myweb.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("kakaoAPI")
public class KaKaoAPI {

	public String getToken(String code) {
		
		String access_token = "";
		String refresh_token = "";
		String requestURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST"); //요청 메서드 선언
			conn.setDoOutput(true); //post요청에 필수
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); //헤더값
			
			String req = "grant_type=authorization_code"
					   + "&client_id=2de57b13d994871225b29c4242a12d19"
					   + "&redirect_uri=http://127.0.0.1:8282/user/kakao"
					   + "&code=" + code;
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter( conn.getOutputStream() ));
			bw.write(req); //문자를 씀
			bw.flush(); //데이터를 비워낸다.
			
			//응답이 돌아오면 conn객체에 담김
			if(conn.getResponseCode()== 200) {
				System.out.println("요청 성공함");
				
				BufferedReader br = new BufferedReader(new InputStreamReader( conn.getInputStream() ));
				
				String result = "";
				String str = "";
				while( (str = br.readLine() ) != null) {
					result += str;
				}
				
				System.out.println(result);
				
				
				//GSON사용해서 자바Object로 맵핑
				//파서 -> json엘리먼트 -> json오브젝트 -> 문자열
				JsonParser json = new JsonParser();
				JsonElement element = json.parse(result);
				JsonObject obj = element.getAsJsonObject();
				
				access_token = obj.get("access_token").getAsString();
				refresh_token = obj.get("refresh_token").getAsString();
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return access_token;
	}
	
	
	//카카오 유저 정보 가져오기
	public Map<String, Object> getUser(String token) {
		Map<String, Object> map = new HashMap<>();
		
		String requestURL = "https://kapi.kakao.com/v2/user/me";
		
		try {
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); //헤더값
			conn.setRequestProperty("Authorization", "Bearer " + token); //api를 사용하기 위해 발급받은 토큰값
			
			//쿼리파라미터는 없기 때문에 writer는 생략가능.
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader( conn.getInputStream() ));
				
				String result = "";
				String str = "";
				
				while( (str = br.readLine()) != null ) {
					result += str;
				}
				
				System.out.println(result);
				
				
				
				//JSON데이터에서 nickname, email만 추출
				JsonParser json = new JsonParser(); //파서객체생성
				JsonElement element = json.parse(result); //JSON엘리먼트변경
				JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject(); //JSON오브젝트추출, properties추출, 오브젝트추출
				JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

				String nickname = properties.getAsJsonObject().get("nickname").getAsString(); //JSON오브젝트추출, nickname추출, 문자열추출
				String email = kakao_account.getAsJsonObject().get("email").getAsString();
				
				
				map.put("nickname", nickname); 
				map.put("email", email);
				
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return map;
	}
}
