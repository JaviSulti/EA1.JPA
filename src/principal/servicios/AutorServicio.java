/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.servicios;

import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;
import principal.entidades.Autor;
import principal.persistencia.AutorDAO;

/**
 *
 * @author javie
 */
public class AutorServicio {

    private final AutorDAO daoA;

    public AutorServicio() {
        this.daoA = new AutorDAO();
    }
       
    public Autor crearNuevoAutor() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, indique el nombre del Autor del libro > ");
            String nombreIngresado = teclado.next();
            Autor aux = comprobarDatosAutor(nombreIngresado);
            
            if (aux == null || aux.getNombre().trim().isEmpty()) {
                throw new Exception("El autor esta vacio.");
            }
            
            daoA.guardarAutor(aux);
            return aux;
        } catch (Exception e) {
            throw e;
        }
    }

    public Autor comprobarDatosAutor(String nombreIngresado) {
        try {
            Collection<Autor> autores = null;
            autores = listarTodosLosAutores();

            Autor au = new Autor();

            for (Autor aux : autores) {
                if (aux.getNombre().equalsIgnoreCase(nombreIngresado)) {
                    au = aux;
                    System.out.println("El autor " + aux.getNombre() + " se encuentra en la base de datos.");
                    break;
                } else {
                    au.setId(UUID.randomUUID().hashCode());
                    au.setNombre(nombreIngresado);
                    au.setAlta(Boolean.TRUE);
                }
            }
            return au;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Collection<Autor> listarTodosLosAutores() {
        try {
            Collection<Autor> autores = null;
            autores = daoA.listarTodosLosAutores();
            if (autores.isEmpty()) {
                System.out.println("No hay editoriales ha mostrar.");
            }
            return autores;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void buscarAutorPorNombre() throws Exception {
        try {
            String nombre = null;
            nombre = solicitarNombreAutor().toLowerCase();

            if (nombre.trim().isEmpty()) {
                throw new Exception("El nombre del autor no puede estar vacio.");
            }
            
            Collection<Autor> autoresEncontrados = null;
            autoresEncontrados = daoA.buscarAutorPorNombre(nombre);
            
            if (!autoresEncontrados.isEmpty()) {
                System.out.println("El autor ha sido encontrado en la base de datos.");
            } else {
                System.out.println("El autor no ha sido encontrado en la base de datos.");
            }

        } catch (Exception e) {
            throw new Exception("Ha ocurrido un problema en la busqueda del autor.");
        }
    }

    public String solicitarNombreAutor() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, ingrese el nombre del autor que desea buscar > ");
            String nombre = teclado.next();
            return nombre;
        } catch (Exception e) {
            throw new Exception("Ha habido un error con el nombre del autor.");
        }
    }

}
