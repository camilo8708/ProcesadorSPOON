package uniandes.cupi2.biblioteca.mundo.implementacion1;


/** 
 * Clase que representa al usuario.
 */
@annotation.annotationAnd(featureName = "Usuario", mandaroty = true)
public class Usuario implements java.io.Serializable , uniandes.cupi2.biblioteca.mundo.IUsuario {
    /** 
     * Constante de serialización.
     */
    private static final long serialVersionUID = -7920037289310482998L;
    
    /** 
     * Nombre del usuario.
     */
    private java.lang.String nombre;
    
    /** 
     * Clave del usuario.
     */
    private java.lang.String clave;
    
    /** 
     * Login del usuario.
     */
    private java.lang.String login;
    
    /** 
     * Libros alquilados por el usuario.
     */
    private uniandes.cupi2.collections.lista.Lista<uniandes.cupi2.biblioteca.mundo.ILibro>  librosAlquilados;
    
    /** 
     * Construye un nuevo usuario.
     * @param login Login del usuario - login != null.
     * @param clave Clave del usuario - clave != null.
     * @param nombre Nombre del usuario - nombre != null.
     */
    public Usuario(java.lang.String login ,java.lang.String clave ,java.lang.String nombre) {
        this.nombre = nombre;
        this.clave = clave;
        this.login = login;
        librosAlquilados = new uniandes.cupi2.collections.lista.Lista<uniandes.cupi2.biblioteca.mundo.ILibro> ();
    }
    
    @annotation.annotationExtends(destino = "Libro", origen = "alquilarLibro")
    @annotation.annotationFeature(featureName = "alquilarLibro", parent = "Usuario")
    public void alquilar(uniandes.cupi2.biblioteca.mundo.ILibro libro) {
        librosAlquilados.agregar(libro);
    }
    
    @annotation.annotationExtends(destino = "Libro", origen = "devolverLibro")
    @annotation.annotationFeature(featureName = "devolverLibro", parent = "Usuario")
    public void devolver(uniandes.cupi2.biblioteca.mundo.ILibro libro) {
        librosAlquilados.eliminar(libro);
    }
    
    @annotation.annotationFeature(featureName = "darNombre", parent = "Usuario")
    public java.lang.String darNombre() {
        return nombre;
    }
    
    @annotation.annotationFeature(featureName = "darLogin", parent = "Usuario")
    public java.lang.String darLogin() {
        return login;
    }
    
    @annotation.annotationFeature(featureName = "darClave", parent = "Usuario")
    public java.lang.String darClave() {
        return clave;
    }
    
    @annotation.annotationExtends(destino = "Libro", origen = "darLibrosAlquilados")
    @annotation.annotationFeature(featureName = "darLibrosAlquilados", parent = "Usuario")
    public uniandes.cupi2.collections.iterador.Iterador<uniandes.cupi2.biblioteca.mundo.ILibro>  darLibrosAlquilados() {
        return librosAlquilados.darIterador();
    }
    
    @annotation.annotationExtends(destino = "Libro", origen = "asignarLibrosAlquilados")
    @annotation.annotationFeature(featureName = "asignarLibrosAlquilados", parent = "Usuario")
    public void asignarLibrosAlquilados(uniandes.cupi2.collections.lista.Lista<uniandes.cupi2.biblioteca.mundo.ILibro>  nLibrosAlquilados) {
        librosAlquilados = nLibrosAlquilados;
    }
    
}

