package com.sharedwealth.network;

import java.io.Serializable;

class testobject implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int value ;
String id;
public  testobject(int v, String s ){
this.value=v;
this.id=s;
}
}