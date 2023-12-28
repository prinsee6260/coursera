package com.coursera.repository;

import com.coursera.vo.StudentEnrollments;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomJpaRepository {

    private final EntityManager entityManager;

    public CustomJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<StudentEnrollments> findAllEnrollments() {
        List<Object[]> query
                = (List<Object[]>) entityManager.createQuery(
                "SELECT u.userName as userName,count(*) as enrollmentCount FROM User u JOIN UserCourseDtl ucd on u.id = ucd.userId " +
                        " where u.role='STUDENT' group by userName").getResultList();
        return query.stream().map(data -> new StudentEnrollments(data[0].toString(),(Long)data[1])).collect(Collectors.toList());
    }
}
