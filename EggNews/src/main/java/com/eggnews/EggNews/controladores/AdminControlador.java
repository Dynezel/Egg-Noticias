package com.eggnews.EggNews.controladores;

import com.eggnews.EggNews.entidades.Noticia;
import com.eggnews.EggNews.entidades.Usuario;
import com.eggnews.EggNews.servicios.NoticiaServicio;
import com.eggnews.EggNews.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    NoticiaServicio noticiaServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String paginaAdmin(ModelMap modelo) {
        List<Noticia> noticia = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticia", noticia);
        return "index";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }

}
