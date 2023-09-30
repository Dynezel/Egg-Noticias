package com.eggnews.EggNews.controladores;

import com.eggnews.EggNews.entidades.Noticia;
import com.eggnews.EggNews.excepciones.Excepcion;
import com.eggnews.EggNews.servicios.NoticiaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NoticiaControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(NoticiaControlador.class);
    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }


    @GetMapping("/lista")
    public String obtenerNoticias(ModelMap modelo) {
        List<Noticia> noticia = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticia", noticia);
        return "noticia_lista.html";
    }

    @GetMapping("/noticia/{id}")
    public String probando(@PathVariable Long id, ModelMap modelo) {
        modelo.addAttribute("noticia", noticiaServicio.getOne(id));
        return "prueba";
    }

    @GetMapping("/noticias")
    public String cartas(ModelMap modelo) {
        List<Noticia> noticia = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticia", noticia);
        return "noticia";
    }

    @GetMapping("/registrar")
    public String registrarNoticia() {
        return "noticia_form";
    }

    @PostMapping("/crear")
    public String crearNoticia(@RequestParam String titulo, String cuerpo, String url, ModelMap modelo) {
        try {
            noticiaServicio.guardarNoticia(titulo, cuerpo, url);
        } catch (Excepcion ex) {

            modelo.put("error", ex.getMessage());
            return "noticia_form.html";
        }
        return "index";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {
        modelo.addAttribute("noticia", noticiaServicio.getOne(id));
        return "noticia_modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String titulo, String cuerpo, String url, ModelMap modelo) {
        try {
            noticiaServicio.modificarNoticia(id, titulo, cuerpo, url);

            return "redirect:../lista";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }

        //eliminar
        //@GetMapping("/{id}")
        //public String eliminar(@PathVariable Noticia noticia, ModelMap modelo) throws Excepcion{
        //noticiaServicio.eliminarNoticia(noticia);

        // return "autor_eliminar.html";
        //}

    }
}

