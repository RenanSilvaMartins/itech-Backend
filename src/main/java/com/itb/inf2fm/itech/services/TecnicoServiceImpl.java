package com.itb.inf2fm.itech.services;

import com.itb.inf2fm.itech.exceptions.BadRequest;
import com.itb.inf2fm.itech.exceptions.NotFound;
import com.itb.inf2fm.itech.model.Tecnico;
import com.itb.inf2fm.itech.repository.TecnicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TecnicoServiceImpl implements TecnicoService{

    private final TecnicoRepository tecnicoRepository;

    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    @Transactional
    public Tecnico salvarTecnico(Tecnico tecnico) {
        if(!tecnico.validarTecnico()) {
            throw new BadRequest(tecnico.getMensagemErro());
        }
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Tecnico buscarTecnicoPorId(Long id) {
        try {
            return tecnicoRepository.findById(id).get();
        }catch (Exception ex){
            throw new NotFound("Tecnico não encontrado com o id " + id);
        }
    }

    @Override
    @Transactional
    public Tecnico atualizarTecnico(Tecnico tecnico, Long id) {
        try {
            if(!tecnico.validarTecnico()) {
                throw new BadRequest(tecnico.getMensagemErro());
            }
            Tecnico tecnicoBd = tecnicoRepository.findById(id).get();
            // Prosseguindo na atualização
            tecnicoBd.setNome(tecnico.getNome());
            tecnicoBd.setEmail(tecnico.getEmail());
            tecnicoBd.setTelefone(tecnico.getTelefone());
            tecnicoBd.setEspecialidade(tecnico.getEspecialidade());
            tecnicoBd.setCnpj(tecnico.getCnpj());
            tecnicoBd.setCpf_Tecnico(tecnico.getCpf_Tecnico());
            tecnicoBd.setData_nascimento(tecnico.getData_nascimento());
            tecnicoBd.setCidade(tecnico.getCidade());
            tecnicoBd.setCasa(tecnico.getCasa());
            tecnicoBd.setBairro(tecnico.getBairro());
            tecnicoBd.setRua(tecnico.getRua());
            tecnicoBd.setEstado(tecnico.getEstado());
            tecnicoBd.setComplemento(tecnico.getComplemento());
            return tecnicoRepository.save(tecnicoBd);  // save : Update para objetos já existentes no Banco de dados

        }catch (Exception ex){
            throw new NotFound("Tecnico não encontrado com o id " + id);
        }

    }

    @Override
    public List<Tecnico> listarTodosTecnicos() {
        return tecnicoRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deletarTecnico(Long id) {
        if(!tecnicoRepository.existsById(id)){
            throw new NotFound("Tecnico não encontrado com o id " + id);
        }
        tecnicoRepository.deleteById(id);
        return true;
    }

}
