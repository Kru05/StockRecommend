package hello;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class ItemTest {
Item i=new Item();
	@Test
	public void test1()throws NoSuchFieldException, IllegalAccessException {
		 final Field field = i.getClass().getDeclaredField("name");
	        field.setAccessible(true);
	        field.set(i, "");

	        //when
	        final String result = i.getName();

	        //then
	        assertEquals("field wasn't retrieved properly", result, "");
	}
	@Test
	public void test2()throws NoSuchFieldException, IllegalAccessException {
		 final Field field = i.getClass().getDeclaredField("weight");
	        field.setAccessible(true);
	        field.set(i,0);

	        //when
	        final int a = i.getWeight();

	        //then
	        assertEquals("field wasn't retrieved properly", a,0);
	}
	@Test
	public void test3()throws NoSuchFieldException, IllegalAccessException {
		 final Field field = i.getClass().getDeclaredField("value");
	        field.setAccessible(true);
	        field.set(i,0);

	        //when
	        final int a = i.getValue();

	        //then
	        assertEquals("field wasn't retrieved properly",a,0);
	}
	@Test
	public void test4()throws NoSuchFieldException, IllegalAccessException {
		 final Field field = i.getClass().getDeclaredField("inKnapsack");
	        field.setAccessible(true);
	        field.set(i,0);

	        //when
	        final int a = i.getInKnapsack();

	        //then
	        assertEquals("field wasn't retrieved properly",a,0);
	}
	@Test
	public void test5()throws NoSuchFieldException, IllegalAccessException {
		 final Field field = i.getClass().getDeclaredField("bounding");
	        field.setAccessible(true);
	        field.set(i,1);

	        //when
	        final int a = i.getBounding();

	        //then
	        assertEquals("field wasn't retrieved properly",a,1);
	}
	@Test 
	public void test6()throws NoSuchFieldException, IllegalAccessException
	{
		i.setName("");
		 final Field field = i.getClass().getDeclaredField("name");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(i), "");
	}
	@Test 
	public void test7()throws NoSuchFieldException, IllegalAccessException
	{
		i.setWeight(0);
		 final Field field = i.getClass().getDeclaredField("weight");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(i),0);
	}
	@Test 
	public void test8()throws NoSuchFieldException, IllegalAccessException
	{
		i.setValue(0);
		 final Field field = i.getClass().getDeclaredField("value");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(i),0);
	}
	@Test 
	public void test9()throws NoSuchFieldException, IllegalAccessException
	{
		i.setInKnapsack(0);
		 final Field field = i.getClass().getDeclaredField("inKnapsack");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(i),0);
	}
	@Test
	public void test10()throws NoSuchFieldException, IllegalAccessException
	{
		i.setBounding(1);
		 final Field field = i.getClass().getDeclaredField("bounding");
	        field.setAccessible(true);
	        assertEquals("Fields didn't match", field.get(i),1);
	}
	
}
