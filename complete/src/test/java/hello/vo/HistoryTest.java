package hello.vo;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class HistoryTest {
History c=new History();
	@Test
    public void test1() throws NoSuchFieldException, IllegalAccessException {
        c.setDate("");
        final Field field = c.getClass().getDeclaredField("date");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        c.setName("");
        final Field field = c.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
	@Test
    public void test3() throws NoSuchFieldException, IllegalAccessException {
        c.setCode("");
        final Field field = c.getClass().getDeclaredField("code");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
 @Test
    public void test4() throws NoSuchFieldException, IllegalAccessException {
        c.setCompanyType("");
        final Field field = c.getClass().getDeclaredField("companyType");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(c),"");
    }
 @Test
	public void test5() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("name");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getName();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test6() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("code");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getCode();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test7() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("companyType");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getCompanyType();
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test8() throws NoSuchFieldException, IllegalAccessException {
	        final Field field = c.getClass().getDeclaredField("date");
	        field.setAccessible(true);
	        field.set(c, "");
	        final String result = c.getDate();
	        assertEquals("field wasn't retrieved properly", result, "");
	}


}
