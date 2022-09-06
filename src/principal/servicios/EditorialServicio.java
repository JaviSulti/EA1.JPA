/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.servicios;

import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;
import javax.persistence.NoResultException;
import principal.entidades.Editorial;
import principal.entidades.Libro;
import principal.persistencia.EditorialDAO;

/**
 *
 * @author javie
 */
public class EditorialServicio {

    private final EditorialDAO daoE;

    public EditorialServicio() {
        this.daoE = new EditorialDAO();
    }

    public Editorial crearNuevaEditorial() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, indique el nombre de la editorial del libro > ");
            String nombreIngresado = teclado.next();
            
            Editorial aux = comprobarEditorial(nombreIngresado);
            
            if (aux == null || aux.getNombre().trim().isEmpty()) {
                throw new Exception("La editorial esta vacia.");
            }
            
            if (aux.getAlta().equals(Boolean.FALSE)) {
                throw new Exception("La editorial no ha sido dada de alta.");
            }
            daoE.guardarEditorial(aux);
            return aux;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Collection<Editorial> listarTodasLasEditoriales() {
        try {
            Collection<Editorial> editoriales = null;
            editoriales = daoE.listarTodasLasEditoriales();
            if (editoriales.isEmpty()) {
                System.out.println("No hay editoriales ha mostrar.");
            }
            return editoriales;
        } catch (Exception e) {
            throw e;
        }
    }

    public Editorial comprobarEditorial(String nombreIngresado) {
        try {
            Collection<Editorial> editoriales = null;
            editoriales = listarTodasLasEditoriales();

            Editorial ed = new Editorial();

            for (Editorial aux : editoriales) {
                if (aux.getNombre().equalsIgnoreCase(nombreIngresado)) {
                    ed = aux;
                    System.out.println("La editorial " + aux.getNombre() + " ya existe en el sistema.");
                    break;
                } else {
                    ed.setId(UUID.randomUUID().hashCode());
                    ed.setNombre(nombreIngresado);
                    ed.setAlta(Boolean.TRUE);
                }
            }
            return ed;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void busquedaLibrosPorEditorial() throws Exception {
        try {
            String edit = null;
            edit = ingresarNombreEditorial();
            if (edit.trim().isEmpty()) {
                throw new Exception("No se ha indicado nombre de editorial.");
            }
            Collection<Libro> listado = null;
            listado = daoE.busquedaLibrosPorEditorial(edit);

            if (listado.isEmpty()) {
                System.out.println("No hay libros de dicha editorial.");
            } else {
                System.out.println("Los libros encontrados de la editorial " + edit + " son: ");
                for (Libro aux : listado) {
                    if (aux.getEditorial().getNombre().equalsIgnoreCase(edit)) {
                        System.out.println(aux.getTitulo());
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String ingresarNombreEditorial() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, ingrese la Editorial de/l libro/s > ");
            String titulo = teclado.next();
            return titulo;
        } catch (Exception e) {
            throw new Exception("Ha ocurrido un error al cargar el titulo buscado.");
        }
    }

    public void modificarNombreEditorial() throws Exception {
        try {
            Long id = ingresarIdEditorial();
            if (id == 0) {
                throw new Exception("El id de la editorial no puede ser cero.");
            }
            String nombre = ingresarNombreEditorial();
            Editorial seleccionada = null;
            seleccionada = daoE.buscarNombreEditorial(id, nombre);
            seleccionada.setNombre(nombre);
            daoE.editarEditorial(seleccionada);
        } catch (NoResultException e1) {
            throw new Exception ("No se ha encontrado editorial con ese id.");
        }catch (Exception e) {
            throw e;
        }
    }

        public Long ingresarIdEditorial() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, ingrese el id de la Editorial > ");
            Long id = teclado.nextLong();
            return id;
        } catch (Exception e) {
            throw new Exception("Ha ocurrido un error al cargar el id.");
        }
    }
    
}
