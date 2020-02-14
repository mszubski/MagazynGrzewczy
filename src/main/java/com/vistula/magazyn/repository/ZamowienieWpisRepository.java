package com.vistula.magazyn.repository;

import com.vistula.magazyn.domain.User;
import com.vistula.magazyn.domain.ZamowienieWpis;
import com.vistula.magazyn.domain.enumeration.StatusEnum;
import com.vistula.magazyn.domain.enumeration.StatusZamowieniaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ZamowienieWpis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZamowienieWpisRepository extends JpaRepository<ZamowienieWpis, Long> {

    @Query("select zamowienieWpis from ZamowienieWpis zamowienieWpis where zamowienieWpis.user.login = ?#{principal.username}")
    List<ZamowienieWpis> findByUserIsCurrentUser();

    Page<ZamowienieWpis> findAllByUser(Pageable pageable, Optional<User> user);

    List<ZamowienieWpis> findAllByUserAndStatus(Optional<User> user, StatusZamowieniaEnum statusZamowieniaEnum);
}
