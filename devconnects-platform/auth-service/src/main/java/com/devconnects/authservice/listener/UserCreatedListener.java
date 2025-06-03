package com.devconnects.authservice.listener;


import com.devconnects.authservice.model.UserEntity;
import com.devconnects.authservice.repository.UserRepository;
import com.devconnects.commonlib.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedListener {

    private final UserRepository userRepository;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleUserCreated(UserRegisteredEvent event) {
        if (userRepository.findByUsername(event.getUsername()).isEmpty()) {
            var user = new UserEntity();
            user.setUsername(event.getUsername());
            user.setPassword(event.getPassword()); // already hashed
            user.setRole(event.getRole());
            userRepository.save(user);
        }
    }
}
