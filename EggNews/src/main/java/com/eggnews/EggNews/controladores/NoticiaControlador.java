package com.eggnews.EggNews.controladores;

import com.eggnews.EggNews.entidades.Noticia;
import com.eggnews.EggNews.entidades.Usuario;
import com.eggnews.EggNews.enumeraciones.Rol;
import com.eggnews.EggNews.excepciones.Excepcion;
import com.eggnews.EggNews.servicios.ImagenServicio;
import com.eggnews.EggNews.servicios.NoticiaServicio;
import com.eggnews.EggNews.servicios.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.List;

@Controller
public class NoticiaControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(NoticiaControlador.class);
    @Autowired
    private NoticiaServicio noticiaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/")
    public String index(HttpSession session, ModelMap modelo) {
        List<Noticia> noticia = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticia", noticia);

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "index.html";
    }


    @GetMapping("/lista/Noticias")
    public String obtenerNoticias(ModelMap modelo) {
        List<Noticia> noticia = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticia", noticia);
        return "noticia_lista.html";
    }

    @GetMapping("/noticia/{idNoticia}")
    public String probando(@PathVariable Long idNoticia, ModelMap modelo) {
        modelo.addAttribute("noticia", noticiaServicio.getOne(idNoticia));
        return "prueba";
    }


    @GetMapping("/publicar")
    public String registrarNoticia(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado != null && logueado.getRol() == Rol.ADMIN) {
            return "noticia_form";
        } else
            return "redirect:/";
    }

    @PostMapping("/crear")
    public String crearNoticia(@RequestParam String titulo, String cuerpo, String url, String imagen, ModelMap modelo) {
        try {
            noticiaServicio.guardarNoticia(titulo, cuerpo, url, imagen);
            modelo.put("exito", "La noticia fue cargada correctamente!");

        } catch (Excepcion ex) {

            modelo.put("error", ex.getMessage());
            return "noticia_form.html";
        }
        return "redirect:/";
    }

    @GetMapping("/modificar/{idNoticia}")
    public String modificar(@PathVariable Long idNoticia, ModelMap modelo) {
        modelo.addAttribute("noticia", noticiaServicio.getOne(idNoticia));
        return "noticia_modificar";
    }

    @PostMapping("/modificar/{idNoticia}")
    public String modificar(@PathVariable Long idNoticia, String titulo, String cuerpo, String url, String imagen,ModelMap modelo) {
        try {
            noticiaServicio.modificarNoticia(idNoticia, titulo, cuerpo, url, imagen);

            return "redirect:../lista";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }
    }


    @GetMapping("/registrar")
    public String registrar() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
                           String password2, ModelMap modelo, MultipartFile archivo) {
        try {
            usuarioServicio.registrar(archivo, nombre, email, password, password2);
            modelo.put("exito", "El usuario se registro correctamente");

            return "redirect:/";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if(error != null) {
            modelo.put("error", "Usuario o Contraseña incorrectos");
        }
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password,
                             String password2, ModelMap modelo) {
        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
            return "index";
        } catch (Excepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "usuario_modificar";
        }

    }


}

//eliminar
//@GetMapping("/{id}")
//public String eliminar(@PathVariable Noticia noticia, ModelMap modelo) throws Excepcion{
//noticiaServicio.eliminarNoticia(noticia);

// return "autor_eliminar.html";
//}

