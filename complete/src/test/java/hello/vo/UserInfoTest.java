package hello.vo;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class UserInfoTest {
UserInfo c=new UserInfo();
	@Test
	public void test1() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("id");
	        field.setAccessible(true);
	        field.set(c,0);
	        final int result = c.getId();
	        assertEquals("field wasn't retrieved properly", result,0);
	}
	@Test
	public void test2() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("UserName");
	        field.setAccessible(true);
	        field.set(c,"");
	        final String result = c.getUserName();
	        assertEquals("field wasn't retrieved properly", result,"");
	}
	@Test
	public void test3() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("Password");
	        field.setAccessible(true);
	        field.set(c,"");
	        final String result = c.getPassword();
	        assertEquals("field wasn't retrieved properly", result,"");
	}
	@Test
	public void test4() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("Gender");
	        field.setAccessible(true);
	        field.set(c,"");
	        final String result = c.getGender();
	        assertEquals("field wasn't retrieved properly", result,"");
	}
	@Test
	public void test5() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("DOB");
	        field.setAccessible(true);
	        field.set(c,"");
	        final String result = c.getDOB();
	        assertEquals("field wasn't retrieved properly", result,"");
	}
	@Test
	public void test6() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("Email");
	        field.setAccessible(true);
	        field.set(c,"");
	        final String result = c.getEmail();
	        assertEquals("field wasn't retrieved properly", result,"");
	}
	@Test
	public void test7() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("Risk");
	        field.setAccessible(true);
	        field.set(c,"");
	        final String result = c.getRisk();
	        assertEquals("field wasn't retrieved properly", result,"");
	}
	@Test
    public void test8() throws NoSuchFieldException, IllegalAccessException {
        c.setId(0);
        final Field field = c.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),0);
    }
	@Test
    public void test9() throws NoSuchFieldException, IllegalAccessException {
        c.setUserName("");
        final Field field = c.getClass().getDeclaredField("UserName");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test10() throws NoSuchFieldException, IllegalAccessException {
        c.setPassword("");
        final Field field = c.getClass().getDeclaredField("Password");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test11() throws NoSuchFieldException, IllegalAccessException {
        c.setGender("");
        final Field field = c.getClass().getDeclaredField("Gender");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test12() throws NoSuchFieldException, IllegalAccessException {
        c.setDOB("");
        final Field field = c.getClass().getDeclaredField("DOB");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test13() throws NoSuchFieldException, IllegalAccessException {
        c.setRisk("");
        final Field field = c.getClass().getDeclaredField("Risk");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test14() throws NoSuchFieldException, IllegalAccessException {
        c.setEmail("");
        final Field field = c.getClass().getDeclaredField("Email");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
}
