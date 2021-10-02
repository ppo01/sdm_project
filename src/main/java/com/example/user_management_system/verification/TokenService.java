package com.example.user_management_system.verification;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

@Controller
@AllArgsConstructor
@EnableScheduling
public class TokenService {

    private TokenRepository tokenRepository;

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String tokenID){
        return tokenRepository.findById(tokenID);
    }

    public Token getOne(){
        Iterable<Token> c=tokenRepository.findAll();
        return c.iterator().next();
    }

    public void deleteToken(String tokenID){
        tokenRepository.deleteById(tokenID);
    }

    public void deleteAllTokens(){
        Iterable<Token> c=tokenRepository.findAll();
        for (Token e: c)
           deleteToken(e.getToken());
    }

    @Scheduled(fixedRate = 600000)
    public void scheduleDeleteExpiredTokens() {
        Iterable<Token> c=tokenRepository.findAll();
        LocalDateTime localDateTime=LocalDateTime.now();
        for (Token e: c){
            if(e.getExpiresAt().isBefore(localDateTime)) {
                deleteToken(e.getToken());
            }
        }
    }



}