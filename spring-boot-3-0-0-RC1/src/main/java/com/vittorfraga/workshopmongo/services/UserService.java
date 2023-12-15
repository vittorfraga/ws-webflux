package com.vittorfraga.workshopmongo.services;

import com.vittorfraga.workshopmongo.dto.UserDTO;
import com.vittorfraga.workshopmongo.entities.User;
import com.vittorfraga.workshopmongo.repositories.UserRepository;
import com.vittorfraga.workshopmongo.services.exceptioons.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public Flux<UserDTO> findAll() {
        return repository.findAll().map(UserDTO::new);
    }


    public Mono<UserDTO> findById(String id) {
        return repository.findById(id).map(UserDTO::new).switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
    }

    public Mono<UserDTO> insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        return repository.save(entity).map(UserDTO::new);
    }

    public Mono<UserDTO> update(String id, UserDTO dto) {
        return repository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(dto.getName());
                    existingUser.setEmail(dto.getEmail());
                    return repository.save(existingUser);
                }).map(UserDTO::new)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")))
                .flatMap(existingUser -> repository.delete(existingUser));
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
    }
}
