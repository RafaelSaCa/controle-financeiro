package com.rafaelsaca.gestaofinanceira.mappers;

import com.rafaelsaca.gestaofinanceira.dto.CategoriaDto;
import com.rafaelsaca.gestaofinanceira.models.Categoria;

public class CategoriaMapper {

    public static Categoria toModel (CategoriaDto dto){
        Categoria categoria = new Categoria();
        categoria.setDescricao(dto.descricao());
        
        return categoria;
    }

    public static CategoriaDto toDto (Categoria categoria){
        return new CategoriaDto(
            categoria.getDescricao());
    }

}
