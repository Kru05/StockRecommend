package hello.vo;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class CompanyTest {
Company c=new Company();
	@Test
	public void test1() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("name");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getName();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test2() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("code");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getCode();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test3() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("companyType");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getCompanyType();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test4() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("activeStatus");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getActiveStatus();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test5() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("lastUpdated");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getLastUpdated();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	
	 @Test
	    public void test6() throws NoSuchFieldException, IllegalAccessException {
	        c.setId(0);
	        final Field field = c.getClass().getDeclaredField("id");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(c),0);
	    }
	 @Test
	    public void test7() throws NoSuchFieldException, IllegalAccessException {
	        c.setName("");
	        final Field field = c.getClass().getDeclaredField("name");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(c),"");
	    }
	 @Test
	    public void test8() throws NoSuchFieldException, IllegalAccessException {
	        c.setCode("");
	        final Field field = c.getClass().getDeclaredField("code");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(c),"");
	    }
	 @Test
	    public void test9() throws NoSuchFieldException, IllegalAccessException {
	        c.setCompanyType("");
	        final Field field = c.getClass().getDeclaredField("companyType");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(c),"");
	    }
	 @Test
	    public void test10() throws NoSuchFieldException, IllegalAccessException {
	        c.setActiveStatus("");
	        final Field field = c.getClass().getDeclaredField("activeStatus");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(c),"");
	    }
	 @Test
	    public void test11() throws NoSuchFieldException, IllegalAccessException {
	        c.setLastUpdated("");
	        final Field field = c.getClass().getDeclaredField("lastUpdated");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(c),"");
	    }


}
