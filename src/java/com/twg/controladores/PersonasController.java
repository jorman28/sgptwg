/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.controladores;

import com.twg.persistencia.beans.CargosBean;
import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.CargosDao;
import com.twg.persistencia.daos.PerfilesDao;
import com.twg.persistencia.daos.PersonasDao;
import com.twg.persistencia.daos.TiposDocumentosDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Erika Jhoana
 */
//@WebServlet(name = "PersonasController", urlPatterns = {"/PersonasController"})
public class PersonasController extends HttpServlet {

    private final TiposDocumentosDao tiposDocumentosDao = new TiposDocumentosDao();
    private final CargosDao cargosDao = new CargosDao();
    private final PerfilesDao perfiles = new PerfilesDao();
    private String mensajeInformacion;
    private String mensajeExito;
    private String mensajeError;
    private String mensajeAlerta;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mensajeInformacion = "";
        mensajeExito = "";
        mensajeError = "";
        mensajeAlerta = "";
        
        String accion = request.getParameter("accion");
        String documento = request.getParameter("documento");

        PersonasDao personasDao = new PersonasDao();
        List<PersonasBean> personasB = new ArrayList<>();
        if(accion == null){
            accion = "";
        }
        try {
            switch(accion){
                case "consultar":
                    break;
                case "editar":
                    PersonasBean personaB = new PersonasBean();
                    List<UsuariosBean> usu = new ArrayList<>();
                    if(documento != null && !documento.equals("")){
                       personasB = personasDao.consultarPersonas(true,Integer.valueOf(documento));
                        if(personasB != null && !personasB.isEmpty()){
                            personaB = personasB.get(0);
                            UsuariosDao userDao = new UsuariosDao();
                            usu = userDao.consultarUsuarios(personaB.getId());
                        }
                    }

                    enviarDatos(request, personaB, usu!=null ? usu.get(0) : null);
                    break;
                case "crearPersona":
                    crearPersona(request, response);
                    break;
                case "eliminar":
                    break;
                default:
                    break;
            }
            if(personasB == null || personasB.isEmpty()){
                personasB = personasDao.consultarPersonas(false, null);
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }
        
        request.setAttribute("mensajeInformacion", mensajeInformacion);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.getRequestDispatcher("jsp/personas.jsp").forward(request, response);
    }
    
    /**
     * crearPersona: Método para registrar una nueva persona dentro del sistema.
     * @param request
     * @param response 
     */
    public void crearPersona(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            /*
                Información del canino
            */
            String documento = request.getParameter("documento");
            String tipoDocumento = request.getParameter("tipoDocumento");
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String celular = request.getParameter("celular");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String tipoPersona = request.getParameter("tipoPersona");
            String Id_Cargo = request.getParameter("Id_Cargo");
            String fechaInicio = request.getParameter("fechaInicio");
            String usuario = request.getParameter("usuario");
            String perfil = request.getParameter("perfil");
            String clave1 = request.getParameter("clave1");
            String clave2 = request.getParameter("clave2");
            
            if(documento==null || documento.isEmpty() || tipoDocumento==null || tipoDocumento.equals("0") || nombres==null || nombres.isEmpty() ||
                    apellidos==null || apellidos.isEmpty() || telefono==null || telefono.isEmpty() || direccion==null || direccion.isEmpty()
                    /*|| tipoPersona==null || tipoPersona.equals("0")|| Id_Cargo==null || Id_Cargo.equals("0")*/){
                    
                mensajeError = "Los campos marcados con asterisco (*) son bligatorios.";
                return;
            }else{
                
                if((usuario!=null && !usuario.isEmpty()) || (perfil!=null && !perfil.equals("0"))|| (clave1!=null && !clave1.isEmpty())
                        || (clave2!=null && !clave2.isEmpty())){
                    if(usuario==null || usuario.isEmpty() || perfil==null || perfil.equals("0")|| clave1==null || clave1.isEmpty() || clave2==null || clave2.isEmpty()){
                        mensajeError = "Si desea ingresar la información del usuario, no olvide que los campos marcados con asterisco (*) son bligatorios.";
                        return;
                    }
                }
                if(correo != null && !correo.isEmpty()){
                    if(!validarEmail(correo)){
                        mensajeError = "El formato del correo no es correcto";
                        return;
                    }
                }
                
                List<PersonasBean> persona = new ArrayList<>();
                PersonasDao perDao = new PersonasDao();
                persona = perDao.consultarPersonas(true, Integer.valueOf(documento));
                if(usuario!=null && !usuario.isEmpty()){
                    List<UsuariosBean> usu = new ArrayList<>();
                    UsuariosDao userDao = new UsuariosDao();
                    usu = userDao.consultarUsuarios(usuario);
                    if(usu != null && !usu.isEmpty()){
                        mensajeError = "El usuario ya existe actualmente en el sistema";
                        return;
                    }
                }
                if(persona != null && !persona.isEmpty()){
                    mensajeError = "La persona ya existe actualmente en el sistema";
                    return;
                }else{
                    PersonasBean person = new PersonasBean();
                    person.setDocumento(documento);
                    person.setTipo_documento(tipoDocumento);
                    person.setNombres(nombres);
                    person.setApellidos(apellidos);
                    person.setTelefono(telefono);
                    person.setCelular(celular);
                    person.setCorreo(correo);
                    person.setDirecion(direccion);
                    
                    int resultado = 0;
                    try {
                        resultado = perDao.insertarPersona(person);
                        if(resultado == 1){
                            mensajeExito = "La persona fue registrada exitosamente";
                            return;
                        }else{
                            mensajeError = "Ocurrió un error tratando de registrar la persona";
                            return;
                        }
                    } catch (Exception e) {
                        mensajeError = "Ocurrió un error tratando de registrar la persona";
                        return;
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean validarEmail(String email) {
 
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
    
    private void enviarDatos(HttpServletRequest request, PersonasBean persona, UsuariosBean usuario){
        request.setAttribute("tipoDocumento", persona.getTipo_documento());
        request.setAttribute("documento", persona.getDocumento());
        request.setAttribute("nombres", persona.getNombres());
        request.setAttribute("apellidos", persona.getApellidos());
        request.setAttribute("telefono", persona.getTelefono());
        request.setAttribute("celular", persona.getCelular());
        request.setAttribute("correo", persona.getCorreo());
        request.setAttribute("direccion", persona.getDirecion());
        
        if(usuario!=null){
            request.setAttribute("usuario", usuario.getUsuario());
            PerfilesDao perfilDao = new PerfilesDao();
            try {
                PerfilesBean perfil = perfilDao.consultarPerfil(usuario.getPerfil());
                if(perfil!=null){
                    request.setAttribute("perfil", perfil.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException{
        processRequest(reqeust, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException{
        processRequest(reqeust, response);
    }

}
