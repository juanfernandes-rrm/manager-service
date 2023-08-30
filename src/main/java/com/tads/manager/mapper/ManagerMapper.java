package com.tads.manager.mapper;

import com.tads.manager.dto.ManagerDTO;
import com.tads.manager.dto.UpdateManagerDTO;
import com.tads.manager.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {

    public ManagerDTO managerToDTO(Manager manager) {
        return new ManagerDTO(manager.getId(), manager.getName(), manager.getEmail(), manager.getCpf(), manager.getPhonenumber());
    }

    public Manager DTOToManager(ManagerDTO managerDTO) {
        return Manager.builder()
                .id(managerDTO.id())
                .name(managerDTO.name())
                .email(managerDTO.email())
                .cpf(managerDTO.cpf())
                .phonenumber(managerDTO.phonenumber())
                .build();
    }

    public Manager updateManager(Manager manager, UpdateManagerDTO updateManagerDTO) {
        manager.setName(updateManagerDTO.name());
        manager.setEmail(updateManagerDTO.email());
        manager.setPhonenumber(updateManagerDTO.phonenumber());
        return manager;
    }
}
