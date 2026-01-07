package com.api.planning.repository;

import com.api.planning.entity.Sprint;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
  Boolean existsByName(String name);

  @EntityGraph(attributePaths = "creator")
  Optional<Sprint> findById(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "members"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithMembers(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "members"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithStories(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "userStories.estimationResults",
    "members"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithStoriesWithEstimationResults(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "userStories.estimations",
    "members"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findWithStoriesWithEstimations(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "userStories",
    "userStories.estimations",
    "userStories.estimationResults",
    "members"
  })
  @Query("select s from Sprint s where s.id = :id")
  Optional<Sprint> findFull(Long id);

  @EntityGraph(attributePaths = {
    "creator",
    "members"
  })
  @Query("""
  select distinct s
  from Sprint s
  join s.members m
  where m.id = :memberId
  """)
  List<Sprint> findAllByMemberId(Long memberId);

  @EntityGraph(attributePaths = {
    "creator"
  })
  @Query("""
    select s
    from Sprint s
    where s.creator.id = (
        select u.inviter.id
        from User u
        where u.id = :userId
          and u.inviter is not null
    )
    and not exists (
        select 1
        from s.members m
        where m.id = :userId
    )
""")
  List<Sprint> findAllJoinableForUser(Long userId);
}
