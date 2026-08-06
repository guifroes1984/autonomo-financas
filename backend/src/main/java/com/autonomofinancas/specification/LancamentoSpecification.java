package com.autonomofinancas.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.autonomofinancas.dto.request.LancamentoFiltroRequest;
import com.autonomofinancas.entity.Lancamento;
import com.autonomofinancas.entity.enums.TipoLancamento;

public final class LancamentoSpecification {

    private LancamentoSpecification() {
    }

    public static Specification<Lancamento> comFiltros(
            Long usuarioId,
            LancamentoFiltroRequest filtro) {

        Specification<Lancamento> specification = usuarioIgual(usuarioId);

        if (filtro.getTipo() != null) {
            specification = specification.and(tipoIgual(filtro.getTipo()));
        }

        if (filtro.getCategoriaId() != null) {
            specification = specification.and(categoriaIgual(filtro.getCategoriaId()));
        }

        if (filtro.getPlataformaId() != null) {
            specification = specification.and(plataformaIgual(filtro.getPlataformaId()));
        }

        if (filtro.getInicio() != null) {
            specification = specification.and(dataMaiorOuIgual(filtro.getInicio()));
        }

        if (filtro.getFim() != null) {
            specification = specification.and(dataMenorOuIgual(filtro.getFim()));
        }

        if (filtro.getDescricao() != null && !filtro.getDescricao().isBlank()) {
            specification = specification.and(descricaoContem(filtro.getDescricao()));
        }

        return specification;
    }

    private static Specification<Lancamento> categoriaIgual(
            Long categoriaId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("categoria").get("id"),
                categoriaId);

    }

    private static Specification<Lancamento> usuarioIgual(Long usuarioId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("usuario").get("id"),
                usuarioId);

    }

    private static Specification<Lancamento> plataformaIgual(
            Long plataformaId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("plataforma").get("id"),
                plataformaId);

    }

    private static Specification<Lancamento> dataMaiorOuIgual(
            LocalDate inicio) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
                root.get("dataLancamento"),
                inicio);

    }

    private static Specification<Lancamento> dataMenorOuIgual(
            LocalDate fim) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
                root.get("dataLancamento"),
                fim);
    }

    private static Specification<Lancamento> descricaoContem(
            String descricao) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get("descricao")),
                "%" + descricao.toLowerCase().trim() + "%");
    }

    private static Specification<Lancamento> tipoIgual(
            TipoLancamento tipo) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("tipo"),
                tipo);
    }

}
