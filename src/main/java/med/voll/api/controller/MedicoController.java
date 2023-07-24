package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/vollmed")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping(value = "/cadastraMedico")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {

        Medico medico = new Medico(dados);

        medicoRepository.save(medico);

        var uri = uriBuilder.path("/cadastraMedico/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalheMedico(medico));
    }

    @GetMapping(value = "/listaMedicos")
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);

    }

    @PutMapping(value = "/atualizaMedico")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaMedico dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalheMedico(medico));
    }

    @DeleteMapping(value = "/inativaMedico/{id}")
    @Transactional
    public ResponseEntity inativar(@PathVariable Integer id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.inativar();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/deletaMedico/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Integer id) {
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity detalhesMedico(@PathVariable Integer id) {
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheMedico(medico));
    }

}
