package principal.persistencia;

import java.util.Collection;
import principal.entidades.Libro;

/**
 *
 * @author javie
 */
public final class LibroDAO extends DAO {

    public void guardarLibro(Libro libro) {
        super.guardar(libro);
    }

    public Libro buscarLibroPorISBN(Long ISBN) {
        Libro libroAux = (Libro) em.createQuery("SELECT l"
                                                + " FROM Libro l"
                                                + " JOIN l.editorial e"
                                                + " JOIN l.autor a"
                                                + " WHERE l.isbn = :isbn").
                                                setParameter("isbn", ISBN).
                                                getSingleResult();
        return libroAux;
    }

    public Libro buscarLibroPorTitulo(String titulo) {
        Libro libroAux1 = (Libro) em.createQuery("SELECT l"
                                                + " FROM Libro l"
                                                + " WHERE l.titulo = :titulo").
                                                setParameter("titulo", titulo).
                                                getSingleResult();
        return libroAux1;
    }

    public Collection <Libro> buscarLibroPorAutor(String buscar){
        Collection<Libro> todosLibros = null;
        todosLibros = em.createQuery("SELECT l "
                                    + "FROM Libro l "
                                    + "JOIN l.autor a "
                                    + "WHERE a.nombre = :nombre").
                                    setParameter("nombre", buscar).
                                    getResultList();
        return todosLibros;
    }
    
    public Collection <Libro> listarTodosLibros (){
        Collection<Libro> todosLibros = null;
        todosLibros = em.createQuery("SELECT l"
                                    + " FROM Libro l"
                                    + " JOIN l.autor a"
                                    + " JOIN l.editorial e").getResultList();
        return todosLibros;
    }
    
}
