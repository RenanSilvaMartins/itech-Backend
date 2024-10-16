package com.itb.inf2fm.itech.services;

import com.itb.inf2fm.itech.model.Tecnico;

import java.util.List;

public interface TecnicoService {

    public Tecnico salvarTecnico(Tecnico tecnico);
    public Tecnico buscarTecnicoPorId(Long id);
    public Tecnico atualizarTecnico(Tecnico tecnico, Long id);
    public List<Tecnico> listarTodosTecnicos();
    public boolean deletarTecnico(Long id);

}
