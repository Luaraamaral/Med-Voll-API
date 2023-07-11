package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;
}
