package com.eggnews.EggNews.repositorios;

import com.eggnews.EggNews.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepositorio extends JpaRepository <Imagen, String> {

}
