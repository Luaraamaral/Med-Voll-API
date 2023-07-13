package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vollmed")
public class Controller {
    @Autowired
    private MedicoRepository medico;

    @PostMapping(value = "/cadastraMedico")
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        medico.save(new Medico(dados));
    }

    @GetMapping(value = "/listaMedicos")
    public Page<DadosListagemMedico> listarMedicos(Pageable paginacao) {
        return medico.findAll(paginacao).map(DadosListagemMedico::new);
    }

}
