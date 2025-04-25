package com.rafaelsaca.gestaofinanceira.mappers;

import com.rafaelsaca.gestaofinanceira.dto.TransacaoDTO;
import com.rafaelsaca.gestaofinanceira.models.Categoria;
import com.rafaelsaca.gestaofinanceira.models.TipoTransacao;
import com.rafaelsaca.gestaofinanceira.models.Transacao;
import com.rafaelsaca.gestaofinanceira.models.Usuario;

public class TransacaoMapper {

    public static Transacao toModel (TransacaoDTO dto){
        Transacao transacao = new Transacao();
        
        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setTipo(TipoTransacao.valueOf(dto.tipo().toUpperCase()));

        Categoria categoria = new Categoria();
        categoria.setId(dto.categoriaId());

        Usuario usuario = new Usuario();
        usuario.setId(dto.usuarioId());

        transacao.setCategoria(categoria);
        transacao.setUsuario(usuario);
        transacao.setDataVencimento(dto.dataVencimento());

        return transacao;
    }


    public static TransacaoDTO toDTO (Transacao transacao){
        return new TransacaoDTO(
            transacao.getDescricao(), 
            transacao.getValor(),
            transacao.getTipo().toString(), 
            transacao.getCategoria().getId(), 
            transacao.getUsuario().getId(),
            transacao.getDataVencimento()
        );
    }

}
