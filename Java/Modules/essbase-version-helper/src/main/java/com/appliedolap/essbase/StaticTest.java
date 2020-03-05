package com.appliedolap.essbase;

import java.lang.reflect.Field;

import com.essbase.api.session.IEssbase;

public class StaticTest {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
		
		Class<?> clazz = Class.forName("com.essbase.api.session.IEssbase");
		System.out.println("Clazz: " + clazz);
		
		Field field2 = clazz.getField("JAPI_VERSION");
		System.out.println("Field2: " + field2);
		System.out.println("Field2 val: " + field2.get(clazz));
		
		// TODO Auto-generated method stub
		Field field = IEssbase.class.getField("JAPI_VERSION");
		System.out.println("Field: " + field);
		System.out.println("Field: " + field.get(IEssbase.class));

	}

}
