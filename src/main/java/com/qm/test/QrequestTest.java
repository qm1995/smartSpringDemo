package com.qm.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.qm.framework.handlerMapping.QRequest;

public class QrequestTest {
	
	@Test
	public void test_equals(){
		QRequest q1 = new QRequest("/user/edit", "get");
		QRequest q2 = new QRequest("/user/edit", "get");
		Map<QRequest, Integer> map = new HashMap<>();
		map.put(q1, 1);
		map.put(q2, 2);
		System.out.println(map.size()+":"+map.get(q1));
	}
}
