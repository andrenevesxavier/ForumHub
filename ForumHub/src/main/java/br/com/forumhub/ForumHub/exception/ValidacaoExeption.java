package br.com.forumhub.ForumHub.exception;

public class ValidacaoExeption extends RuntimeException {
    public ValidacaoExeption(String mensagem) {
        super(mensagem);
    }
}