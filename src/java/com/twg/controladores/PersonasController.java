package com.twg.controladores;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PerfilesDao;
import com.twg.persistencia.daos.PersonasDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private final PersonasDao personasDao = new PersonasDao();
    
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
        String mensajeExito = "";
        String mensajeError = "";
        String mensajeAlerta = "";
        
        String accion = request.getParameter("accion");
        if(accion == null){
            accion = "";
        }
        
        String idPersonaStr = request.getParameter("idPersona");
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
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");
            
        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }
        
        try {
            switch(accion){
                case "consultar":
                    listaPersonas = personasDao.consultarPersonas(idPersona, documento, tipo_documento, nombres, apellidos,
                        telefono, celular, correo, direccion, usuario, perfil);
                    persona = new PersonasBean();
                    persona.setDocumento(documento);
                    persona.setTipo_documento(tipo_documento);
                    persona.setNombres(nombres);
                    persona.setApellidos(apellidos);
                    persona.setTelefono(telefono);
                    persona.setCelular(celular);
                    persona.setCorreo(correo);
                    persona.setDireccion(direccion);
                    enviarDatos(request, persona, null);
                    break;
                case "editar":
                    /*persona = new PersonasBean();
                    if(idPersona != null){
                        List<PersonasBean> usu = new ArrayList<>();
                        if(documento != null && !documento.equals("")){
                           personasB = personasDao.consultarPersonas(true,Integer.valueOf(documento));
                            if(personasB != null && !personasB.isEmpty()){
                                personaB = personasB.get(0);
                                UsuariosDao userDao = new UsuariosDao();
                                usu = userDao.consultarUsuarios(personaB.getId());
                            }
                        }
                    }
                    enviarDatos(request, personaB, usu!=null ? usu.get(0) : null);
                    */
                    break;
                case "crearPersona":
                    persona = new PersonasBean();
                    persona.setDocumento(documento);
                    persona.setTipo_documento(tipo_documento);
                    persona.setNombres(nombres);
                    persona.setApellidos(apellidos);
                    persona.setTelefono(telefono);
                    persona.setCelular(celular);
                    persona.setCorreo(correo);
                    persona.setDireccion(direccion);
                    crearPersona(request, response, persona);
                    enviarDatos(request, new PersonasBean(), null);
                    break;
                case "eliminar":
                    if(idPersona != null){
                        int eliminar = personasDao.eliminarPersona(idPersona);
                        if(eliminar >0){
                            mensajeExito = "El registro fue eliminado con éxito";
                        } else {
                            mensajeError = "El registro no pudo ser eliminado";
                        }
                    }
                    break;
                default:
                    enviarDatos(request, new PersonasBean(), null);
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }
        
        if(listaPersonas == null){
            try {
                listaPersonas = personasDao.consultarPersonas(null, null, null, null, null, null, null, null, null, null, null);
            } catch (Exception e) {
                mensajeError = "Ha currido un error en el registro de la persona";
                e.printStackTrace();
                return;
            }
            
        }
        
        request.setAttribute("mensajeInformacion", mensajeInformacion);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("listaPersonas", listaPersonas);
        request.getRequestDispatcher("jsp/personas.jsp").forward(request, response);
    }
    
    /**
     * crearPersona: Método para registrar una nueva persona dentro del sistema.
     * @param request
     * @param response 
     */
    public void crearPersona(HttpServletRequest request, HttpServletResponse response, PersonasBean persona){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String idPersonaStr = request.getParameter("idPersona");
        String documento = request.getParameter("documento");
        String tipo_documento = request.getParameter("tipoDocumento");
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
        
        try {
            
            boolean insertUser =  false;
            if(persona.getDocumento()==null || persona.getDocumento().isEmpty() || persona.getTipo_documento()==null || persona.getTipo_documento().equals("0") || persona.getNombres()==null || persona.getNombres().isEmpty() ||
                    persona.getApellidos()==null || persona.getApellidos().isEmpty() || persona.getTelefono()==null || persona.getTelefono().isEmpty() || persona.getDireccion()==null || persona.getDireccion().isEmpty()
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
                
                List<PersonasBean> person = new ArrayList<>();
                PersonasBean personBena = new PersonasBean();
                PersonasDao perDao = new PersonasDao();
                person = perDao.consultarPersonas(null, documento, null, null, null, null, null, null, null, null, null);
                if(usuario!=null && !usuario.isEmpty()){
                    List<UsuariosBean> usu = new ArrayList<>();
                    UsuariosDao userDao = new UsuariosDao();
                    usu = userDao.consultarUsuarios(usuario);
                    if(usu != null && !usu.isEmpty()){
                        mensajeError = "El usuario ya existe actualmente en el sistema";
                        return;
                    }else{
                        insertUser = true;
                    }
                }
                if(person != null && !person.isEmpty()){
                    mensajeError = "La persona ya existe actualmente en el sistema";
                    return;
                }else{
                    personBena = new PersonasBean();
                    personBena.setDocumento(documento);
                    personBena.setTipo_documento(tipo_documento);
                    personBena.setNombres(nombres);
                    personBena.setApellidos(apellidos);
                    personBena.setTelefono(telefono);
                    personBena.setCelular(celular);
                    personBena.setCorreo(correo);
                    personBena.setDireccion(direccion);
                    
                    int resultado = 0;
                    try {
                        resultado = perDao.insertarPersona(personBena);
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
    
    public boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
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
        request.setAttribute("direccion", persona.getDireccion());
        
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
