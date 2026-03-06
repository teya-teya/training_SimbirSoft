package utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            log.warn("Повторная попытка {} для теста {} из-за {}",
                    retryCount,
                    result.getTestClass().getName() + "#" + result.getMethod().getMethodName(),
                    result.getThrowable() != null ? result.getThrowable().getMessage() : "без ошибки");

            return true;
        }

        log.error("Тест {} окончательно упал после {} попыток",
                result.getTestClass().getName() + "#" + result.getMethod().getMethodName(),
                MAX_RETRY_COUNT);

        return false;
    }
}