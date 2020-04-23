package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.CreatingResponseException;
import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.entity.Chat;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.ChatRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.ChatRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;
    private final UserRepo userRepo;

    public void createChat(ChatRepresent represent) throws RuntimeException {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getId().equals(represent.getExecutorId()))
            throw new CreatingResponseException("Нельзя создать чат с самим собой!");
        User executor = userRepo.findById(represent.getExecutorId())
                .orElseThrow(NotFoundException::new);
        User customer = userRepo.findById(represent.getCustomerId())
                .orElseThrow(NotFoundException::new);
        if (chatRepo.existsByExecutorAndCustomer(executor, customer))
            throw new CreatingResponseException("Этот чат уже есть!");
        chatRepo.save(new Chat(executor, customer));

    }

    public Chat getChatById(UUID chatId) {
        Optional<Chat> chatOptional= chatRepo.findById(chatId);
        return chatOptional.orElse(null);
    }
}