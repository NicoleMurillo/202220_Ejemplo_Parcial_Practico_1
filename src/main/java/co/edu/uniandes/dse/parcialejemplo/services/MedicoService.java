package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedico(MedicoEntity medicoEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creacion del medico");
 
        if ((medicoEntity.getRegistroMedico()).substring(0,2) != "RM" )
                throw new IllegalOperationException("El registro medico no es valido");

        log.info("Termina proceso de creacion del medico");
        return medicoRepository.save(medicoEntity);
}
    
}
