package code;


@annotation.Feature(featureName = "Class")
public class TestClass {
    int i;
    
    java.lang.String s;
    
    java.lang.Object o;
    
    @annotation.Feature(featureName = "Constructor")
    public TestClass() {
    }
    
    @annotation.Feature(featureName = "Metodo")
    private void foo() {
        (this.i)++;
    }
    
}

