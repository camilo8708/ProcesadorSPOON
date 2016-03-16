package annotation;

public @interface annotationAnd{

	String featureName() default "";
	String parent() default "";
	boolean mandaroty() default false;
}


