package annotation;

public @interface annotationAlt {

	String featureName() default "";
	String parent() default "";
	boolean mandaroty() default false;
}


