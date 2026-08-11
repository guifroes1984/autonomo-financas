package com.autonomofinancas.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.service.EmailService;
import com.autonomofinancas.service.EmailTemplateService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;

    @Value("${app.email.remetente}")
    private String remetente;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            EmailTemplateService emailTemplateService) {

        this.mailSender = mailSender;
        this.emailTemplateService = emailTemplateService;
    }

    @Override
    public void enviarRecuperacaoSenha(
            Usuario usuario,
            String token) {

        String linkRecuperacao = frontendUrl
                + "/redefinir-senha?token="
                + token;

        String html = emailTemplateService
                .criarRecuperacaoSenha(
                        usuario.getNome(),
                        linkRecuperacao);

        try {

            MimeMessage mensagem = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    mensagem,
                    "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(usuario.getEmail());
            helper.setSubject(
                    "Recuperação de senha - Autônomo Finanças");
            helper.setText(html, true);

            mailSender.send(mensagem);

        } catch (MessagingException exception) {

            throw new IllegalStateException(
                    "Não foi possível preparar o e-mail de recuperação.",
                    exception);
        }

    }
}