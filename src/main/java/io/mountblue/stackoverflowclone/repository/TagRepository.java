package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
