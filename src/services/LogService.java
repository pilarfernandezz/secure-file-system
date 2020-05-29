package services;

import repositories.LogRepository;

import java.time.LocalDateTime;

public class LogService {

    private static LogService instance;
    private static LogRepository logRepository;

    public static LogService getInstance() {
        if (instance == null) {
            logRepository = LogRepository.getInstance();
            instance = new LogService();
        }
        return instance;
    }

    private LogService() {

    }

    public void registerLogMessage(int code, String login, String arq, LocalDateTime creationDatetime) {
        String messagePattern = logRepository.getMessagePattern(code);
        if (messagePattern != null && messagePattern.trim().length() > 0) {
            logRepository.insertLogMessage(login, arq, code, creationDatetime);
        } else {
            System.out.println("Ocorreu um erro ao registrar log cujo código é " + code);
        }
    }

}
