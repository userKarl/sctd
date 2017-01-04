package com.sc.td.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



public class ParseXml {
   
    
    public  List<String> parseStringXml(String xml,String nodeName){    
    	Document doc = null;
    	StringBuffer sbf=new StringBuffer();
    	List<String> list=new ArrayList<String>();
		try{
			  doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			  Element rootElt = doc.getRootElement(); // 获取根节点	 
			  sbf=getNodes(sbf,rootElt,nodeName);
			  list.add(sbf.toString());
		 }catch(DocumentException e){
			e.printStackTrace();
		 }catch(Exception e){
			e.printStackTrace();
		 }
		 return list;		 
    }
    
    public  StringBuffer getNodes(StringBuffer sbf,Element node,String nodeName){         
        //当前节点的名称、文本内容和属性  
//        System.out.println("当前节点名称："+node.getName());//当前节点名称  
//        System.out.println("当前节点的内容："+node.getTextTrim());//当前节点内容  
        @SuppressWarnings("unchecked")
		List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list          
        for(@SuppressWarnings("unused") Attribute attr:listAttr){//遍历当前节点的所有属性  
//            String name=attr.getName();//属性名称  
//            String value=attr.getValue();//属性的值  
//            sbf.append(attr.getValue());
//            sbf.append("|");
//            System.out.println("属性名称："+name+"属性值："+value);  
        }  
        
        
        if(nodeName.equals(node.getName())){
       	sbf.append(node.getTextTrim());
       	sbf.append("|");
        }
       
        //递归遍历当前节点所有的子节点  
        @SuppressWarnings("unchecked")
		List<Element> listElement=node.elements();//所有一级子节点的list  
        for(Element e:listElement){//遍历所有一级子节点          	 
            this.getNodes(sbf,e,nodeName);//递归  
        }  
        return sbf;
    }  
    
    

	@SuppressWarnings("unchecked")
	public static StringBuffer getNodeText(String xml,String nodeName){
    	Document doc = null;
    	StringBuffer sbf=new StringBuffer();    	
		try{
			  doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			  Element node = doc.getRootElement(); // 获取根节点	
			  List<Element> listElement=node.elements();//所有一级子节点的list  
			  for(int i=0;i<listElement.size();i++){//遍历所有一级子节点          	 
				  if(nodeName.equals(listElement.get(i).getName())){					  
					     sbf.append(listElement.get(i).getTextTrim());
					  }
		        }  			  
		 }catch(DocumentException e){
			e.printStackTrace();
		 }catch(Exception e){
			e.printStackTrace();
		 }
		return sbf;
    }

	
	public String analList0(String xml,String nodeName){
		List<String> list=parseStringXml(xml,nodeName);
		String value=list.get(0).split("\\|")[0];		
		return value;
	}

}
