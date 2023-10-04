package com.eggnews.EggNews.servicios;

import com.eggnews.EggNews.entidades.Noticia;
import com.eggnews.EggNews.excepciones.Excepcion;
import com.eggnews.EggNews.repositorios.NoticiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;


    public List<Noticia> listarNoticias(){
        return noticiaRepositorio.findAll();
    }


    public Noticia buscarNoticiaPorId(Long id) throws Excepcion {
        Noticia noticia = noticiaRepositorio.findById(id).orElse(null);
        return noticia;
    }


    public Noticia guardarNoticia(String titulo, String cuerpo, String url, String imagen) throws Excepcion{
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setUrl(url);
        noticia.setImagen(imagen);
        return noticiaRepositorio.save(noticia);
    }



    public void eliminarNoticia(Noticia noticia) throws Excepcion {
        noticiaRepositorio.delete(noticia);
    }


    public void modificarNoticia(Long id, String titulo, String cuerpo, String url, String imagen) throws Excepcion {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();

            noticia.setTitulo(titulo);

            noticia.setCuerpo(cuerpo);

            noticia.setUrl(url);

            noticia.setImagen(imagen);

            noticiaRepositorio.save(noticia);

        }
    }

    public Noticia getOne(Long id) {
        return noticiaRepositorio.getOne(id);
    }


}
