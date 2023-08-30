package com.tads.manager.service;

import com.tads.manager.dto.ManagerDTO;
import com.tads.manager.dto.UpdateManagerDTO;
import com.tads.manager.mapper.ManagerMapper;
import com.tads.manager.model.Manager;
import com.tads.manager.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ManagerMapper managerMapper;

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = managerMapper.DTOToManager(managerDTO);
        return managerMapper.managerToDTO(managerRepository.saveAndFlush(manager));
    }

    public ManagerDTO getManager(UUID id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return managerMapper.managerToDTO(manager);
    }

    public Page<ManagerDTO> getManager(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");
        return new PageImpl<>(
                managerRepository.findAll().stream().map(managerMapper::managerToDTO).collect(Collectors.toList()),
                pageRequest, size);
    }

    public ManagerDTO updateManager(UUID id, UpdateManagerDTO updateManagerDTO){
        Optional<Manager> managerOptional = managerRepository.findById(id);
        Manager manager = managerMapper.updateManager(managerOptional.orElseThrow(), updateManagerDTO);
        return managerMapper.managerToDTO(managerRepository.saveAndFlush(manager));
    }

    public void deleteManager(UUID id) {
        managerRepository.deleteById(id);
    }
}
