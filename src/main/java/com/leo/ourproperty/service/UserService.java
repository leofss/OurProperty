package com.leo.ourproperty.service;

import com.leo.ourproperty.entity.EmailMessage;
import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.exception.CpfUniqueViolationException;
import com.leo.ourproperty.exception.EmailUniqueViolationException;
import com.leo.ourproperty.exception.EntityNotFoundExecption;
import com.leo.ourproperty.repository.UserRepository;
import com.leo.ourproperty.repository.projection.UserProjection;
import com.leo.ourproperty.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    private User saveUserWithExceptionHandling(User user) {
        try {
            sendWelcomeEmail(user);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            handleDataIntegrityViolationException(ex, user);
            return null;
        }
    }

    private void sendWelcomeEmail(User user) {
        EmailMessage emailMessage = new EmailMessage(
                user.getEmail(),
                "Welcome to OurProperty",
                "Your account was successfully created"
        );
        rabbitTemplate.convertAndSend("emailQueue", emailMessage);
    }

    private void handleDataIntegrityViolationException(DataIntegrityViolationException ex, User user) {
        String constraintName = ex.getMostSpecificCause().getMessage();
        if (constraintName.contains("cpf")) {
            throw new CpfUniqueViolationException(String.format("CPF %s já cadastrado", user.getCpf()));
        } else if (constraintName.contains("email")) {
            throw new EmailUniqueViolationException(String.format("E-mail %s já cadastrado", user.getEmail()));
        }
        throw ex;
    }
    @Transactional
    public User create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return saveUserWithExceptionHandling(user);
    }

    @Transactional(readOnly = true)
    public Page<UserProjection> findAll(Pageable pageable){
        return userRepository.findAllPageable(pageable);
    }

    @Transactional
    public void delete(Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundExecption("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User edit(Long id, UserDto userDto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundExecption("User with id " + id + " not found"));

        if (userRepository.existsByCpf(userDto.getCpf())){
                throw new CpfUniqueViolationException(String.format("User with CPF %s already exists", userDto.getCpf()));
        }

        if (userRepository.existsByEmail(userDto.getEmail())){
                throw new CpfUniqueViolationException(String.format("User with Email %s already exists", userDto.getEmail()));

        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(userDto, user);


        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundExecption("User with email " + email + " not found"));
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByEmail(String email){
        return userRepository.findRoleByEmail(email);
    }
}
