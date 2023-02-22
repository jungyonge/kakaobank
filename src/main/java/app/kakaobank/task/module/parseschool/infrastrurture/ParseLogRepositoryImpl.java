package app.kakaobank.task.module.parseschool.infrastrurture;

import app.kakaobank.task.module.parseschool.domain.ParseLog;
import app.kakaobank.task.module.parseschool.domain.ParseLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ParseLogRepositoryImpl implements ParseLogRepository {

    private final ParseLogJpaRepository parseLogJpaRepository;

    @Override
    public ParseLog save(ParseLog parseLog) {
        return parseLogJpaRepository.save(parseLog);
    }

}
