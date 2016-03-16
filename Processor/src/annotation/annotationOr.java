package annotation;

public @interface annotationOr {

	String featureName() default "";
	String parent() default "";
	boolean mandaroty() default false;
}


