package app.kakaobank.task.module.parseschool.domain;

import java.util.List;

public interface ParseLogRepository {

    ParseLog save(ParseLog parseLog);

    Iterable<ParseLog> saveAll(List<ParseLog> parseLogs);

}
