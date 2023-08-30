package com.tads.manager.controller;

import com.tads.manager.dto.ManagerDTO;
import com.tads.manager.dto.UpdateManagerDTO;
import com.tads.manager.service.ManagerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private static final String messageGet = "Get manager";
    private static final String messageUpdate = "Update manager";
    private static final String messageDelete = "Delete manager";

    @Autowired
    private ManagerService managerService;

    @PostMapping
    @Transactional
    public ResponseEntity<EntityModel<ManagerDTO>> createManager(@RequestBody @Valid ManagerDTO managerDTO) {
        ManagerDTO createdManager = managerService.createManager(managerDTO);

        EntityModel<ManagerDTO> managerResource = EntityModel.of(createdManager);
        managerResource.add(linkTo(methodOn(ManagerController.class).getManager(createdManager.id())).withSelfRel().withTitle(messageGet));
        managerResource.add(linkTo(methodOn(ManagerController.class).updateManager(createdManager.id(),
                new UpdateManagerDTO(createdManager.name(), createdManager.email(), createdManager.phonenumber()))).withSelfRel().withTitle(messageUpdate));
        managerResource.add(linkTo(methodOn(ManagerController.class).deleteManager(createdManager.id())).withSelfRel().withTitle(messageDelete));

        return ResponseEntity.created(URI.create(managerResource.getRequiredLink(IanaLinkRelations.SELF).getHref())).body(managerResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ManagerDTO>> getManager(@PathVariable UUID id) {
        ManagerDTO manager = managerService.getManager(id);

        EntityModel<ManagerDTO> managerResource = EntityModel.of(manager);
        managerResource.add(linkTo(methodOn(ManagerController.class).updateManager(manager.id(),
                new UpdateManagerDTO(manager.name(), manager.email(), manager.phonenumber()))).withSelfRel().withTitle(messageUpdate));
        managerResource.add(linkTo(methodOn(ManagerController.class).deleteManager(manager.id())).withSelfRel().withTitle(messageDelete));

        return ResponseEntity.ok(managerResource);
    }

    @GetMapping
    public ResponseEntity<Page<ManagerDTO>> getManager(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(managerService.getManager(page, size));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EntityModel<ManagerDTO>> updateManager(@PathVariable UUID id, @RequestBody @Valid UpdateManagerDTO updateManagerDTO) {
        ManagerDTO updatedManager = managerService.updateManager(id, updateManagerDTO);
        EntityModel<ManagerDTO> managerResource = EntityModel.of(updatedManager);
        managerResource.add(linkTo(methodOn(ManagerController.class).getManager(updatedManager.id())).withSelfRel().withTitle(messageGet));
        managerResource.add(linkTo(methodOn(ManagerController.class).deleteManager(updatedManager.id())).withSelfRel().withTitle(messageDelete));

        return ResponseEntity.ok(managerResource);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteManager(@PathVariable UUID id){
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
