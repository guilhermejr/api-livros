package net.guilhermejr.apilivros.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livros")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String capa;
    
    @Column(nullable = false)
    private String extensao;

    @Column(nullable = false)
    private String titulo;

    private String subtitulo;

    @Column(length = 13, nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false, columnDefinition="TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Editora editora;

    @ManyToOne(fetch = FetchType.LAZY)
    private Idioma idioma;

    @Column(nullable = false)
    private Integer anoPublicacao;

    @Column(nullable = false)
    private Integer paginas;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime criado;
    
    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime atualizado;
    
    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @ManyToMany
    private List<Autor> autores;

    @ManyToMany
    private List<Genero> generos;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tipo tipo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Estante estante;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        this.usuario = usuario1;
    }

}
