package org.example.data.repository;

import org.example.data.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByFullName(String fullName);
}
