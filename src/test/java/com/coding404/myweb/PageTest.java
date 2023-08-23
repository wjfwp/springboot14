package com.coding404.myweb;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.product.service.ProductMapper;
import com.coding404.myweb.util.Criteria;

@SpringBootTest
public class PageTest {

	
	@Autowired
	private ProductMapper productMapper;
	
//	@Test
//	public void testCode01() {
//		
//		for(int i = 1; i <= 100; i++) {
//			ProductVO vo = ProductVO.builder()
//					.prod_enddate("2023-06-15")
//					.prod_writer("aaa123")
//					.prod_name("test" + i)
//					.prod_price(1000 + i)
//					.prod_count(100 + i)
//					.prod_discount(1 + i)
//					.prod_purchase_yn("Y")
//					.prod_content("abcdef" + i)
//					.prod_comment("test")
//					.build();
//			productMapper.productRegist(vo);
//		}
//	}
	
	
	@Test
	public void testCode02() {
		
		//Criteria cri = new Criteria(1, 10); //1번페이지, 10개데이터
		//Criteria cri = new Criteria(2, 10); //2번페이지, 10개데이터
		Criteria cri = new Criteria(1, 100); //2번페이지, 100데이터
		
		ArrayList<ProductVO> list = productMapper.getList("admin", cri);
		System.out.println(list.toString());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
