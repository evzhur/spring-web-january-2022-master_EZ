package ru.specialist.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.specialist.spring.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitle(String title);

    List<Post> findByDtCreatedBetween(LocalDateTime from, LocalDateTime to);

    @Query(value = """
            select p.*
                from post p
            join post_tag pt
                on p.post_id = pt.post_id
            group by p.post_id
            order by count(*) desc
            """, nativeQuery = true)
    List<Post> findSortedTagSorted();

    @Query(value = """
            select p.*
                from post p
            where p.content ilike %?1%
            """, nativeQuery = true)
    List<Post> findByContent(String content);

}
