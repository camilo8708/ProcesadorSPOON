package annotation;

public @interface annotationFeature {

	String featureName() default "";
	String parent() default "";
	boolean mandaroty() default false;
}


