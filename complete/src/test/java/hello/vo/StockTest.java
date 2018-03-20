package hello.vo;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class StockTest {
Stock c=new Stock();
@Test
public void test1() throws NoSuchFieldException, IllegalAccessException {
        final Field field = c.getClass().getDeclaredField("Code");
        field.setAccessible(true);
        field.set(c, "");
        final String result = c.getCode();
        assertEquals("field wasn't retrieved properly", result, "");
}
@Test
public void test2() throws NoSuchFieldException, IllegalAccessException {
        final Field field = c.getClass().getDeclaredField("Date");
        field.setAccessible(true);
        field.set(c, "");
        final String result = c.getDate();
        assertEquals("field wasn't retrieved properly", result, "");
}
@Test
public void test8() throws NoSuchFieldException, IllegalAccessException {
    c.setCode("");
    final Field field = c.getClass().getDeclaredField("Code");
    field.setAccessible(true);
    assertEquals("Fields didn't match", field.get(c),"");
}
@Test
public void test9() throws NoSuchFieldException, IllegalAccessException {
    c.setDate("");
    final Field field = c.getClass().getDeclaredField("Date");
    field.setAccessible(true);
    assertEquals("Fields didn't match", field.get(c),"");
}
}
