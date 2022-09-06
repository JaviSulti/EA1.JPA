/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.servicios;

import java.util.Collection;
import java.util.Scanner;
import javax.persistence.NoResultException;
import principal.persistencia.LibroDAO;
import principal.entidades.Autor;
import principal.entidades.Editorial;
import principal.entidades.Libro;

/**
 *
 * @author javie
 */
public class LibroServicio {

    private final LibroDAO daoL;

    public LibroServicio() {
        this.daoL = new LibroDAO();
    }
      
    public void crearNuevoLibro() throws Exception {
        try {
            
            Libro aux = solicitarDatosLibro();

            if (aux.getIsbn() == 0 || aux.getIsbn() < 0) {
                throw new Exception("El isbn no puede ser cero, ni negativo.");
            }
            if (aux.getAnio() == 0) {
                throw new Exception("El anio no puede ser cero.");
            }

            if (aux.getAutor() == null || aux.getAutor().getNombre().trim().isEmpty() || aux.getAutor().getId() == 0) {
                throw new Exception("Error en la informacion del Autor.");
            }

            if (aux.getEditorial() == null || aux.getEditorial().getNombre().trim().isEmpty() || aux.getEditorial().getId() == 0) {
                throw new Exception("Error en la informacion de la editorial.");
            }
            
            if (aux.getAlta().equals(false)) {
                throw new Exception ("El libro no fue dado de alta.");
            }
            
            daoL.guardarLibro(aux);
            
        } catch (Exception e) {
            throw e;
        }
    }

    public Libro solicitarDatosLibro() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            Libro aux = new Libro();
            System.out.print("Por favor, ingrese el isbn del libro > ");
            Long ISBN = teclado.nextLong();
            aux.setIsbn(ISBN);
            comprobacionISBN(aux);
                        
            System.out.print("Ingrese el titulo del libro cuyo isbn es " + aux.getIsbn() + " > ");
            aux.setTitulo(teclado.next());
            System.out.print("Ingrese el anio de publicacion del libro " + aux.getTitulo() + " > ");
            aux.setAnio(teclado.nextInt());
            System.out.print("Ingrese la cantidad de ejemplares del libro " + aux.getTitulo() + " > ");
            aux.setEjemplares(teclado.nextInt());
            System.out.print("Ingrese la cantidad de ejemplares prestados del libro " + aux.getTitulo() + " > ");
            aux.setEjemplaresPrestados(teclado.nextInt());
            int restantes = aux.getEjemplares() - aux.getEjemplaresPrestados();
            aux.setEjemplaresRestantes(restantes);
            aux.setAlta(true);

            Editorial ed = new Editorial();
            ed = crearEditorial();
            aux.setEditorial(ed);

            Autor au = new Autor();
            au = crearAutor();            
            aux.setAutor(au);
            
