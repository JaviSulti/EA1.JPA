package principal.persistencia;

import java.util.Collection;
import principal.entidades.Autor;

/**
 *
 * @author javie
 */
public final class AutorDAO extends DAO{
    
    public void guardarAutor (Autor autor){
        super.guardar(autor);
    }

    public Collection <Autor> buscarAutorPorNombre(String nombre) {
        Collection<Autor> autorAux = em.createQuery("SELECT a"
                + " FROM Autor a"
                + " WHERE a.nombre = :nombre").
                setParameter("nombre", nombre).
                getResultList();
        return autorAux;
    }

    public Collection<Autor> listarTodosLosAutores() {
        Collection<Autor> autores = null;
        autores = em.createQuery("SELECT a"
                                + " FROM Autor a").
                                getResultList();
        return autores;
    }

}
