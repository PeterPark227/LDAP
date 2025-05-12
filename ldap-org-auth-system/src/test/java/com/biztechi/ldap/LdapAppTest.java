package com.biztechi.ldap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = LdapApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
class LdapAppTest {

	@Test
	void contextLoads() {

	}

}