            System.out.println("El libro " + aux.getTitulo() + " ha sido correctamente ingresado a la base de datos.\n");
            return aux;
        } catch (Exception e) {
            throw e;
        }
    }

    public void comprobacionISBN(Libro libro) throws Exception {
        try {
            Collection<Libro> listado = null;
            listado = listarTodosLibros();
            for (Libro aux : listado) {
                if (aux.getIsbn().equals(libro.getIsbn())) {
                    throw new Exception("El ISBN " + aux.getIsbn() + " ya se encuentra en la base de datos.");
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Editorial crearEditorial() throws Exception {
        try {
            Editorial ed = new Editorial();
            EditorialServicio es = new EditorialServicio();
            ed = es.crearNuevaEditorial();
            if (ed.getNombre().trim().isEmpty()) {
                throw new Exception("La editorial esta vacia.");
            }
            return ed;
        } catch (Exception e) {
            throw e;
        }
    }

    public Autor crearAutor() throws Exception {
        try {
            Autor au = new Autor();
            AutorServicio as = new AutorServicio();
            au = as.crearNuevoAutor();
            if (au.getNombre().trim().isEmpty()) {
                throw new Exception("El autor esta vacio.");
            }
            if (au.getAlta().equals(Boolean.FALSE)) {
                throw new Exception ("El autor no esta dado de alta.");
            }
            return au;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void buscarLibroPorISBN() throws Exception {
        try {
            Long ISBN = null;
            ISBN = ingresarISBN();
            if (ISBN <= 0) {
                throw new Exception("El ISBN no puede ser cero ni un numero negativo.");
            }
            Libro libroAux = null;
            libroAux = daoL.buscarLibroPorISBN(ISBN);
            if (libroAux != null) {
                System.out.println("El libro cuyo ISBN es " + libroAux.getIsbn() + ", se llama: " + libroAux.getTitulo() + " de " + libroAux.getAutor().getNombre());
            }
        } catch (NoResultException e1) {
            System.out.println("No se encontro libro alguno con el ISBN indicado.");
        } catch (Exception e) {
            throw e;
        }
    }

    public Long ingresarISBN() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, ingrese el ISBN a buscar > ");
            Long ISBN = teclado.nextLong();
            return ISBN;
        } catch (Exception e) {
            throw new Exception("Ha ocurrido un error al cargar el ISBN.");
        }
    }

    public void buscarLibroTitulo() throws Exception {
        try {
            String titulo = null;
            titulo = ingresarTitulo();
            if (titulo.trim().isEmpty()) {
                throw new Exception("El titulo no puede estar vacio.");
            }
            Libro buscado = new Libro();
            buscado = null;
            buscado = daoL.buscarLibroPorTitulo(titulo);

            if (buscado != null) {
                System.out.println("El libro " + buscado.getTitulo() + " se encuentra en la base de datos.");
            }
        } catch (NoResultException e1) {
            System.out.println("No se encontro el libro buscado.");
        } catch (Exception e) {
            throw new Exception("Ha habido un error en la busqueda del libro.");
        }
    }

    public String ingresarTitulo() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, ingrese el titulo del libro a buscar > ");
            String titulo = teclado.next();
            return titulo;
        } catch (Exception e) {
            throw new Exception("Ha ocurrido un error al cargar el titulo buscado.");
        }
    }

    public void buscarLibrosPorAutor() throws Exception {
        try {
            String buscar = ingresarAutor();
            if (buscar.trim().isEmpty()) {
                throw new Exception("El nombre del autor no es correcto.");
            }

            Collection<Libro> todosLibros = null;
            todosLibros = daoL.buscarLibroPorAutor(buscar);

            if (todosLibros.isEmpty()) {
                System.out.println("No hay libros cuya autoria sea:" + buscar);
            } else {
                for (Libro aux : todosLibros) {
                    if (aux.getAutor().getNombre().equalsIgnoreCase(buscar)) {
                        System.out.println("El libro " + aux.getTitulo() + " tiene por autor/a a: " + aux.getAutor().getNombre());
                    }
                }
            }

        } catch (NoResultException e2) {
            throw new Exception("No hay libros con dicho autor.");
        } catch (Exception e) {
            throw e;
        }
    }
    
    public String ingresarAutor() throws Exception {
        try {
            Scanner teclado = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Por favor, ingrese el Autor de/l libro/s a buscar > ");
            String titulo = teclado.next();
            return titulo;
        } catch (Exception e) {
            throw new Exception("Ha ocurrido un error al cargar el titulo buscado.");
        }
    }

    public Collection<Libro> listarTodosLibros() {
        try {
            Collection<Libro> todosLibros = null;
            todosLibros = daoL.listarTodosLibros();
            return todosLibros;
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimirTodosLibros() throws Exception {
        try {
            Collection<Libro> todosLibros = null;
            todosLibros = listarTodosLibros();
            System.out.println("Los libros dados de alta son: ");
            for (Libro aux : todosLibros) {
                System.out.println(aux);
            }
        } catch (NoResultException e2) {
            throw new Exception("No hay libros ingresados.");
        } catch (Exception e) {
        }
    }

}
