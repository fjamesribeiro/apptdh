package br.com.zoi.apptdah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zoi.apptdah.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByNome(String nome);

}
