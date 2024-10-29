package com.usco.demo_security.services;

import com.usco.demo_security.Repository.GestionRepository;
import com.usco.demo_security.Repository.TipoVehiculoRepository;
import com.usco.demo_security.models.Gestion;
import com.usco.demo_security.models.TipoVehiculo;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GestionService {

    @Autowired
    private GestionRepository gestionRepository;
    @Autowired
    TipoVehiculoRepository tipoVehiculoRepository;

    public Gestion crearGestion(Gestion gestion) {

        return gestionRepository.save(gestion);
    }

    public List<Gestion> listarGestion() {
        return gestionRepository.findAll();
    }

    public Gestion actualizarGestion(Gestion request, Long id) {

        Gestion gestion = gestionRepository.findById(id).orElseThrow();

        if (request.getTipoVehiculo() != null && request.getTipoVehiculo().getId() != null) {
            TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(request.getTipoVehiculo().getId())
                    .orElseThrow(() -> new NoSuchElementException("Tipo de Vehículo con ID " + request.getTipoVehiculo().getId() + " no encontrado"));
            gestion.setTipoVehiculo(tipoVehiculo);
        }

        gestion.setId(request.getId());
        gestion.setPlaca(request.getPlaca());
        gestion.setUbicacion(request.getUbicacion());
        gestion.setHoraSalida(gestion.getHoraSalida());
        return gestionRepository.save(gestion);

    }

    public Gestion actualizarSalida(Gestion request, Long id) {
        Gestion gestion = gestionRepository.findById(id).orElseThrow();

        if (request.getTipoVehiculo() != null && request.getTipoVehiculo().getId() != null) {
            TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(request.getTipoVehiculo().getId())
                    .orElseThrow(() -> new NoSuchElementException("Tipo de Vehículo con ID " + request.getTipoVehiculo().getId() + " no encontrado"));
            gestion.setTipoVehiculo(tipoVehiculo);
        }

        gestion.setId(request.getId());
        gestion.setPlaca(request.getPlaca());
        gestion.setUbicacion(request.getUbicacion());
        return gestionRepository.save(gestion);

    }

    public boolean eliminarGestion(Long id) {
        try {
            if (!gestionRepository.existsById(id)) {
                throw new NoSuchElementException("Empleado con ID " + id + " no encontrado");
            }
            gestionRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar empleado con ID " + id + ": " + e.getMessage());
        }
    }
}
