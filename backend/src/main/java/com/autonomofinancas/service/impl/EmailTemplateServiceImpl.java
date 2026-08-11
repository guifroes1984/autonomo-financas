package com.autonomofinancas.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.autonomofinancas.service.EmailTemplateService;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final ResourceLoader resourceLoader;

    public EmailTemplateServiceImpl(
            ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    @Override
    public String criarRecuperacaoSenha(String nome, String linkRecuperacao) {
        
        String html = carregarTemplate("classpath:templates/email/recuperacao-senha.html");

        return html
                .replace("{{NOME}}", nome)
                .replace("{{LINK_RECUPERACAO}}" ,linkRecuperacao);

    }

    private String carregarTemplate(String caminho) {

        try {

            Resource resource = resourceLoader.getResource(caminho);

            return new String(
                    resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Não foi possível carregar o template de e-mail.",
                    exception);
        }
    }

}
