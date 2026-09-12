package com.autonomofinancas.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.autonomofinancas.dto.response.ErroResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmailJaCadastradoException.class)
        public ResponseEntity<ErroResponse> tratarEmailJaCadastrado(
                        EmailJaCadastradoException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.CONFLICT;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(status).body(erro);

        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErroResponse> tratarErroValidacao(
                        MethodArgumentNotValidException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                String mensagem = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(erro -> {

                                        if ("typeMismatch".equals(erro.getCode())) {
                                                return String.format(
                                                                "O parâmetro '%s' possui um valor inválido.",
                                                                erro.getField());
                                        }

                                        return erro.getDefaultMessage();
                                })
                                .orElse("Dados inválidos.");

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                mensagem,
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(RegraDeNegocioException.class)
        public ResponseEntity<ErroResponse> tratarRegraDeNegocio(
                        RegraDeNegocioException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.CONFLICT;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(RecursoNaoEncontradoException.class)
        public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(
                        RecursoNaoEncontradoException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.NOT_FOUND;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErroResponse> tratarJsonInvalido(
                        HttpMessageNotReadableException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                "O corpo da requisição está inválido. Verifique os tipos e valores informados.",
                                request.getRequestURI());

                return ResponseEntity.status(status).body(erro);

        }

        @ExceptionHandler(BindException.class)
        public ResponseEntity<ErroResponse> tratarErroDeVinculacao(
                        BindException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                String campo = exception.getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(erro -> erro.getField())
                                .orElse("desconhecido");

                String mensagem = String.format(
                                "O parâmetro '%s' possui um valor inválido.",
                                campo);

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                mensagem,
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErroResponse> tratarErroInesperado(
                        Exception exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                "Ocorreu um erro interno inesperado.",
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(SenhaInvalidaException.class)
        public ResponseEntity<ErroResponse> tratarSenhaInvalida(
                        SenhaInvalidaException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(status).body(erro);

        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErroResponse> tratarCredenciaisInvalidas(
                        BadCredentialsException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.UNAUTHORIZED;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                "E-mail ou senha inválidos.",
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(TokenRecuperacaoExpiradoException.class)
        public ResponseEntity<ErroResponse> tratarTokenRecuperacaoExpirado(
                        TokenRecuperacaoExpiradoException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(TokenRecuperacaoInvalidoException.class)
        public ResponseEntity<ErroResponse> tratarTokenRecuperacaoInvalido(
                        TokenRecuperacaoInvalidoException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(RefreshTokenInvalidoException.class)
        public ResponseEntity<ErroResponse> tratarRefreshTokenInvalido(
                        RefreshTokenInvalidoException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.UNAUTHORIZED;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

        @ExceptionHandler(RefreshTokenExpiradoException.class)
        public ResponseEntity<ErroResponse> tratarRefreshTokenExpirado(
                        RefreshTokenExpiradoException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.UNAUTHORIZED;

                ErroResponse erro = new ErroResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(erro);
        }

}
