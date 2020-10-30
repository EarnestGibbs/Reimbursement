package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gibbs.data.ReimbursementStatusPostgres;
import com.gibbs.models.User;

public class JunitTestModel {

	@Test
	public void testSetUserName() {
		User user = new User();
		user.setUsername("testUser");
		assertEquals("testUser",user.getUsername());
	}
	
	@Test
	public void testFailUserName() {
		User user = new User();
		user.setUsername("FailTest");
		assertNotEquals("testUser",user.getUsername());
	}
	

	
	

}
