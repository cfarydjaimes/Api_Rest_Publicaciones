package dev.cris.blogger.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.cris.blogger.DTO.PublicacionDTO;
import dev.cris.blogger.DTO.PublicacionRespuesta;
import dev.cris.blogger.Services.PublicacionService;
import dev.cris.blogger.Util.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public PublicacionRespuesta listarPublicaciones(
            @RequestParam(value = "numeroDePagina", defaultValue = AppConstantes.numero_pagina_defecto, required = false) int numeroDePagina,
            @RequestParam(value = "tamañoPagina", defaultValue = AppConstantes.medida_pagina, required = false) int tamañoPagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ordenar_por_defecto, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ordenar_direccion_por_defecto, required = false) String sortDir) {
        return publicacionService.obtenerTodasLasPublicaciones(numeroDePagina, tamañoPagina, ordenarPor, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO) {
        return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @PathVariable Long id,
            @RequestBody PublicacionDTO publicacionDTO) {
        return new ResponseEntity<>(publicacionService.actualizarPublicacion(publicacionDTO, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable Long id) {
        publicacionService.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion eliminada exitosamente", HttpStatus.OK);
    }
}
