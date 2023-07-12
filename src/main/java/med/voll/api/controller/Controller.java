package med.voll.api.controller;

import jakarta.transaction.Transactional;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medvoll")
public class Controller {
    @Autowired
    private MedicoRepository medico;

    @PostMapping(value = "/cadastraMedico")
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        medico.save(new Medico(dados));
    }

}
