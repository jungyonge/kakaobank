package app.kakaobank.task.module.parseschool.infrastrurture;

import app.kakaobank.task.module.parseschool.domain.ParseLog;
import app.kakaobank.task.module.parseschool.domain.ParseLogRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ParseLogRepositoryImpl implements ParseLogRepository {

    private final ParseLogJpaRepository parseLogJpaRepository;

    public ParseLogRepositoryImpl(ParseLogJpaRepository parseLogJpaRepository) {
        this.parseLogJpaRepository = parseLogJpaRepository;
    }

    @Override
    public ParseLog save(ParseLog parseLog) {
        return parseLogJpaRepository.save(parseLog);
    }

    @Override
    public Iterable<ParseLog> saveAll(List<ParseLog> parseLogs) {
        return parseLogJpaRepository.saveAll(parseLogs);
    }
}
