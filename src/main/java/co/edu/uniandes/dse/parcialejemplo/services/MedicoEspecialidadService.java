package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private EspecialidadRepository especialidadRepository;

	/** Asocia una especialidad existente a un medico*/
    @Transactional
	public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una especialidad al medico con id = {0}", medicoId);

		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el medico con ese id");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la especialidad con ese id");

		especialidadEntity.get().getMedicos().add(medicoEntity.get());
		log.info("Termina proceso de asociarle una especialidad al medico con id = {0}", medicoId);
		return especialidadEntity.get();
	}

	/** Obtiene una instancia de EspecialidadEntity asociada a una instancia de Medico */
	@Transactional
	public EspecialidadEntity getEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la especialidad con id = {0} del medico con id = " + medicoId, especialidadId);
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el medico con ese id");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la especialidad con ese id");

		log.info("Termina proceso de consultar la especialidad con id = {0} del medico con id = " + medicoId, especialidadId);
		if (especialidadEntity.get().getMedicos().contains(medicoEntity.get()))
			return especialidadEntity.get();

		throw new IllegalOperationException("La especialidad no esta asociada con el medico");
	}
    
}
