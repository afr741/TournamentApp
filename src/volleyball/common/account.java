package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class account implements Serializable{
	String name ;
	String email;
	String password;
	
	public account(String n, String e){
		setName(n);
		name = getName();
		setEmail(e);
		email = getEmail();
	}
	public account(String n){
		setName(n);
	}
	public void setName(String n){
		this.name = n;
	}
	public void setEmail(String e){
		this.email = e;
	}
	public void setPass(String p){
		this.password = p;
	}
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public String getPass(){
		return password;
	}
}