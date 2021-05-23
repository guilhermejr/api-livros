package net.guilhermejr.apilivros.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.guilhermejr.apilivros.model.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>  {

	Optional<RefreshToken> findByToken(String token);

}
