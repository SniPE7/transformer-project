package com.ibm.ncs.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortMap<E> {
	
	
	/**
	 * 
	 * @param unsortMap
	 * @param method
	 * @param sort
	 * @return
	 */
	public Map sortByValue(Map unsortMap,final String method,final String sort){
		
		List<E> list = new LinkedList(unsortMap.entrySet());
		
		//sort list based on  comparator
		Collections.sort(list, new Comparator(){
			public int compare(Object a, Object b){

				int ret = 0;
				try{
					Method m1 = ((E)a).getClass().getMethod(method, null);
					Method m2 = ((E)b).getClass().getMethod(method, null);
				//	System.out.println("return type is "+m1.getReturnType().getName());
					if(m1.getReturnType().getName()!= "long"){
					if(sort != null && "desc".equalsIgnoreCase(sort)){
//						ret = m2.invoke((E)b,
//								null).toString().compareToIgnoreCase(m1.invoke((E)a, null).toString());
						Object test1 = m1.invoke((E)a, null);
						Object test2 = m2.invoke((E)b, null);
						ret = (test2==null?"null":test2.toString()).compareToIgnoreCase(test1==null?"null":test1.toString());
					}else{
//						ret = m1.invoke((E)a, 
//								null).toString().compareToIgnoreCase(m2.invoke((E)b, null).toString());
						Object test1 = m1.invoke((E)a, null);
						Object test2 = m2.invoke((E)b, null);
						ret = (test1==null?"null":test1.toString()).compareToIgnoreCase(test2==null?"null":test2.toString());
					}
					}else{
						if(sort != null && "desc".equalsIgnoreCase(sort)){
							ret =  (int)((Long)m2.invoke((E)b, null) - (Long)m1.invoke((E)a, null));
						}else{
							ret = (int)((Long)m1.invoke((E)a, null) - (Long)m2.invoke((E)b, null));
						}
					}
				}catch(NoSuchMethodException ne){
					System.out.println(ne);	
					ne.printStackTrace();
				}catch(IllegalAccessException ie){
					System.out.println(ie);
					ie.printStackTrace();
				}catch(InvocationTargetException it){
					System.out.println(it);
					it.printStackTrace();
				}
				return ret;
				
			
			}
		});
		
		//put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for(Iterator it = list.iterator(); it.hasNext();){
			Map.Entry entry =(Map.Entry)it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
