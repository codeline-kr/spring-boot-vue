package kr.codeline.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import kr.codeline.demo.common.util.Util;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(Util.encrypt("1234qwer"));
	}

}
