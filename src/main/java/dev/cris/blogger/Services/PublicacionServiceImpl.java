package dev.cris.blogger.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.cris.blogger.DTO.PublicacionDTO;
import dev.cris.blogger.DTO.PublicacionRespuesta;
import dev.cris.blogger.Exceptions.ResourceNotFountException;
import dev.cris.blogger.Models.Publicacion;
import dev.cris.blogger.Repositories.PublicacionRepository;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);
        return modelMapper.map(nuevaPublicacion, PublicacionDTO.class);
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int nPaginas, int mPaginas, String ordenarPor,
            String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable page = PageRequest.of(nPaginas, mPaginas, sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(page);

        List<Publicacion> listaPublicaciones = publicaciones.getContent();
        List<PublicacionDTO> publicacionDTO = listaPublicaciones.stream()
                .map(publicacion -> modelMapper.map(publicacion, PublicacionDTO.class)).collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(publicacionDTO);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Publicacion", "id", id));
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        return publicacionDTO;
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Publicacion", "id", id));
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());
        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);
        return modelMapper.map(publicacionActualizada, PublicacionDTO.class);
    }

    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Publicacion", "id", id));
        publicacionRepository.delete(publicacion);

    }
}
