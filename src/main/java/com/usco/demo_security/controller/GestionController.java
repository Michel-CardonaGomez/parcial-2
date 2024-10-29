package com.usco.demo_security.controller;

import com.usco.demo_security.Repository.GestionRepository;
import com.usco.demo_security.Repository.TipoVehiculoRepository;
import com.usco.demo_security.services.GestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.usco.demo_security.models.Gestion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
public class GestionController {

    @Autowired
    GestionService gestionService;

    @Autowired
    TipoVehiculoRepository tipoVehiculoRepository;
    @Autowired
    private GestionRepository gestionRepository;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("gestiones", gestionService.listarGestion());
        return "inicio";
    }

    @GetMapping("/new")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tiposVehiculos", tipoVehiculoRepository.findAll());
        model.addAttribute("gestion", new Gestion());
        return "crear";
    }

    @PostMapping("new")
    private String guardarGestion(Gestion gestion, RedirectAttributes redirectAttributes) {
        gestionService.crearGestion(gestion);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormulario(@PathVariable Long id, Model model) {
        // Busca la gestión existente por ID
        Gestion gestion = gestionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gestión con ID " + id + " no encontrada"));

        // Agrega la gestión al modelo
        model.addAttribute("gestion", gestion);
        model.addAttribute("tiposVehiculos", tipoVehiculoRepository.findAll());
        return "editar"; // Retorna el nombre de la vista para el formulario de edición
    }

    @PostMapping("/edit/{id}")
    public String actualizarGestion(@PathVariable Long id, @ModelAttribute Gestion request, RedirectAttributes redirectAttributes) {
        try {
            gestionService.actualizarGestion(request, id);

            redirectAttributes.addFlashAttribute("mensaje", "Gestión actualizada correctamente.");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/inicio";
    }

    @GetMapping("/delete/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        gestionService.eliminarGestion(id);
        return "redirect:/incio"; // Redirige a la lista de empleados
    }

    @PostMapping("/edit/salida/{id}")
    public String actualizarHoraSalida(@PathVariable Long id, @ModelAttribute Gestion request, RedirectAttributes redirectAttributes) {
        try {
            gestionService.actualizarSalida(request, id);

            redirectAttributes.addFlashAttribute("mensaje", "Gestión actualizada correctamente.");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/inicio";
    }

}
