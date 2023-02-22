package app.kakaobank.task.module.parseschool.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class ParseLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long replyId;

    @Column(length = 20000)
    private String originReply;

    @Column(length = 20000)
    private String parseResult;

    private String csvFileName;

    @Enumerated(EnumType.STRING)
    private ParseLogStatus status;

    private LocalDateTime created;

    private ParseLog(String originReply, String parseResult, String csvFileName, Long replyId, ParseLogStatus status) {
        this.setOriginReply(originReply);
        this.setParseResult(parseResult);
        this.setCsvFileName(csvFileName);
        this.setReplyId(replyId);
        this.setParseLogStatus(status);
        this.setCreated(LocalDateTime.now());
    }

    public static ParseLog create(String originReply, String parseResult, String csvFileName, Long replyId,
        ParseLogStatus status) {
        return new ParseLog(originReply, parseResult, csvFileName, replyId, status);
    }

    private void setOriginReply(String originReply) {
        this.originReply = originReply;
    }

    private void setParseResult(String parseResult) {
        this.parseResult = parseResult;
    }

    private void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    private void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    private void setParseLogStatus(ParseLogStatus parseLogStatus) {
        this.status = parseLogStatus;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
