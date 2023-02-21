package app.kakaobank.task.module.parseschool.infrastrurture;

import app.kakaobank.task.module.parseschool.domain.ParseLog;
import org.springframework.data.repository.CrudRepository;

public interface ParseLogJpaRepository extends CrudRepository<ParseLog, Long> {

}
