package com.example.euprava.controllers;

import com.example.euprava.Models.VestObolelima;
import com.example.euprava.Services.VestObolelimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class VestObolelimaController {

    @Autowired
    private VestObolelimaService vestObolelimaService;

    @GetMapping("/vestObolelima")
    public String prikazVesti(Model model){
        List<VestObolelima> lista = vestObolelimaService.findAll();
        model.addAttribute("vest", new VestObolelima());
        model.addAttribute("listVest", lista);
        return "admin_pages/vest_o_obolelima";
    }

    @GetMapping("/vestObolelima/create")
    public String showForm(Model model){
        model.addAttribute("vest", new VestObolelima());
        return "admin_pages/vest_o_obolelima_edit";
    }

    @PostMapping("/vestObolelima/save")
    public String save(VestObolelima vest, RedirectAttributes redirect){
        vestObolelimaService.save(vest);
        redirect.addFlashAttribute("poruka", "Vest je sacuvana");
        return "redirect:/vestObolelima";
    }

    @GetMapping("/vestObolelima/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        VestObolelima vest = vestObolelimaService.getOne(id);
        model.addAttribute("vest", vest);
        return "admin_pages/vest_o_obolelima_edit";
    }

    @GetMapping("/vestObolelima/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        vestObolelimaService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Vest je obrisana");
        return "redirect:/vestObolelima";
    }
}
