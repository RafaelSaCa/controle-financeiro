package com.rafaelsaca.gestaofinanceira.mappers;

import com.rafaelsaca.gestaofinanceira.dto.CategoriaDTO;
import com.rafaelsaca.gestaofinanceira.models.Categoria;

public class CategoriaMapper {

    public static Categoria toModel (CategoriaDTO dto){
        Categoria categoria = new Categoria();
        categoria.setDescricao(dto.descricao());
        
        return categoria;
    }

    public static CategoriaDTO toDto (Categoria categoria){
        return new CategoriaDTO(
            categoria.getDescricao());
    }

}
