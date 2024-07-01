package com.demo.demo.Controller;

import com.demo.demo.Service.TypeService;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/types")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }


    @GetMapping("/{id}")
    public Type getTypeById(@PathVariable Long id) {
        return typeService.getTypeById(id);
    }

    @GetMapping("/types")
    public List<TypeDto> getAllTypes() {
        return typeService.getAllTypes();
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<?> createType(@RequestBody TypeDto type) {
        return ResponseEntity.ok().body(typeService.createType(type));
    }


    @PutMapping("/{id}")
    public TypeDto updateType(@PathVariable Long id, @RequestBody TypeDto typeDTO) {
        return typeService.updateType(id, typeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
    }

    @DeleteMapping("/types")
    public void deleteAllTypes() {
        typeService.deleteAllTypes();
    }
}