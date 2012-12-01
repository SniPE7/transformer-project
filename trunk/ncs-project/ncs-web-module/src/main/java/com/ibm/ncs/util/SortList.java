package com.ibm.ncs.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortList<E> {
	public void Sort(List<E> list,final String method,final String sort){
		Collections.sort(list,new Comparator(){
			public int compare(Object a,Object b){
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
	}
	
	
	
	public void sortmulti(List<E> list,final String method1,final String method2,final String method3){
		Collections.sort(list, new Comparator(){
			public int compare(Object a,Object b){
				int ret = 0;
				try{
					Method ma1 = ((E)a).getClass().getMethod(method1, null);
					Method mb1 = ((E)b).getClass().getMethod(method1, null);
					Method ma2 = ((E)a).getClass().getMethod(method2, null);
					Method mb2 = ((E)b).getClass().getMethod(method2, null);
					Method ma3 = ((E)a).getClass().getMethod(method3, null);
					Method mb3 = ((E)b).getClass().getMethod(method3, null);
					
					
//					if(ma1.invoke((E)a, null).toString().compareToIgnoreCase(mb1.invoke((E)b, null).toString())!= 0){
//						ret = ma1.invoke((E)a, null).toString().compareToIgnoreCase(mb1.invoke((E)b, null).toString());
//					}else if(ma2.invoke((E)a, null).toString().compareToIgnoreCase(m22.invoke((E)b, null).toString()) != 0){
//						ret = ma2.invoke((E)a, null).toString().compareToIgnoreCase(m22.invoke((E)b, null).toString());
//					}else{
//						//if(method3 != ""){
//						ret = m13.invoke((E)a, null).toString().compareToIgnoreCase(m23.invoke((E)b, null).toString());
//						//}
//					}
					
					Object testA1 = ma1.invoke((E)a, null);
					Object testB1 = mb1.invoke((E)b, null);
					Object testA2 = ma2.invoke((E)a, null);
					Object testB2 = mb2.invoke((E)b, null);
					Object testA3 = ma3.invoke((E)a, null);
					Object testB3 = mb3.invoke((E)b, null);
					
					if((testA1==null?"null":testA1.toString()).compareToIgnoreCase(testB1==null?"null":testB1.toString())!= 0){
						ret = (testA1==null?"null":testA1.toString()).compareToIgnoreCase(testB1==null?"null":testB1.toString());
					}else if((testA2==null?"null":testA2.toString()).compareToIgnoreCase(testB2==null?"null":testB2.toString()) != 0){
						ret = (testA2==null?"null":testA2.toString()).compareToIgnoreCase(testB2==null?"null":testB2.toString());
					}else{
						//if(method3 != ""){
						ret = (testA3==null?"null":testA3.toString()).compareToIgnoreCase(testB3==null?"null":testB3.toString());
						//}
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
	}

}
