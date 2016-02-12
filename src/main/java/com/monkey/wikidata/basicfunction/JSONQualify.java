package com.monkey.wikidata.basicfunction;

import java.util.Stack;

public class JSONQualify {
	public static boolean isQualified(String s) {
		if(s.charAt(0)!='{') return false;
		Stack<Character> stack = new Stack<Character>();
		int len = s.length();
		for(int i = 0; i < len; i++) {
			if(s.charAt(i) == '{' || s.charAt(i) == '[' || s.charAt(i) == '(') {
				stack.push(s.charAt(i));
			}else if(s.charAt(i) == '}') {
				if(stack.isEmpty() || stack.pop()!='{'){
					return false;
				}
			}else if(s.charAt(i) == ']') {
				if(stack.isEmpty() || stack.pop()!='['){
					return false;
				}
			}else if(s.charAt(i) == ')') {
				if(stack.isEmpty() || stack.pop()!='('){
					return false;
				}
			}
		}
		if(stack.isEmpty()) {
			return true;
		}else{
			return false;
		}
	}
	
	
	public static String qualify(String json) {
		StringBuffer sb = new StringBuffer(json);
		while(sb.length() != 0 && sb.charAt(sb.length()-1) != '}' && sb.charAt(sb.length()-1) != ']') {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
