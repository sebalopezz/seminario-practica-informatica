public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String telefono;

    public Usuario(int id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }
}
