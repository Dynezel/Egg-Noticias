package com.eggnews.EggNews.entidades;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Noticia {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idNoticia;
    private String titulo;
    @Column(columnDefinition = "longtext")
    private String cuerpo;
    private String url;

    private String imagen;
}
