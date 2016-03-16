package code;

import annotation.annotationFeature;

@annotationFeature(featureName="Class")
public class TestClass {

	int i;
	String s;
	Object o;
	
	@annotationFeature(featureName="Constructor")
	public TestClass(){}
	
	@annotationFeature(featureName="Metodo")
	private void foo(){
		this.i++;
	}
}
