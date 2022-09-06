package principal;

import java.util.Scanner;
import principal.servicios.AutorServicio;
import principal.servicios.EditorialServicio;
import principal.servicios.LibroServicio;

public class Orquestador {

    public void menu() {
        try {
            do {
                int opc = ingresarOpcion();
                implementarOpcion(opc);
            } while (elegirOtraOpcion() == true);
            System.out.println("Ud. ha decidido no realizar mas consultas.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error del sistema debido a " + e.getMessage());
        }
    }
    
    public int ingresarOpcion() throws Exception {
        Scanner teclado = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.println("Por favor, ingrese la opcion del menu que desea, indicando su numero correspondiente:"
                    + "\n1. Ingresar un libro."
                    + "\n2. Ingresar una editorial."
                    + "\n3. Ingresar un autor."
                    + "\n4. Buscar un autor por su nombre."
                    + "\n5. Buscar un libro por su ISBN."
                    + "\n6. Buscar un libro por su titulo."
                    + "\n7. Listar el/los libro/s por el nombre de su Autor."
                    + "\n8. Listar el/los libro/s por el nombre de su Editorial."
                    + "\n9. Mostrar todos los libros."
                    + "\n10. Mostrar todas las editoriales."
                    + "\n11. Modificar el nombre de una editorial.");
            System.out.print("Opcion a ingresar > ");
            int opc = teclado.nextInt();
            if (opc < 1 || opc > 11) {
                do {
                    System.out.print("Por favor, ingrese una opcion correcta > ");
                    opc = teclado.nextInt();
                } while (opc < 1 || opc > 11);
            }
            return opc;
        } catch (Exception e) {
            throw new Exception("no haber ingresado una opcion numerica.");
        }
    }

    public void implementarOpcion(int opc) throws Exception {
        LibroServicio ls = new LibroServicio();
        EditorialServicio es = new EditorialServicio();
        AutorServicio as = new AutorServicio();
        try {
            switch (opc) {
                case 1:
                    ls.crearNuevoLibro();
                    break;
                case 2:
                    es.crearNuevaEditorial();
                    break;
                case 3:
                    as.crearNuevoAutor();
                    break;
                case 4:
                    as.buscarAutorPorNombre();
                    break;
                case 5:
                    ls.buscarLibroPorISBN();
                    break;
                case 6:
                    ls.buscarLibroTitulo();
                    break;
                case 7:
                    ls.buscarLibrosPorAutor();
                    break;
                case 8:
                    es.busquedaLibrosPorEditorial();
                    break;
                case 9:
                    ls.imprimirTodosLibros();
                    break;
                case 10:
                    es.listarTodasLasEditoriales();
                    break;
                case 11:
                    es.modificarNombreEditorial();
                    break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean elegirOtraOpcion() throws Exception {
        Scanner teclado = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.print("\nSi desea elegir una nueva opcion, indique si. De lo contrario, indique no > ");
            String respuesta = teclado.next();
            Boolean aux;
            return aux = respuesta.equalsIgnoreCase("si");
        } catch (Exception e) {
            throw new Exception("Ud. selecciono una opcion no numerica.");
        }
    }

}
