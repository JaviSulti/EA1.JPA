package principal.persistencia;

import java.util.Collection;
import principal.entidades.Editorial;
import principal.entidades.Libro;

/**
 *
 * @author javie
 */
public final class EditorialDAO extends DAO {

    public void guardarEditorial(Editorial editorial) {
        super.guardar(editorial);
    }

    public Collection <Libro> busquedaLibrosPorEditorial(String edit){
        Collection <Libro> listado = null;
        listado = em.createQuery("SELECT l"
                                    + " FROM Libro l"
                                    + " JOIN l.editorial e"
                                    + " JOIN l.autor a"
                                    + " WHERE e.nombre = :nombre").
                                    setParameter("nombre", edit).
                                    getResultList();
        return listado;
    }
    
    public Collection<Editorial> listarTodasLasEditoriales() {
        Collection<Editorial> editoriales = null;
        editoriales = em.createQuery("SELECT e"
                                    + " FROM Editorial e").
                                    getResultList();
        return editoriales;
    }
    
    public Editorial buscarNombreEditorial(Long id, String nombre) {
        Editorial editorial = (Editorial) em.createQuery("SELECT e"
                                                        + " FROM Editorial e"
                                                        + " WHERE e.id = :id").
                                                        setParameter("id", id).
                                                        getSingleResult();
        return editorial;
    }
    
    public void editarEditorial(Editorial seleccionada) {
        super.editar(seleccionada);
    }
    
}
