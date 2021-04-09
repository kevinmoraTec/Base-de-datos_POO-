package BASE_OBD4;

import Modelo_Per.Docente;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Conexion {

    private ObjectContainer oc;

    private void open() {
        this.oc = Db4o.openFile("base.yap");
    }

    public boolean Insertar(Docente persona) {
        try {
            this.open();
            oc.set(persona);
            this.oc.close();
            return true;
        } catch (Exception e) {
            System.out.println("e" + e.getMessage());
            return false;
            
        }
    }
    public void d (Docente d){
        List <Docente> f = (List <Docente>) buscarPersona(d);
        for (int i = 0; i < f.size(); i++) {
       
            
        }
       
       
        
    }
    public Docente[] leer() {
        Docente[] docentes = new Docente[10];
        try {
            Docente docentexml = null;
            File archivo = new File("datos.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(archivo);
            d.getDocumentElement().normalize();
            System.out.println("elemento primcipal: " + d.getDocumentElement().getNodeName());
            //Cargando todos los docentes en una coleccion de tipo nodo
            NodeList listaDocentes = d.getElementsByTagName("docente");
            for (int i = 0; i < listaDocentes.getLength(); i++) {
                Node nodo = listaDocentes.item(i);
                System.out.println("Docente: " + nodo.getNodeName());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String id = element.getAttribute("id");
                    String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();

                    docentexml = new Docente(id, nombre, username, password);
                    docentes[i] = docentexml;
                    System.out.println("id: " + element.getAttribute("id"));
                    System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Username: " + element.getElementsByTagName("username").item(0).getTextContent());
                    System.out.println("Password: " + element.getElementsByTagName("password").item(0).getTextContent());
                    Insertar(docentexml);

                }
            }

        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
        }
        return docentes;
    }

    public boolean Actualizar(Docente objeto) {
        try {
            //BUSCAMOS SI EXISTE EL OBJETO, SI ES ASÍ LO ACTUALIZAMOS EN LA BASE DE DATOS
            this.open();
            ObjectSet resultados = this.oc.get(new Docente(null, null, null, objeto.getId()));
            if (resultados.size() > 0) {
                Docente resultado = (Docente) resultados.next();
                resultado.setNombre(objeto.getNombre());
                resultado.setApellido(objeto.getApellido());

                this.oc.set(resultado);
                this.oc.close();
                return true;
            } else {
                this.oc.close();
                return false;
            }
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println("bdoo.Controlador.insertarPersona() : " + e);
            return false;
        }
    }

    public Docente[] Consultar(Docente objeto) {
        try {
            Docente[] personas = null;
            this.open();
            ObjectSet resultados = this.oc.get(objeto);
            int i = 0;
            if (resultados.hasNext()) {
                personas = new Docente[resultados.size()];
                while (resultados.hasNext()) {
                    personas[i] = (Docente) resultados.next();
                    i++;
                }
            }
            this.oc.close();
            return personas;

        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            return null;
        }

        //this.oc.close()
    }

    public Docente buscarPersona(Docente objeto) {
        this.open();
        Docente encontrado = null;
        ObjectSet resultados = this.oc.get(objeto);
        if (resultados.hasNext()) {
            encontrado = (Docente) resultados.next();
        }
        return encontrado;
    }

    public boolean Eliminar(Docente objeto) {
        try {
            //CONSULTAMOS LOS OBJETOS ALMACENADOS EN LA BASE DE DATOS Y SI EXISTE UNA COINCIDENCIA LA ELIMINAMOS            
            this.open();
            ObjectSet resultados = this.oc.get(objeto);
            if (resultados.size() > 0) {
                Docente persona = (Docente) resultados.next();
                this.oc.delete(persona);
                this.oc.close();
                return true;
            } else {
                this.oc.close();
                return false;

            }
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println("bdoo.Controlador.insertarPersona() : " + e);
            return false;
        }
    }

}
