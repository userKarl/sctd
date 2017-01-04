package com.sc.td;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User implements Iterable<User>{  
	private int id;
	private int age;
	 private List<User> userList=new ArrayList<User>();  
	public User(int id,int age){
		this.id = id;
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public Iterator<User> iterator() {
		// TODO Auto-generated method stub
		 return new MyIterator();//返回一个MyIterator实例对象  
	}
	
	class MyIterator implements Iterator<User>{
		private int index =0;  
		@Override  
        public boolean hasNext() {  
            return index!=userList.size();  
        }  
  
        @Override  
        public User next() {  
            return userList.get(index++);  
        }  
		
	}
}
